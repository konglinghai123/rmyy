package com.ewcms.personal.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 消息内容
 * 
 * <ul>
 * <li>message:消息对象</li>
 * <li>content:内容</li>
 * </ul>
 *
 * @author wu_zhijun
 */
@Entity
@Table(name = "per_message_content")
@SequenceGenerator(name="seq", sequenceName="seq_per_message_content_id", allocationSize = 1)
@Proxy(lazy = true, proxyClass = MessageContent.class)
public class MessageContent extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 870888206182721829L;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Message message;
	@Length(min = 5, max = 50000, message = "{length.not.valid}")
	@Column(name = "content")
	private String content;

	@JSONField(serialize = false)
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
