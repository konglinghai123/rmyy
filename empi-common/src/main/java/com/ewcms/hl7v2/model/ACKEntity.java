package com.ewcms.hl7v2.model;

/**
 * <li>messageTriggerEvent:消息触发事件插入MSG-9-2. 例如: "A01"</li>
 * <li>processingId:消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)</li>
 * <li>version:版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)</li>
 * <li>style:HL7使用样式(xml或er7)</li>
 * <li>acknowledgementCode:AcknowledgmentCode枚举对象值</li>
 * <li>textMessage:返回消息内容</li>
 * <li>receivingApplication:接收应用名称</li>
 * <li>messageControlId:消息控制ID号</li>
 *
 * @author wu_zhijun
 */
public class ACKEntity {

	private String messageTriggerEvent;
	private String processingId = "P";
	private String version = "v2.4";
	private String style = "er7";
	private String acknowledgmentCode = "AA";
	private String textMessage = "";
	private String receivingApplication = "";
	private String messageControlId;

	public String getMessageTriggerEvent() {
		return messageTriggerEvent;
	}

	public void setMessageTriggerEvent(String messageTriggerEvent) {
		this.messageTriggerEvent = messageTriggerEvent;
	}

	public String getProcessingId() {
		return processingId;
	}

	public void setProcessingId(String processingId) {
		this.processingId = processingId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

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

	public String getReceivingApplication() {
		return receivingApplication;
	}

	public void setReceivingApplication(String receivingApplication) {
		this.receivingApplication = receivingApplication;
	}

	public String getMessageControlId() {
		return messageControlId;
	}

	public void setMessageControlId(String messageControlId) {
		this.messageControlId = messageControlId;
	}

}
