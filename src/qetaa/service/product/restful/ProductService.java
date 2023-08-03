package qetaa.service.product.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.org.apache.xml.internal.resolver.Catalog;

import qetaa.service.product.dao.DAO;
import qetaa.service.product.filters.Secured;
import qetaa.service.product.filters.SecuredCustomer;
import qetaa.service.product.filters.SecuredUser;
import qetaa.service.product.filters.ValidApp;
import qetaa.service.product.helpers.AppConstants;
import qetaa.service.product.helpers.Helper;
import qetaa.service.product.model.Category;
import qetaa.service.product.model.CategoryHolder;
import qetaa.service.product.model.Manufacturer;
import qetaa.service.product.model.ManufacturerHolder;
import qetaa.service.product.model.Product;
import qetaa.service.product.model.ProductName;
import qetaa.service.product.model.ProductNameAlternative;
import qetaa.service.product.model.ProductPrice;
import qetaa.service.product.model.ProductStock;
import qetaa.service.product.model.PublicProduct;
import qetaa.service.product.model.meta.ProductReview;
import qetaa.service.product.model.meta.ProductSpecification;
import qetaa.service.product.model.meta.ProductTag;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductService {

	@EJB
	private DAO dao;

	@Secured
	@GET
	@SecuredCustomer
	@SecuredUser
	@ValidApp
	public void test() {

	}
	
	@SecuredCustomer
	@Path("public-product-price/{price-id}/discount/{percentage}")
	@GET
	public Response getPublicProductPrice(@PathParam(value = "price-id") long priceId, @PathParam(value = "percentage") double percentage) {
		try {
			ProductPrice productPrice = dao.find(ProductPrice.class, priceId);
			Product product = dao.find(Product.class, productPrice.getProductId());
			Manufacturer m = dao.find(Manufacturer.class, product.getManufacturerId());
			Category c = dao.find(Category.class, product.getCategoryId());
			PublicProduct pp = new PublicProduct(product, productPrice, percentage, m, c,null, null , null , null, null);
			return Response.status(200).entity(pp).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("product-number-and-vendor-id-and-cost-price/{price-id}/")
	@SecuredUser
	public Response getProductNumberAndVendorAndCost(@PathParam(value = "price-id") long priceId) {
		try {
			ProductPrice pp = dao.find(ProductPrice.class, priceId);
			Product product = dao.find(Product.class, pp.getProductId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productNumber", product.getProductNumber());
			map.put("cost", pp.getCostPriceWv());
			map.put("vendorId", pp.getVendorId());
			return Response.status(200).entity(map).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@GET
	@Path("active-catalogs")
	@ValidApp
	public Response getActiveCatalogs() {
		try {
			List<Catalog> catalogs = dao.getCondition(Catalog.class, "status", 'A');
			return Response.status(200).entity(catalogs).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@GET
	@Path("product/number/{param}/make/{make}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchProduct(@PathParam(value = "param") String number, @PathParam(value = "make") int makeId) {
		try {
			String undecor = Helper.removeNoneAlphaNumeric(number.trim().toUpperCase());
			String jpql = "select b from Product b where b.productNumberUndecorated = :value0 and b.makeId =:value1";
			List<Product> products = dao.getJPQLParams(Product.class, jpql, undecor, makeId);
			if (products.isEmpty()) {
				return Response.status(404).build();
			}
			Product product = products.get(0);
			List<ProductPrice> pp = dao.getTwoConditions(ProductPrice.class, "productId", "status", product.getId(),
					'A');
			product.setPriceList(pp);
			return Response.status(200).entity(product).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@Secured
	@POST
	@Path("find-product/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProduct(Map<String,Object> map) {
		try {
			String number = (String) map.get("number");
			Integer makeId = ((Number) map.get("makeId")).intValue();
			String name = (String) map.get("name");
			String undecor = Helper.removeNoneAlphaNumeric(number.trim().toUpperCase());
			String jpql = "select b from Product b where b.productNumberUndecorated = :value0 and b.makeId =:value1";
			List<Product> products = dao.getJPQLParams(Product.class, jpql, undecor, makeId);
			Product product = new Product();
			if (products.isEmpty()) {
				product.setCreated(new Date());
				product.setMakeId(makeId);
				product.setProductNumber(number);
				product.setProductNumberUndecorated(undecor);
				product.setProductName(getCreatedProductName(name));
				dao.persist(product);
			} else {
				product = products.get(0);
				checkAndCreatedProductName(product, name);
			}
			List<ProductPrice> pp = dao.getTwoConditions(ProductPrice.class, "productId", "status", product.getId(),
					'A');
			product.setPriceList(pp);
			return Response.status(200).entity(product).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}

	@Secured
	@GET
	@Path("product-id/number/{param}/make/{param2}/name/{param3}/create-if-not-available")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductId(@PathParam(value = "param") String number,
			@PathParam(value = "param2") Integer makeId, @PathParam(value = "param3") String name) {
		try {
			String undecor = Helper.removeNoneAlphaNumeric(number.trim().toUpperCase());
			String jpql = "select b from Product b where b.productNumberUndecorated = :value0 and b.makeId =:value1";
			List<Product> products = dao.getJPQLParams(Product.class, jpql, undecor, makeId);
			if (products.isEmpty()) {
				Product product = new Product();
				product.setCreated(new Date());
				product.setMakeId(makeId);
				product.setProductNumber(number);
				product.setProductNumberUndecorated(undecor);
				product.setProductName(getCreatedProductName(name));
				dao.persist(product);
				return Response.status(200).entity(product.getId()).build();
			}

			Product found = products.get(0);
			checkAndCreatedProductName(found, name);
			return Response.status(200).entity(found.getId()).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	// creates a product name if the product name does not have a name,
	// or adds an alternative name if the name is new
	private void checkAndCreatedProductName(Product product, String name) {
		if (product.getProductName() == null) {
			product.setProductName(getCreatedProductName(name));
			dao.update(product);
		} else {
			String test = name.toLowerCase().trim();
			if (test != product.getProductName().getName().toLowerCase().trim()
					&& test != product.getProductName().getNameAr().toLowerCase().trim()) {
				boolean found = false;
				if (null != product.getProductName().getAltProductNames()) {
					for (ProductNameAlternative alt : product.getProductName().getAltProductNames()) {
						if (test == alt.getName().toLowerCase()) {
							found = true;
						}
					}
				}

				if (!found) {
					ProductNameAlternative alt = new ProductNameAlternative();
					alt.setCreated(new Date());
					alt.setName(name);
					alt.setProductName(product.getProductName());
					alt.setCreatedBy(0);
					dao.persist(alt);
				}
			}
		}
	}

	// creates a product name if not available before
	private ProductName getCreatedProductName(String name) {
		String jpql = "select b from ProductName b where lower(b.name) like :value0 or lower(b.nameAr) = :value0 or b.id in ("
				+ "select c.productName.id from ProductNameAlternative c where lower(c.name) like :value0)";
		List<ProductName> pnames = dao.getJPQLParams(ProductName.class, jpql, "%" + name.toLowerCase() + "%");
		if (pnames.isEmpty()) {
			ProductName pn = new ProductName();
			pn.setCreated(new Date());
			pn.setName(name);
			pn.setNameAr("");
			pn.setCreatedBy(0);
			dao.persist(pn);
			return pn;
		}
		return pnames.get(0);
	}

	@SecuredUser
	@PUT
	@Path("product-price")
	public Response productPrice(ProductPrice pp) {
		try {
			pp.setCreated(new Date());
			pp.setStatus('A');
			String sql = "select b from ProductPrice b where b.productId = :value0 and b.vendorId = :value1 and b.status =:value2";
			List<ProductPrice> pps = dao.getJPQLParams(ProductPrice.class, sql, pp.getProductId(), pp.getVendorId(),
					'A');
			if (!pps.isEmpty()) {
				for (ProductPrice testpp : pps) {
					testpp.setStatus('X');// archive old price
					dao.update(testpp);
				}
			}
			dao.persist(pp);

			return Response.status(200).entity(pp).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("product-ids/search/{param}")
	public Response serachProductIds(@PathParam(value = "param") String search) {
		try {
			List<Long> productIds = new ArrayList<>();
			String undecor = "";
			if (Helper.isProbablyArabic(search)) {
				undecor = search;
			} else {
				undecor = Helper.removeNoneAlphaNumeric(search);
			}
			String jpql = "select b.id from Product b " + " left join b.productName w "
					+ " where (upper(b.productNumberUndecorated) like upper(:value0))"
					+ " or lower(w.name) like lower(:value1)" + " or w.nameAr like :value1 " + "or w.id in ("
					+ "select c.productName.id from ProductNameAlternative c "
					+ "where lower(c.name) like lower(:value1))";
			productIds = dao.getJPQLParams(Long.class, jpql, "%" + undecor + "%", "%" + search.trim() + "%");
			return Response.status(200).entity(productIds).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("sales-return-ids-in-stock")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReturnSalesIds() {
		try {
			List<Long> returnIds = new ArrayList<>();
			String jpql = "select distinct b.salesReturnId from ProductStock b where b.salesReturnId is not null";
			returnIds = dao.getJPQLParams(Long.class, jpql);
			return Response.status(200).entity(returnIds).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@PUT
	@Path("product/set-name-id/advance")
	public Response updateProductAdvance(Product product) {
		try {
			ProductName pname = dao.find(ProductName.class, product.getProductName().getId());
			product.setProductName(pname);
			dao.update(product);
			return Response.status(201).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("search-products/name/{param}/number/{param2}/make/{param3}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchProducts(@PathParam(value = "param") String name, @PathParam(value = "param2") String number,
			@PathParam(value = "param3") Long makeId) {
		try {
			String sql = "select * from prd_product where id > 0 ";
			if (!number.equals(" ")) {
				sql = sql + " and product_number_undecorated like '%"
						+ Helper.removeNoneAlphaNumeric(number.toUpperCase().trim()) + "%' ";
			}
			if (makeId > 0) {
				sql = sql + " and make_id = " + makeId;
			}
			if (!name.equals(" ")) {
				sql = sql + " and product_name_id in (" + "select n.id from prd_name n where lower(n.name) like '%"
						+ name.toLowerCase() + "%' or lower(n.name_ar) like '%" + name.toLowerCase() + "%' or n.id in ("
						+ "select a.product_name_id from prd_name_alternative a where lower(a.name) like '%"
						+ name.toLowerCase() + "%'))";
			}
			List<Product> prods = dao.getNative(Product.class, sql);
			return Response.status(200).entity(prods).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("search-product-names/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchProductNames(@PathParam(value = "param") String name) {
		try {
			String jpql = "select b from ProductName b where lower(b.name) like :value0 or lower(b.nameAr) = :value0 or b.id in ("
					+ "select c.productName.id from ProductNameAlternative c where lower(c.name) like :value0)";
			List<ProductName> pnames = dao.getJPQLParams(ProductName.class, jpql, "%" + name.toLowerCase() + "%");
			for (ProductName pname : pnames) {
				List<ProductNameAlternative> pna = dao.getCondition(ProductNameAlternative.class, "productName", pname);
				pname.setAltProductNames(pna);
			}
			return Response.status(200).entity(pnames).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("product/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam(value = "param") long productId) {
		try {
			Product p = dao.find(Product.class, productId);
			if (p == null) {
				return Response.status(404).build();
			}
			List<ProductStock> stock = dao.getConditionOrdered(ProductStock.class, "product", p, "created", "asc");
			p.setStockList(stock);
			return Response.status(200).entity(p).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}
	
	@SecuredUser
	@GET
	@Path("product-full/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDetailedProduct(@PathParam(value = "param") long productId) {
		try {
			Product p = dao.find(Product.class, productId);
			if (p == null) {
				return Response.status(404).build();
			}
			List<ProductStock> stock = dao.getConditionOrdered(ProductStock.class, "product", p, "created", "asc");
			p.setStockList(stock);
			List<ProductPrice> prices = dao.getCondition(ProductPrice.class, "productId", p.getId());
			p.setPriceList(prices);
			List<ProductTag> tags = dao.getCondition(ProductTag.class, "productId", p.getId());
			p.setTags(tags);
			List<ProductSpecification> specs = dao.getCondition(ProductSpecification.class, "productId", p.getId());
			p.setSpecs(specs);
			List<ProductReview> reviews = dao.getCondition(ProductReview.class, "productId", p.getId());
			p.setReviews(reviews);
			return Response.status(200).entity(p).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("product/{param}/with-price-list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductWithPriceList(@PathParam(value = "param") long productId) {
		try {
			Product p = dao.find(Product.class, productId);
			if (p == null) {
				return Response.status(404).build();
			}
			List<ProductStock> stock = dao.getConditionOrdered(ProductStock.class, "product", p, "created", "asc");
			p.setStockList(stock);
			List<ProductPrice> pp = dao.getTwoConditions(ProductPrice.class, "productId", "status", p.getId(), 'A');
			p.setPriceList(pp);
			return Response.status(200).entity(p).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("product-price/{param}")
	public Response getProductPrice(@PathParam(value = "param") long productPriceId) {
		try {
			ProductPrice pp = dao.find(ProductPrice.class, productPriceId);
			if (pp == null) {
				return Response.status(404).build();
			}
			return Response.status(200).entity(pp).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("product/{param}/cart/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam(value = "param") long productId, @PathParam(value = "param2") Long cartId) {
		try {
			Product p = dao.find(Product.class, productId);
			if (p == null) {
				return Response.status(404).build();
			}
			String jpql = "select b from ProductStock b where product = :value0 and cartId = :value1 order by created asc";
			List<ProductStock> stock = dao.getJPQLParams(ProductStock.class, jpql, p, cartId);
			p.setStockList(stock);
			return Response.status(200).entity(p).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("categories")
	public Response getCategories() {
		try {
			List<Category> cats = dao.get(Category.class);
			return Response.status(200).entity(cats).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}
	
	@SecuredUser
	@GET
	@Path("manufacturers")
	public Response getManufacturers() {
		try {
			List<Manufacturer> mans= dao.get(Manufacturer.class);
			return Response.status(200).entity(mans).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@POST
	@Path("category")
	public Response createCategory(@HeaderParam("Authorization") String authHeader, CategoryHolder holder) {
		try {
			holder.getCategory().setCreated(new Date());
			dao.persist(holder.getCategory());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("main", holder.getMain());
			map.put("thumbnail", holder.getThumbnail());
			map.put("id", holder.getCategory().getId());
			Response r = this.postSecuredRequest(AppConstants.POST_CATEGORY_IMAGE, map, authHeader);
			if (r.getStatus() == 201) {
				return Response.status(201).build();
			} else {
				return Response.status(500).build();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}
	
	@SecuredUser
	@POST
	@Path("manufacturer")
	public Response createManufacturer(@HeaderParam("Authorization") String authHeader, ManufacturerHolder holder) {
		try {
			holder.getManufacturer().setCreated(new Date());
			dao.persist(holder.getManufacturer());
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("main", holder.getMain());
			map.put("thumbnail", holder.getThumbnail());
			map.put("id", holder.getManufacturer().getId());
			Response r = this.postSecuredRequest(AppConstants.POST_MANUFACTURER_IMAGE, map, authHeader);
			if (r.getStatus() == 201) {
				return Response.status(201).build();
			} else {
				return Response.status(500).build();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@POST
	@Path("product")
	public Response createProduct(Product product) {
		try {
			product.setProductNumber(product.getProductNumber().trim().toUpperCase());
			String undecor = Helper.removeNoneAlphaNumeric(product.getProductNumber());
			String jpql = "select b from Product b where b.productNumberUndecorated = :value0 and b.makeId = :value1";
			List<Product> check = dao.getJPQLParams(Product.class, jpql, undecor, product.getMakeId());
			if (!check.isEmpty()) {
				return Response.status(409).build();
			}
			product.setProductNumberUndecorated(undecor);
			dao.persist(product);
			return Response.status(201).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@POST
	@Path("product-name")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProductName(ProductName productName) {
		try {
			productName.setName(productName.getName().trim());
			// check product name available
			String jpql = "select b from ProductName b where lower(b.name) = :value0 or lower(b.nameAr) = :value0 or b.id in ("
					+ "select c.productName.id from ProductNameAlternative c where lower(c.name) = :value0)";
			List<ProductName> check = dao.getJPQLParams(ProductName.class, jpql, productName.getName().toLowerCase());
			if (!check.isEmpty()) {
				return Response.status(409).build();
			}
			// check if alternative names available
			boolean found = false;
			for (ProductNameAlternative pna : productName.getAltProductNames()) {
				pna.setName(pna.getName().trim());
				List<ProductName> check2 = dao.getJPQLParams(ProductName.class, jpql, pna.getName());
				if (!check2.isEmpty()) {
					found = true;
					break;
				}
			}
			if (found) {
				return Response.status(409).build();
			}

			productName.setCreated(new Date());
			dao.persist(productName);
			for (ProductNameAlternative pna : productName.getAltProductNames()) {
				pna.setId(0);
				pna.setProductName(productName);
				pna.setCreated(new Date());
				dao.persist(pna);
			}
			return Response.status(201).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@Path("product-stocks")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteProductStock(List<Map<String, Long>> list) {
		try {
			for (Map<String, Long> map : list) {
				Long productId = map.get("productId");
				Long purchaseId = map.get("purchaseId");
				Long cartId = map.get("cartId");
				Integer quantity = map.get("quantity").intValue();
				ProductStock ps = dao.findThreeConditions(ProductStock.class, "product.id", "purchaseId", "cartId",
						productId, purchaseId, cartId);
				if (ps != null) {
					if (ps.getQuantity() == quantity.intValue()) {
						dao.delete(ps);
					} else if (ps.getQuantity() > quantity.intValue()) {
						ps.setQuantity(ps.getQuantity() - quantity.intValue());
						dao.update(ps);
					}
				}
			}
			return Response.status(201).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@Path("product-stocks/return-purchase")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeProductStockFromPurchaseReturn(List<Map<String, Long>> list) {
		try {
			for (Map<String, Long> map : list) {
				Long productId = map.get("productId");
				Long purchaseId = map.get("purchaseId");
				Long cartId = map.get("cartId");
				Integer quantity = map.get("quantity").intValue();
				Long salesReturnId = map.get("salesReturnId");
				List<ProductStock> pss = dao.getFourConditions(ProductStock.class, "salesReturnId", "purchaseId",
						"product.id", "cartId", salesReturnId, purchaseId, productId, cartId);
				for (ProductStock ps : pss) {
					if (quantity.intValue() == ps.getQuantity()) {
						dao.delete(ps);
					}

				}
			}
			return Response.status(201).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@Path("product-stocks/return-sales")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProductStockFromSalesReturn(List<Map<String, Long>> list) {
		try {
			for (Map<String, Long> map : list) {
				Long productId = map.get("productId");
				Long purchaseId = map.get("purchaseId");
				Long cartId = map.get("cartId");
				Integer quantity = map.get("quantity").intValue();
				Long salesReturnId = map.get("salesReturnId");
				ProductStock ps = new ProductStock();
				Product p = dao.find(Product.class, productId);
				ps.setCreated(new Date());
				ps.setProduct(p);
				ps.setPurchaseId(purchaseId);
				ps.setQuantity(quantity);
				ps.setCartId(cartId);
				ps.setSalesReturnId(salesReturnId);
				String jpql = "select b from ProductStock b where b.cartId = :value0 and b.purchaseId = :value1 and b.product = :value2 and b.salesReturnId = :value3";
				List<ProductStock> pss = dao.getJPQLParams(ProductStock.class, jpql, cartId, purchaseId, p,
						salesReturnId);
				if (pss.isEmpty()) {
					dao.persist(ps);
				}
			}

			return Response.status(201).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@Path("product-stocks")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProductStock(List<Map<String, Long>> list) {
		try {
			for (Map<String, Long> map : list) {
				Long productId = map.get("productId");
				Long purchaseId = map.get("purchaseId");
				Long cartId = map.get("cartId");
				Integer quantity = map.get("quantity").intValue();
				ProductStock ps = new ProductStock();
				Product p = dao.find(Product.class, productId);
				ps.setCreated(new Date());
				ps.setProduct(p);
				ps.setPurchaseId(purchaseId);
				ps.setQuantity(quantity);
				ps.setCartId(cartId);
				List<ProductStock> pss = dao.getThreeConditions(ProductStock.class, "cartId", "purchaseId", "product",
						cartId, purchaseId, p);
				if (pss.isEmpty()) {
					dao.persist(ps);
				}
			}

			return Response.status(201).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}

	public <T> Response postSecuredRequest(String link, T t, String authHeader) {
		Builder b = ClientBuilder.newClient().target(link).request();
		b.header(HttpHeaders.AUTHORIZATION, authHeader);
		Response r = b.post(Entity.entity(t, "application/json"));// not secured
		return r;
	}

}
