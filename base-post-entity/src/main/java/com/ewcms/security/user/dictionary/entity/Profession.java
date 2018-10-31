package com.ewcms.security.user.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 职业
 * <ul>
 * <li>name:名称</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "user_zd_profession")
@SequenceGenerator(name="seq", sequenceName="seq_user_zd_profession_id", allocationSize = 1)
public class Profession extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 6640574440371025721L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
