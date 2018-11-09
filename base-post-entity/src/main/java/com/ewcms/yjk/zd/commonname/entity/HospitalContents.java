package com.ewcms.yjk.zd.commonname.entity;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 医院在用药品总目录
 * 
 * <ul>
 * <li>commonName:药品通用名</li>
 * <li>drugCode:药品代码</li>
 * <li>common:通用名对象(CommonName)</li>
 * <li>pill:剂型</li>
 * <li>specifications:规格</li>
 * <li>amount:包装数量</li>
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
 * <li>spell:通用名称拼音</li>
 * <li>spellSimplify:通用名称拼音简写</li>
 * <li>originalCategory:原类别</li>
 * <li>medicalInfo:医保等信息</li>
 * <li>qualityLevel:原质量层次</li>
 * </ul>
 * 
 * @author zhoudongchu
 */

@Entity
@Table(name = "zd_hospital_contents")
@SequenceGenerator(name="seq", sequenceName="seq_zd_hospital_contents_id", allocationSize = 1)
public class HospitalContents extends BaseSequenceEntity<Long> implements LogicDeleteable {
	private static final long serialVersionUID = -1099925594474353241L;
	
	@Column(name = "common_name", nullable = false)
	private String commonName;	
	
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "common_name_id")
    private CommonName common;
	
	@Column(name = "drug_code")
	private String drugCode;	

	@Column(name = "pill")
	private String pill;
	
	@Column(name = "amount")
	private String amount;
	
	@Column(name = "specifications")
	private String specifications;
	
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
	
	@Column(name = "spell")
	private String spell;
	
	@Column(name = "spell_simplify")
	private String spellSimplify;
	
	@Column(name = "original_category")
	private String originalCategory;	
	
	@Column(name = "medical_info")
	private String medicalInfo;	
	
	@Column(name = "quality_level")
	private String qualityLevel;

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getPill() {
		return pill;
	}

	public void setPill(String pill) {
		this.pill = pill;
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

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public CommonName getCommon() {
		return common;
	}

	public void setCommon(CommonName common) {
		this.common = common;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getSpellSimplify() {
		return spellSimplify;
	}

	public void setSpellSimplify(String spellSimplify) {
		this.spellSimplify = spellSimplify;
	}

	public String getOriginalCategory() {
		return originalCategory;
	}

	public void setOriginalCategory(String originalCategory) {
		this.originalCategory = originalCategory;
	}

	public String getMedicalInfo() {
		return medicalInfo;
	}

	public void setMedicalInfo(String medicalInfo) {
		this.medicalInfo = medicalInfo;
	}

	public String getQualityLevel() {
		return qualityLevel;
	}

	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
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
