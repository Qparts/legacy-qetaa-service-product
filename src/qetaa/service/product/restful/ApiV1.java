package qetaa.service.product.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import qetaa.service.product.dao.DAO;
import qetaa.service.product.filters.ValidApp;
import qetaa.service.product.model.Category;
import qetaa.service.product.model.Manufacturer;
import qetaa.service.product.model.Product;
import qetaa.service.product.model.ProductPrice;
import qetaa.service.product.model.PublicManufacturer;
import qetaa.service.product.model.PublicProduct;
import qetaa.service.product.model.meta.ProductReview;
import qetaa.service.product.model.meta.ProductSpecification;

@Path("/api/v1/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApiV1 {
	
	@EJB
	private DAO dao;
	private static Integer TYRES_CATEGORY = 2;

	
	@ValidApp
	@Path("product/{param}")
	@GET
	public Response getProduct(@PathParam(value="param") long id) {
		try {
			PublicProduct pp = prepareDetailedPublicProduct(id);
			return Response.status(200).entity(pp).build();
		}catch(Exception ex) {
			return Response.status(500).build();
		}
	}
	
	private PublicProduct prepareDetailedPublicProduct(long id) {
		Product product = dao.find(Product.class, id);
		ProductPrice price = dao.findTwoConditions(ProductPrice.class, "productId", "status", id, 'A');
		Manufacturer m = dao.find(Manufacturer.class, product.getManufacturerId());
		Category c = dao.find(Category.class, product.getCategoryId());
		List<ProductReview> reviews = dao.getTwoConditions(ProductReview.class, "productId", "status", id, 'A');
		Number n = dao.findJPQLParams(Number.class, "select avg(b.rating) from ProductReview b where b.productId = :value0 and b.status =:value1", id, 'A');
		Double rating = 0D;
		if(n != null )
			rating = n.doubleValue();
		String tagsJpql = "select b.tag.name from ProductTag b where b.productId = :value0";
		List<String> tags = dao.getJPQLParams(String.class, tagsJpql, id);
		List<ProductSpecification> pss = dao.getCondition(ProductSpecification.class, "productId", id);	
		Map<String,String> specs = new HashMap<String,String>();
		Map<String,String> specsAr = new HashMap<String,String>();
		for(ProductSpecification ps : pss) {
			specs.put(ps.getSpecification().getName(), ps.getValue());
			specsAr.put(ps.getSpecification().getNameAr(), ps.getValueAr());
		}
		return new PublicProduct(product,price, 0.05, m, c, tags, specs, specsAr, reviews, rating);
	}
	
	@ValidApp
	@Path("best-sellers")
	@GET
	public Response getBestSellersPrice() {
		try {
			String jpql = "select b from ProductPrice b where b.id in (:value0, :value1 , :value2 , :value3 , :value4 , :value5, :value6, :value7, :value8)";
			List<ProductPrice> productPrices = dao.getJPQLParams(ProductPrice.class, jpql, 200L, 201L, 204L, 205L, 206L, 365L,366L,376L,377L);
			List<PublicProduct> pps = new ArrayList<>();
			for(ProductPrice productPrice : productPrices) {
				Product product = dao.find(Product.class, productPrice.getProductId());
				Manufacturer m = dao.find(Manufacturer.class, product.getManufacturerId());
				Category c = dao.find(Category.class, product.getCategoryId());
				PublicProduct pp = new PublicProduct(product, productPrice, 5, m, c, null, null , null , null, null);
				pps.add(pp);
			}
			return Response.status(200).entity(pps).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}
	
	@ValidApp
	@Path("tyres/top-manufacturers")
	@GET
	public Response getTyresManufacturers() {
		try {
			String jpql = "select b from Manufacturer b where b.status = :value0 and b.categoryId = :value1";
			List<Manufacturer> mans = dao.getJPQLParams(Manufacturer.class, jpql, 'A', TYRES_CATEGORY);
			List<PublicManufacturer> pmans = new ArrayList<>();
			for(Manufacturer man : mans) {
				PublicManufacturer pm = new PublicManufacturer(man);
				pmans.add(pm);
			}
			return Response.status(200).entity(pmans).build();
		}catch(Exception ex) {
			return Response.status(500).build();
		}
	}
	
	@ValidApp
	@Path("offers")
	@GET
	public Response getProductOffers() {
		try {
			String jpql = "select b from ProductPrice b where b.id in (:value0, :value1 , :value2 , :value3 , :value4 , :value5, :value6, :value7, :value8)";
			List<ProductPrice> productPrices = dao.getJPQLParams(ProductPrice.class, jpql, 386L, 387L, 388L, 389L, 390L, 391L, 392L, 393L, 394L);
			List<PublicProduct> pps = new ArrayList<>();
			for(ProductPrice productPrice : productPrices) {
				Product product = dao.find(Product.class, productPrice.getProductId());
				Manufacturer m = dao.find(Manufacturer.class, product.getManufacturerId());
				Category c = dao.find(Category.class, product.getCategoryId());
				PublicProduct pp = new PublicProduct(product, productPrice, 5, m, c,null, null , null , null, null);
				pps.add(pp);
			}
			return Response.status(200).entity(pps).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(500).build();
		}
	}
	
}
