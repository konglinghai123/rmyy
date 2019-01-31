package com.ewcms.security.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 技术职称(资格)
 * <ul>
 * <li>name:名称</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "sec_technical_title")
@SequenceGenerator(name = "seq", sequenceName = "seq_sec_technical_title_id", allocationSize = 1)
public class TechnicalTitle extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -4968997866651361411L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
