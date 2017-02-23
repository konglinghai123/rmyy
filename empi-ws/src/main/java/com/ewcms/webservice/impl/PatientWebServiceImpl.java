package com.ewcms.webservice.impl;

import java.io.IOException;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.hl7v2.HL7Exception;

import com.ewcms.WebServiceConstants;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.PracticeCardIndex;
import com.ewcms.empi.card.manage.service.PracticeCardIndexService;
import com.ewcms.empi.card.manage.service.PracticeCardService;
import com.ewcms.empi.system.service.ParameterSetService;
import com.ewcms.hl7.message.v2.Hl7v2Parser;
import com.ewcms.hl7.message.v2.PatientMessage;
import com.ewcms.webservice.IPatientWebService;

/**
 *
 * @author wu_zhijun
 */
@Service("patientWebService")
@WebService(endpointInterface = WebServiceConstants.PATIENT_ENDPOINTINTERFACE, targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE)
public class PatientWebServiceImpl implements IPatientWebService {

	@Autowired
	private PracticeCardService practiceCardService;
	@Autowired
	private PracticeCardIndexService practiceCardIndexService;
	@Autowired
	private ParameterSetService parameterSetService;
	
	@Override
	public String findPatientId(String practiceNo) {
		PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
		return practiceCardIndex.getPatientId();
	}
	
	@Override
	public String doPatientIdToHl7(String hl7Message) {
		try {
			PatientBaseInfo patientBaseInfo = Hl7v2Parser.v24Parser(hl7Message);
			String patientId = patientBaseInfo.getPatientId();
			
			String practiceNo = patientBaseInfo.getPracticeNo();
			if (EmptyUtil.isStringEmpty(practiceNo)) return null;
			
			PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
			
			if (EmptyUtil.isNull(practiceCardIndex)){
				
			} else {
				
			}
			
		} catch (HL7Exception e) {
			// TODO Auto-generated catch block
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
	}
	
	@Override
	public String findPatientIdHl7v2(String practiceNo, String version) {
		PatientBaseInfo patientBaseInfo = practiceCardService.findPatientBaseInfoByPracticeNo(practiceNo, Boolean.FALSE);
		try {
			return PatientMessage.createHl7v2(patientBaseInfo, practiceNo, version, findPracticeIdLengthValue());
		} catch (HL7Exception e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	private Integer findPracticeIdLengthValue(){
		return parameterSetService.findPracticeIdLengthValue();
	}


}
