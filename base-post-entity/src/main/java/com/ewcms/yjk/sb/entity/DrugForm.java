package com.ewcms.yjk.sb.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;

/**
 * 药品申报表填写
 * 
 * <ul>
 * <li>userId:申报医生ID</li>
 * <li>systemParameterId:启动的系统参数申报ID</li>
 * <li>realName:申报医生姓名</li>
 * <li>departName:科室名称</li>
 * <li>createDate:申报日期</li>
 * <li>commonNameContents:申请大通用名药品</li>
 * <li>declared:是否申报</li>
 * <li>auditStatus:审核状态</li>
 * <li>remark:说明</li>
 * <li>fillInDate:填报时间</li>
 * <li>declareDate:申报时间</li>
 * <li>auditDate:初审时间</li>
 * <li>dosage:用法用量</li>
 * <li>indicationsEffect:适应症及药理作用</li>
 * <li>declareReason:申请理由</li>
 * <li>constituent:成分</li>
 * <li>preparationed:是否复方制剂</li>
 * <li>declareCategory:申报类型</li>
 * <li>reviewed:是否已评审</li>
 * <li>projectRemark:项目说明</li>
 * </ul>
 * 
 * @author zhoudongchu
 */
@Entity
@Table(name = "sb_drug_form")
@SequenceGenerator(name = "seq", sequenceName = "seq_sb_drug_form_id", allocationSize = 1)
public class DrugForm extends BaseSequenceEntity<Long> {
	private static final long serialVersionUID = 9001257669684367907L;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "system_parameter_id", nullable = false)
	private Long systemParameterId;
	
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	
	@Formula(value = "(select s_o.project_remark  from sp_system_parameter s_o where s_o.id=system_parameter_id)")
	private String projectRemark;
	
	@Formula(value = "(select string_agg(t2.name,',') from sec_organization t2  where t2.id in (select t1.organization_id from sec_user_organization_job t1 where t1.user_id=user_id))")
	private String departName;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "fill_in_date", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fillInDate = new Date();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "declare_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date declareDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "audit_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditDate;	
	
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "commonNameContents_id")
	private CommonNameContents commonNameContents;

	@Enumerated(EnumType.STRING)
	private AuditStatusEnum auditStatus = AuditStatusEnum.nodeclare;
	
	@Column(name = "is_declared")
	private Boolean declared = Boolean.FALSE;
	
	@Column(name = "remark")
	private String remark;	
	
	@Column(name = "dosage", nullable = false, columnDefinition = "text")
	private String dosage;
	
	@Column(name = "indications_effect", nullable = false, columnDefinition = "text")
	private String indicationsEffect;
	
	@Column(name = "declare_reason", nullable = false, columnDefinition = "text")
	private String declareReason;
	
	@Column(name = "constituent", columnDefinition = "text")
	private String constituent;

	@Column(name = "declareCategory")
	private String declareCategory;
	
	@Column(name = "is_preparationed")
	private Boolean preparationed = Boolean.FALSE;

	@Column(name = "is_reviewed")
	private Boolean reviewed = Boolean.FALSE;
	
	public String getRealName() {
		return realName;
	}

	public String getDepartName() {
		return departName;
	}

	public Long getSystemParameterId() {
		return systemParameterId;
	}

	public void setSystemParameterId(Long systemParameterId) {
		this.systemParameterId = systemParameterId;
	}

	public String getFormatId() {
		return String.format("%08d", getId());
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getFillInDate() {
		return fillInDate;
	}

	public void setFillInDate(Date fillInDate) {
		this.fillInDate = fillInDate;
	}
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public CommonNameContents getCommonNameContents() {
		return commonNameContents;
	}

	public void setCommonNameContents(CommonNameContents commonNameContents) {
		this.commonNameContents = commonNameContents;
	}

	public AuditStatusEnum getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatusEnum auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Boolean getDeclared() {
		return declared;
	}

	public void setDeclared(Boolean declared) {
		this.declared = declared;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuditStatusInfo(){
    	return auditStatus == null ? "" : auditStatus.getInfo();
    }

	public String getDrugCategoryInfo(){
		return commonNameContents == null ? "" : commonNameContents.getDrugCategoryInfo();
	}
	
	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getIndicationsEffect() {
		return indicationsEffect;
	}

	public void setIndicationsEffect(String indicationsEffect) {
		this.indicationsEffect = indicationsEffect;
	}

	public String getDeclareReason() {
		return declareReason;
	}

	public void setDeclareReason(String declareReason) {
		this.declareReason = declareReason;
	}
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getConstituent() {
		return constituent;
	}

	public void setConstituent(String constituent) {
		this.constituent = constituent;
	}

	public Boolean getPreparationed() {
		return preparationed;
	}

	public void setPreparationed(Boolean preparationed) {
		this.preparationed = preparationed;
	}

	public String getDeclareCategory() {
		return declareCategory;
	}

	public void setDeclareCategory(String declareCategory) {
		this.declareCategory = declareCategory;
	}

	public Boolean getReviewed() {
		return reviewed;
	}

	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
	}

	public String getProjectRemark() {
		return projectRemark;
	}
	
	
}
