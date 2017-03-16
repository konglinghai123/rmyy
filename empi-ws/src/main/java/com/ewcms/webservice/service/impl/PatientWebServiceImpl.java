package com.ewcms.webservice.service.impl;

import java.util.Date;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.HL7Exception;

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
import com.ewcms.hl7v2.defined.MessageTriggerEvent;
import com.ewcms.hl7v2.message.ACKMessage;
import com.ewcms.hl7v2.message.ADRMessage;
import com.ewcms.hl7v2.message.ADTMessage;
import com.ewcms.hl7v2.message.QRYMessage;
import com.ewcms.hl7v2.model.ACKEntity;
import com.ewcms.hl7v2.model.ADREntity;
import com.ewcms.hl7v2.model.ADTEntity;
import com.ewcms.hl7v2.model.QRYEntity;
import com.ewcms.webservice.WebServiceConstants;
import com.ewcms.webservice.service.IPatientWebService;
import com.ewcms.webservice.util.WebServiceUtil;

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
	public String compositePracticeNo(String practiceNo, String version, String processingId, String style) {
		QRYEntity qryEntity = new QRYEntity();
		
		qryEntity.setPracticeNo(practiceNo);
		qryEntity.setVersion(version);
		qryEntity.setProcessingId(processingId);
		qryEntity.setStyle(style);
		
		WebServiceUtil.log("compositePracticeNo", "success");
		
		return QRYMessage.encode(qryEntity);
	}
	
	@Override
	public String queryPatient(String qryMessage, String version, String processingId, String style) {
		String practiceNo = "";
		String receivingApplication = "";
		String messageControlId = "";

		ACKEntity ackEntity = new ACKEntity();

		ackEntity.setMessageTriggerEvent(MessageTriggerEvent.A19.getCode());
		ackEntity.setProcessingId(processingId);
		ackEntity.setVersion(version);
		ackEntity.setStyle(style);
		
		try {
			QRYEntity qryEntity = QRYMessage.parser(qryMessage, version, style);
			practiceNo = qryEntity.getPracticeNo();
			receivingApplication = qryEntity.getReceivingApplication();
			messageControlId = qryEntity.getMessageControlId();

			ackEntity.setReceivingApplication(receivingApplication);
			ackEntity.setMessageControlId(messageControlId);
		} catch (HL7Exception e) {			
			ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
			ackEntity.setTextMessage("解析HL7错误");
			
			WebServiceUtil.log("queryPatient", "HL7 encode error : {}", e.toString());
			
			return ADRMessage.encode(ackEntity);
		}
		
		if (EmptyUtil.isStringEmpty(practiceNo)) {			
			ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
			ackEntity.setTextMessage("患者卡号为空");
			
			WebServiceUtil.log("queryPatient", "practiceNo is empty");
			
			return ADRMessage.encode(ackEntity);
		}
		
		PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
		if (EmptyUtil.isNull(practiceCardIndex)) {
			ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
			ackEntity.setTextMessage("患者唯一索引号不存在，必须先进行注册");
			
			WebServiceUtil.log("queryPatient", "PracticeCardIndex object is empty");
			
			return ADRMessage.encode(ackEntity);
		}
		
		PatientBaseInfo patientBaseInfo = patientBaseInfoService.findOne(practiceCardIndex.getPatientBaseInfoId());
		if (EmptyUtil.isNull(patientBaseInfo)) {
			ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
			ackEntity.setTextMessage("患者基本信息不存在，必须先进行注册");
			
			WebServiceUtil.log("queryPatient", "PatientBaseInfo object is empty");
			
			return ADRMessage.encode(ackEntity);
		}
		
		Integer patientIdLen = parameterSetService.findPatientIdVariableValue();
		
		ADREntity adrEntity = new ADREntity();
		
		adrEntity.setPatientBaseInfo(patientBaseInfo);
		adrEntity.setPracticeNo(practiceNo);
		adrEntity.setPatientIdLen(patientIdLen);
		adrEntity.setProcessingId(processingId);
		adrEntity.setVersion(version);
		adrEntity.setStyle(style);
		adrEntity.setReceivingApplication(receivingApplication);
		adrEntity.setMessageControlId(messageControlId);
		
		WebServiceUtil.log("queryPatient", "success");

		return ADRMessage.encode(adrEntity);
	}
	
	@Override
	public String registerPatient(String adtMessage, String version, String processingId, String style) {
		String acknowledgmentCode = AcknowledgmentCode.AA.name();
		String textMessage = "注册成功";
		
		PatientBaseInfo patientBaseInfo = null;
		String receivingApplication = "";
		String messageControlId = "";
		
		try {
			ADTEntity adtEntity = ADTMessage.parser(adtMessage, version, style);
			patientBaseInfo = adtEntity.getPatientBaseInfo();
			receivingApplication = adtEntity.getReceivingApplication();
			messageControlId = adtEntity.getMessageControlId();
			
			String practiceNo = patientBaseInfo.getPracticeNo();
			if (EmptyUtil.isStringEmpty(practiceNo)) {
				acknowledgmentCode = AcknowledgmentCode.AE.name();
				textMessage = "患者卡号不能为空";
				
				WebServiceUtil.log("registerPatient", "practiceNo is empty");
			} else {
				PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
				
				if (EmptyUtil.isNull(practiceCardIndex)){//未注册
					MessageLog messageLog = new MessageLog();
					messageLog.setIp(WebServiceUtil.getClientIpCxf());
					messageLog.setPracticeNo(practiceNo);
					messageLog.setHapiOperate(HapiOperate.receive);
					messageLog.setReceiveDate(new Date());
					
					PracticeCard practiceCard = practiceCardService.register(practiceNo, patientBaseInfo, messageLog);
					textMessage = practiceCard.getPatientBaseInfo().getPatientId();
					WebServiceUtil.log("registerPatient", "information register success");
				} else {//已存在
					textMessage = practiceCardIndex.getPatientId();
					WebServiceUtil.log("registerPatient", "information already exists");
				}
			}
		} catch (HL7Exception e) {
			acknowledgmentCode = AcknowledgmentCode.AE.name();
			textMessage = "HL7格式错误";
			WebServiceUtil.log("registerPatient", "HL7 encode error : {}", e.toString());
		} catch (Exception e) {
			acknowledgmentCode = AcknowledgmentCode.AE.name();
			textMessage = "系统错误";
			WebServiceUtil.log("registerPatient", "exception : {}", e.toString());
		}
		
		ACKEntity ackEntity = new ACKEntity();
		
		ackEntity.setMessageTriggerEvent(MessageTriggerEvent.A04.getTriggerEvent());
		ackEntity.setProcessingId(processingId);
		ackEntity.setVersion(version);
		ackEntity.setStyle(style);
		ackEntity.setAcknowledgmentCode(acknowledgmentCode);
		ackEntity.setTextMessage(textMessage);
		ackEntity.setReceivingApplication(receivingApplication);
		ackEntity.setMessageControlId(messageControlId);
		
		WebServiceUtil.log("registerPatient", "return ACK message");
		
		return ACKMessage.encode(ackEntity);
	}
}
