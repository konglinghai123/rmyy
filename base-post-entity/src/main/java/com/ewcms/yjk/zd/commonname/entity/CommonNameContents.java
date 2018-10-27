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
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 通用名总目录
 * 
 * <ul>
 * <li>drugId:药品ID</li>
 * <li>serialNo:编号</li>
 * <li>antibacterialsed:抗菌药物</li>
 * <li>orderNo:序号</li>
 * <li>projectName:项目名称</li>
 * <li>batch:批次</li>
 * <li>source:来源</li>
 * <li>extractCommonName:提取通用名</li>
 * <li>medicalDirNo:医保目录编号</li>
 * <li>medicalDirName:医保目录药品名称</li>
 * <li>medicalDirPill:医保目录药品剂型</li>
 * <li>medicalCategory:医保类别</li>
 * <li>medicalRemark:医保备注</li>
 * <li>drugType:药品类型</li>
 * <li>drugRoute:给药途径</li>
 * <li>pill:剂型</li>
 * <li>specNumber:规格*数量</li>
 * <li>productName:商品名</li>
 * <li>packageUnit:包装单位</li>
 * <li>manufacturer:生产企业</li>
 * <li>purchasePrice:采购价</li>
 * <li>packageMaterials:包材</li>
 * <li>minimalUnit:最小制剂单位</li>
 * <li>importEnterprise:进口企业</li>
 * <li>common:通用名</li>
 * <li>createDate:创建时间</li>
 * <li>updateDate:更新时间</li>
 * <li>declared:是否允许申报</li>
 * <li>deleted:是否删除</li>
 * <li>remark1:备注1</li>
 * <li>remark2:备注2</li>
 * <li>remark3:备注3</li>
 * </ul>
 * 
 * @author zhoudongchu
 */
@Entity
@Table(name = "zd_common_name_contents")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_zd_common_name_contents_id", allocationSize = 1)
public class CommonNameContents extends BaseSequenceEntity<Long> implements LogicDeleteable {
	private static final long serialVersionUID = 8558593211261565814L;
    
	@Column(name = "project_name")
	private String projectName;	
    
	@Column(name = "batch")
	private String batch;	
	
	@Column(name = "source")
	private String source;
	
	@Column(name = "extract_common_name", nullable = false)
	private String extractCommonName;
	
	@Column(name = "medical_dir_no")
	private String medicalDirNo;
	
	@Column(name = "medical_dir_name")
	private String medicalDirName;
	
	@Column(name = "medical_dir_pill")
	private String medicalDirPill;
	
	@Column(name = "medical_category")
	private String medicalCategory;
	
	@Column(name = "medical_remark")
	private String medicalRemark;
	
	@Column(name = "drug_type")
	private String drugType;
	
	@Column(name = "drug_route")
	private String drugRoute;
	
	@Column(name = "pill")
	private String pill;
	
	@Column(name = "spec_number")
	private String specNumber;
	
	@Column(name = "product_name")
	private String productName; 
	
	@Column(name = "package_unit")
	private String packageUnit;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "purchase_price")
	private Double purchasePrice;
	
	@Column(name = "package_materials")
	private String packageMaterials;
	
	@Column(name = "minimal_unit")
	private String minimalUnit;
	
	@Column(name = "import_enterprise")
	private String importEnterprise;
	
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "common_name_id")
    private CommonName common;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date", columnDefinition = "Timestamp default CURRENT_DATE", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_date", columnDefinition = "Timestamp default CURRENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    
	@Column(name = "is_declared")
	private Boolean declared = Boolean.TRUE;
	
	@Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
	
	@Column(name = "drug_id")
	private String drugId;	
	
	@Column(name = "serial_no")
	private String serialNo;	
	
	@Column(name = "is_antibacterialsed")
	private Boolean antibacterialsed = Boolean.FALSE;;	
	
	@Column(name = "order_no")
	private String orderNo;	
	
	@Column(name = "remark1")
	private String remark1;	
	
	@Column(name = "remark2")
	private String remark2;	
	
	@Column(name = "remark3")
	private String remark3;	
	
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getExtractCommonName() {
		return extractCommonName;
	}

	public void setExtractCommonName(String extractCommonName) {
		this.extractCommonName = extractCommonName;
	}

	public String getMedicalDirNo() {
		return medicalDirNo;
	}

	public void setMedicalDirNo(String medicalDirNo) {
		this.medicalDirNo = medicalDirNo;
	}

	public String getMedicalDirName() {
		return medicalDirName;
	}

	public void setMedicalDirName(String medicalDirName) {
		this.medicalDirName = medicalDirName;
	}

	public String getMedicalDirPill() {
		return medicalDirPill;
	}

	public void setMedicalDirPill(String medicalDirPill) {
		this.medicalDirPill = medicalDirPill;
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

	public String getDrugType() {
		return drugType;
	}

	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}

	public String getDrugRoute() {
		return drugRoute;
	}

	public void setDrugRoute(String drugRoute) {
		this.drugRoute = drugRoute;
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

	public String getDrugId() {
		return drugId;
	}

	public void setDrugId(String drugId) {
		this.drugId = drugId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Boolean getAntibacterialsed() {
		return antibacterialsed;
	}

	public void setAntibacterialsed(Boolean antibacterialsed) {
		this.antibacterialsed = antibacterialsed;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
