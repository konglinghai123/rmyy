package com.ewcms.hl7v2.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;

/**
 *
 * @author wu_zhijun
 */
public class NK1Util {
	
	private String contactName;
	private String contactRelation;
	private String contactAddress;
	private String contactTelephone;
	
	public NK1Util(String contactName, String contactRelation, String contactAddress, String contactTelephone) {
		this.contactName = contactName; // 联系人姓名
		this.contactRelation = contactRelation;// 与联系人关系
		this.contactAddress = contactAddress;// 联系人地址
		this.contactTelephone = contactTelephone;// 联系人电话
	}

	public void setNk1(AbstractSegment nk1) throws HL7Exception{
		if (nk1 instanceof ca.uhn.hl7v2.model.v21.segment.NK1){
			setNk1_V21((ca.uhn.hl7v2.model.v21.segment.NK1)nk1);
		}else if (nk1 instanceof ca.uhn.hl7v2.model.v22.segment.NK1){
			setNk1_V22((ca.uhn.hl7v2.model.v22.segment.NK1)nk1);
		}else if (nk1 instanceof ca.uhn.hl7v2.model.v23.segment.NK1){
			setNk1_V23((ca.uhn.hl7v2.model.v23.segment.NK1)nk1);
		}else if (nk1 instanceof ca.uhn.hl7v2.model.v231.segment.NK1){
			setNk1_V231((ca.uhn.hl7v2.model.v231.segment.NK1)nk1);
		}else if (nk1 instanceof ca.uhn.hl7v2.model.v24.segment.NK1){
			setNk1_V24((ca.uhn.hl7v2.model.v24.segment.NK1)nk1);
		}else if (nk1 instanceof ca.uhn.hl7v2.model.v25.segment.NK1){
			setNk1_V25((ca.uhn.hl7v2.model.v25.segment.NK1)nk1);
		}else if (nk1 instanceof ca.uhn.hl7v2.model.v251.segment.NK1){
			setNk1_V251((ca.uhn.hl7v2.model.v251.segment.NK1)nk1);
		}else if (nk1 instanceof ca.uhn.hl7v2.model.v26.segment.NK1){
			setNk1_V26((ca.uhn.hl7v2.model.v26.segment.NK1)nk1);
		}
	}
	
	private void setNk1_V21(ca.uhn.hl7v2.model.v21.segment.NK1 nk1) throws HL7Exception{
		nk1.getNk12_NEXTOFKINNAME().getPn1_FamilyName().setValue(getContactName());
		nk1.getNk13_NEXTOFKINRELATIONSHIP().setValue(getContactRelation());
		nk1.getNk14_NEXTOFKINADDRESS().getAd1_StreetAddress().setValue(getContactAddress());
		nk1.getNk15_NEXTOFKINPHONENUMBER(0).setValue(getContactTelephone());
	}
	
	private void setNk1_V22(ca.uhn.hl7v2.model.v22.segment.NK1 nk1) throws HL7Exception{
		nk1.getNk12_NKName().getPn1_FamilyName().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address().getAd1_StreetAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).setValue(getContactTelephone());
	}
	
	private void setNk1_V23(ca.uhn.hl7v2.model.v23.segment.NK1 nk1) throws HL7Exception{
		nk1.getNk12_NKName(0).getXpn1_FamilyName().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().setValue(getContactTelephone());
	}
	
	private void setNk1_V231(ca.uhn.hl7v2.model.v231.segment.NK1 nk1) throws HL7Exception{
		nk1.getNk12_NKName(0).getXpn1_FamilyLastName().getFn1_FamilyName().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().setValue(getContactTelephone());
	}
	
	private void setNk1_V24(ca.uhn.hl7v2.model.v24.segment.NK1 nk1) throws HL7Exception{
		nk1.getNk12_NKName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().setValue(getContactTelephone());
	}
	
	private void setNk1_V25(ca.uhn.hl7v2.model.v25.segment.NK1 nk1) throws HL7Exception{
		nk1.getNk12_NKName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().setValue(getContactTelephone());
	}
	
	private void setNk1_V251(ca.uhn.hl7v2.model.v251.segment.NK1 nk1) throws HL7Exception{
		nk1.getNk12_Name(0).getXpn1_FamilyName().getFn1_Surname().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().setValue(getContactTelephone());
	}
	
	private void setNk1_V26(ca.uhn.hl7v2.model.v26.segment.NK1 nk1) throws HL7Exception{
		nk1.getNk12_Name(0).getXpn1_FamilyName().getFn1_Surname().setValue(getContactName());
		nk1.getNk13_Relationship().getCwe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().setValue(getContactTelephone());
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactRelation() {
		return contactRelation;
	}

	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}
}
