package com.ewcms.empi.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ewcms.common.entity.AbstractEntity;

/**
 * 婚姻状况字典库
 * 
 * <ul>
 * <li>id：关键值</li>
 * <li>enName：英文名</li>
 * <li>cnName：中文名</li>
 * </ul>
 * 
 *@author zhoudongchu
 */
@Entity
@Table(name = "dict_marital")
public class Marital extends AbstractEntity<String> {
	private static final long serialVersionUID = -3045764890210023009L;
	
	@Id
    private String id;
	@NotNull(message = "{not.null}")
    @Column(name = "en_name", nullable = false)
	private String enName;
	@NotNull(message = "{not.null}")
    @Column(name = "cn_name", nullable = false)
	private String cnName;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id  = id;

	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
}
