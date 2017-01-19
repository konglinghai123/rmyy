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
 * 就诊卡的金额流水操作记录
 * <ul>
 * <li>journalSum：流水金额</li>
 * <li>journalDate：流水日期</li>
 * <li>remark：说明</li>
 * <li>operateUseId：操作员Id</li>
 * <li>operateUserName：操作员名称</li>
 * <li>journalOperate：流水操作状态</li>
 * <li>practiceCard：诊疗卡</li>
 * </ul>
 *@author zhoudongchu
 */
@Entity
@Table(name = "card_practice_card_journal")
public class PracticeCardJournal extends BaseEntity<Long> {
	private static final long serialVersionUID = 1328659336074207644L;
	
	@Column(name = "journal_sum")
	private Double journalSum = 0D;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "journal_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date journalDate = Calendar.getInstance().getTime();
	@Column(name = "remark",columnDefinition = "text")
	private String remark;
	@Column(name = "operate_use_id")
	private Long operateUseId;
	@Formula(value = "(select u.user_name + case when u.real_name is not null then ('(' + u.real_name + ')') else '' end from sec_user u where u.id=operate_use_id)")
	private String operateUserName;
	@Enumerated(EnumType.STRING)
	@Column(name = "journal_operate")
	private PracticeCardJournalOperate journalOperate = PracticeCardJournalOperate.recharge;
	@ManyToOne(optional = true, fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @Fetch(FetchMode.SELECT)
	@JoinColumn(name = "practice_card_id")
	private PracticeCard practiceCard;
	
	public Double getJournalSum() {
		return journalSum;
	}
	
	public void setJournalSum(Double journalSum) {
		this.journalSum = journalSum;
	}
	
	public Date getJournalDate() {
		return journalDate;
	}
	
	public void setJournalDate(Date journalDate) {
		this.journalDate = journalDate;
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
	
	public String getOperateUserName() {
		return operateUserName;
	}
	
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public PracticeCardJournalOperate getJournalOperate() {
		return journalOperate;
	}
	
	public void setJournalOperate(PracticeCardJournalOperate journalOperate) {
		this.journalOperate = journalOperate;
	}
	
	public String getJournalOperateInfo(){
		return journalOperate == null ? PracticeCardJournalOperate.recharge.getInfo() : journalOperate.getInfo();
	}

	@JSONField(serialize=false)
	public PracticeCard getPracticeCard() {
		return practiceCard;
	}

	public void setPracticeCard(PracticeCard practiceCard) {
		this.practiceCard = practiceCard;
	}

	public String getPracticeNo(){
		return practiceCard == null ? "" : practiceCard.getPracticeNo();
	}
	
	public PracticeCardJournal() {
		super();
	}

	public PracticeCardJournal(Double journalSum,String remark, Long operateUseId,PracticeCardJournalOperate journalOperate) {
		super();
		this.journalSum = journalSum;
		this.remark = remark;
		this.operateUseId = operateUseId;
		this.journalOperate = journalOperate;
	}
	
}
