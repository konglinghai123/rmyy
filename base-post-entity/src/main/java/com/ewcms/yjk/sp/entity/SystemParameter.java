package com.ewcms.yjk.sp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 系统参数设置
 * 
 * <ul>
 * <li>applyStartDate:申请开始时间</li>
 * <li>applyEndDate:申请结束时间</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */

@Entity
@Table(name = "sp_system_parameter")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_zd_system_parameter_id", allocationSize = 1)
public class SystemParameter extends BaseSequenceEntity<Long> implements LogicDeleteable{

	private static final long serialVersionUID = 4409780231040092738L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "apply_start_date", nullable = false, unique = true)
    @Temporal(TemporalType.TIMESTAMP)
	private Date applyStartDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "apply_end_date", nullable = false, unique = true)
    @Temporal(TemporalType.TIMESTAMP)
	private Date applyEndDate;

    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyStartDate() {
		return applyStartDate;
	}

	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyEndDate() {
		return applyEndDate;
	}

	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
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
