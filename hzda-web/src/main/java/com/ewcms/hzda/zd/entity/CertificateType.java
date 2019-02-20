package com.ewcms.hzda.zd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 证件类型字典库
 * 
 * <ul>
 * <li>name:名称</li>
 * <li>length:长度</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_zd_certificate_type", uniqueConstraints = {
		@UniqueConstraint(name = "unique_hzda_zd_certificate_type_name", columnNames = "name") })
@SequenceGenerator(name = "seq", sequenceName = "seq_hzda_zd_certificate_type_id", allocationSize = 1)
public class CertificateType extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -6419080568466719714L;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "length", nullable = false)
	private Long length;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}
}
