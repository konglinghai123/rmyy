package com.ewcms.webservice.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.hl7v2.HL7Exception;

import com.ewcms.WebServiceConstants;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.HapiOperate;
import com.ewcms.empi.card.manage.entity.MessageLog;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.entity.PracticeCardIndex;
import com.ewcms.empi.card.manage.service.MessageLogService;
import com.ewcms.empi.card.manage.service.PatientBaseInfoService;
import com.ewcms.empi.card.manage.service.PracticeCardIndexService;
import com.ewcms.empi.card.manage.service.PracticeCardService;
import com.ewcms.empi.system.service.ParameterSetService;
import com.ewcms.hl7v2.message.PatientMessage;
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
	private PatientBaseInfoService patientBaseInfoService;
	@Autowired
	private ParameterSetService parameterSetService;
	@Autowired
	private MessageLogService messageLogService;
	
	@Override
	public String findPatientId(String practiceNo) {
		PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
		return practiceCardIndex.getPatientId();
	}
	
	@Override
	public String doPatientIdToHl7(String hl7Message, String version) {
		String patientIdStr = null;
		try {
			PatientBaseInfo patientBaseInfo = PatientMessage.parserHl7v2(hl7Message, version);
			
			String practiceNo = patientBaseInfo.getPracticeNo();
			if (EmptyUtil.isStringEmpty(practiceNo)) return null;
			
			PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
			
			if (EmptyUtil.isNull(practiceCardIndex)){//未注册
				Integer patientIdLength = parameterSetService.findPatientIdVariableValue();
				List<PatientBaseInfo> list = patientBaseInfoService.match(patientBaseInfo);
				
				Long mathPatientId = null;
				if (EmptyUtil.isCollectionNotEmpty(list)){
					PatientBaseInfo mathPatientBaseInfo = list.get(0);
					mathPatientId = mathPatientBaseInfo.getId();
				} else {
					patientBaseInfo = patientBaseInfoService.save(patientBaseInfo);
					mathPatientId = patientBaseInfo.getId();
				}
				
				patientIdStr = String.format("%0" + patientIdLength + "d", mathPatientId);
				
				practiceCardIndex = new PracticeCardIndex();
				
				practiceCardIndex.setId(practiceNo);
				practiceCardIndex.setPatientBaseInfoId(mathPatientId);
				practiceCardIndex.setPatientId(patientIdStr);
				
				practiceCardIndexService.save(practiceCardIndex);
				
				PracticeCard practiceCard = new PracticeCard();
				practiceCard.setPatientBaseInfo(patientBaseInfo);
				practiceCard.setPracticeNo(practiceNo);
				
				practiceCardService.save(practiceCard);
			} else {//已存在
				patientIdStr = practiceCardIndex.getPatientId();
			}
			
			MessageLog messageLog = new MessageLog();
			messageLog.setHapiOperate(HapiOperate.receive);
			messageLog.setPracticeNo(practiceNo);
			messageLog.setReceiveDate(new Date());
			
			messageLogService.save(messageLog);
		} catch (HL7Exception e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return patientIdStr;
	}
	
	@Override
	public String findPatientIdHl7v2(String practiceNo, String version, String messageTriggerEvent, String processingId, String style) {
		PracticeCard practiceCard = practiceCardService.findByPracticeNoAndDeleted(practiceNo, Boolean.FALSE);
		try {
			if ("er7".equals(style.toLowerCase())){
				return PatientMessage.createHl7v2ER7(practiceCard.getPatientBaseInfo(), practiceNo, version, parameterSetService.findPatientIdVariableValue(), messageTriggerEvent, processingId);
				
			} else {
				return PatientMessage.createHl7v2XML(practiceCard.getPatientBaseInfo(), practiceNo, version, parameterSetService.findPatientIdVariableValue(), messageTriggerEvent, processingId);
			}
		} catch (HL7Exception e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}
