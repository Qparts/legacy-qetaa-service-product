package qetaa.service.product.model.meta;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="prd_meta_product_specification")
@Entity
@IdClass(ProductSpecification.ProductSpecificationPK.class)
public class ProductSpecification implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@JoinColumn(name="spec_id", referencedColumnName="id")
	@ManyToOne
	private Specification specification;
	@Id
	@Column(name="product_id")
	private long productId;
	@Column(name="value")
	private String value;
	@Column(name="value_ar")
	private String valueAr;
	@Column(name="created")
	private Date created;
	@Column(name="created_by")
	private int createdBy;
	@Column(name="status")
	private char status;
	
	public Specification getSpecification() {
		return specification;
	}
	public void setSpecification(Specification specification) {
		this.specification = specification;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
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
	
	public String getValueAr() {
		return valueAr;
	}
	public void setValueAr(String valueAr) {
		this.valueAr = valueAr;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + createdBy;
		result = prime * result + (int) (productId ^ (productId >>> 32));
		result = prime * result + ((specification == null) ? 0 : specification.hashCode());
		result = prime * result + status;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((valueAr == null) ? 0 : valueAr.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductSpecification other = (ProductSpecification) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (createdBy != other.createdBy)
			return false;
		if (productId != other.productId)
			return false;
		if (specification == null) {
			if (other.specification != null)
				return false;
		} else if (!specification.equals(other.specification))
			return false;
		if (status != other.status)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (valueAr == null) {
			if (other.valueAr != null)
				return false;
		} else if (!valueAr.equals(other.valueAr))
			return false;
		return true;
	}





	public static class ProductSpecificationPK implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected long specification;
		protected long productId;
		
		public ProductSpecificationPK() {}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (productId ^ (productId >>> 32));
			result = prime * result + (int) (specification ^ (specification >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ProductSpecificationPK other = (ProductSpecificationPK) obj;
			if (productId != other.productId)
				return false;
			if (specification != other.specification)
				return false;
			return true;
		}		
		
		
	}
}
