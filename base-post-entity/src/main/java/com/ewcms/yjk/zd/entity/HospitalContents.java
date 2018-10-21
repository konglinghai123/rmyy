package com.ewcms.yjk.zd.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 医院在用药品总目录
 * 
 * <ul>
 * <li>drugCode:药品代码</li>
 * <li>genericDrugName:药品通用名</li>
 * <li>acidGroup:酸根</li>
 * <li>chemicalName:化学名</li>
 * <li>pill:剂型</li>
 * <li>pill1:剂型1</li>
 * <li>productName:商品名</li>
 * <li>specNumber:规格*数量</li>
 * <li>manufacturer:生产企业</li>
 * <li>importEnterprise:进口企业</li>
 * <li>bidPrice:中标价</li>
 * <li>medical:医保</li>
 * <li>limitRange:限制范围</li>
 * <li>contentCategory:目录分类</li>
 * <li>drugMajor:药品分类大类</li>
 * <li>drugCategory:药品分类</li>
 * <li>discom:配送公司</li>
 * <li>remark:备注</li>
 * <li>oldRemark:原备注</li>
 * <li>createDate:创建时间</li>
 * <li>updateDate:更新时间</li>
 * <li>deleted:是否删除</li>
 * </ul>
 * 
 * @author zhoudongchu
 */

@Entity
@Table(name = "zd_hospital_contents")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_zd_hospital_contents_id", allocationSize = 1)
public class HospitalContents extends BaseSequenceEntity<Long> implements LogicDeleteable {
	private static final long serialVersionUID = -1099925594474353241L;
	
	@Column(name = "drug_code")
	private String drugCode;	
    
	@Column(name = "generic_drug_name")
	private String genericDrugName;	
	
	@Column(name = "acid_group")
	private String acidGroup;
	
	@Column(name = "chemical_name")
	private String chemicalName;
	
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "pill_id")
	private Pill pill;
	
	@Column(name = "product_name")
	private String productName; 
	
	@Column(name = "spec_number")
	private String specNumber;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "import_enterprise")
	private String importEnterprise;
	
	@Column(name = "bid_price")
	private Double bidPrice;
	
	@Column(name = "medical")
	private String medical;
	
	@Column(name = "limit_range")
	private String limitRange;
	
	@Column(name = "content_category")
	private String contentCategory;
	
	@Column(name = "drug_major")
	private String drugMajor;
	
	@Column(name = "drug_category")
	private String drugCategory;
	
	@Column(name = "discomp")
	private String discom;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "old_remark")
	private String oldRemark;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date", columnDefinition = "Timestamp default CURRENT_DATE", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_date", columnDefinition = "Timestamp default CURRENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    
	@Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
	
	
	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getGenericDrugName() {
		return genericDrugName;
	}

	public void setGenericDrugName(String genericDrugName) {
		this.genericDrugName = genericDrugName;
	}

	public String getAcidGroup() {
		return acidGroup;
	}

	public void setAcidGroup(String acidGroup) {
		this.acidGroup = acidGroup;
	}

	public String getChemicalName() {
		return chemicalName;
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}
	
	public Pill getPill() {
		return pill;
	}

	public void setPill(Pill pill) {
		this.pill = pill;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpecNumber() {
		return specNumber;
	}

	public void setSpecNumber(String specNumber) {
		this.specNumber = specNumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getImportEnterprise() {
		return importEnterprise;
	}

	public void setImportEnterprise(String importEnterprise) {
		this.importEnterprise = importEnterprise;
	}

	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String getMedical() {
		return medical;
	}

	public void setMedical(String medical) {
		this.medical = medical;
	}

	public String getLimitRange() {
		return limitRange;
	}

	public void setLimitRange(String limitRange) {
		this.limitRange = limitRange;
	}

	public String getContentCategory() {
		return contentCategory;
	}

	public void setContentCategory(String contentCategory) {
		this.contentCategory = contentCategory;
	}

	public String getDrugMajor() {
		return drugMajor;
	}

	public void setDrugMajor(String drugMajor) {
		this.drugMajor = drugMajor;
	}

	public String getDrugCategory() {
		return drugCategory;
	}

	public void setDrugCategory(String drugCategory) {
		this.drugCategory = drugCategory;
	}

	public String getDiscom() {
		return discom;
	}

	public void setDiscom(String discom) {
		this.discom = discom;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOldRemark() {
		return oldRemark;
	}

	public void setOldRemark(String oldRemark) {
		this.oldRemark = oldRemark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }
}
