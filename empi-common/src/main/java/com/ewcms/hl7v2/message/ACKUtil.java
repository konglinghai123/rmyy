package com.ewcms.hl7v2.message;

import java.io.IOException;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.hl7v2.segment.MSAUtil;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.parser.Parser;

/**
 *
 * @author wu_zhijun
 */
public class ACKUtil {

	private static HapiContext hapiContext = new DefaultHapiContext();

	private static String messageCode = "ACK";

	public String encode(String messageTriggerEvent, String processingId, String version, String style, String code, String textMessage) {
		Parser parser = hapiContext.getPipeParser();
		if ("xml".equals(style.toLowerCase())) {
			parser = hapiContext.getXMLParser();
		}
		
		AbstractMessage ack = null;
		AbstractSegment msa = null;

		try{
			if (("2.1").equals(version) || ("v2.1").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v21.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msa = ((ca.uhn.hl7v2.model.v21.message.ACK) ack).getMSA();
	
			} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v22.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msa = ((ca.uhn.hl7v2.model.v22.message.ACK) ack).getMSA();
	
			} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v23.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msa = ((ca.uhn.hl7v2.model.v23.message.ACK) ack).getMSA();
	
			} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v231.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msa = ((ca.uhn.hl7v2.model.v231.message.ACK) ack).getMSA();
	
			} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v24.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msa = ((ca.uhn.hl7v2.model.v24.message.ACK) ack).getMSA();
	
			} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v25.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msa = ((ca.uhn.hl7v2.model.v25.message.ACK) ack).getMSA();
	
			} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v251.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msa = ((ca.uhn.hl7v2.model.v251.message.ACK) ack).getMSA();
	
			} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
				ack = new ca.uhn.hl7v2.model.v26.message.ACK();
				ack.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msa = ((ca.uhn.hl7v2.model.v26.message.ACK) ack).getMSA();
			}
	
			MSAUtil.setMsa(msa, code, textMessage);
			
			return (EmptyUtil.isNotNull(ack)) ? parser.encode(ack) : "";
		} catch (HL7Exception e){
		} catch (IOException e){
		}
		return "";
	}
}
