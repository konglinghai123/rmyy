package com.ewcms.hzda.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/**
 * 随访时间
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>generalInformationId:一般信息编号</li>
 * <li>nextTime:下一个时间</li>
 * <li>tip:是否提醒</li>
 * <li>sms:是否短信通知</li>
 * <li>smsDate:短信发送时间</li>
 * <li>code:返回消息代码</li>
 * <li>message:返回说明</li>
 * <li>requestId:请求ID</li>
 * </ul>
 * 
 * @author wu_zhijun
 *
 */
@Entity
@Table(name = "hzda_followup_time")
@SequenceGenerator(name = "seq", sequenceName = "hzda_followup_time_id", allocationSize = 1)
public class FollowupTime extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -8676615086013235673L;

	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	@Column(name = "organization_id")
	private Long organizationId;
	@Formula(value = "(select s_o.name from sec_organization s_o where s_o.id=organization_id)")
	private String organizationName;
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	private GeneralInformation generalInformation;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "next_time")
	@Temporal(TemporalType.DATE)
	private Date nextTime;
	@Column(name = "is_tip")
	private Boolean tip = Boolean.TRUE;
	@Column(name = "is_sms")
	private Boolean sms = Boolean.FALSE;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "sms_date")
	@Temporal(TemporalType.DATE)
	private Date smsDate;
	@Column(name = "code")
	private String code;
	@Column(name = "message")
	private String message;
	@Column(name = "requestId")
	private String requestId;

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

	public GeneralInformation getGeneralInformation() {
		return generalInformation;
	}

	public void setGeneralInformation(GeneralInformation generalInformation) {
		this.generalInformation = generalInformation;
	}

	@JSONField(format = "yyyy-MM-dd")
	public Date getNextTime() {
		return nextTime;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public Boolean getTip() {
		return tip;
	}

	public void setTip(Boolean tip) {
		this.tip = tip;
	}

	public Boolean getSms() {
		return sms;
	}

	public void setSms(Boolean sms) {
		this.sms = sms;
	}

	public Date getSmsDate() {
		return smsDate;
	}

	public void setSmsDate(Date smsDate) {
		this.smsDate = smsDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
