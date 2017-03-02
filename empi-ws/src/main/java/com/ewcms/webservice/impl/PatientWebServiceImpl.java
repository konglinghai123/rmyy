package com.ewcms.webservice.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.hl7v2.AcknowledgmentCode;
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
import com.ewcms.hl7v2.MessageTriggerEvent;
import com.ewcms.hl7v2.message.ACKUtil;
import com.ewcms.hl7v2.message.ADRUtil;
import com.ewcms.hl7v2.message.ADTUtil;
import com.ewcms.hl7v2.message.QRYUtil;
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
	public String queryPatient(String practiceNo) {
		PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
		return practiceCardIndex.getPatientId();
	}
	
	@Override
	public String combinationQRY(String practiceNo, String version, String processingId, String style) {
		String hl7Result = "";
		try {
			hl7Result = QRYUtil.encode(practiceNo, version, processingId, style);
		} catch (HL7Exception | IOException e) {
			e.printStackTrace();
		}
		return hl7Result;
	}
	
	@Override
	public String queryPatientToHl7(String qryMessage, String version, String processingId, String style) {
		ADRUtil adrUtil = new ADRUtil();

		String practiceNo = "";
		try {
			practiceNo = QRYUtil.parser(qryMessage, version, style);
		} catch (HL7Exception e) {
			return adrUtil.encode(MessageTriggerEvent.A19.getCode(), processingId, version, style, AcknowledgmentCode.AE.name(), "解析HL7错误");
		}
		if (EmptyUtil.isStringEmpty(practiceNo)) return adrUtil.encode(MessageTriggerEvent.A19.getCode(), processingId, version, style, AcknowledgmentCode.AE.name(), "患者卡号为空");
		PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
		if (EmptyUtil.isNull(practiceCardIndex)) return adrUtil.encode(MessageTriggerEvent.A19.getCode(), processingId, version, style, AcknowledgmentCode.AE.name(), "患者唯一索引号不存在，必须先进行注册");
		PatientBaseInfo patientBaseInfo = patientBaseInfoService.findOne(practiceCardIndex.getPatientBaseInfoId());
		if (EmptyUtil.isNull(patientBaseInfo)) return adrUtil.encode(MessageTriggerEvent.A19.getCode(), processingId, version, style, AcknowledgmentCode.AE.name(), "患者基本信息不存在，必须先进行注册");
		
		Integer patientIdLen = parameterSetService.findPatientIdVariableValue();
		
		return adrUtil.encode(patientBaseInfo, practiceNo, patientIdLen, processingId, version, style);
	}
	
	@Override
	public String registerPatient(String adtMessage, String version, String style) {
		ACKUtil ackUtil = new ACKUtil();
		
		String patientIdStr = null;
		String code = AcknowledgmentCode.AA.name();
		String textMessage = "注册成功";
		try {
			PatientBaseInfo patientBaseInfo = ADTUtil.parser(adtMessage, version, style);
			
			String practiceNo = patientBaseInfo.getPracticeNo();
			if (EmptyUtil.isStringEmpty(practiceNo)) ackUtil.encode(MessageTriggerEvent.A04.getTriggerEvent(), "P", version, style, AcknowledgmentCode.AE.name(), "患者卡号不能为空");
			
			PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
			
			if (EmptyUtil.isNull(practiceCardIndex)){//未注册
				Integer patientIdLength = parameterSetService.findPatientIdVariableValue();
				List<PatientBaseInfo> list = patientBaseInfoService.match(patientBaseInfo);
				
				Long mathPatientId = null;
				if (EmptyUtil.isCollectionNotEmpty(list)){
					PatientBaseInfo mathPatientBaseInfo = list.get(0);
					mathPatientId = mathPatientBaseInfo.getId();
				} else {
					patientBaseInfo = patientBaseInfoService.saveAndFlush(patientBaseInfo);
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
				
				code = AcknowledgmentCode.AA.name();
			}
			
			//MessageLog messageLog = new MessageLog();
			//messageLog.setHapiOperate(HapiOperate.receive);
			//messageLog.setPracticeNo(practiceNo);
			//messageLog.setReceiveDate(new Date());
			
			//messageLogService.save(messageLog);
			
			textMessage = patientIdStr;
		} catch (HL7Exception e) {
			code = AcknowledgmentCode.AE.name();
			textMessage = "HL7格式错误";
		} catch (Exception e) {
			code = AcknowledgmentCode.AE.name();
			textMessage = "系统错误";
		}
		return ackUtil.encode(MessageTriggerEvent.A04.getTriggerEvent(), "P", version, style, code, textMessage);
	}
}
