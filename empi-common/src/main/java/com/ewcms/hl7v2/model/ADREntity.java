package com.ewcms.hl7v2.model;

/**
 * ADR实体
 * 
 * <li>acknowledgementCode:AcknowledgmentCode枚举对象值</li>
 * <li>textMessage:返回消息内容</li>
 * 
 * @author wu_zhijun
 */
public class ADREntity extends ADTEntity{
	
	private String acknowledgmentCode = "AA";
	private String textMessage = "";

	public String getAcknowledgmentCode() {
		return acknowledgmentCode;
	}

	public void setAcknowledgmentCode(String acknowledgmentCode) {
		this.acknowledgmentCode = acknowledgmentCode;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
}
