package com.ewcms.empi.card.manage.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseEntity;

/**
 * 诊疗卡操作历史
 * <ul>
 * <li>operateDate：操作日期</li>
 * <li>remark：操作说明</li>
 * <li>operateUseId：操作用户Id</li>
 * <li>operateUserName：操作用户名</li>
 * <li>historyOperate：诊疗卡操作</li>
 * <li>historyStatus：历史状态</li>
 * <li>practiceCard：诊疗卡</li>
 * </ul>
 *@author zhoudongchu
 */
@Entity
@Table(name = "card_practice_card_history")
public class PracticeCardHistory extends BaseEntity<Long> {
	private static final long serialVersionUID = 1180008921122586010L;
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "operate_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date operateDate = Calendar.getInstance().getTime();
	@Column(name = "remark",columnDefinition = "text")
	private String remark;
	@Column(name = "operate_use_id")
	private Long operateUseId;
	@Formula(value = "(select u.user_name + case when u.real_name is not null then ('(' + u.real_name + ')') else '' end from sec_user u where u.id=operate_use_id)")
	private String operateUserName;	
	@Enumerated(EnumType.STRING)
	@Column(name = "history_operate")
	private PracticeCardOperate historyOperate = PracticeCardOperate.distribute;
	@ManyToOne(optional = true, fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @Fetch(FetchMode.SELECT)
	@JoinColumn(name = "practice_card_id")
	private PracticeCard practiceCard;
	@Enumerated(EnumType.STRING)
	@Column(name = "history_status")
	private PracticeCardStatus historyStatus = PracticeCardStatus.normal;
	
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getOperateUseId() {
		return operateUseId;
	}
	public void setOperateUseId(Long operateUseId) {
		this.operateUseId = operateUseId;
	}
	public PracticeCardOperate getHistoryOperate() {
		return historyOperate;
	}
	public void setHistoryOperate(PracticeCardOperate historyOperate) {
		this.historyOperate = historyOperate;
	}
	public String getHistoryOperateInfo(){
		return historyOperate == null ? PracticeCardOperate.distribute.getInfo() : historyOperate.getInfo();
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	@JSONField(serialize=false)
	public PracticeCard getPracticeCard() {
		return practiceCard;
	}
	public void setPracticeCard(PracticeCard practiceCard) {
		this.practiceCard = practiceCard;
	}
	public PracticeCardHistory() {
		super();
	}
	public PracticeCardHistory(String remark, Long operateUseId,PracticeCardOperate historyOperate, PracticeCardStatus historyStatus) {
		super();
		this.remark = remark;
		this.operateUseId = operateUseId;
		this.historyOperate = historyOperate;
		this.historyStatus = historyStatus;
	}

	
}
