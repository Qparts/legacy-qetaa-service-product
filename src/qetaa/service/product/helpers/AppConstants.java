package qetaa.service.product.helpers;

public final class AppConstants {
	private static final String HOST_PUBLIC = "http://qtest.fareed9.com:8080/";
	private static final String CUSTOMER_SERVICE = "http://localhost:8080/service-qetaa-customer/rest/";
	private static final String USER_SERVICE = "http://localhost:8080/service-qetaa-user/rest/";
	private static final String VENDOR_SERVICE = "http://localhost:8080/service-qetaa-vendor/rest/";
	private static final String IMAGES_SERVER = HOST_PUBLIC + "image-provider/";
	private static final String IMAGE_PROVIDER_SERVICE = "http://localhost:8080/image-provider/rest/";
	
	
	public static final String CUSTOMER_MATCH_TOKEN = CUSTOMER_SERVICE + "match-token";
	public static final String USER_MATCH_TOKEN = USER_SERVICE + "match-token";
	public static final String VENDOR_MATCH_TOKEN = VENDOR_SERVICE + "match-token";
	public static final String POST_CATEGORY_IMAGE = IMAGE_PROVIDER_SERVICE + "category";
	public static final String POST_MANUFACTURER_IMAGE = IMAGE_PROVIDER_SERVICE + "manufacturer";
	
	
	public static String getProductImageThumbnailLink(long productId) {
		return IMAGES_SERVER + "product/" + productId + "-thumbnail.png";
	}
	
	public static String getProductImageMainLink(long productId) {
		return IMAGES_SERVER + "product/" + productId + "-main.png";
	}
	
	public static String getManufacturerImageThumbnailLink(long manId) {
		return IMAGES_SERVER + "manufacturer/" + manId + "-thumbnail.png";
	}
	
	public static String getManufacturerImageMainLink(long manId) {
		return IMAGES_SERVER + "manufacturer/" + manId + "-main.png";
	}
	
	public static String getCategoryImageThumbnailLink(long categoryId) {
		return IMAGES_SERVER + "category/" + categoryId + "-thumbnail.png";
	}
	
	public static String getCategoryImageMainLink(long categoryId) {
		return IMAGES_SERVER + "category/" + categoryId + "-main.png";
	}
	
	
}
