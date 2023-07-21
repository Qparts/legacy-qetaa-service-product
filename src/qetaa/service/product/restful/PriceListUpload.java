package qetaa.service.product.restful;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import qetaa.service.product.dao.DAO;
import qetaa.service.product.helpers.Helper;
import qetaa.service.product.model.PriceHolder;
import qetaa.service.product.model.Product;
import qetaa.service.product.model.ProductName;
import qetaa.service.product.model.ProductPrice;

@Path("/uploader/")
public class PriceListUpload {

	private static final String BASE = "C:\\Users\\faree\\Desktop\\PriceList\\";
	private static final String FILE_NAME_KIA = BASE + "Kia\\kia.xlsx";
	private static final String FILE_NAME_HYUNDAI = BASE + "Hyundai\\hyundai.xlsx";
	private static final String FILE_NAME_TOYOTA = BASE + "Toyota\\TOYOTA SPARE.xlsx";
	

	@EJB
	private DAO dao;
	
	
	
	@GET
	@Path("upload-pricelist/toyota")
	public Response uploadKiaPriceList() {
		try {
			//uploadKia();
//			uploadHyundaiMajd();
//			uploadHyundaiNagh();
			//uploadToyota();
			return Response.status(200).entity("200").build();
		}catch(Exception ex) {
			return Response.status(500).entity("500").build();
		}
	}
	
	public void uploadToyota() {
		int i = 0;
		try {
			List<PriceHolder> holders = readToyotaExcel();
			System.out.println("started will process "+ holders.size());
			if (holders != null) {
				for (PriceHolder h : holders) {
					long productId = getProductIdCreateIfNot(h.getNumber(), h.getMakeId(), h.getDesc());
					String jpql = "select b from ProductPrice b where b.productId = :value0 and b.vendorId = :value1 and makeId = :value2";
					ProductPrice found = dao.findJPQLParams(ProductPrice.class, jpql, productId, h.getVendorId(), h.getMakeId());
					if (found != null) {
						if (found.getCostPrice() != h.getPrice()) {
							found.setCostPrice(h.getPrice());
							found.setCostPriceWv(h.getPriceWv());
							found.setCreated(new Date());
							dao.update(found);
						}
					} else {
						ProductPrice pp = new ProductPrice();
						pp.setVendorId(h.getVendorId());
						pp.setMakeId(h.getMakeId());
						pp.setCreated(new Date());
						pp.setCreatedBy(1);
						pp.setCostPrice(h.getPrice());
						pp.setCostPriceWv(h.getPriceWv());
						pp.setProductId(productId);
						dao.persist(pp);
					}
					i++;
				}
				System.out.println("finished at "+i);
			}
		}catch(Exception ex) {
			System.out.println("stopped at " + i);
			ex.printStackTrace();
		}
	}
	
	public void uploadHyundaiNagh() {
		int i = 0;
		try {
			List<PriceHolder> holders = readHyundaiNaghiExcel();
			System.out.println("started will process "+ holders.size());
			if (holders != null) {
				for (PriceHolder h : holders) {
					long productId = getProductIdCreateIfNot(h.getNumber(), h.getMakeId(), h.getDesc());
					String jpql = "select b from ProductPrice b where b.productId = :value0 and b.vendorId = :value1 and makeId = :value2";
					ProductPrice found = dao.findJPQLParams(ProductPrice.class, jpql, productId, h.getVendorId(),
							h.getMakeId());
					if (found != null) {
						if (found.getCostPrice() != h.getPrice()) {
							found.setCostPrice(h.getPrice());
							found.setCostPriceWv(h.getPriceWv());
							found.setCreated(new Date());
							dao.update(found);
						}
					} else {
						ProductPrice pp = new ProductPrice();
						pp.setVendorId(h.getVendorId());
						pp.setMakeId(h.getMakeId());
						pp.setCreated(new Date());
						pp.setCreatedBy(1);
						pp.setCostPrice(h.getPrice());
						pp.setCostPriceWv(h.getPriceWv());
						pp.setProductId(productId);
						dao.persist(pp);
					}
					i++;
				}
				System.out.println("finished at "+i);
			}
		}catch(Exception ex) {
			
		}
	}
	
	public void uploadHyundaiMajd() {
		int i = 0;
		try {
			List<PriceHolder> holders = readHyundaiMajdExcel();
			System.out.println("started will process "+ holders.size());
			if (holders != null) {
				for (PriceHolder h : holders) {
					long productId = getProductIdCreateIfNot(h.getNumber(), h.getMakeId(), h.getDesc());
					String jpql = "select b from ProductPrice b where b.productId = :value0 and b.vendorId = :value1 and makeId = :value2";
					ProductPrice found = dao.findJPQLParams(ProductPrice.class, jpql, productId, h.getVendorId(),
							h.getMakeId());
					if (found != null) {
						if (found.getCostPrice() != h.getPrice()) {
							found.setCostPrice(h.getPrice());
							found.setCostPriceWv(h.getPriceWv());
							found.setCreated(new Date());
							dao.update(found);
						}
					} else {
						ProductPrice pp = new ProductPrice();
						pp.setVendorId(h.getVendorId());
						pp.setMakeId(h.getMakeId());
						pp.setCreated(new Date());
						pp.setCreatedBy(1);
						pp.setCostPrice(h.getPrice());
						pp.setCostPriceWv(h.getPriceWv());
						pp.setProductId(productId);
						dao.persist(pp);
					}
					i++;
				}
				System.out.println("finished at "+i);
			}
			
		}catch(Exception ex) {
			
		}
	}
	
	public void uploadKia() {
		int i = 0;
		try {
			List<PriceHolder> holders = readKiaExcel();
			System.out.println("started will process "+ holders.size());
			if (holders != null) {
				for (PriceHolder h : holders) {
					long productId = getProductIdCreateIfNot(h.getNumber(), h.getMakeId(), h.getDesc());
					String jpql = "select b from ProductPrice b where b.productId = :value0 and b.vendorId = :value1 and makeId = :value2";
					ProductPrice found = dao.findJPQLParams(ProductPrice.class, jpql, productId, h.getVendorId(),
							h.getMakeId());
					if (found != null) {
						if (found.getCostPrice() != h.getPrice()) {
							found.setCostPrice(h.getPrice());
							found.setCostPriceWv(h.getPriceWv());
							found.setCreated(new Date());
							dao.update(found);
						}
					} else {
						ProductPrice pp = new ProductPrice();
						pp.setVendorId(h.getVendorId());
						pp.setMakeId(h.getMakeId());
						pp.setCreated(new Date());
						pp.setCreatedBy(1);
						pp.setCostPrice(h.getPrice());
						pp.setCostPriceWv(h.getPriceWv());
						pp.setProductId(productId);
						dao.persist(pp);
					}
					i++;
				}
				System.out.println("finished at "+i);
			}
		} catch (Exception ex) {
			System.out.println("stopped at " + i);
			ex.printStackTrace();
		}
	}

	private Long getProductIdCreateIfNot(String number, int makeId, String desc) throws Exception {
		String undecor = Helper.removeNoneAlphaNumeric(number.trim().toUpperCase());
		String jpql = "select b from Product b where b.productNumberUndecorated = :value0 and b.makeId =:value1";
		List<Product> products = dao.getJPQLParams(Product.class, jpql, undecor, makeId);
		if (products.isEmpty()) {
			Product product = new Product();
			product.setCreated(new Date());
			product.setMakeId(makeId);
			product.setProductNumber(number);
			product.setProductNumberUndecorated(undecor);
			if(desc != null)
				product.setProductName(searchProductName(desc));
			dao.persist(product);
			ProductName pn = new ProductName();
			pn.setCreated(new Date());
			pn.setName(desc);
			return product.getId();
		} else {
			return products.get(0).getId();
		}

	}

	private ProductName searchProductName(String name) {
		String jpql = "select b from ProductName b where upper(b.name) = upper(:value0)";
		return dao.findJPQLParams(ProductName.class, jpql, name.trim());

	}
	
	
	private List<PriceHolder> readHyundaiMajdExcel(){
		int index = 0;
		try {
			List<PriceHolder> holders = new ArrayList<>();
			List<String>errors = new ArrayList<>();
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME_HYUNDAI));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			iterator.next();//skip first row
			while(iterator.hasNext()) {
				Row currentRow = iterator.next();
				PriceHolder holder = new PriceHolder();
				if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
					holder.setNumber(currentRow.getCell(0).getStringCellValue().trim().toUpperCase());
				} else {
					errors.add("Number cell is not string");
				}

				if (currentRow.getCell(1).getCellTypeEnum() == CellType.STRING) {
					holder.setDesc(currentRow.getCell(1).getStringCellValue());
				} else {
					errors.add("Name cell is not string" + index);
				}
				
				if (currentRow.getCell(2).getCellTypeEnum() == CellType.NUMERIC) {
					holder.setPrice(currentRow.getCell(2).getNumericCellValue() - currentRow.getCell(2).getNumericCellValue() * 0.58);
				} else {
					errors.add("Price cell is not double " + index);
				}
				if(!(holder.getPrice() > 0)) {
					errors.add("invalid price " + index);
				}
				holder.setPriceWv(holder.getPrice() * .05 + holder.getPrice());
				holder.setMakeId(4);// Hyundai
				holder.setVendorId(5);// Majdouei
				holders.add(holder);
				index++;
			}
			System.out.println(errors.size());
			for(String e : errors) {
				System.out.println(e);
			}
			return holders;
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	private List<PriceHolder> readHyundaiNaghiExcel(){
		int index = 0;
		List<String>errors = new ArrayList<>();
		try {
			List<PriceHolder> holders = new ArrayList<>();
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME_HYUNDAI));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(1);
			Iterator<Row> iterator = datatypeSheet.iterator();
			iterator.next();//skip first row
			while(iterator.hasNext()) {
				boolean invalid = false;
				Row currentRow = iterator.next();
				PriceHolder holder = new PriceHolder();
				if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
					holder.setNumber(currentRow.getCell(0).getStringCellValue().trim().toUpperCase().substring(2));
				} else {
					invalid = true;
					errors.add("Number cell is not string");
				}

				if (currentRow.getCell(3).getCellTypeEnum() == CellType.STRING) {
					holder.setDesc(currentRow.getCell(3).getStringCellValue());
				} else {
					errors.add("Name cell is not string" + index);
				}
				
				if (currentRow.getCell(1).getCellTypeEnum() == CellType.NUMERIC) {
					holder.setPrice(currentRow.getCell(1).getNumericCellValue() - currentRow.getCell(1).getNumericCellValue() * 0.5);
				} else {
					errors.add("Price cell is not double");
				}
				if(!(holder.getPrice() > 0)) {
					//errors.add("invalid price " + index);
				}
				holder.setPriceWv(holder.getPrice() * .05 + holder.getPrice());
				holder.setMakeId(4);// Hyundai
				holder.setVendorId(16);// Naghi
				if(holder.getPrice() > 0) {
					holders.add(holder);
				}
				index++;
			}
			System.out.println("Errors Size" + errors.size());
			System.out.println("Data Size" + holders.size());
			for(String e : errors) {
				System.out.println(e);
			}
			return holders;
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	private List<PriceHolder> readToyotaExcel(){
		int index = 0;
		List<String>errors = new ArrayList<>();
		try {
			List<PriceHolder> holders = new ArrayList<>();
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME_TOYOTA));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			iterator.next();//skip first row
			while(iterator.hasNext()) {
				Row currentRow = iterator.next();
				PriceHolder holder = new PriceHolder();
				if (currentRow.getCell(1).getCellTypeEnum() == CellType.STRING) {
					holder.setNumber(currentRow.getCell(1).getStringCellValue().trim().toUpperCase().substring(1));
				} else {
					if(currentRow.getCell(1).getCellTypeEnum() == CellType.NUMERIC) {
						if(currentRow.getCell(1).getNumericCellValue() == 0.0) {
							
						}
						else {
							//errors.add("Number cell is not string " + index);
						}
					}
				}

//				if (currentRow.getCell(3).getCellTypeEnum() == CellType.STRING) {
	//				holder.setDesc(currentRow.getCell(3).getStringCellValue());
	//			} else {
		//			errors.add("Name cell is not string" + index);
			//	}
				
				if (currentRow.getCell(2).getCellTypeEnum() == CellType.NUMERIC) {
					holder.setPrice(currentRow.getCell(2).getNumericCellValue() - currentRow.getCell(2).getNumericCellValue() * 0.35);
				} else {
					errors.add("Price cell is not double" + index);
				}
				holder.setPriceWv(holder.getPrice() * .05 + holder.getPrice());
				holder.setMakeId(2);// Toyota
				holder.setVendorId(20);// Naghi
				if(holder.getPrice() > 0.5 && holder.getNumber() != null) {
					holders.add(holder);
				}
				index++;
			}
			System.out.println("Errors Size" + errors.size());
			System.out.println("Data Size" + holders.size());
			for(String e : errors) {
				System.out.println(e);
			}
			return holders;
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private List<PriceHolder> readKiaExcel() {
		try {
			List<PriceHolder> holders = new ArrayList<>();
			List<String> errors = new ArrayList<>();
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME_KIA));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			iterator.next();// skip first row
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				PriceHolder holder = new PriceHolder();
				// Iterator<Cell> cellIterator = currentRow.iterator();
				if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
					holder.setNumber(currentRow.getCell(0).getStringCellValue().trim().toUpperCase().substring(2));
				} else {
					errors.add("Number cell is not string");
				}

				if (currentRow.getCell(1).getCellTypeEnum() == CellType.STRING) {
					holder.setDesc(currentRow.getCell(1).getStringCellValue());
				} else {
					errors.add("Name cell is not string");
				}

				if (currentRow.getCell(2).getCellTypeEnum() == CellType.NUMERIC) {
					holder.setPrice(currentRow.getCell(2).getNumericCellValue() - currentRow.getCell(2).getNumericCellValue() * 0.3);
				} else {
					errors.add("Price cell is not double");
				}
				holder.setPriceWv(holder.getPrice() + holder.getPrice() * .05);
				holder.setMakeId(8);// KIA
				holder.setVendorId(12);// Jabr
				holders.add(holder);
			}
			System.out.println("errors: " + errors.size());
			return holders;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}


}
