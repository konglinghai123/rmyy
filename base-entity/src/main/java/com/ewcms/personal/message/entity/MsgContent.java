package com.ewcms.personal.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseEntity;

/**
 * 信息内容
 * 
 * <ul>
 * <li>title:标题</li>
 * <li>detail:内容</li>
 * </ul>
 * 
 * @author wu_zhijun
 * 
 */
@Entity
@Table(name = "per_message_content")
public class MsgContent extends BaseEntity<Long> {

	private static final long serialVersionUID = 8153404292985294025L;

	@Column(name = "title", nullable = false, length = 200)
	private String title;
	@Column(name = "detail", columnDefinition = "text")
	private String detail;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
