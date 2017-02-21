package com.ewcms.empi.card.manage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseEntity;

/**
 * hapi 消息操作日志
 * <ul>
 * <li>ip：ip地址</li>
 * <li>practiceNo：诊疗卡号</li>
 * <li>receiveDate：消息接收时间</li>
 * <li>sendDate:消息发送时间</li>
 * <li>hapiOperate:hapi操作</li>
 * </ul>
 *@author zhoudongchu
 */
@Entity
@Table(name = "card_message_log")
public class MessageLog extends BaseEntity<Long> {
	private static final long serialVersionUID = 3941590345850460460L;
	@Column(name = "ip")
	private String ip;
	@Column(name = "practice_no")
	private String practiceNo;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "receive_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date receiveDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "send_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date sendDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "hapi_operate")
	private HapiOperate hapiOperate = HapiOperate.receive;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPracticeNo() {
		return practiceNo;
	}
	public void setPracticeNo(String practiceNo) {
		this.practiceNo = practiceNo;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public HapiOperate getHapiOperate() {
		return hapiOperate;
	}
	public void setHapiOperate(HapiOperate hapiOperate) {
		this.hapiOperate = hapiOperate;
	}	
	public String getHapiOperateInfo(){
		return hapiOperate == null ? HapiOperate.receive.getInfo() : hapiOperate.getInfo();
	}	
}
