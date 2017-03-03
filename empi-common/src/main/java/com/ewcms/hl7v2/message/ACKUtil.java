package com.ewcms.hl7v2.message;

import java.io.IOException;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.hl7v2.HL7Constants;
import com.ewcms.hl7v2.segment.MSAUtil;
import com.ewcms.hl7v2.segment.MSHUtil;

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
public class ACKUtil {

	private static HapiContext hapiContext = new DefaultHapiContext();

	private static String messageCode = "ACK";

	/**
	 * ADT消息处理完成后使用此应答回复ACK消息
	 * 
	 * @param messageTriggerEvent 消息触发事件插入MSG-9-2. 例如: "A01"
	 * @param processingId 消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param style HL7使用样式(xml或er7)
	 * @param acknowledgementCode AcknowledgmentCode枚举对象值
	 * @param textMessage 返回消息内容
	 * @param receivingApplication 接收应用名称
	 * @return ACK消息
	 */
	public static String encode(String messageTriggerEvent, String processingId, String version, String style, String acknowledgementCode, String textMessage, String receivingApplication) {
		Parser parser = hapiContext.getPipeParser();
		if ("xml".equals(style.toLowerCase())) {
			parser = hapiContext.getXMLParser();
		}
		
		AbstractMessage ack = null;
		
		AbstractSegment msh = null;
		AbstractSegment msa = null;

		String result = "";
		try{
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
				MSHUtil mshUtil = new MSHUtil(HL7Constants.SENDING_APPLICATION, receivingApplication);
				mshUtil.setMsh(msh);
			}
			if (EmptyUtil.isNotNull(msa)) {
				MSAUtil msaUtil = new MSAUtil(acknowledgementCode, textMessage);
				msaUtil.setMsa(msa);
			}
			
			if (EmptyUtil.isNotNull(ack)) result = parser.encode(ack);
		} catch (HL7Exception e){
		} catch (IOException e){
		}
		return result;
	}
}
