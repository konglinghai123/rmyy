package com.ewcms.yjk.zd.commonname.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * <li>extractCommonName:提取通用名</li>
 * <li>administration:给药途径</li>
 * <li>serialNo:编号</li>
 * <li>drugCode:药品代码</li>
 * <li>genericDrugName:药品通用名</li>
 * <li>pill:剂型</li>
 * <li>specNumber:规格*数量</li>
 * <li>manufacturer:生产企业</li>
 * <li>contentCategory:目录分类</li>
 * <li>drugMajor:药品分类大类</li>
 * <li>drugCategory:药品分类</li>
 * <li>discom:配送公司</li>
 * <li>remark:备注</li>
 * <li>oldRemark:原备注</li>
 * <li>createDate:创建时间</li>
 * <li>updateDate:更新时间</li>
 * <li>deleted:是否删除</li>
 * <li>remark1:备注1</li>
 * <li>remark2:备注2</li>
 * <li>remark3:备注3</li>
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
	
	@Column(name = "extract_common_name", nullable = false)
	private String extractCommonName;	
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	private Administration administration;
	
	@Column(name = "serial_no")
	private String serialNo;
	
	@Column(name = "drug_code")
	private String drugCode;	
    
	@Column(name = "generic_drug_name")
	private String genericDrugName;	
	
	@Column(name = "pill")
	private String pill;
	
	@Column(name = "spec_number")
	private String specNumber;
	
	@Column(name = "manufacturer")
	private String manufacturer;
		
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
	
	@Column(name = "remark1")
	private String remark1;	
	
	@Column(name = "remark2")
	private String remark2;	
	
	@Column(name = "remark3")
	private String remark3;		
	
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


	public String getPill() {
		return pill;
	}

	public void setPill(String pill) {
		this.pill = pill;
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

	public String getExtractCommonName() {
		return extractCommonName;
	}

	public void setExtractCommonName(String extractCommonName) {
		this.extractCommonName = extractCommonName;
	}

	public Administration getAdministration() {
		return administration;
	}

	public void setAdministration(Administration administration) {
		this.administration = administration;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
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
