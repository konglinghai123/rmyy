package com.ewcms.hl7v2.model;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

/**
 *
 * @author wu_zhijun
 */
public class ADTEntity {

	private String receivingApplication;
	private String messageControlId;
	private PatientBaseInfo patientBaseInfo;

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

	public PatientBaseInfo getPatientBaseInfo() {
		return patientBaseInfo;
	}

	public void setPatientBaseInfo(PatientBaseInfo patientBaseInfo) {
		this.patientBaseInfo = patientBaseInfo;
	}

}
