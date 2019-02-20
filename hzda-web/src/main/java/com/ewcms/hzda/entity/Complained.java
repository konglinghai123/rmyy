package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 主诉
 * 
 * <ul>
 * <li>content:内容</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_complained")
@SequenceGenerator(name = "seq", sequenceName = "hzda_complained_id", allocationSize = 1)
public class Complained extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -9150592695635962934L;
	
	@Column(name = "conent", columnDefinition = "text")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
