package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 肾移植患者
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationName:医院名称</li>
 * <li>generalInformationId:患者ID</li>
 * <li>diabeticNephropathyTime:糖尿病肾病时长</li>
 * <li>hypertensiveNephropathyTime:高血压肾病时长</li>
 * <li>glomerulonephritisTime:肾小球肾炎时长</li>
 * <li>polycysticKidneyTime:多囊肾病时长</li>
 * <li>lupusNephritisTime:狼疮肾炎时长 </li>
 * <li>igANephropathyTime:IgA肾病时长</li>
 * <li>otherNephropathy:其他肾病</li>
 * <li>otherNephropathyTime:其他肾病时长</li>
 * <li>dialysisTime:维持性透析时长</li>
 * <li>hemodialysisTime:血液透析时长</li>
 * <li>peritonealDialysisTime:腹膜透析时长</li>
 * <li>transplantationAge:移植年龄</li>
 * <li>transplantationYear:移植年份</li>
 * <li>transplantationMonth:移植月份</li>
 * <li>renalAge:肾移植龄</li>
 * <li>renalOrigin:移植肾来源</li>
 * <li>rejectionNumber:排斥反应次数</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_renal_transplant")
@SequenceGenerator(name = "seq", sequenceName = "hzda_renal_transplant_id", allocationSize = 1)
public class RenalTransplant extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -1908104378790577657L;

	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	@Column(name = "organization_id")
	private Long organizationId;
	@Formula(value = "(select s_o.name from sec_organization s_o where s_o.id=organization_id)")
	private String organizationName;
	@Column(name = "general_information_id", nullable = false)
	private Long generalInformationId;
	
    @Column(name = "diabetic_nephropathy_time")
    private Long diabeticNephropathyTime;
    @Column(name = "hypertensive_nephropathy_time")
    private Long hypertensiveNephropathyTime;
    @Column(name = "glomerulonephritis_time")
    private Long glomerulonephritisTime;
    
    @Column(name = "polycystic_kidney_time")
    private Long polycysticKidneyTime;
    @Column(name = "lupusNephritis_time")
    private Long lupusNephritisTime;
    @Column(name = "iga_nephropathy_time")
    private Long igANephropathyTime;
    
    @Column(name = "other_nephropathy")
    private String otherNephropathy;
    @Column(name = "other_nephropathy_time")
    private Long otherNephropathyTime;
    @Column(name = "dialysis_time")
    private Long dialysisTime;   
    @Column(name = "hemodialysis_time")
    private Long hemodialysisTime;
    @Column(name = "peritoneal_dialysis_time")
    private Long peritonealDialysisTime;  
    
    @Column(name = "transplantation_age")
    private String transplantationAge;
    @Column(name = "transplantation_year")
    private Long transplantationYear;
    @Column(name = "transplantation_month")
    private Long transplantationMonth;   
    @Column(name = "renal_age")
    private Long renalAge;
    
    @Column(name = "renal_origin")
    private String renalOrigin;
    @Column(name = "rejection_number")
    private Long rejectionNumber;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Long getGeneralInformationId() {
		return generalInformationId;
	}
	public void setGeneralInformationId(Long generalInformationId) {
		this.generalInformationId = generalInformationId;
	}
	public Long getDiabeticNephropathyTime() {
		return diabeticNephropathyTime;
	}
	public void setDiabeticNephropathyTime(Long diabeticNephropathyTime) {
		this.diabeticNephropathyTime = diabeticNephropathyTime;
	}
	public Long getHypertensiveNephropathyTime() {
		return hypertensiveNephropathyTime;
	}
	public void setHypertensiveNephropathyTime(Long hypertensiveNephropathyTime) {
		this.hypertensiveNephropathyTime = hypertensiveNephropathyTime;
	}
	public Long getGlomerulonephritisTime() {
		return glomerulonephritisTime;
	}
	public void setGlomerulonephritisTime(Long glomerulonephritisTime) {
		this.glomerulonephritisTime = glomerulonephritisTime;
	}
	public Long getPolycysticKidneyTime() {
		return polycysticKidneyTime;
	}
	public void setPolycysticKidneyTime(Long polycysticKidneyTime) {
		this.polycysticKidneyTime = polycysticKidneyTime;
	}
	public Long getLupusNephritisTime() {
		return lupusNephritisTime;
	}
	public void setLupusNephritisTime(Long lupusNephritisTime) {
		this.lupusNephritisTime = lupusNephritisTime;
	}
	public Long getIgANephropathyTime() {
		return igANephropathyTime;
	}
	public void setIgANephropathyTime(Long igANephropathyTime) {
		this.igANephropathyTime = igANephropathyTime;
	}
	public String getOtherNephropathy() {
		return otherNephropathy;
	}
	public void setOtherNephropathy(String otherNephropathy) {
		this.otherNephropathy = otherNephropathy;
	}
	public Long getOtherNephropathyTime() {
		return otherNephropathyTime;
	}
	public void setOtherNephropathyTime(Long otherNephropathyTime) {
		this.otherNephropathyTime = otherNephropathyTime;
	}
	public Long getDialysisTime() {
		return dialysisTime;
	}
	public void setDialysisTime(Long dialysisTime) {
		this.dialysisTime = dialysisTime;
	}
	public Long getHemodialysisTime() {
		return hemodialysisTime;
	}
	public void setHemodialysisTime(Long hemodialysisTime) {
		this.hemodialysisTime = hemodialysisTime;
	}
	public Long getPeritonealDialysisTime() {
		return peritonealDialysisTime;
	}
	public void setPeritonealDialysisTime(Long peritonealDialysisTime) {
		this.peritonealDialysisTime = peritonealDialysisTime;
	}
	public String getTransplantationAge() {
		return transplantationAge;
	}
	public void setTransplantationAge(String transplantationAge) {
		this.transplantationAge = transplantationAge;
	}
	public Long getTransplantationYear() {
		return transplantationYear;
	}
	public void setTransplantationYear(Long transplantationYear) {
		this.transplantationYear = transplantationYear;
	}
	public Long getTransplantationMonth() {
		return transplantationMonth;
	}
	public void setTransplantationMonth(Long transplantationMonth) {
		this.transplantationMonth = transplantationMonth;
	}
	public Long getRenalAge() {
		return renalAge;
	}
	public void setRenalAge(Long renalAge) {
		this.renalAge = renalAge;
	}
	public Long getRejectionNumber() {
		return rejectionNumber;
	}
	public void setRejectionNumber(Long rejectionNumber) {
		this.rejectionNumber = rejectionNumber;
	}
	public String getRenalOrigin() {
		return renalOrigin;
	}
	public void setRenalOrigin(String renalOrigin) {
		this.renalOrigin = renalOrigin;
	}  
    
    
}
