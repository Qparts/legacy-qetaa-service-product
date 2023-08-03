package qetaa.service.product.model;

import java.io.Serializable;

import qetaa.service.product.helpers.AppConstants;

public class PublicManufacturer implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String nameAr;
	private int categoryId;
	private boolean genuine;
	private String imageMain;
	private String imageThumbnail;
	
	
	public PublicManufacturer() {
		
	}
	
	public PublicManufacturer(Manufacturer man) {
		this.id = man.getId();
		this.name = man.getName();
		this.nameAr = man.getNameAr();
		this.categoryId = man.getCategoryId();
		this.genuine = man.isGenuine();
		this.imageMain = AppConstants.getManufacturerImageMainLink(id);
		this.imageThumbnail = AppConstants.getManufacturerImageThumbnailLink(id);
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
	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public boolean isGenuine() {
		return genuine;
	}
	public void setGenuine(boolean genuine) {
		this.genuine = genuine;
	}
	
	public String getImageThumbnail() {
		return imageThumbnail;
	}

	public void setImageThumbnail(String imageThumbnail) {
		this.imageThumbnail = imageThumbnail;
	}

	public String getImageMain() {
		return imageMain;
	}

	public void setImageMain(String imageMain) {
		this.imageMain = imageMain;
	}	
	
	
	
	

}
