package com.ewcms.webservice.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.HL7Exception;

import com.ewcms.WebServiceConstants;
import com.ewcms.common.utils.EmptyUtil;
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
import com.ewcms.hl7v2.model.ACKEntity;
import com.ewcms.hl7v2.model.ADREntity;
import com.ewcms.hl7v2.model.ADTEntity;
import com.ewcms.hl7v2.model.QRYEntity;
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
	public String compositePracticeNo(String practiceNo, String version, String processingId, String style) {
		return QRYUtil.encode(practiceNo, version, processingId, style);
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
			QRYEntity qryEntity = QRYUtil.parser(qryMessage, version, style);
			practiceNo = qryEntity.getPracticeNo();
			receivingApplication = qryEntity.getReceivingApplication();
			messageControlId = qryEntity.getMessageControlId();

			ackEntity.setReceivingApplication(receivingApplication);
			ackEntity.setMessageControlId(messageControlId);
		} catch (HL7Exception e) {
			ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
			ackEntity.setTextMessage("解析HL7错误");
			
			return ADRUtil.encode(ackEntity);
		}
		
		if (EmptyUtil.isStringEmpty(practiceNo)) {
			ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
			ackEntity.setTextMessage("患者卡号为空");
			
			return ADRUtil.encode(ackEntity);
		}
		
		PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
		if (EmptyUtil.isNull(practiceCardIndex)) {
			ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
			ackEntity.setTextMessage("患者唯一索引号不存在，必须先进行注册");
			
			return ADRUtil.encode(ackEntity);
		}
		
		PatientBaseInfo patientBaseInfo = patientBaseInfoService.findOne(practiceCardIndex.getPatientBaseInfoId());
		if (EmptyUtil.isNull(patientBaseInfo)) {
			ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
			ackEntity.setTextMessage("患者基本信息不存在，必须先进行注册");
			
			return ADRUtil.encode(ackEntity);
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
		
		return ADRUtil.encode(adrEntity);
	}
	
	@Override
	public String registerPatient(String adtMessage, String version, String processingId, String style) {
		String acknowledgmentCode = AcknowledgmentCode.AA.name();
		String textMessage = "注册成功";
		
		PatientBaseInfo patientBaseInfo = null;
		String receivingApplication = "";
		String messageControlId = "";
		
		try {
			ADTEntity adtEntity = ADTUtil.parser(adtMessage, version, style);
			patientBaseInfo = adtEntity.getPatientBaseInfo();
			receivingApplication = adtEntity.getReceivingApplication();
			messageControlId = adtEntity.getMessageControlId();
			
			String practiceNo = patientBaseInfo.getPracticeNo();
			if (EmptyUtil.isStringEmpty(practiceNo)) {
				acknowledgmentCode = AcknowledgmentCode.AE.name();
				textMessage = "患者卡号不能为空";
			} else {
				PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
				
				if (EmptyUtil.isNull(practiceCardIndex)){//未注册
					PracticeCard practiceCard = practiceCardService.register(practiceNo, patientBaseInfo);
					textMessage = practiceCard.getPatientBaseInfo().getPatientId();
				} else {//已存在
					textMessage = practiceCardIndex.getPatientId();
				}
			}
		} catch (HL7Exception e) {
			acknowledgmentCode = AcknowledgmentCode.AE.name();
			textMessage = "HL7格式错误";
		} catch (Exception e) {
			acknowledgmentCode = AcknowledgmentCode.AE.name();
			textMessage = "系统错误";
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
		
		return ACKUtil.encode(ackEntity);
	}
}
