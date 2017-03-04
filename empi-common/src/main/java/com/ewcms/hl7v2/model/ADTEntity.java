package com.ewcms.hl7v2.model;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

/**
 * ADT实体
 *
 * <ul>
 * <li>patientBaseInfo:患者基本信息对象
 * </ul>
 * 
 * @author wu_zhijun
 */
public class ADTEntity extends AbstractEntity {

	private PatientBaseInfo patientBaseInfo;
	private String practiceNo;
	private Integer patientIdLen;
	
	
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
}
