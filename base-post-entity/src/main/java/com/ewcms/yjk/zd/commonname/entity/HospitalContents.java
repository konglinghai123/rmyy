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

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 医院在用药品总目录
 * 
 * <ul>
 * <li>projectName:项目名称</li>
 * <li>drugCode:药品代码</li>
 * <li>commonName:药品通用名</li>
 * <li>administration:给药途径</li>
 * <li>pill:剂型</li>
 * <li>productName:商品名</li>
 * <li>specifications:规格</li>
 * <li>amount:包装数量</li>
 * <li>manufacturer:生产企业</li>
 * <li>importEnterprise:进口企业</li>
 * <li>bidPrice:中标价</li>
 * <li>medical:医保</li>
 * <li>limitRange:限制范围</li>
 * <li>remark:备注</li>
 * <li>common:通用名对象(CommonName)</li>
 * <li>createDate:创建时间</li>
 * <li>updateDate:更新时间</li>
 * <li>deleted:是否删除</li>
 * <li>remark1:备注1</li>
 * <li>remark2:备注2</li>
 * <li>remark3:备注3</li>
 * <li>spell:通用名拼音</li>
 * <li>spellSimplify:通用名简拼</li>
 * </ul>
 * 
 * @author zhoudongchu
 */

@Entity
@Table(name = "zd_hospital_contents")
@SequenceGenerator(name="seq", sequenceName="seq_zd_hospital_contents_id", allocationSize = 1)
public class HospitalContents extends BaseSequenceEntity<Long> implements LogicDeleteable {
	private static final long serialVersionUID = -1099925594474353241L;
	
	@Column(name = "project_name")
	private String projectName;	
	
	@Column(name = "drug_code")
	private String drugCode;	
	
	@Column(name = "common_name", nullable = false)
	private String commonName;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	@JoinColumn(name = "administration_id")
	private Administration administration;
	
	@Column(name = "pill")
	private String pill;
	
	@Column(name = "product_name")
	private String productName; 
	
	@Column(name = "amount")
	private String amount;
	
	@Column(name = "specifications")
	private String specifications;
	
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
	
	@Column(name = "remark")
	private String remark;
	
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "common_name_id")
    private CommonName common;	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date",  updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_date")
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

	
	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

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



	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
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


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImportEnterprise() {
		return importEnterprise;
	}

	public void setImportEnterprise(String importEnterprise) {
		this.importEnterprise = importEnterprise;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Administration getAdministration() {
		return administration;
	}

	public void setAdministration(Administration administration) {
		this.administration = administration;
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
