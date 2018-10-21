package com.ewcms.yjk.zd.commonname.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 剂型
 * 
 * <ul>
 * <li>pillName:剂型名称</li>
 * <li>deleted:是否删除</li>
 * </ul>
 * 
 *@author zhoudongchu
 */
@Entity
@Table(name = "zd_pill")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_zd_pill_id", allocationSize = 1)
public class Pill extends BaseSequenceEntity<Long> implements LogicDeleteable {
	private static final long serialVersionUID = -6782642861213349317L;
	
	@NotEmpty(message = "{not.null}")
	@Column(name = "pill_name", nullable = false, unique = true)
	private String pillName;
	
	@Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
    
	public String getPillName() {
		return pillName;
	}

	public void setPillName(String pillName) {
		this.pillName = pillName;
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
