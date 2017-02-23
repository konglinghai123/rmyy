package com.ewcms.hl7.message.v2;

import java.text.SimpleDateFormat;
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
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Map<String, PatientBaseInfo> v21Parser(String message) throws HL7Exception, Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		context.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v21.message.ADT_AXX.class));
		Message msg = context.getPipeParser().parse(message);
		ca.uhn.hl7v2.model.v21.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v21.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v21.segment.PID pid = axx.getPID();
			
		String practiceNo = pid.getPid2_PATIENTIDEXTERNALEXTERNALID().getCk1_IDNumber().getValue();
		
		patientBaseInfo.setPatientId(pid.getPid3_PATIENTIDINTERNALINTERNALID().getCk1_IDNumber().getValue());
		patientBaseInfo.setName(pid.getPid5_PATIENTNAME().getPn1_FamilyName().getValue());
		patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DATEOFBIRTH().getValue()));
		patientBaseInfo.setSex(pid.getPid8_SEX().getValue());
		patientBaseInfo.setNation(pid.getPid10_ETHNICGROUP().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PATIENTADDRESS().getAd1_StreetAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PATIENTADDRESS().getAd3_City().getValue());
		patientBaseInfo.setProvince(pid.getPid11_PATIENTADDRESS().getAd4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PATIENTADDRESS().getAd6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PHONENUMBERHOME(0).getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PHONENUMBERBUSINESS(0).getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MARITALSTATUS().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PATIENTACCOUNTNUMBER().getCk1_IDNumber().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNUMBERPATIENT().getValue());
		ca.uhn.hl7v2.model.v21.segment.NK1 nk1 = axx.getNK1();
		patientBaseInfo.setContactName(nk1.getNk12_NEXTOFKINNAME().getPn1_FamilyName().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_NEXTOFKINRELATIONSHIP().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_NEXTOFKINADDRESS().getAd1_StreetAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_NEXTOFKINPHONENUMBER(0).getValue());
		Map<String, PatientBaseInfo> result = Maps.newHashMap();
		result.put(practiceNo, patientBaseInfo);
		
		return result;
	}
	
	public static Map<String, PatientBaseInfo> v22Parser(String message) throws HL7Exception,Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		context.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v22.message.ADT_AXX.class));
		Message msg = context.getPipeParser().parse(message);
		ca.uhn.hl7v2.model.v22.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v22.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v22.segment.PID pid = axx.getPID();
			
		String practiceNo = pid.getPid2_PatientIDExternalID().getCk1_IDNumber().getValue();
		
		patientBaseInfo.setPatientId(pid.getPid3_PatientIDInternalID(0).getCm_pat_id1_IDNumber().getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName().getPn1_FamilyName().getValue());
		patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateOfBirth().getTs2_DegreeOfPrecision().getValue()));
		patientBaseInfo.setSex(pid.getPid8_Sex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0).getAd1_StreetAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getAd3_City().getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0).getAd4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0).getAd6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0).getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0).getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber().getCk1_IDNumber().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SocialSecurityNumberPatient().getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());
		ca.uhn.hl7v2.model.v22.segment.NK1 nk1 = axx.getNK1();
		
		patientBaseInfo.setContactName(nk1.getNk12_NKName().getPn1_FamilyName().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship().getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address().getAd1_StreetAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0).getValue());
		ca.uhn.hl7v2.model.v22.segment.AL1 al1 = axx.getAL1();
		
		patientBaseInfo.setAllergyHistory(al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().getValue());

		Map<String, PatientBaseInfo> result = Maps.newHashMap();
		result.put(practiceNo, patientBaseInfo);
		
		return result;
	}
	
	public static Map<String, PatientBaseInfo> v23Parser(String message) throws HL7Exception, Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		context.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v23.message.ADT_AXX.class));
		Message msg = context.getPipeParser().parse(message);
		ca.uhn.hl7v2.model.v23.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v23.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v23.segment.PID pid = axx.getPID();
			
		String practiceNo = pid.getPid2_PatientIDExternalID().getCx1_ID().getValue();
		Long patientId = Long.parseLong(pid.getPid3_PatientIDInternalID(0).getCx1_ID().getValue());
		patientBaseInfo.setId(patientId);
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName().getValue());
		patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateOfBirth().getTs2_DegreeOfPrecision().getValue()));
		patientBaseInfo.setSex(pid.getPid8_Sex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City().getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0).getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus(0).getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber().getCx1_ID().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient().getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());
		
		ca.uhn.hl7v2.model.v23.segment.NK1 nk1 = axx.getNK1();
		
		patientBaseInfo.setContactName(nk1.getNk12_NKName(0).getXpn1_FamilyName().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship().getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0).getXad1_StreetAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().getValue());

		ca.uhn.hl7v2.model.v23.segment.AL1 al1 = axx.getAL1();
		
		patientBaseInfo.setAllergyHistory(al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().getValue());

		Map<String, PatientBaseInfo> result = Maps.newHashMap();
		result.put(practiceNo, patientBaseInfo);
		
		return result;
	}
	
	public static Map<String, PatientBaseInfo> v231Parser(String message) throws HL7Exception, Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		context.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v231.message.ADT_AXX.class));
		Message msg = context.getPipeParser().parse(message);
		ca.uhn.hl7v2.model.v231.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v231.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v231.segment.PID pid = axx.getPID();
			
		String practiceNo = pid.getPid2_PatientID().getCx1_ID().getValue();
		Long patientId = Long.parseLong(pid.getPid3_PatientIdentifierList(0).getCx1_ID().getValue());
		patientBaseInfo.setId(patientId);
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyLastName().getFn1_FamilyName().getValue());
		patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().getValue()));
		patientBaseInfo.setSex(pid.getPid8_Sex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race(0).getCe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City().getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0).getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus().getCe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber().getID().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient().getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());
		
		ca.uhn.hl7v2.model.v231.segment.NK1 nk1 = axx.getNK1();
		
		patientBaseInfo.setContactName(nk1.getNk12_NKName(0).getXpn1_FamilyLastName().getFn1_FamilyName().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship().getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0).getXad1_StreetAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().getValue());

		ca.uhn.hl7v2.model.v231.segment.AL1 al1 = axx.getAL1();
		
		patientBaseInfo.setAllergyHistory(al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().getValue());

		Map<String, PatientBaseInfo> result = Maps.newHashMap();
		result.put(practiceNo, patientBaseInfo);
		
		return result;
	}
	
	public static PatientBaseInfo v24Parser(String message) throws HL7Exception, Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		context.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v24.message.ADT_AXX.class));
		Message msg = context.getPipeParser().parse(message);
		ca.uhn.hl7v2.model.v24.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v24.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v24.segment.PID pid = axx.getPID();
			
		patientBaseInfo.setPracticeNo(pid.getPid2_PatientID().getCx1_ID().getValue());
		patientBaseInfo.setPatientId(pid.getPid3_PatientIdentifierList(0).getCx1_ID().getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().getValue()));
		patientBaseInfo.setSex(pid.getPid8_AdministrativeSex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race(0).getCe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City().getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0).getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus().getCe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber().getCx1_ID().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient().getValue());
		patientBaseInfo.setBirthPlace(pid.getPid19_SSNNumberPatient().getValue());
		
		ca.uhn.hl7v2.model.v24.segment.NK1 nk1 = axx.getNK1();
		
		patientBaseInfo.setContactName(nk1.getNk12_NKName(0).getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship().getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().getValue());

		ca.uhn.hl7v2.model.v24.segment.AL1 al1 = axx.getAL1();
		patientBaseInfo.setAllergyHistory(al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().getValue());
		
		return patientBaseInfo;		
	}
	
	public static Map<String, PatientBaseInfo> v25Parser(String message) throws HL7Exception, Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		context.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v25.message.ADT_AXX.class));
		Message msg = context.getPipeParser().parse(message);
		ca.uhn.hl7v2.model.v25.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v25.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v25.segment.PID pid = axx.getPID();
			
		String practiceNo = pid.getPid2_PatientID().getCx1_IDNumber().getValue();
		Long patientId = Long.parseLong(pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().getValue());
		patientBaseInfo.setId(patientId);
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().getValue()));
		patientBaseInfo.setSex(pid.getPid8_AdministrativeSex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race(0).getCe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City().getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0).getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus().getCe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber().getCx1_IDNumber().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient().getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());
		
		ca.uhn.hl7v2.model.v25.segment.NK1 nk1 = axx.getNK1();
		
		patientBaseInfo.setContactName(nk1.getNk12_NKName(0).getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship().getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().getValue());

		ca.uhn.hl7v2.model.v25.segment.AL1 al1 = axx.getAL1();
		patientBaseInfo.setAllergyHistory(al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().getValue());
		
		
		
		Map<String, PatientBaseInfo> result = Maps.newHashMap();
		result.put(practiceNo, patientBaseInfo);
		
		return result;	
		
	}
	
	public static Map<String, PatientBaseInfo> v251Parser(String message) throws HL7Exception, Exception{
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		context.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v251.message.ADT_AXX.class));
		Message msg = context.getPipeParser().parse(message);
		ca.uhn.hl7v2.model.v251.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v251.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v251.segment.PID pid = axx.getPID();
			
		String practiceNo = pid.getPid2_PatientID().getCx1_IDNumber().getValue();
		Long patientId = Long.parseLong(pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().getValue());
		patientBaseInfo.setId(patientId);
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().getValue()));
		patientBaseInfo.setSex(pid.getPid8_AdministrativeSex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race(0).getCe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City().getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0).getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus().getCe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber().getCx1_IDNumber().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient().getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());
		
		ca.uhn.hl7v2.model.v251.segment.NK1 nk1 = axx.getNK1();
		
		patientBaseInfo.setContactName(nk1.getNk12_Name(0).getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship().getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().getValue());

		ca.uhn.hl7v2.model.v251.segment.AL1 al1 = axx.getAL1();
		patientBaseInfo.setAllergyHistory(al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().getValue());

		Map<String, PatientBaseInfo> result = Maps.newHashMap();
		result.put(practiceNo, patientBaseInfo);
		
		return result;	
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
