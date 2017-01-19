package com.ewcms.empi.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.ewcms.common.entity.BaseEntity;

/**
 * 民族
 * <ul>
 * <li>name:名称</li>
 * </ul>
 * @author wu_zhijun
 */

@Entity
@Table(name = "dict_nation")
public class Nation extends BaseEntity<Long> {

	private static final long serialVersionUID = 3535761977387920948L;
	@NotNull(message = "{not.null}")
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
