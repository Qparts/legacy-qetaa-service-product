package qetaa.service.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="prd_name")
@Entity 
public class ProductName implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "prd_name_id_seq_gen", sequenceName = "prd_name_id_seq", initialValue=1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prd_name_id_seq_gen")
	@Column(name="id")
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="name_ar")
	private String nameAr;
	@Column(name="created_by")
	@JsonIgnore 
	private Integer createdBy;
	@Column(name="created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Transient
	private List<ProductNameAlternative> altProductNames;

	
	public List<ProductNameAlternative> getAltProductNames() {
		return altProductNames;
	}
	public void setAltProductNames(List<ProductNameAlternative> altProductNames) {
		this.altProductNames = altProductNames;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getNameAr() {
		return nameAr;
	}
	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
	
	
}
