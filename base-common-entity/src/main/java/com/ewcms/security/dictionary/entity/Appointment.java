package com.ewcms.security.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 聘任
 * <ul>
 * <li>name:名称</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "sec_appointment")
@SequenceGenerator(name="seq", sequenceName="seq_sec_appointment_id", allocationSize = 1)
public class Appointment extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -2177707567798592264L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
