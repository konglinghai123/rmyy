package com.ewcms.webservice.impl;

import java.io.IOException;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.hl7v2.HL7Exception;

import com.ewcms.WebServiceConstants;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.hl7.message.v2.PatientMessage;
import com.ewcms.service.PracticeCardService;
import com.ewcms.webservice.IPatientWebService;

/**
 *
 * @author wu_zhijun
 */
@Service("patientWebService")
@WebService(endpointInterface = WebServiceConstants.PATIENT_ENDPOINTINTERFACE, targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE)
public class PatientWebServiceImpl implements IPatientWebService {

	private Integer patientIdLen = 20;
	
	@Autowired
	private PracticeCardService practiceCardService;
	
	@Override
	public Long findPatientId(String practiceNo, Boolean deleted) {
		return practiceCardService.findPatientIdByPracticeNo(practiceNo, deleted);
	}

	@Override
	public String findPatientIdHl7v2(String practiceNo, Boolean deleted, String version) {
		PatientBaseInfo patientBaseInfo = practiceCardService.findPatientBaseInfoByPracticeNo(practiceNo, deleted);
		try {
			return PatientMessage.createHl7v2(patientBaseInfo, practiceNo, version, patientIdLen);
		} catch (HL7Exception e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	private Integer getPatientIdLen(){
		return patientIdLen;
	}
}
