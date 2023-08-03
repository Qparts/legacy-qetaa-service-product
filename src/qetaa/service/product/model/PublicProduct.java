package qetaa.service.product.model;

import java.util.List;
import java.util.Map;

import qetaa.service.product.helpers.AppConstants;
import qetaa.service.product.model.meta.ProductReview;

public class PublicProduct {
	
	private long id;
	private String desc;
	private double salesPrice;
	private String productNumber;
	private Integer makeId;
	private PublicManufacturer manufacturer;
	private PublicCategory category;
	private String imageMain;
	private String imageThumbnail;
	private List<String> tags;
	private Map<String,String> specs;
	private Map<String,String> specsAr;
	private List<ProductReview> reviews;
	private Double averageRating;
	
	
	public PublicProduct(Product product, 
			ProductPrice productPrice, 
			double percentage, 
			Manufacturer man, 
			Category cat, 
			List<String> tags, 
			Map<String,String> specs, 
			Map<String,String> specsAr, 
			List<ProductReview> reviews, 
			Double rating)
	{
		this.id = product.getId();
		this.desc = product.getDesc();
		this.salesPrice = productPrice.getCostPrice() * (1+percentage);
		this.productNumber = product.getProductNumber();
		this.makeId = product.getMakeId();
		if(man != null) {
			this.manufacturer = new PublicManufacturer(man);
		}
		if(cat != null) {
			this.category = new PublicCategory(cat);
		}
		this.imageMain = AppConstants.getProductImageMainLink(id);
		this.imageThumbnail = AppConstants.getProductImageThumbnailLink(id);
		this.tags = tags;
		this.specs = specs;
		this.specsAr = specsAr;
		this.reviews = reviews;
		this.averageRating = rating;
	}
	
	
	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Map<String, String> getSpecs() {
		return specs;
	}

	public void setSpecs(Map<String, String> specs) {
		this.specs = specs;
	}

	public List<ProductReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<ProductReview> reviews) {
		this.reviews = reviews;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	
	
	public Map<String, String> getSpecsAr() {
		return specsAr;
	}

	public void setSpecsAr(Map<String, String> specsAr) {
		this.specsAr = specsAr;
	}

	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public PublicManufacturer getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(PublicManufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	public PublicCategory getCategory() {
		return category;
	}
	public void setCategory(PublicCategory category) {
		this.category = category;
	}
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public Integer getMakeId() {
		return makeId;
	}
	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public String getImageMain() {
		return imageMain;
	}

	public void setImageMain(String imageMain) {
		this.imageMain = imageMain;
	}

	public String getImageThumbnail() {
		return imageThumbnail;
	}

	public void setImageThumbnail(String imageThumbnail) {
		this.imageThumbnail = imageThumbnail;
	}	
	
	
}
