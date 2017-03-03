package com.ewcms.hl7v2.model;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

/**
 * 
 * <li>patientBaseInfo:患者基本信息对象</li>
 * <li>practiceNo:患者卡号</li>
 * <li>patientIdLen:患者唯一索引号长度</li>
 * <li>processingId:消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)</li>
 * <li>version:版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)</li>
 * <li>style:HL7使用样式(xml或er7)</li>
 * <li>receivingApplication:接收应用名称</li>
 * <li>messageControlId:消息控制ID号</li>
 * 
 * @author wu_zhijun
 */
public class ADREntity {

	private PatientBaseInfo patientBaseInfo;
	private String practiceNo;
	private Integer patientIdLen;
	private String processingId = "P";
	private String version = "v2.4";
	private String style = "er7";
	private String receivingApplication;
	private String messageControlId;

	public PatientBaseInfo getPatientBaseInfo() {
		return patientBaseInfo;
	}

	public void setPatientBaseInfo(PatientBaseInfo patientBaseInfo) {
		this.patientBaseInfo = patientBaseInfo;
	}

	public String getPracticeNo() {
		return practiceNo;
	}

	public void setPracticeNo(String practiceNo) {
		this.practiceNo = practiceNo;
	}

	public Integer getPatientIdLen() {
		return patientIdLen;
	}

	public void setPatientIdLen(Integer patientIdLen) {
		this.patientIdLen = patientIdLen;
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
