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
 * 骨密度
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationName:医院名称</li>
 * <li>generalInformationId:患者ID</li>
 * <li>examinationDate:检查日期</li>
 * <li>lumbarBMD:腰椎 BMD</li>
 * <li>lumbarT:腰椎T值</li>
 * <li>lumbarZ:腰椎Z值</li>
 * <li>femoralDiameterBMD:股骨颈 BMD</li>
 * <li>femoralDiameterT:股骨颈T值</li>
 * <li>femoralDiameterZ:股骨颈Z值</li>
 * <li>coxaBMD:髋总 BMD</li>
 * <li>coxaT:髋总T值</li>
 * <li>coxaZ:髋总Z值</li>
 * <li>remark:备注</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_bone_density")
@SequenceGenerator(name = "seq", sequenceName = "hzda_bone_density_id", allocationSize = 1)
public class BoneDensity extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 7934071651906269300L;
	
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "examination_date")
    @Temporal(TemporalType.DATE)
    private Date examinationDate;
    @Column(name = "lumbar_bmd")
    private String lumbarBMD;    
    @Column(name = "lumbar_t")
    private String lumbarT;
    @Column(name = "lumbar_z")
    private String lumbarZ;
    @Column(name = "femoral_diameter_bmd")
    private String femoralDiameterBMD;
    @Column(name = "femoral_diameter_t")
    private String femoralDiameterT;
    @Column(name = "femoral_diameter_z")
    private String femoralDiameterZ;
    @Column(name = "coxa_bmd")
    private String coxaBMD;
    @Column(name = "coxa_z")
    private String coxaZ;   
    @Column(name = "coxa_t")
    private String coxaT; 
    @Column(name = "remark")
    private String remark;
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
	public String getLumbarBMD() {
		return lumbarBMD;
	}
	public void setLumbarBMD(String lumbarBMD) {
		this.lumbarBMD = lumbarBMD;
	}
	public String getLumbarT() {
		return lumbarT;
	}
	public void setLumbarT(String lumbarT) {
		this.lumbarT = lumbarT;
	}
	public String getLumbarZ() {
		return lumbarZ;
	}
	public void setLumbarZ(String lumbarZ) {
		this.lumbarZ = lumbarZ;
	}
	public String getFemoralDiameterBMD() {
		return femoralDiameterBMD;
	}
	public void setFemoralDiameterBMD(String femoralDiameterBMD) {
		this.femoralDiameterBMD = femoralDiameterBMD;
	}
	public String getFemoralDiameterT() {
		return femoralDiameterT;
	}
	public void setFemoralDiameterT(String femoralDiameterT) {
		this.femoralDiameterT = femoralDiameterT;
	}
	public String getFemoralDiameterZ() {
		return femoralDiameterZ;
	}
	public void setFemoralDiameterZ(String femoralDiameterZ) {
		this.femoralDiameterZ = femoralDiameterZ;
	}
	public String getCoxaBMD() {
		return coxaBMD;
	}
	public void setCoxaBMD(String coxaBMD) {
		this.coxaBMD = coxaBMD;
	}
	public String getCoxaZ() {
		return coxaZ;
	}
	public void setCoxaZ(String coxaZ) {
		this.coxaZ = coxaZ;
	}
	public String getCoxaT() {
		return coxaT;
	}
	public void setCoxaT(String coxaT) {
		this.coxaT = coxaT;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
