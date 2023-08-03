package qetaa.service.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import qetaa.service.product.model.meta.ProductReview;
import qetaa.service.product.model.meta.ProductSpecification;
import qetaa.service.product.model.meta.ProductTag;

@Table(name="prd_product")
@Entity 
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "prd_product_id_seq_gen", sequenceName = "prd_product_id_seq", initialValue=1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prd_product_id_seq_gen")
	@Column(name="id")
	private long id;
	@Column(name="product_number")
	private String productNumber;
	@Column(name="product_number_undecorated")
	private String productNumberUndecorated;
	@JoinColumn(name="product_name_id")
	@ManyToOne
	private ProductName productName;
	@Column(name="product_desc")
	private String desc;
	@Column(name="created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column(name="make_id")
	private Integer makeId;//could be null
	@Column(name="manufacturer_id")
	private Integer manufacturerId;
	@Column(name="category_id")
	private Integer categoryId;
	
	@Transient
	private List<ProductTag> tags;
	@Transient
	private List<ProductSpecification> specs;
	@Transient
	private List<ProductReview> reviews;
	@Transient
	private List<ProductStock> stockList;
	@Transient
	private List<ProductPrice> priceList;
	
	
	public List<ProductPrice> getPriceList() {
		return priceList;
	}
	public void setPriceList(List<ProductPrice> priceList) {
		this.priceList = priceList;
	}
	public List<ProductStock> getStockList() {
		return stockList;
	}
	public void setStockList(List<ProductStock> stockList) {
		this.stockList = stockList;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public ProductName getProductName() {
		return productName;
	}
	public void setProductName(ProductName productName) {
		this.productName = productName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getMakeId() {
		return makeId;
	}
	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getProductNumberUndecorated() {
		return productNumberUndecorated;
	}
	public void setProductNumberUndecorated(String productNumberUndecorated) {
		this.productNumberUndecorated = productNumberUndecorated;
	}
	


	public List<ProductTag> getTags() {
		return tags;
	}
	public void setTags(List<ProductTag> tags) {
		this.tags = tags;
	}
	public Integer getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public List<ProductSpecification> getSpecs() {
		return specs;
	}
	public void setSpecs(List<ProductSpecification> specs) {
		this.specs = specs;
	}
	public List<ProductReview> getReviews() {
		return reviews;
	}
	public void setReviews(List<ProductReview> reviews) {
		this.reviews = reviews;
	}
	
	
	
	
}
