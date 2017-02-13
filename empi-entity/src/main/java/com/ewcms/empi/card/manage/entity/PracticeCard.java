package com.ewcms.empi.card.manage.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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

/**
 * 诊疗卡基本信息
 * 
 * <ul>
 * <li>practiceNo：诊疗卡号</li>
 * <li>createDate：上传时间</li>
 * <li>patientBaseInfo：病人基础信息</li>
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = Calendar.getInstance().getTime();	
	@ManyToOne(optional = true, fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH} )
    @Fetch(FetchMode.SELECT)
	private PatientBaseInfo patientBaseInfo = new PatientBaseInfo();	
	@Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
	
	public String getPracticeNo() {
		return practiceNo;
	}

	public void setPracticeNo(String practiceNo) {
		this.practiceNo = practiceNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JSONField(serialize = false)
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

}
