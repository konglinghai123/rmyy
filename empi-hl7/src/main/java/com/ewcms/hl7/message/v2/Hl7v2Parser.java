package com.ewcms.hl7.message.v2;

import java.util.Map;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.google.common.collect.Maps;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;

/**
 *
 * @author wu_zhijun
 */
public class Hl7v2Parser {
	private static HapiContext context = new DefaultHapiContext();
	
	public static Map<String, PatientBaseInfo> v21Parser(String message) throws HL7Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		return null;
	}
	
	public static Map<String, PatientBaseInfo> v22Parser(String message) throws HL7Exception{
		return null;
	}
	
	public static Map<String, PatientBaseInfo> v23Parser(String message) throws HL7Exception{
		return null;
	}
	
	public static Map<String, PatientBaseInfo> v231Parser(String message) throws HL7Exception{
		return null;
	}
	
	public static Map<String, PatientBaseInfo> v24Parser(String message) throws HL7Exception{
		return null;
	}
	
	public static Map<String, PatientBaseInfo> v25Parser(String message) throws HL7Exception{
		return null;
	}
	
	public static Map<String, PatientBaseInfo> v251Parser(String message) throws HL7Exception{
		return null;
	}
	
	public static Map<String, PatientBaseInfo> v26Parser(String message) throws HL7Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		context.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v26.message.ADT_AXX.class));
		Message msg = context.getPipeParser().parse(message);
		ca.uhn.hl7v2.model.v26.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v26.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v26.segment.PID pid = axx.getPID();
			
		String practiceNo = pid.getPid2_PatientID().getCx1_IDNumber().getValue();
		Long patientId = Long.parseLong(pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().getValue());
			
		patientBaseInfo.setId(patientId);
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setBirthday(pid.getPid7_DateTimeOfBirth().getValueAsDate());
		patientBaseInfo.setSex(pid.getPid8_AdministrativeSex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race(0).getCwe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City().getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0).getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus().getCwe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber().getCx1_IDNumber().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient().getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());
			
		ca.uhn.hl7v2.model.v26.segment.NK1 nk1 = ((ca.uhn.hl7v2.model.v26.message.ADT_A01) msg).getNK1();
			
		patientBaseInfo.setContactName(nk1.getNk12_Name(0).getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship().getCwe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().getValue());
			
		ca.uhn.hl7v2.model.v26.segment.AL1 al1 = ((ca.uhn.hl7v2.model.v26.message.ADT_A01) msg).getAL1();
		patientBaseInfo.setAllergyHistory(al1.getAl13_AllergenCodeMnemonicDescription().getCwe2_Text().getValue());
			
			
		Map<String, PatientBaseInfo> result = Maps.newHashMap();
		result.put(practiceNo, patientBaseInfo);
		
		return result;
	}
}
