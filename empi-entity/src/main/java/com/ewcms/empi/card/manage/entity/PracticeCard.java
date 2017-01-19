package com.ewcms.empi.card.manage.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.google.common.collect.Lists;

/**
 * 诊疗卡基本信息
 * 
 * <ul>
 * <li>practiceNo：诊疗卡号</li>
 * <li>depositOperate：押金操作</li>
 * <li>deposit：押金金额</li>
 * <li>balance：余额</li>
 * <li>status：诊疗卡状态</li>
 * <li>createDate：发卡时间</li>
 * <li>patientBaseInfo：病人基础信息</li>
 * <li>practiceCardJournals：诊疗卡流水号</li>
 * <li>practiceCardHistorys：诊疗卡操作历史</li>
 * <li>deleted:是否删除(逻辑删除)</li>
 * </ul>
 * 
 *@author zhoudongchu
 */
@Entity
@Table(name = "card_practice_card")
public class PracticeCard extends BaseEntity<Long> implements LogicDeleteable{
	private static final long serialVersionUID = 4420574543322797358L;
	@NotNull(message = "{not.null}")
    @Column(name = "practice_no", nullable = false, unique = true)
	private String practiceNo;
	@Column(name = "deposit_operate")
	private PracticeCardDepositOperate depositOperate = PracticeCardDepositOperate.paydeposit;
	@Column(name = "deposit", nullable = false)
	private Double deposit = 0D;
	@Column(name = "balance", nullable = false)
	private Double balance = 0D;
	@Enumerated(EnumType.STRING)
	@Column(name = "card_status")
	private PracticeCardStatus status = PracticeCardStatus.normal;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = Calendar.getInstance().getTime();	
	@ManyToOne(optional = true, fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH} )
    @Fetch(FetchMode.SELECT)
	private PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
	@OneToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = PracticeCardJournal.class)
	@Basic(optional = true, fetch = FetchType.EAGER)
	@OrderBy()
	@JoinColumn(name = "practice_card_id")
	private List<PracticeCardJournal> practiceCardJournals = Lists.newArrayList();
	@OneToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = PracticeCardHistory.class)
	@Basic(optional = true, fetch = FetchType.EAGER)
	@OrderBy()
	@JoinColumn(name = "practice_card_id")
	private List<PracticeCardHistory> practiceCardHistorys = Lists.newArrayList();	
	
	@Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
	
	
	public String getPracticeNo() {
		return practiceNo;
	}

	public void setPracticeNo(String practiceNo) {
		this.practiceNo = practiceNo;
	}


	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public PracticeCardDepositOperate getDepositOperate() {
		return depositOperate;
	}

	public void setDepositOperate(PracticeCardDepositOperate depositOperate) {
		this.depositOperate = depositOperate;
	}
	public String getDepositInfo() {
		return depositOperate == null ? PracticeCardDepositOperate.paydeposit.getInfo() : depositOperate.getInfo();
	}
	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public PracticeCardStatus getStatus() {
		return status;
	}

	public void setStatus(PracticeCardStatus status) {
		this.status = status;
	}
	
	public String getStatusInfo(){
		return status == null ? PracticeCardStatus.normal.getInfo() : status.getInfo();
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

	public PatientBaseInfo getPatientBaseInfo() {
		return patientBaseInfo;
	}

	public void setPatientBaseInfo(PatientBaseInfo patientBaseInfo) {
		this.patientBaseInfo = patientBaseInfo;
	}

	@Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }

    @JSONField(serialize = false)
	public List<PracticeCardJournal> getPracticeCardJournals() {
    	return (practiceCardJournals == null) ? Lists.<PracticeCardJournal>newArrayList() : practiceCardJournals;
	}
    
	public void setPracticeCardJournals(List<PracticeCardJournal> practiceCardJournals) {
		this.practiceCardJournals = practiceCardJournals;
	}
	@JSONField(serialize = false)
	public List<PracticeCardHistory> getPracticeCardHistorys() {
		return (practiceCardHistorys == null) ? Lists.<PracticeCardHistory>newArrayList() : practiceCardHistorys;
	}

	public void setPracticeCardHistorys(List<PracticeCardHistory> practiceCardHistorys) {
		this.practiceCardHistorys = practiceCardHistorys;
	}
	
	
}
