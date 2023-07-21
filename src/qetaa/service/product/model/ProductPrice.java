package qetaa.service.product.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="prd_price_list")
public class ProductPrice implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "prd_price_list_id_seq_gen", sequenceName = "prd_price_list_id_seq", initialValue=1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prd_price_list_id_seq_gen")
	@Column(name="id")
	private long id;
	@Column(name="product_id")
	private long productId;
	@Column(name="vendor_id")
	private int vendorId;
	@Column(name="make_id")
	private int makeId;
	@Column(name="cost_price")
	private double costPrice;
	@Column(name="cost_price_wv")
	private double costPriceWv;
	@Column(name="created")
	private Date created;
	@Column(name="created_by")
	private int createdBy;
	
	
	
	
	
	public double getCostPriceWv() {
		return costPriceWv;
	}
	public void setCostPriceWv(double costPriceWv) {
		this.costPriceWv = costPriceWv;
	}
	public int getMakeId() {
		return makeId;
	}
	public void setMakeId(int makeId) {
		this.makeId = makeId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
}
