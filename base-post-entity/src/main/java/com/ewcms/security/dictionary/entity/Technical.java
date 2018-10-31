package com.ewcms.security.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 职称
 * <ul>
 * <li>name:名称</li>
 * <li>level:等级</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "sec_technical")
@SequenceGenerator(name = "seq", sequenceName = "seq_sec_technical_id", allocationSize = 1)
public class Technical extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -4968997866651361411L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@Column(name = "level")
	private String level;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
