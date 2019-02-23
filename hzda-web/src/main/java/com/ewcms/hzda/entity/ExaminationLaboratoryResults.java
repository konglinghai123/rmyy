package com.ewcms.hzda.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 检查及化验结果
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationName:医院名称</li>
 * <li>generalInformationId:患者ID</li>
 * <li>examinationDate:检查日期</li>
 * <li>bloodALP:血液ALP</li>
 * <li>bloodCr:血液Cr</li>
 * <li>bloodCa:血液Ca</li>
 * <li>bloodP:血液P</li>
 * <li>bloodTco2:血液Tco2</li>
 * <li>bloodOsteocalcin:血液骨钙素</li>
 * <li>bloodPINP:血液PINP</li>
 * <li>bloodCTX:血液CTX</li>
 * <li>bloodVitD:血液VitD</li>
 * <li>urinePTH:24h尿PTH</li>
 * <li>urineCa:24h尿Ca</li>
 * <li>urineP:24h尿P</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_examination_laboratory_results")
@SequenceGenerator(name = "seq", sequenceName = "hzda_examination_laboratory_results_id", allocationSize = 1)
public class ExaminationLaboratoryResults extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 5865754068366891028L;

	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	@Column(name = "organization_id", nullable = false)
	private Long organizationId;
	@Formula(value = "(select s_o.name from sec_organization s_o where s_o.id=organization_id)")
	private String organizationName;
	@Column(name = "general_information_id", nullable = false)
	private Long generalInformationId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "examination_date")
    @Temporal(TemporalType.DATE)
    private Date examinationDate;
    
    @Column(name = "blood_alp")
    private String bloodALP;    
    @Column(name = "blood_cr")
    private String bloodCr;
    @Column(name = "blood_ca")
    private String bloodCa;
    @Column(name = "blood_p")
    private String bloodP;
    @Column(name = "blood_tco2")
    private String bloodTco2;
    @Column(name = "blood_osteocalcin")
    private String bloodOsteocalcin;
    @Column(name = "blood_pinp")
    private String bloodPINP;
    @Column(name = "blood_ctx")
    private String bloodCTX;   
    @Column(name = "blood_vitd")
    private String bloodVitD; 
    @Column(name = "urine_pth")
    private String urinePTH;  
    @Column(name = "urine_ca")
    private String urineCa;  
    @Column(name = "urine_p")
    private String urineP;
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
	@JSONField(format = "yyyy-MM-dd")
	public Date getExaminationDate() {
		return examinationDate;
	}
	public void setExaminationDate(Date examinationDate) {
		this.examinationDate = examinationDate;
	}
	public String getBloodALP() {
		return bloodALP;
	}
	public void setBloodALP(String bloodALP) {
		this.bloodALP = bloodALP;
	}
	public String getBloodCr() {
		return bloodCr;
	}
	public void setBloodCr(String bloodCr) {
		this.bloodCr = bloodCr;
	}
	public String getBloodCa() {
		return bloodCa;
	}
	public void setBloodCa(String bloodCa) {
		this.bloodCa = bloodCa;
	}
	public String getBloodP() {
		return bloodP;
	}
	public void setBloodP(String bloodP) {
		this.bloodP = bloodP;
	}
	public String getBloodTco2() {
		return bloodTco2;
	}
	public void setBloodTco2(String bloodTco2) {
		this.bloodTco2 = bloodTco2;
	}
	public String getBloodOsteocalcin() {
		return bloodOsteocalcin;
	}
	public void setBloodOsteocalcin(String bloodOsteocalcin) {
		this.bloodOsteocalcin = bloodOsteocalcin;
	}
	public String getBloodPINP() {
		return bloodPINP;
	}
	public void setBloodPINP(String bloodPINP) {
		this.bloodPINP = bloodPINP;
	}
	public String getBloodCTX() {
		return bloodCTX;
	}
	public void setBloodCTX(String bloodCTX) {
		this.bloodCTX = bloodCTX;
	}
	public String getBloodVitD() {
		return bloodVitD;
	}
	public void setBloodVitD(String bloodVitD) {
		this.bloodVitD = bloodVitD;
	}
	public String getUrinePTH() {
		return urinePTH;
	}
	public void setUrinePTH(String urinePTH) {
		this.urinePTH = urinePTH;
	}
	public String getUrineCa() {
		return urineCa;
	}
	public void setUrineCa(String urineCa) {
		this.urineCa = urineCa;
	}
	public String getUrineP() {
		return urineP;
	}
	public void setUrineP(String urineP) {
		this.urineP = urineP;
	}    
}
