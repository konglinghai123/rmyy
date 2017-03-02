package com.ewcms.hl7v2.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;

/**
 *
 * @author wu_zhijun
 */
public class MSAUtil {
	
	public static void setMsa(AbstractSegment msa, String code, String textMessage) throws HL7Exception{
		if (msa instanceof ca.uhn.hl7v2.model.v21.segment.MSA){
			setMas_V21((ca.uhn.hl7v2.model.v21.segment.MSA)msa, code, textMessage);
		}else if (msa instanceof ca.uhn.hl7v2.model.v22.segment.MSA){
			setMas_V22((ca.uhn.hl7v2.model.v22.segment.MSA)msa, code, textMessage);
		}else if (msa instanceof ca.uhn.hl7v2.model.v23.segment.MSA){
			setMas_V23((ca.uhn.hl7v2.model.v23.segment.MSA)msa, code, textMessage);
		}else if (msa instanceof ca.uhn.hl7v2.model.v231.segment.MSA){
			setMas_V231((ca.uhn.hl7v2.model.v231.segment.MSA)msa, code, textMessage);
		}else if (msa instanceof ca.uhn.hl7v2.model.v24.segment.MSA){
			setMas_V24((ca.uhn.hl7v2.model.v24.segment.MSA)msa, code, textMessage);
		}else if (msa instanceof ca.uhn.hl7v2.model.v25.segment.MSA){
			setMas_V25((ca.uhn.hl7v2.model.v25.segment.MSA)msa, code, textMessage);
		}else if (msa instanceof ca.uhn.hl7v2.model.v251.segment.MSA){
			setMas_V251((ca.uhn.hl7v2.model.v251.segment.MSA)msa, code, textMessage);
		}else if (msa instanceof ca.uhn.hl7v2.model.v26.segment.MSA){
			setMas_V26((ca.uhn.hl7v2.model.v26.segment.MSA)msa, code, textMessage);
		}
	}

	private static void setMas_V21(ca.uhn.hl7v2.model.v21.segment.MSA msa, String code, String textMessage) throws HL7Exception{
		msa.getMsa1_ACKNOWLEDGMENTCODE().setValue(code);
		msa.getMsa3_TEXTMESSAGE().setValue(textMessage);
	}

	private static void setMas_V22(ca.uhn.hl7v2.model.v22.segment.MSA msa, String acknowledgementCode, String textMessage) throws HL7Exception{
		msa.getMsa1_AcknowledgementCode().setValue(acknowledgementCode);
		msa.getMsa3_TextMessage().setValue(textMessage);
	}
	
	private static void setMas_V23(ca.uhn.hl7v2.model.v23.segment.MSA msa, String acknowledgementCode, String textMessage) throws HL7Exception{
		msa.getMsa1_AcknowledgementCode().setValue(acknowledgementCode);
		msa.getMsa3_TextMessage().setValue(textMessage);
	}
	
	private static void setMas_V231(ca.uhn.hl7v2.model.v231.segment.MSA msa, String acknowledgementCode, String textMessage) throws HL7Exception{
		msa.getMsa1_AcknowledgementCode().setValue(acknowledgementCode);
		msa.getMsa3_TextMessage().setValue(textMessage);
	}
	
	private static void setMas_V24(ca.uhn.hl7v2.model.v24.segment.MSA msa, String acknowledgementCode, String textMessage) throws HL7Exception{
		msa.getMsa1_AcknowledgementCode().setValue(acknowledgementCode);
		msa.getMsa3_TextMessage().setValue(textMessage);
	}

	private static void setMas_V25(ca.uhn.hl7v2.model.v25.segment.MSA msa, String acknowledgementCode, String textMessage) throws HL7Exception{
		msa.getMsa1_AcknowledgmentCode().setValue(acknowledgementCode);
		msa.getMsa3_TextMessage().setValue(textMessage);
	}
	
	private static void setMas_V251(ca.uhn.hl7v2.model.v251.segment.MSA msa, String acknowledgementCode, String textMessage) throws HL7Exception{
		msa.getMsa1_AcknowledgmentCode().setValue(acknowledgementCode);
		msa.getMsa3_TextMessage().setValue(textMessage);
	}
	
	private static void setMas_V26(ca.uhn.hl7v2.model.v26.segment.MSA msa, String acknowledgementCode, String textMessage) throws HL7Exception{
		msa.getMsa1_AcknowledgmentCode().setValue(acknowledgementCode);
		msa.getMsa3_TextMessage().setValue(textMessage);
	}
}
