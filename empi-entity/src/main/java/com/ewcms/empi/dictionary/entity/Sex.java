package com.ewcms.empi.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ewcms.common.entity.AbstractEntity;

/**
 * 性别字典库
 * 
 * <ul>
 * <li>id：关键值</li>
 * <li>enName：英文名</li>
 * <li>cnName：中文名</li>
 * </ul>
 *@author zhoudongchu
 */

@Entity
@Table(name = "dict_sex")
public class Sex extends AbstractEntity<String> {
	private static final long serialVersionUID = -3924181967935382194L;
	
	@Id
    private String id;
	@NotNull(message = "{not.null}")
    @Column(name = "en_name", nullable = false)
	private String enName;
	@NotNull(message = "{not.null}")
    @Column(name = "cn_name", nullable = false)
	private String cnName;
	

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
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id  = id;

	}
}
