package com.ewcms.empi.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.ewcms.common.entity.BaseEntity;

/**
 * 证件类型
 * <ul>
 * <li>name:名称</li>
 * </ul>
 *@author zhoudongchu
 */
@Entity
@Table(name = "dict_certificate_type", uniqueConstraints = { @UniqueConstraint(name = "unique_dict_certificate_type_name", columnNames = "name") })
public class CertificateType extends BaseEntity<Long> {
	private static final long serialVersionUID = -3115806945777814138L;
	@NotNull(message = "{not.null}")
    @Column(name = "name", nullable = false)
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
