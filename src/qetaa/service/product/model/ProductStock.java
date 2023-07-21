package qetaa.service.product.model;

import java.io.Serializable;
import java.util.Date;

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

@Table(name="prd_stock")
@Entity 
public class ProductStock implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "prd_stock_id_seq_gen", sequenceName = "prd_stock_id_seq", initialValue=1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prd_stock_id_seq_gen")
	@Column(name="id")
	private long id;
	@Column(name="created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@JoinColumn(name="product_id")
	@ManyToOne
	private Product product;
	@Column(name="quantity")
	private int quantity;
	@Column(name="purchase_id")
	private long purchaseId;
	@Column(name="cart_id")
	private Long cartId;
	@Column(name="sales_return_id")
	private Long salesReturnId;
	
	
	
	public Long getSalesReturnId() {
		return salesReturnId;
	}
	public void setSalesReturnId(Long salesReturnId) {
		this.salesReturnId = salesReturnId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	
	
}
