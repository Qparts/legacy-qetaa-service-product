package qetaa.service.product.model;

import qetaa.service.product.helpers.AppConstants;

public class PublicCategory {

	private int id;
	private String name;
	private String nameAr;
	private String imageMain;
	private String imageThumbnail;
	
	public PublicCategory() {
		
	}
	
	public PublicCategory(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.nameAr = category.getNameAr();
		this.imageMain = AppConstants.getCategoryImageMainLink(id);
		this.imageThumbnail = AppConstants.getCategoryImageThumbnailLink(id);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAr() {
		return nameAr;
	}
	public void setNameAr(String ameAr) {
		this.nameAr = ameAr;
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
