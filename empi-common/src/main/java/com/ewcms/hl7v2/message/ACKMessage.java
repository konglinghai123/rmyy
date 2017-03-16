package com.ewcms.hl7v2.message;

import java.io.IOException;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.hl7v2.HL7Constants;
import com.ewcms.hl7v2.model.ACKEntity;
import com.ewcms.hl7v2.segment.MSASegment;
import com.ewcms.hl7v2.segment.MSHSegment;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.parser.Parser;

/**
 * 回复ACK消息(ADT消息请求)
 *
 * @author wu_zhijun
 */
public class ACKMessage {

	private static HapiContext hapiContext = new DefaultHapiContext();

	private static String messageCode = "ACK";

	/**
	 * ADT消息处理完成后使用此应答回复ACK消息
	 * 
	 * @param ackEntity ACKEntity对象
	 * @return ACK消息
	 */
	public static String encode(ACKEntity ackEntity) {
		Parser parser = hapiContext.getPipeParser();
		if ("xml".equals(ackEntity.getStyle().toLowerCase())) {
			parser = hapiContext.getXMLParser();
		}
		
		AbstractMessage ack = null;
		
		AbstractSegment msh = null;
		AbstractSegment msa = null;

		String result = "";
		try{
			String version = ackEntity.getVersion();
			String messageTriggerEvent = ackEntity.getMessageTriggerEvent();
			String processingId = ackEntity.getProcessingId();
			
			if (("2.1").equals(version) || ("v2.1").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v21.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v21.message.ACK) ack).getMSH();
				msa = ((ca.uhn.hl7v2.model.v21.message.ACK) ack).getMSA();
			} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v22.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v22.message.ACK) ack).getMSH();
				msa = ((ca.uhn.hl7v2.model.v22.message.ACK) ack).getMSA();
			} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v23.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v23.message.ACK) ack).getMSH();
				msa = ((ca.uhn.hl7v2.model.v23.message.ACK) ack).getMSA();
			} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v231.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v231.message.ACK) ack).getMSH();
				msa = ((ca.uhn.hl7v2.model.v231.message.ACK) ack).getMSA();
			} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v24.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v24.message.ACK) ack).getMSH();
				msa = ((ca.uhn.hl7v2.model.v24.message.ACK) ack).getMSA();
			} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v25.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v25.message.ACK) ack).getMSH();
				msa = ((ca.uhn.hl7v2.model.v25.message.ACK) ack).getMSA();
			} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v251.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v251.message.ACK) ack).getMSH();
				msa = ((ca.uhn.hl7v2.model.v251.message.ACK) ack).getMSA();
			} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v26.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v26.message.ACK) ack).getMSH();
				msa = ((ca.uhn.hl7v2.model.v26.message.ACK) ack).getMSA();
			}
			
			if (EmptyUtil.isNotNull(msh)) {
				MSHSegment mshSegment = new MSHSegment(HL7Constants.SENDING_APPLICATION, ackEntity.getReceivingApplication());
				mshSegment.setMshSegment(msh);
			}
			if (EmptyUtil.isNotNull(msa)) {
				MSASegment msaSegment = new MSASegment(ackEntity.getAcknowledgmentCode(), ackEntity.getMessageControlId(), ackEntity.getTextMessage());
				msaSegment.setMsaSegment(msa);
			}
			
			if (EmptyUtil.isNotNull(ack)) result = parser.encode(ack);
		} catch (HL7Exception e){
		} catch (IOException e){
		}
		return result;
	}
}
