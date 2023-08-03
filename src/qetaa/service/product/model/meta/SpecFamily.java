package qetaa.service.product.model.meta;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name="prd_meta_specification_family")
@Entity 
public class SpecFamily implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "prd_meta_specification_family_id_seq_gen", sequenceName = "prd_meta_specification_family_id_seq", initialValue=1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prd_meta_specification_family_id_seq_gen")
	@Column(name="id")
	private int id;
	@Column(name="family_name")
	private String name;
	@Column(name="family_name_ar")
	private String nameAr;
	@Column(name="created")
	private Date created;
	@Column(name="created_by")
	private int createdBy;
	
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
