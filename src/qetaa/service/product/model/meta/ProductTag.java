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

@Table(name="prd_meta_product_tag")
@Entity 
@IdClass(ProductTag.ProductTagPK.class)
public class ProductTag implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Id
	@JoinColumn(name="tag_id")
	@ManyToOne
	private Tag tag;

	@Id
	@Column(name="product_id")
	private long productId;
	
	@Column(name="created")
	private Date created;
	@Column(name="created_by")
	private int createdBy;
	
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
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
	
	
	public static class ProductTagPK implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected long tag;
		protected long productId;
		
		public ProductTagPK() {}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (productId ^ (productId >>> 32));
			result = prime * result + (int) (tag ^ (tag>>> 32));
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
			ProductTagPK other = (ProductTagPK) obj;
			if (productId != other.productId)
				return false;
			if (tag != other.tag)
				return false;
			return true;
		}		
		
		
	}
}
