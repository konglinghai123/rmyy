package com.ewcms.hl7v2.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;

/**
 *
 * @author wu_zhijun
 */
public class MSASegment {
	
	//回复消息代码
	private String acknowledgementCode;
	//信息控制ID号
	private String messageControlId;
	//回复消息
	private String textMessage;
	
	public MSASegment(String acknowledgementCode, String messageControlId, String textMessage){
		this.acknowledgementCode = acknowledgementCode;
		this.messageControlId = messageControlId;
		this.textMessage = textMessage;
	}
	
	public void setMsaSegment(AbstractSegment msa) throws HL7Exception{
		if (msa instanceof ca.uhn.hl7v2.model.v21.segment.MSA){
			setMas_V21((ca.uhn.hl7v2.model.v21.segment.MSA)msa);
		}else if (msa instanceof ca.uhn.hl7v2.model.v22.segment.MSA){
			setMas_V22((ca.uhn.hl7v2.model.v22.segment.MSA)msa);
		}else if (msa instanceof ca.uhn.hl7v2.model.v23.segment.MSA){
			setMas_V23((ca.uhn.hl7v2.model.v23.segment.MSA)msa);
		}else if (msa instanceof ca.uhn.hl7v2.model.v231.segment.MSA){
			setMas_V231((ca.uhn.hl7v2.model.v231.segment.MSA)msa);
		}else if (msa instanceof ca.uhn.hl7v2.model.v24.segment.MSA){
			setMas_V24((ca.uhn.hl7v2.model.v24.segment.MSA)msa);
		}else if (msa instanceof ca.uhn.hl7v2.model.v25.segment.MSA){
			setMas_V25((ca.uhn.hl7v2.model.v25.segment.MSA)msa);
		}else if (msa instanceof ca.uhn.hl7v2.model.v251.segment.MSA){
			setMas_V251((ca.uhn.hl7v2.model.v251.segment.MSA)msa);
		}else if (msa instanceof ca.uhn.hl7v2.model.v26.segment.MSA){
			setMas_V26((ca.uhn.hl7v2.model.v26.segment.MSA)msa);
		}
	}

	/****************************************************************v2.1版本*********************************************************/
	private void setMas_V21(ca.uhn.hl7v2.model.v21.segment.MSA msa) throws HL7Exception{
		msa.getMsa1_ACKNOWLEDGMENTCODE().setValue(getAcknowledgementCode());
		msa.getMsa2_MESSAGECONTROLID().setValue(getMessageControlId());
		msa.getMsa3_TEXTMESSAGE().setValue(getTextMessage());
	}

	/****************************************************************v2.2版本*********************************************************/
	private void setMas_V22(ca.uhn.hl7v2.model.v22.segment.MSA msa) throws HL7Exception{
		msa.getMsa1_AcknowledgementCode().setValue(getAcknowledgementCode());
		msa.getMsa2_MessageControlID().setValue(getMessageControlId());
		msa.getMsa3_TextMessage().setValue(getTextMessage());
	}
	
	/****************************************************************v2.3版本*********************************************************/
	private void setMas_V23(ca.uhn.hl7v2.model.v23.segment.MSA msa) throws HL7Exception{
		msa.getMsa1_AcknowledgementCode().setValue(getAcknowledgementCode());
		msa.getMsa2_MessageControlID().setValue(getMessageControlId());
		msa.getMsa3_TextMessage().setValue(getTextMessage());
	}
	
	/****************************************************************v2.3.1版本*********************************************************/
	private void setMas_V231(ca.uhn.hl7v2.model.v231.segment.MSA msa) throws HL7Exception{
		msa.getMsa1_AcknowledgementCode().setValue(getAcknowledgementCode());
		msa.getMsa2_MessageControlID().setValue(getMessageControlId());
		msa.getMsa3_TextMessage().setValue(getTextMessage());
	}
	
	/****************************************************************v2.4版本*********************************************************/
	private void setMas_V24(ca.uhn.hl7v2.model.v24.segment.MSA msa) throws HL7Exception{
		msa.getMsa1_AcknowledgementCode().setValue(getAcknowledgementCode());
		msa.getMsa2_MessageControlID().setValue(getMessageControlId());
		msa.getMsa3_TextMessage().setValue(getTextMessage());
	}

	/****************************************************************v2.5版本*********************************************************/
	private void setMas_V25(ca.uhn.hl7v2.model.v25.segment.MSA msa) throws HL7Exception{
		msa.getMsa1_AcknowledgmentCode().setValue(getAcknowledgementCode());
		msa.getMsa2_MessageControlID().setValue(getMessageControlId());
		msa.getMsa3_TextMessage().setValue(getTextMessage());
	}
	
	/****************************************************************v2.5.1版本*********************************************************/
	private void setMas_V251(ca.uhn.hl7v2.model.v251.segment.MSA msa) throws HL7Exception{
		msa.getMsa1_AcknowledgmentCode().setValue(getAcknowledgementCode());
		msa.getMsa2_MessageControlID().setValue(getMessageControlId());
		msa.getMsa3_TextMessage().setValue(getTextMessage());
	}
	
	/****************************************************************v2.6版本*********************************************************/
	private void setMas_V26(ca.uhn.hl7v2.model.v26.segment.MSA msa) throws HL7Exception{
		msa.getMsa1_AcknowledgmentCode().setValue(getAcknowledgementCode());
		msa.getMsa2_MessageControlID().setValue(getMessageControlId());

		msa.getMsa3_TextMessage().setValue(getTextMessage());
	}

	public String getAcknowledgementCode() {
		return acknowledgementCode;
	}

	public void setAcknowledgementCode(String acknowledgementCode) {
		this.acknowledgementCode = acknowledgementCode;
	}

	public String getMessageControlId() {
		return messageControlId;
	}

	public void setMessageControlId(String messageControlId) {
		this.messageControlId = messageControlId;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
}
