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
		try {
			QRYEntity qryEntity = QRYUtil.parser(qryMessage, version, style);
			practiceNo = qryEntity.getPracticeNo();
			receivingApplication = qryEntity.getReceivingApplication();
		} catch (HL7Exception e) {
			return ADRUtil.encode(MessageTriggerEvent.A19.getCode(), processingId, version, style, AcknowledgmentCode.AE.name(), "解析HL7错误", receivingApplication);
		}
		if (EmptyUtil.isStringEmpty(practiceNo)) return ADRUtil.encode(MessageTriggerEvent.A19.getCode(), processingId, version, style, AcknowledgmentCode.AE.name(), "患者卡号为空", receivingApplication);
		PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
		if (EmptyUtil.isNull(practiceCardIndex)) return ADRUtil.encode(MessageTriggerEvent.A19.getCode(), processingId, version, style, AcknowledgmentCode.AE.name(), "患者唯一索引号不存在，必须先进行注册", receivingApplication);
		PatientBaseInfo patientBaseInfo = patientBaseInfoService.findOne(practiceCardIndex.getPatientBaseInfoId());
		if (EmptyUtil.isNull(patientBaseInfo)) return ADRUtil.encode(MessageTriggerEvent.A19.getCode(), processingId, version, style, AcknowledgmentCode.AE.name(), "患者基本信息不存在，必须先进行注册", receivingApplication);
		
		Integer patientIdLen = parameterSetService.findPatientIdVariableValue();
		
		return ADRUtil.encode(patientBaseInfo, practiceNo, patientIdLen, processingId, version, style, receivingApplication);
	}
	
	@Override
	public String registerPatient(String adtMessage, String version, String processingId, String style) {
		String patientIdStr = null;
		String code = AcknowledgmentCode.AA.name();
		String textMessage = "注册成功";
		
		PatientBaseInfo patientBaseInfo = null;
		String receivingApplication = "";
		try {
			ADTEntity adtEntity = ADTUtil.parser(adtMessage, version, style);
			patientBaseInfo = adtEntity.getPatientBaseInfo();
			receivingApplication = adtEntity.getReceivingApplication();
			
			String practiceNo = patientBaseInfo.getPracticeNo();
			if (EmptyUtil.isStringEmpty(practiceNo)) ACKUtil.encode(MessageTriggerEvent.A04.getTriggerEvent(), processingId, version, style, AcknowledgmentCode.AE.name(), "患者卡号不能为空", receivingApplication);
			
			PracticeCardIndex practiceCardIndex = practiceCardIndexService.findByIdAndDeleted(practiceNo, Boolean.FALSE);
			
			if (EmptyUtil.isNull(practiceCardIndex)){//未注册
				PracticeCard practiceCard = practiceCardService.register(practiceNo, patientBaseInfo);
				patientIdStr = practiceCard.getPatientBaseInfo().getPatientId();
			} else {//已存在
				patientIdStr = practiceCardIndex.getPatientId();
			}
			textMessage = patientIdStr;
		} catch (HL7Exception e) {
			code = AcknowledgmentCode.AE.name();
			textMessage = "HL7格式错误";
		} catch (Exception e) {
			code = AcknowledgmentCode.AE.name();
			textMessage = "系统错误";
		}
		return ACKUtil.encode(MessageTriggerEvent.A04.getTriggerEvent(), processingId, version, style, code, textMessage, receivingApplication);
	}
}
