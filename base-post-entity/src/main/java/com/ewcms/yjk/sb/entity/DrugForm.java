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

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;

/**
 * 药品申报表填写
 * 
 * <ul>
 * <li>userId:申报医生ID</li>
 * <li>userName:申报医生姓名</li>
 * <li>createDate:申报日期</li>
 * <li>commonNameContents:申请大通用名药品</li>
 * <li>declared:是否申报</li>
 * <li>auditStatus:审核状态</li>
 * <li>remark:说明</li>
 * 
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

	@Formula(value = "(select s_o.username  from sec_user s_o where s_o.id=user_id)")
	private String userName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "create_date", columnDefinition = "Timestamp default CURRENT_DATE", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "commonNameContents_id")
	private CommonNameContents commonNameContents;

	@Enumerated(EnumType.STRING)
	private AuditStatusEnum auditStatus = AuditStatusEnum.init;
	
	@Column(name = "is_declared")
	private Boolean declared = Boolean.FALSE;
	
	@Column(name = "remark")
	private String remark;	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
}
