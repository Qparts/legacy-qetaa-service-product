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
	private int makeId;
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
	public int getMakeId() {
		return makeId;
	}
	public void setMakeId(int makeId) {
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
	
	
	
	
	
	
	
	

}
