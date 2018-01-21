package com.ewcms.personal.message.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseEntity;
import com.google.common.collect.Sets;

/**
 * 系统消息 1.收件箱和发件箱内的消息默认265天后被放入垃圾箱 2.垃圾箱内的消息30天后自动物理删除
 * 3.垃圾箱内的消息只有当收件人和发件人把消息都从垃圾箱中删除后才能物理删除 4.收藏箱的不能删除 <br/>
 * 如果type==system_message_all表示是发给所有人的消息，策略如下：
 * 1.首先在展示时（第一页），会自动查所有的system_message_all
 * 2.如果用户阅读了，直接复制一份，放入它的收件箱，状态改为system_message <br/>
 * 如果消息是草稿，那么收件人状态是null
 * 
 * <ul>
 * <li>senderId:消息发送者Id</li>
 * <li>receiverId:消息接收者Id</li>
 * <li>sendDate:消息发送时间</li>
 * <li>title:标题</li>
 * <li>contents:内容对象集合</li>
 * <li>senderState:发件人状态</li> 
 * <li>senderStateChangeDate:发件人状态改变时间</li>
 * <li>receiverState:收件人状态</li> 
 * <li>receiverStateChangeDate:收件人状态改变时间</li>
 * <li>type:消息类型</li>
 * <li>read:是否已读</li>
 * <li>replied:是否已回复</li>
 * <li>parentId:父编号</li>
 * <li>parentIds:父消息编号列表,如1/2/3/4</li>
 * </ul>
 *
 * @author wu_zhijun
 */
@Entity
@Table(name = "per_message")
@Proxy(lazy = true, proxyClass = Message.class)
public class Message extends BaseEntity<Long> {

	private static final long serialVersionUID = -1873113419135381658L;

	@Column(name = "sender_id")
	private Long senderId;
	@Formula(value = "(select u.user_name + case when u.real_name is not null then ('(' + u.real_name + ')') else '' end from sec_user u where u.id=sender_id)")
	private String senderUsername;
	@Column(name = "receiver_id")
	private Long receiverId;
	@Formula(value = "(select u.user_name + case when u.real_name is not null then ('(' + u.real_name + ')') else '' end from sec_user u where u.id=receiver_id)")
	private String receiverUsername;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "send_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDate;
	@Length(min = 5, max = 200, message = "{length.not.valid}")
	@Column(name = "title")
	private String title;
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "message", orphanRemoval = true)
	private Set<MessageContent> contents;
	@Column(name = "sender_state")
	@Enumerated(EnumType.STRING)
	private MessageState senderState = MessageState.out_box;
	@Column(name = "sender_state_chanege_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date senderStateChangeDate;
	@Column(name = "receiver_state")
	@Enumerated(EnumType.STRING)
	private MessageState receiverState = MessageState.in_box;
	@Column(name = "receiver_state_change_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date receiverStateChangeDate;
	@Enumerated(EnumType.STRING)
	private MessageType type = MessageType.user_message;
	@Column(name = "is_read")
	private Boolean read = Boolean.FALSE;
	@Column(name = "is_replied")
	private Boolean replied = Boolean.FALSE;
	@Column(name = "parent_id")
	private Long parentId;
	@Column(name = "parent_ids")
	private String parentIds;

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	
	public String getSenderUsername(){
		return senderUsername;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	
	public String getReceiverUsername(){
		return receiverUsername;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JSONField(serialize = false)
	public MessageContent getContent() {
		if (contents != null && contents.size() > 0){
			return contents.iterator().next();
		}
		return null;
	}

	public void setContent(MessageContent content) {
		if (contents == null){
			contents = Sets.newHashSet();
		}
		contents.add(content);
	}

	public MessageState getSenderState() {
		return senderState;
	}

	public void setSenderState(MessageState senderState) {
		this.senderState = senderState;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getSenderStateChangeDate() {
		return senderStateChangeDate;
	}

	public void setSenderStateChangeDate(Date senderStateChangeDate) {
		this.senderStateChangeDate = senderStateChangeDate;
	}

	public MessageState getReceiverState() {
		return receiverState;
	}

	public void setReceiverState(MessageState receiverState) {
		this.receiverState = receiverState;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getReceiverStateChangeDate() {
		return receiverStateChangeDate;
	}

	public void setReceiverStateChangeDate(Date receiverStateChangeDate) {
		this.receiverStateChangeDate = receiverStateChangeDate;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getReplied() {
		return replied;
	}

	public void setReplied(Boolean replied) {
		this.replied = replied;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String makeSelfAsParentIds(){
		return (getParentIds() != null ? getParentIds() : "") + getId() + "/";
	}
}
