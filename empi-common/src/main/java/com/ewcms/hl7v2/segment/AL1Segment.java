package com.ewcms.hl7v2.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;

/**
 *
 * @author wu_zhijun
 */
public class AL1Segment{

	// 过敏史
	private String allergyHistory;
	
	public AL1Segment(String allergyHistory) {
		this.allergyHistory = allergyHistory;
	}
	
	public void setAl1Segment(AbstractSegment al1) throws HL7Exception{
		if (al1 instanceof ca.uhn.hl7v2.model.v22.segment.AL1){
			setAl1_V22((ca.uhn.hl7v2.model.v22.segment.AL1)al1);
		}else if (al1 instanceof ca.uhn.hl7v2.model.v23.segment.AL1){
			setAl1_V23((ca.uhn.hl7v2.model.v23.segment.AL1)al1);
		}else if (al1 instanceof ca.uhn.hl7v2.model.v231.segment.AL1){
			setAl1_V231((ca.uhn.hl7v2.model.v231.segment.AL1)al1);
		}else if (al1 instanceof ca.uhn.hl7v2.model.v24.segment.AL1){
			setAl1_V24((ca.uhn.hl7v2.model.v24.segment.AL1)al1);
		}else if (al1 instanceof ca.uhn.hl7v2.model.v25.segment.AL1){
			setAl1_V25((ca.uhn.hl7v2.model.v25.segment.AL1)al1);
		}else if (al1 instanceof ca.uhn.hl7v2.model.v251.segment.AL1){
			setAl1_V251((ca.uhn.hl7v2.model.v251.segment.AL1)al1);
		}else if (al1 instanceof ca.uhn.hl7v2.model.v26.segment.AL1){
			setAl1_V26((ca.uhn.hl7v2.model.v26.segment.AL1)al1);
		}
	}
	
	/****************************************************************v2.1版本*********************************************************/
	//TODO 无AL1
	
	/****************************************************************v2.2版本*********************************************************/
	private void setAl1_V22(ca.uhn.hl7v2.model.v22.segment.AL1 al1) throws HL7Exception{
		al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());
	}
	
	/****************************************************************v2.3版本*********************************************************/
	
	private void setAl1_V23(ca.uhn.hl7v2.model.v23.segment.AL1 al1) throws HL7Exception{
		al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());
	}
	
	/****************************************************************v2.3.1版本*********************************************************/
	private void setAl1_V231(ca.uhn.hl7v2.model.v231.segment.AL1 al1) throws HL7Exception{
		al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());
	}
	
	/****************************************************************v2.4版本*********************************************************/
	private void setAl1_V24(ca.uhn.hl7v2.model.v24.segment.AL1 al1) throws HL7Exception{
		al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());
	}
	
	/****************************************************************v2.5版本*********************************************************/
	private void setAl1_V25(ca.uhn.hl7v2.model.v25.segment.AL1 al1) throws HL7Exception{
		al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());
	}

	/****************************************************************v2.5.1版本*********************************************************/
	private void setAl1_V251(ca.uhn.hl7v2.model.v251.segment.AL1 al1) throws HL7Exception{
		al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());
	}

	/****************************************************************v2.6版本*********************************************************/
	private void setAl1_V26(ca.uhn.hl7v2.model.v26.segment.AL1 al1) throws HL7Exception{
		al1.getAl13_AllergenCodeMnemonicDescription().getCwe2_Text().setValue(getAllergyHistory());
	}

	public String getAllergyHistory() {
		return allergyHistory;
	}

	public void setAllergyHistory(String allergyHistory) {
		this.allergyHistory = allergyHistory;
	}
}
