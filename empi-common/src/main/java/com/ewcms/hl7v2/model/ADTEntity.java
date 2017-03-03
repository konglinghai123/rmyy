package com.ewcms.hl7v2.model;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

/**
 *
 * @author wu_zhijun
 */
public class ADTEntity {

	private String receivingApplication;
	private PatientBaseInfo patientBaseInfo;

	public String getReceivingApplication() {
		return receivingApplication;
	}

	public void setReceivingApplication(String receivingApplication) {
		this.receivingApplication = receivingApplication;
	}

	public PatientBaseInfo getPatientBaseInfo() {
		return patientBaseInfo;
	}

	public void setPatientBaseInfo(PatientBaseInfo patientBaseInfo) {
		this.patientBaseInfo = patientBaseInfo;
	}

}
