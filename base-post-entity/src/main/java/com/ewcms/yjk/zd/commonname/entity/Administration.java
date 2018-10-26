package com.ewcms.yjk.zd.commonname.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 给药途径
 * 
 * <ul>
 * <li>name:名称</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "zd_administration")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_zd_administration_id", allocationSize = 1)
public class Administration extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -1149599775900950943L;
	
	@Column(name = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
