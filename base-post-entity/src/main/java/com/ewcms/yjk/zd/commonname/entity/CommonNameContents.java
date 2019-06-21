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
 * 通用名总目录
 * 
 * <ul>
 * <li>projectName:项目名称</li>
 * <li>batch:批次</li>
 * <li>commonName:通用名</li>
 * <li>bidDrugId:省招标药品ID</li>
 * <li>countryId:国家ID</li>
 * <li>administration:给药途径</li>
 * <li>pill:剂型</li>
 * <li>specifications:规格</li>
 * <li>amount:包装数量</li>
 * <li>productName:商品名</li>
 * <li>packageUnit:包装单位</li>
 * <li>manufacturer:生产企业</li>
 * <li>importEnterprise:进口企业</li>
 * <li>purchasePrice:采购价</li>
  * <li>medicalDirNo:医保目录编号</li>
 * <li>medicalCategory:医保类别</li>
 * <li>medicalRemark:医保备注</li>
 * <li>consistencyEvaluation:一致性评价</li>
 * <li>heds:基药</li>
 * <li>gynaecology:妇科</li>
 * <li>pediatric:儿科</li>
 * <li>firstAid:急救</li>
 * <li>basicInfusion:基础输液</li>
 * <li>cheapShortage:廉价短缺</li>
 * <li>negotiationVariety:国家谈判品种</li>
 * <li>licenseNumber:批准文号</li>
 * <li>bidPill:招标剂型</li>
 * <li>bidSpecifications:招标规格</li>
 * <li>bidUnit:招标单位</li>
 * <li>packageMaterials:包材</li>
 * <li>minimalUnit:最小制剂单位</li>
 * <li>common:通用名对象(CommonName)</li>
 * <li>createDate:创建时间</li>
 * <li>updateDate:更新时间</li>
 * <li>declared:是否允许申报</li>
 * <li>deleted:是否删除</li>
 * <li>remark1:备注1</li>
 * <li>remark2:备注2</li>
 * <li>remark3:备注3</li>
 * <li>spell:通用名拼音</li>
 * <li>spellSimplify:通用名简拼</li>
 * 
 * 
 * </ul>
 * 
 * @author zhoudongchu
 */
@Entity
@Table(name = "zd_common_name_contents")
@SequenceGenerator(name="seq", sequenceName="seq_zd_common_name_contents_id", allocationSize = 1)
public class CommonNameContents extends BaseSequenceEntity<Long> implements LogicDeleteable {
	private static final long serialVersionUID = 8558593211261565814L;
    
	@Column(name = "project_name")
	private String projectName;	
    
	@Column(name = "batch")
	private String batch;	
	
	@Column(name = "common_name", nullable = false)
	private String commonName;
	
	@Column(name = "bid_drug_id")
	private String bidDrugId;	
	
	@Column(name = "country_id")
	private String countryId;	
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	@JoinColumn(name = "administration_id")
	private Administration administration;	
	
	@Column(name = "pill")
	private String pill;
	
	@Column(name = "amount")
	private String amount;
	
	@Column(name = "specifications")
	private String specifications;
	
	@Column(name = "product_name")
	private String productName; 
	
	@Column(name = "package_unit")
	private String packageUnit;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "import_enterprise")
	private String importEnterprise;
	
	@Column(name = "purchase_price")
	private Double purchasePrice;
	
	@Column(name = "medical_dir_no")
	private String medicalDirNo;

	@Column(name = "medical_category")
	private String medicalCategory;
	
	@Column(name = "medical_remark")
	private String medicalRemark;	
	
	@Column(name = "consistency_evaluation")
	private String consistencyEvaluation;
	
	@Column(name = "heds")
	private String heds;
	
	@Column(name = "gynaecology")
	private String gynaecology;
	
	@Column(name = "pediatric")
	private String pediatric;
	
	@Column(name = "first_aid")
	private String firstAid;
	
	@Column(name = "basic_infusion")
	private String basicInfusion;
	
	@Column(name = "cheap_shortage")
	private String cheapShortage;
	
	@Column(name = "negotiation_variety")
	private String negotiationVariety;
	
	@Column(name = "license_number")
	private String licenseNumber;
	
	@Column(name = "bid_pill")
	private String bidPill;
	
	@Column(name = "bid_specifications")
	private String bidSpecifications;
	
	@Column(name = "bid_unit")
	private String bidUnit;
	
	@Column(name = "package_materials")
	private String packageMaterials;
	
	@Column(name = "minimal_unit")
	private String minimalUnit;
	
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "common_name_id")
    private CommonName common;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    
	@Column(name = "is_declared")
	private Boolean declared = Boolean.TRUE;
	
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getMedicalDirNo() {
		return medicalDirNo;
	}

	public void setMedicalDirNo(String medicalDirNo) {
		this.medicalDirNo = medicalDirNo;
	}


	public String getMedicalCategory() {
		return medicalCategory;
	}

	public void setMedicalCategory(String medicalCategory) {
		this.medicalCategory = medicalCategory;
	}

	public String getMedicalRemark() {
		return medicalRemark;
	}

	public void setMedicalRemark(String medicalRemark) {
		this.medicalRemark = medicalRemark;
	}

	public String getPill() {
		return pill;
	}

	public void setPill(String pill) {
		this.pill = pill;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPackageUnit() {
		return packageUnit;
	}

	public void setPackageUnit(String packageUnit) {
		this.packageUnit = packageUnit;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getPackageMaterials() {
		return packageMaterials;
	}

	public void setPackageMaterials(String packageMaterials) {
		this.packageMaterials = packageMaterials;
	}

	public String getMinimalUnit() {
		return minimalUnit;
	}

	public void setMinimalUnit(String minimalUnit) {
		this.minimalUnit = minimalUnit;
	}

	public String getImportEnterprise() {
		return importEnterprise;
	}

	public void setImportEnterprise(String importEnterprise) {
		this.importEnterprise = importEnterprise;
	}

	public CommonName getCommon() {
		return common;
	}

	public void setCommon(CommonName common) {
		this.common = common;
	}

	public Boolean getDeclared() {
		return declared;
	}

	public void setDeclared(Boolean declared) {
		this.declared = declared;
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
	
	public String getBidDrugId() {
		return bidDrugId;
	}

	public void setBidDrugId(String bidDrugId) {
		this.bidDrugId = bidDrugId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public Administration getAdministration() {
		return administration;
	}

	public void setAdministration(Administration administration) {
		this.administration = administration;
	}

	public String getConsistencyEvaluation() {
		return consistencyEvaluation;
	}

	public void setConsistencyEvaluation(String consistencyEvaluation) {
		this.consistencyEvaluation = consistencyEvaluation;
	}

	public String getHeds() {
		return heds;
	}

	public void setHeds(String heds) {
		this.heds = heds;
	}

	public String getGynaecology() {
		return gynaecology;
	}

	public void setGynaecology(String gynaecology) {
		this.gynaecology = gynaecology;
	}

	public String getPediatric() {
		return pediatric;
	}

	public void setPediatric(String pediatric) {
		this.pediatric = pediatric;
	}

	public String getFirstAid() {
		return firstAid;
	}

	public void setFirstAid(String firstAid) {
		this.firstAid = firstAid;
	}

	public String getBasicInfusion() {
		return basicInfusion;
	}

	public void setBasicInfusion(String basicInfusion) {
		this.basicInfusion = basicInfusion;
	}

	public String getCheapShortage() {
		return cheapShortage;
	}

	public void setCheapShortage(String cheapShortage) {
		this.cheapShortage = cheapShortage;
	}

	public String getNegotiationVariety() {
		return negotiationVariety;
	}

	public void setNegotiationVariety(String negotiationVariety) {
		this.negotiationVariety = negotiationVariety;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getBidPill() {
		return bidPill;
	}

	public void setBidPill(String bidPill) {
		this.bidPill = bidPill;
	}

	public String getBidSpecifications() {
		return bidSpecifications;
	}

	public void setBidSpecifications(String bidSpecifications) {
		this.bidSpecifications = bidSpecifications;
	}

	public String getBidUnit() {
		return bidUnit;
	}

	public void setBidUnit(String bidUnit) {
		this.bidUnit = bidUnit;
	}

	public String getDrugCategoryInfo() {
		return common == null||common.getDrugCategory()==null ? "" : common.getDrugCategory().getInfo();
	}
	
	public String getAdministrationName() {
		return administration.getName();
	}
	
	public String getExtractCommonName() {
		return common == null? "" : common.getCommonName();
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
