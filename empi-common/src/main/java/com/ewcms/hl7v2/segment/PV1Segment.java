package com.ewcms.hl7v2.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;

/**
 *
 * @author wu_zhijun
 */
public class PV1Segment {
	
	//患者类型
	private String patientType;
	
	public PV1Segment(String patientType){
		this.patientType = patientType;
	}
	
	public void setPv1Segment(AbstractSegment pv1) throws HL7Exception{
		if (pv1 instanceof ca.uhn.hl7v2.model.v21.segment.PV1){
			setPv1_V21((ca.uhn.hl7v2.model.v21.segment.PV1)pv1);
		}else if (pv1 instanceof ca.uhn.hl7v2.model.v22.segment.PV1){
			setPv1_V22((ca.uhn.hl7v2.model.v22.segment.PV1)pv1);
		}else if (pv1 instanceof ca.uhn.hl7v2.model.v23.segment.PV1){
			setPv1_V23((ca.uhn.hl7v2.model.v23.segment.PV1)pv1);
		}else if (pv1 instanceof ca.uhn.hl7v2.model.v231.segment.PV1){
			setPv1_V231((ca.uhn.hl7v2.model.v231.segment.PV1)pv1);
		}else if (pv1 instanceof ca.uhn.hl7v2.model.v24.segment.PV1){
			setPv1_V24((ca.uhn.hl7v2.model.v24.segment.PV1)pv1);
		}else if (pv1 instanceof ca.uhn.hl7v2.model.v25.segment.PV1){
			setPv1_V25((ca.uhn.hl7v2.model.v25.segment.PV1)pv1);
		}else if (pv1 instanceof ca.uhn.hl7v2.model.v251.segment.PV1){
			setPv1_V251((ca.uhn.hl7v2.model.v251.segment.PV1)pv1);
		}else if (pv1 instanceof ca.uhn.hl7v2.model.v26.segment.PV1){
			setPv1_V26((ca.uhn.hl7v2.model.v26.segment.PV1)pv1);
		}
	}
	
	/****************************************************************v2.1版本*********************************************************/
	private void setPv1_V21(ca.uhn.hl7v2.model.v21.segment.PV1 pv1) throws HL7Exception{
		pv1.getPv12_PATIENTCLASS().setValue(getPatientType());
	}
	
	/****************************************************************v2.2版本*********************************************************/
	private void setPv1_V22(ca.uhn.hl7v2.model.v22.segment.PV1 pv1) throws HL7Exception{
		pv1.getPv12_PatientClass().setValue(getPatientType());
	}
	
	/****************************************************************v2.3版本*********************************************************/
	
	private void setPv1_V23(ca.uhn.hl7v2.model.v23.segment.PV1 pv1) throws HL7Exception{
		pv1.getPv12_PatientClass().setValue(getPatientType());
	}
	
	/****************************************************************v2.3.1版本*********************************************************/
	private void setPv1_V231(ca.uhn.hl7v2.model.v231.segment.PV1 pv1) throws HL7Exception{
		pv1.getPv12_PatientClass().setValue(getPatientType());
	}
	
	/****************************************************************v2.4版本*********************************************************/
	private void setPv1_V24(ca.uhn.hl7v2.model.v24.segment.PV1 pv1) throws HL7Exception{
		pv1.getPv12_PatientClass().setValue(getPatientType());
	}
	
	/****************************************************************v2.5版本*********************************************************/
	private void setPv1_V25(ca.uhn.hl7v2.model.v25.segment.PV1 pv1) throws HL7Exception{
		pv1.getPv12_PatientClass().setValue(getPatientType());
	}

	/****************************************************************v2.5.1版本*********************************************************/
	private void setPv1_V251(ca.uhn.hl7v2.model.v251.segment.PV1 pv1) throws HL7Exception{
		pv1.getPv12_PatientClass().setValue(getPatientType());
	}

	/****************************************************************v2.6版本*********************************************************/
	private void setPv1_V26(ca.uhn.hl7v2.model.v26.segment.PV1 pv1) throws HL7Exception{
		pv1.getPv12_PatientClass().setValue(getPatientType());
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}
}
