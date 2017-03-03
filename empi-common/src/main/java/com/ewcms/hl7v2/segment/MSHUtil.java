package com.ewcms.hl7v2.segment;

import java.util.Date;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;

/**
 * MSH片段
 * 
 * @author wu_zhijun
 */
public class MSHUtil {
	
	private String sendingApplication;
	private String receivingApplication;
	
	public MSHUtil(String sendingApplication, String receivingApplication){
		this.sendingApplication = sendingApplication;
		this.receivingApplication = receivingApplication;
	}
	
	public void setMsh(AbstractSegment msh) throws HL7Exception{
		if (msh instanceof ca.uhn.hl7v2.model.v21.segment.MSH){
			setMah_V21((ca.uhn.hl7v2.model.v21.segment.MSH)msh);
		}else if (msh instanceof ca.uhn.hl7v2.model.v22.segment.MSH){
			setMah_V22((ca.uhn.hl7v2.model.v22.segment.MSH)msh);
		}else if (msh instanceof ca.uhn.hl7v2.model.v23.segment.MSH){
			setMah_V23((ca.uhn.hl7v2.model.v23.segment.MSH)msh);
		}else if (msh instanceof ca.uhn.hl7v2.model.v231.segment.MSH){
			setMah_V231((ca.uhn.hl7v2.model.v231.segment.MSH)msh);
		}else if (msh instanceof ca.uhn.hl7v2.model.v24.segment.MSH){
			setMah_V24((ca.uhn.hl7v2.model.v24.segment.MSH)msh);
		}else if (msh instanceof ca.uhn.hl7v2.model.v25.segment.MSH){
			setMah_V25((ca.uhn.hl7v2.model.v25.segment.MSH)msh);
		}else if (msh instanceof ca.uhn.hl7v2.model.v251.segment.MSH){
			setMah_V251((ca.uhn.hl7v2.model.v251.segment.MSH)msh);
		}else if (msh instanceof ca.uhn.hl7v2.model.v26.segment.MSH){
			setMah_V26((ca.uhn.hl7v2.model.v26.segment.MSH)msh);
		}
	}

	/****************************************************************v2.1版本*********************************************************/
	private void setMah_V21(ca.uhn.hl7v2.model.v21.segment.MSH msh) throws HL7Exception{
		msh.getMsh3_SENDINGAPPLICATION().setValue(getSendingApplication());
		msh.getMsh5_RECEIVINGAPPLICATION().setValue(getReceivingApplication());
		msh.getMsh7_DATETIMEOFMESSAGE().setValue(new Date());
	}

	/****************************************************************v2.2版本*********************************************************/
	private void setMah_V22(ca.uhn.hl7v2.model.v22.segment.MSH msh) throws HL7Exception{
		msh.getMsh3_SendingApplication().setValue(getSendingApplication());
		msh.getMsh5_ReceivingApplication().setValue(getReceivingApplication());
		msh.getMsh7_DateTimeOfMessage().getTs1_TimeOfAnEvent().setValue(new Date());
	}
	
	/****************************************************************v2.3版本*********************************************************/
	private void setMah_V23(ca.uhn.hl7v2.model.v23.segment.MSH msh) throws HL7Exception{
		msh.getMsh3_SendingApplication().getHd1_NamespaceID().setValue(getSendingApplication());
		msh.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue(getReceivingApplication());
		msh.getMsh7_DateTimeOfMessage().getTs1_TimeOfAnEvent().setValue(new Date());
	}
	
	/****************************************************************v2.3.1版本*********************************************************/
	private void setMah_V231(ca.uhn.hl7v2.model.v231.segment.MSH msh) throws HL7Exception{
		msh.getMsh3_SendingApplication().getHd1_NamespaceID().setValue(getSendingApplication());
		msh.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue(getReceivingApplication());
		msh.getMsh7_DateTimeOfMessage().getTs1_TimeOfAnEvent().setValue(new Date());
	}
	
	/****************************************************************v2.4版本*********************************************************/
	private void setMah_V24(ca.uhn.hl7v2.model.v24.segment.MSH msh) throws HL7Exception{
		msh.getMsh3_SendingApplication().getHd1_NamespaceID().setValue(getSendingApplication());
		msh.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue(getReceivingApplication());
		msh.getMsh7_DateTimeOfMessage().getTs1_TimeOfAnEvent().setValue(new Date());
	}

	/****************************************************************v2.5版本*********************************************************/
	private void setMah_V25(ca.uhn.hl7v2.model.v25.segment.MSH msh) throws HL7Exception{
		msh.getMsh3_SendingApplication().getHd1_NamespaceID().setValue(getSendingApplication());
		msh.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue(getReceivingApplication());
		msh.getMsh7_DateTimeOfMessage().getTs1_Time().setValue(new Date());
	}
	
	/****************************************************************v2.5.1版本*********************************************************/
	private void setMah_V251(ca.uhn.hl7v2.model.v251.segment.MSH msh) throws HL7Exception{
		msh.getMsh3_SendingApplication().getHd1_NamespaceID().setValue(getSendingApplication());
		msh.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue(getReceivingApplication());
		msh.getMsh7_DateTimeOfMessage().getTs1_Time().setValue(new Date());
	}
	
	/****************************************************************v2.6版本*********************************************************/
	private void setMah_V26(ca.uhn.hl7v2.model.v26.segment.MSH msh) throws HL7Exception{
		msh.getMsh3_SendingApplication().getHd1_NamespaceID().setValue(getSendingApplication());
		msh.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue(getReceivingApplication());
		msh.getMsh7_DateTimeOfMessage().setValue(new Date());
	}

	public String getSendingApplication() {
		return sendingApplication;
	}

	public void setSendingApplication(String sendingApplication) {
		this.sendingApplication = sendingApplication;
	}

	public String getReceivingApplication() {
		return receivingApplication;
	}

	public void setReceivingApplication(String receivingApplication) {
		this.receivingApplication = receivingApplication;
	}
}
