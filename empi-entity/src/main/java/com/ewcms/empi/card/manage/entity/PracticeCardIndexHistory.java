package com.ewcms.empi.card.manage.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseEntity;

/**
 * 唯一索引号变更历史
 * 
 * <ul>
 * <li>practiceNo：诊疗卡号</li>
 * <li>beforePatientId：变更前唯一索引号</li>
 * <li>afterPatientId：变更后唯一索引号</li>
 * <li>remark:备注</li>
 * <li>changeDate:变更日期</li>
 * </ul>
 *@author zhoudongchu
 */
@Entity
@Table(name = "card_index_history")
public class PracticeCardIndexHistory extends BaseEntity<Long> {
	private static final long serialVersionUID = 7072590444580411772L;
	@NotNull(message = "{not.null}")
    @Column(name = "practice_no", nullable = false, unique = true)
	private String practiceNo;
	@Column(name = "before_patient_id")
	private String beforePatientId;
	@Column(name = "after_patient_id")
	private String afterPatientId;
	@Column(name = "remark")
	private String remark;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "change_date")	
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate = Calendar.getInstance().getTime();
	
	public String getPracticeNo() {
		return practiceNo;
	}
	public void setPracticeNo(String practiceNo) {
		this.practiceNo = practiceNo;
	}
	public String getBeforePatientId() {
		return beforePatientId;
	}
	public void setBeforePatientId(String beforePatientId) {
		this.beforePatientId = beforePatientId;
	}
	public String getAfterPatientId() {
		return afterPatientId;
	}
	public void setAfterPatientId(String afterPatientId) {
		this.afterPatientId = afterPatientId;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
}
