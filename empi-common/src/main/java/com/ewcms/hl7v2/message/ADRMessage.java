package com.ewcms.hl7v2.message;

import java.io.IOException;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.hl7v2.HL7Constants;
import com.ewcms.hl7v2.defined.MessageTriggerEvent;
import com.ewcms.hl7v2.model.ACKEntity;
import com.ewcms.hl7v2.model.ADREntity;
import com.ewcms.hl7v2.segment.AL1Segment;
import com.ewcms.hl7v2.segment.MSASegment;
import com.ewcms.hl7v2.segment.MSHSegment;
import com.ewcms.hl7v2.segment.NK1Segment;
import com.ewcms.hl7v2.segment.PIDSegment;
import com.ewcms.hl7v2.segment.PV1Segment;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.parser.Parser;

/**
 * 回复ADR消息(QRY消息请求)
 * 
 * @author wu_zhijun
 */
public class ADRMessage {

	private static HapiContext hapiContext = new DefaultHapiContext();
	private static String messageCode = MessageTriggerEvent.A19.getCode();
	private static String messageTriggerEvent = MessageTriggerEvent.A19.getTriggerEvent();

	/**
	 * 对象转化成ADR消息
	 * 
	 * @param adrEntity ADREntity对象 
	 * @return ADR消息
	 */
	public static String encode(ADREntity adrEntity) {
		ACKEntity ackEntity = new ACKEntity();
		ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());

		String result = "";
		try{
			PatientBaseInfo patientBaseInfo = adrEntity.getPatientBaseInfo();
			//String messageTriggerEvent = adrEntity.getMessageTriggerEvent();
			String version = adrEntity.getVersion();
			String processingId = adrEntity.getProcessingId();
			String practiceNo = adrEntity.getPracticeNo();
			Integer patientIdLen = adrEntity.getPatientIdLen();
			String receivingApplication = adrEntity.getReceivingApplication();
			String messageControlId = adrEntity.getMessageControlId();
			
			ackEntity.setMessageControlId(messageControlId);
			ackEntity.setProcessingId(processingId);
			ackEntity.setMessageTriggerEvent(messageTriggerEvent);
			ackEntity.setVersion(version);
			ackEntity.setReceivingApplication(receivingApplication);
			
			if (EmptyUtil.isNull(patientBaseInfo)){
				ackEntity.setTextMessage("传递的患者基本信息为空，请重新传递");
				return ADRMessage.encode(ackEntity);
			}
			
			if (EmptyUtil.isNull(practiceNo)){
				ackEntity.setTextMessage("传递的患者卡号为空，请重新传递");
				return ADRMessage.encode(ackEntity);
			}
			
			Parser parser = hapiContext.getPipeParser();
			if ("xml".equals(adrEntity.getStyle().toLowerCase())) {
				parser = hapiContext.getXMLParser();
			}

			AbstractMessage adr = null;

			AbstractSegment msh = null;
			AbstractSegment msa = null;
			AbstractSegment pid = null;
			AbstractSegment nk1 = null;
			AbstractSegment pv1 = null;
			AbstractSegment al1 = null;
			
			if (("2.1").equals(version) || ("v2.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v21.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				pv1 = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
			} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v22.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v23.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v231.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2().getAL1();
			} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v24.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v25.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v251.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v26.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			}
			
			if (EmptyUtil.isNotNull(msh)) {
				MSHSegment mshSegment = new MSHSegment(HL7Constants.SENDING_APPLICATION, adrEntity.getReceivingApplication());
				mshSegment.setMshSegment(msh);
			}
			
			if (EmptyUtil.isNotNull(msa)) {
				MSASegment masSegment = new MSASegment(AcknowledgmentCode.AA.name(), adrEntity.getMessageControlId(), "查询成功");
				masSegment.setMsaSegment(msa);
			}
				
			if (EmptyUtil.isNotNull(pid)) {
				PIDSegment pidSegment = new PIDSegment(patientBaseInfo, practiceNo, patientIdLen);
				pidSegment.setPidSegment(pid);
			}
			
			if (EmptyUtil.isNotNull(nk1)) {
				NK1Segment nk1Segment = new NK1Segment(patientBaseInfo.getContactName(), patientBaseInfo.getContactRelation(), patientBaseInfo.getContactAddress(), patientBaseInfo.getContactTelephone());
				nk1Segment.setNk1Segment(nk1);
			}
			
			if (EmptyUtil.isNotNull(pv1)){
				PV1Segment pv1Segment = new PV1Segment(patientBaseInfo.getPatientType());
				pv1Segment.setPv1Segment(pv1);
			}
			
			if (EmptyUtil.isNotNull(al1)) {
				AL1Segment al1Segment = new AL1Segment(patientBaseInfo.getAllergyHistory());
				al1Segment.setAl1Segment(al1);
			}
			
			result = parser.encode(adr);
		} catch (HL7Exception e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		} catch (IOException e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		} catch (Exception e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		}
		return result;
	}
	
	/**
	 * QRY消息处理完成后使用此应答回复ADR消息
	 * 
	 * @param ackEntity ADREntity对象 
	 * @return ADR消息
	 * @return
	 */
	public static String encode(ACKEntity ackEntity) {
		Parser parser = hapiContext.getPipeParser();
		if ("xml".equals(ackEntity.getStyle().toLowerCase())) {
			parser = hapiContext.getXMLParser();
		}

		AbstractMessage adr = null;

		AbstractSegment msh = null;
		AbstractSegment msa = null;

		String result = "";
		try{
			String version = ackEntity.getVersion();
			String processingId = ackEntity.getProcessingId();
			if (("2.1").equals(version) || ("v2.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v21.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getMSA();
			} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v22.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getMSA();
			} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v23.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getMSA();
			} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v231.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getMSA();
			} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v24.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getMSA();
			} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v25.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getMSA();
			} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v251.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getMSA();
			} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v26.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getMSA();
			}
			
			if (EmptyUtil.isNotNull(msh)) {
				MSHSegment mshSegment = new MSHSegment(HL7Constants.SENDING_APPLICATION, ackEntity.getReceivingApplication());
				mshSegment.setMshSegment(msh);
			}
			if (EmptyUtil.isNotNull(msa)) {
				MSASegment msaSegment = new MSASegment(ackEntity.getAcknowledgmentCode(), ackEntity.getMessageControlId(), ackEntity.getTextMessage());
				msaSegment.setMsaSegment(msa);
			}
			
			result = parser.encode(adr);
		} catch (HL7Exception e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		} catch (IOException e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		} catch (Exception e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		}
		return result;
	}
}
