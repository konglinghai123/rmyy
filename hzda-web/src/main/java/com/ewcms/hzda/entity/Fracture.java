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

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 影像学检查:骨折
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationName:医院名称</li>
 * <li>generalInformationId:患者ID</li>
 * <li>examinationDate:检查日期</li>
 * <li>part:部位</li>
 * <li>degree:程度</li>
 * <li>reason:原因</li>
 * <li>remark:备注</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_fracture")
@SequenceGenerator(name = "seq", sequenceName = "hzda_fracture_id", allocationSize = 1)
public class Fracture extends BaseSequenceEntity<Long>{
	private static final long serialVersionUID = -4508431888237273925L;
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
    @Column(name = "part")
    private String part;    
    @Column(name = "degree")
    private String degree;
    @Column(name = "reason")
    private String reason;
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
	public Date getExaminationDate() {
		return examinationDate;
	}
	public void setExaminationDate(Date examinationDate) {
		this.examinationDate = examinationDate;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
