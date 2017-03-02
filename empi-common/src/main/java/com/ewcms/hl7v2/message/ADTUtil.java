package com.ewcms.hl7v2.message;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.model.AbstractSuperMessage;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.hl7v2.segment.AL1Util;
import com.ewcms.hl7v2.segment.NK1Util;
import com.ewcms.hl7v2.segment.PIDUtil;
import com.ewcms.hl7v2.util.HL7StringUtil;

/**
 *
 * @author wu_zhijun
 */
public class ADTUtil {

	private static HapiContext hapiContext = new DefaultHapiContext();
	
	private static String messageCode = "ADT";
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 分解ADT或ADR消息并转化为PatientBaseInfo
	 * 
	 * @param message
	 *            ADT消息
	 * @param version
	 *            版本(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4,
	 *            2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param style
	 *            HL7使用样式(xml或er7)
	 * @return PatientBaseInfo对象
	 * @throws HL7Exception
	 */
	public static PatientBaseInfo parser(String message, String version, String style) throws HL7Exception {
		Parser parser = hapiContext.getXMLParser();
		if (!"xml".equals(style.toLowerCase())) {
			parser = hapiContext.getPipeParser();
			message = HL7StringUtil.format(message);
		}

		if (("2.1").equals(version) || ("v2.1").equals(version)) {
			return v21Parser(message, parser);
		} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
			return v22Parser(message, parser);
		} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
			return v23Parser(message, parser);
		} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
			return v231Parser(message, parser);
		} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
			return v24Parser(message, parser);
		} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
			return v25Parser(message, parser);
		} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
			return v251Parser(message, parser);
		} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
			return v26Parser(message, parser);
		} else {
			throw new HL7Exception("The version used is out of range");
		}
	}

	/**
	 * 把信息组合成HL7格式
	 * 
	 * @param messageCode
	 *            消息代码(消息类型)插入MSH-9-1. 例如："ADT"
	 * @param messageTriggerEvent
	 *            消息触发事件插入MSG-9-2. 例如: "A01"
	 * @param processingId
	 *            消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param version
	 *            版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4,
	 *            2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param style
	 *            HL7使用样式(xml或er7)
	 * @return HL7消息格式
	 * @throws HL7Exception
	 * @throws IOException
	 */
	public String encode(PatientBaseInfo patientBaseInfo, String practiceNo, Integer patientIdLen,
			String messageTriggerEvent, String processingId, String version,
			String style) throws HL7Exception, IOException {
		
		Parser parser = hapiContext.getPipeParser();
		if ("xml".equals(style.toLowerCase())) {
			parser = hapiContext.getXMLParser();
		}

		AbstractSuperMessage adt = null;

		AbstractSegment pid = null;
		AbstractSegment nk1 = null;
		AbstractSegment al1 = null;

		if (("2.1").equals(version) || ("v2.1").equals(version)) {
			adt = new ca.uhn.hl7v2.model.v21.message.ADT_AXX();
			adt.initQuickstart(messageCode, messageTriggerEvent, processingId);

			pid = ((ca.uhn.hl7v2.model.v21.message.ADT_AXX) adt).getPID();
			nk1 = ((ca.uhn.hl7v2.model.v21.message.ADT_AXX) adt).getNK1();
		} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
			adt = new ca.uhn.hl7v2.model.v22.message.ADT_AXX();
			adt.initQuickstart(messageCode, messageTriggerEvent, processingId);

			pid = ((ca.uhn.hl7v2.model.v22.message.ADT_AXX) adt).getPID();
			nk1 = ((ca.uhn.hl7v2.model.v22.message.ADT_AXX) adt).getNK1();
			al1 = ((ca.uhn.hl7v2.model.v22.message.ADT_AXX) adt).getAL1();
		} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
			adt = new ca.uhn.hl7v2.model.v23.message.ADT_AXX();
			adt.initQuickstart(messageCode, messageTriggerEvent, processingId);

			pid = ((ca.uhn.hl7v2.model.v23.message.ADT_AXX) adt).getPID();
			nk1 = ((ca.uhn.hl7v2.model.v23.message.ADT_AXX) adt).getNK1();
			al1 = ((ca.uhn.hl7v2.model.v23.message.ADT_AXX) adt).getAL1();
		} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
			adt = new ca.uhn.hl7v2.model.v231.message.ADT_AXX();
			adt.initQuickstart(messageCode, messageTriggerEvent, processingId);

			pid = ((ca.uhn.hl7v2.model.v231.message.ADT_AXX) adt).getPID();
			nk1 = ((ca.uhn.hl7v2.model.v231.message.ADT_AXX) adt).getNK1();
			al1 = ((ca.uhn.hl7v2.model.v231.message.ADT_AXX) adt).getAL1();
		} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
			adt = new ca.uhn.hl7v2.model.v24.message.ADT_AXX();
			adt.initQuickstart(messageCode, messageTriggerEvent, processingId);

			pid = ((ca.uhn.hl7v2.model.v24.message.ADT_AXX) adt).getPID();
			nk1 = ((ca.uhn.hl7v2.model.v24.message.ADT_AXX) adt).getNK1();
			al1 = ((ca.uhn.hl7v2.model.v24.message.ADT_AXX) adt).getAL1();
		} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
			adt = new ca.uhn.hl7v2.model.v25.message.ADT_AXX();
			adt.initQuickstart(messageCode, messageTriggerEvent, processingId);

			pid = ((ca.uhn.hl7v2.model.v25.message.ADT_AXX) adt).getPID();
			nk1 = ((ca.uhn.hl7v2.model.v25.message.ADT_AXX) adt).getNK1();
			al1 = ((ca.uhn.hl7v2.model.v25.message.ADT_AXX) adt).getAL1();
		} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
			adt = new ca.uhn.hl7v2.model.v251.message.ADT_AXX();
			adt.initQuickstart(messageCode, messageTriggerEvent, processingId);

			pid = ((ca.uhn.hl7v2.model.v251.message.ADT_AXX) adt).getPID();
			nk1 = ((ca.uhn.hl7v2.model.v251.message.ADT_AXX) adt).getNK1();
			al1 = ((ca.uhn.hl7v2.model.v251.message.ADT_AXX) adt).getAL1();
		} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
			adt = new ca.uhn.hl7v2.model.v26.message.ADT_AXX();
			adt.initQuickstart(messageCode, messageTriggerEvent, processingId);

			pid = ((ca.uhn.hl7v2.model.v26.message.ADT_AXX) adt).getPID();
			nk1 = ((ca.uhn.hl7v2.model.v26.message.ADT_AXX) adt).getNK1();
			al1 = ((ca.uhn.hl7v2.model.v26.message.ADT_AXX) adt).getAL1();
		} else {
			throw new HL7Exception("The version used is out of range");
		}

		if (EmptyUtil.isNotNull(pid)) {
			PIDUtil pidUtil = new PIDUtil(patientBaseInfo, practiceNo, patientIdLen);
			pidUtil.setPid(pid);
		}
		if (EmptyUtil.isNotNull(nk1)) {
			NK1Util nk1Util = new NK1Util(patientBaseInfo.getContactName(),
					patientBaseInfo.getContactRelation(),
					patientBaseInfo.getContactAddress(),
					patientBaseInfo.getContactTelephone());
			nk1Util.setNk1(nk1);
		}
		if (EmptyUtil.isNotNull(al1)) {
			AL1Util al1Util = new AL1Util(patientBaseInfo.getAllergyHistory());
			al1Util.setAl1(al1);
		}

		return (EmptyUtil.isNotNull(adt)) ? parser.encode(adt) : "";
	}

	/********************************* parser *******************************************************/

	private static PatientBaseInfo v21Parser(String message, Parser parser)
			throws HL7Exception {
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();

		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v21.message.ADT_AXX.class));

		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v21.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v21.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v21.segment.PID pid = axx.getPID();

		patientBaseInfo.setPatientId(pid.getPid3_PATIENTIDINTERNALINTERNALID()
				.getCk1_IDNumber().getValue());
		patientBaseInfo.setPracticeNo(pid.getPid2_PATIENTIDEXTERNALEXTERNALID()
				.getCk1_IDNumber().getValue());
		patientBaseInfo.setName(pid.getPid5_PATIENTNAME().getPn1_FamilyName()
				.getValue());
		try {
			patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DATEOFBIRTH()
					.getValue()));
		} catch (ParseException e) {
		}
		patientBaseInfo.setSex(pid.getPid8_SEX().getValue());
		patientBaseInfo.setNation(pid.getPid10_ETHNICGROUP().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PATIENTADDRESS()
				.getAd1_StreetAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PATIENTADDRESS().getAd3_City()
				.getValue());
		patientBaseInfo.setProvince(pid.getPid11_PATIENTADDRESS()
				.getAd4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PATIENTADDRESS()
				.getAd6_Country().getValue());
		patientBaseInfo
				.setTelephone(pid.getPid13_PHONENUMBERHOME(0).getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PHONENUMBERBUSINESS(0)
				.getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MARITALSTATUS()
				.getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PATIENTACCOUNTNUMBER()
				.getCk1_IDNumber().getValue());
		patientBaseInfo.setCertificateType(pid.getPid18_PATIENTACCOUNTNUMBER()
				.getCk2_CheckDigit().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNUMBERPATIENT()
				.getValue());

		ca.uhn.hl7v2.model.v21.segment.NK1 nk1 = axx.getNK1();

		patientBaseInfo.setContactName(nk1.getNk12_NEXTOFKINNAME()
				.getPn1_FamilyName().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_NEXTOFKINRELATIONSHIP()
				.getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_NEXTOFKINADDRESS()
				.getAd1_StreetAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_NEXTOFKINPHONENUMBER(0)
				.getValue());

		return patientBaseInfo;
	}

	private static PatientBaseInfo v22Parser(String message, Parser parser)
			throws HL7Exception {

		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(
				ca.uhn.hl7v2.model.v22.message.ADT_AXX.class));

		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v22.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v22.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v22.segment.PID pid = axx.getPID();

		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();

		patientBaseInfo.setPatientId(pid.getPid3_PatientIDInternalID(0)
				.getCm_pat_id1_IDNumber().getValue());
		patientBaseInfo.setPracticeNo(pid.getPid2_PatientIDExternalID()
				.getCk1_IDNumber().getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName().getPn1_FamilyName()
				.getValue());
		try {
			patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateOfBirth()
					.getTs2_DegreeOfPrecision().getValue()));
		} catch (ParseException e) {
		}
		patientBaseInfo.setSex(pid.getPid8_Sex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0)
				.getAd1_StreetAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getAd3_City()
				.getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0)
				.getAd4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0)
				.getAd6_Country().getValue());
		patientBaseInfo
				.setTelephone(pid.getPid13_PhoneNumberHome(0).getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0)
				.getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus()
				.getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber()
				.getCk1_IDNumber().getValue());
		patientBaseInfo.setCertificateType(pid.getPid18_PatientAccountNumber()
				.getCk2_CheckDigit().getValue());
		patientBaseInfo.setMedicalAccount(pid
				.getPid19_SocialSecurityNumberPatient().getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());

		ca.uhn.hl7v2.model.v22.segment.NK1 nk1 = axx.getNK1();

		patientBaseInfo.setContactName(nk1.getNk12_NKName().getPn1_FamilyName()
				.getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship()
				.getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address()
				.getAd1_StreetAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0)
				.getValue());
		ca.uhn.hl7v2.model.v22.segment.AL1 al1 = axx.getAL1();

		patientBaseInfo.setAllergyHistory(al1
				.getAl13_AllergyCodeMnemonicDescription().getCe2_Text()
				.getValue());

		return patientBaseInfo;
	}

	private static PatientBaseInfo v23Parser(String message, Parser parser)
			throws HL7Exception {
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();

		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(
				ca.uhn.hl7v2.model.v23.message.ADT_AXX.class));

		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v23.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v23.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v23.segment.PID pid = axx.getPID();

		patientBaseInfo.setPatientId(pid.getPid3_PatientIDInternalID(0)
				.getCx1_ID().getValue());
		patientBaseInfo.setPracticeNo(pid.getPid2_PatientIDExternalID()
				.getCx1_ID().getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName()
				.getValue());
		try {
			patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateOfBirth()
					.getTs2_DegreeOfPrecision().getValue()));
		} catch (ParseException e) {
		}
		patientBaseInfo.setSex(pid.getPid8_Sex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0)
				.getXad1_StreetAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City()
				.getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0)
				.getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0)
				.getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0)
				.getXtn9_AnyText().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0)
				.getXtn7_PhoneNumber().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus(0)
				.getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber()
				.getCx1_ID().getValue());
		patientBaseInfo.setCertificateType(pid.getPid18_PatientAccountNumber()
				.getCx2_CheckDigit().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient()
				.getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());

		ca.uhn.hl7v2.model.v23.segment.NK1 nk1 = axx.getNK1();

		patientBaseInfo.setContactName(nk1.getNk12_NKName(0)
				.getXpn1_FamilyName().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship()
				.getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0)
				.getXad1_StreetAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0)
				.getXtn7_PhoneNumber().getValue());

		ca.uhn.hl7v2.model.v23.segment.AL1 al1 = axx.getAL1();

		patientBaseInfo.setAllergyHistory(al1
				.getAl13_AllergyCodeMnemonicDescription().getCe2_Text()
				.getValue());

		return patientBaseInfo;
	}

	private static PatientBaseInfo v231Parser(String message, Parser parser)
			throws HL7Exception {
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();

		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(
				ca.uhn.hl7v2.model.v231.message.ADT_AXX.class));

		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v231.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v231.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v231.segment.PID pid = axx.getPID();

		patientBaseInfo.setPatientId(pid.getPid3_PatientIdentifierList(0)
				.getCx1_ID().getValue());
		patientBaseInfo.setPracticeNo(pid.getPid2_PatientID().getCx1_ID()
				.getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName(0)
				.getXpn1_FamilyLastName().getFn1_FamilyName().getValue());
		try {
			patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateTimeOfBirth()
					.getTs2_DegreeOfPrecision().getValue()));
		} catch (ParseException e) {
		}
		patientBaseInfo.setSex(pid.getPid8_Sex().getValue());
		patientBaseInfo
				.setNation(pid.getPid10_Race(0).getCe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0)
				.getXad1_StreetAddress().getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City()
				.getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0)
				.getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0)
				.getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0)
				.getXtn7_PhoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0)
				.getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus()
				.getCe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber()
				.getCx1_ID().getValue());
		patientBaseInfo.setCertificateType(pid.getPid18_PatientAccountNumber()
				.getCx2_CheckDigit().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient()
				.getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());

		ca.uhn.hl7v2.model.v231.segment.NK1 nk1 = axx.getNK1();

		patientBaseInfo.setContactName(nk1.getNk12_NKName(0)
				.getXpn1_FamilyLastName().getFn1_FamilyName().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship()
				.getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0)
				.getXad1_StreetAddress().getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0)
				.getXtn7_PhoneNumber().getValue());

		ca.uhn.hl7v2.model.v231.segment.AL1 al1 = axx.getAL1();

		patientBaseInfo.setAllergyHistory(al1
				.getAl13_AllergyCodeMnemonicDescription().getCe2_Text()
				.getValue());

		return patientBaseInfo;
	}

	private static PatientBaseInfo v24Parser(String message, Parser parser)
			throws HL7Exception {
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();

		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(
				ca.uhn.hl7v2.model.v24.message.ADT_AXX.class));

		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v24.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v24.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v24.segment.PID pid = axx.getPID();

		patientBaseInfo.setPatientId(pid.getPid3_PatientIdentifierList(0)
				.getCx1_ID().getValue());
		patientBaseInfo.setPracticeNo(pid.getPid2_PatientID().getCx1_ID()
				.getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName()
				.getFn1_Surname().getValue());
		try {
			patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateTimeOfBirth()
					.getTs2_DegreeOfPrecision().getValue()));
		} catch (ParseException e) {
		}
		patientBaseInfo.setSex(pid.getPid8_AdministrativeSex().getValue());
		patientBaseInfo
				.setNation(pid.getPid10_Race(0).getCe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0)
				.getXad1_StreetAddress().getSad1_StreetOrMailingAddress()
				.getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City()
				.getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0)
				.getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0)
				.getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0)
				.getXtn7_PhoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0)
				.getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus()
				.getCe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber()
				.getCx1_ID().getValue());
		patientBaseInfo.setCertificateType(pid.getPid18_PatientAccountNumber()
				.getCx2_CheckDigit().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient()
				.getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());

		ca.uhn.hl7v2.model.v24.segment.NK1 nk1 = axx.getNK1();

		patientBaseInfo.setContactName(nk1.getNk12_NKName(0)
				.getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship()
				.getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0)
				.getXad1_StreetAddress().getSad1_StreetOrMailingAddress()
				.getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0)
				.getXtn7_PhoneNumber().getValue());

		ca.uhn.hl7v2.model.v24.segment.AL1 al1 = axx.getAL1();
		patientBaseInfo.setAllergyHistory(al1
				.getAl13_AllergenCodeMnemonicDescription().getCe2_Text()
				.getValue());

		return patientBaseInfo;
	}

	private static PatientBaseInfo v25Parser(String message, Parser parser)
			throws HL7Exception {
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();

		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(
				ca.uhn.hl7v2.model.v25.message.ADT_AXX.class));

		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v25.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v25.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v25.segment.PID pid = axx.getPID();

		patientBaseInfo.setPatientId(pid.getPid3_PatientIdentifierList(0)
				.getCx1_IDNumber().getValue());
		patientBaseInfo.setPracticeNo(pid.getPid2_PatientID().getCx1_IDNumber()
				.getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName()
				.getFn1_Surname().getValue());
		try {
			patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateTimeOfBirth()
					.getTs2_DegreeOfPrecision().getValue()));
		} catch (ParseException e) {
		}
		patientBaseInfo.setSex(pid.getPid8_AdministrativeSex().getValue());
		patientBaseInfo
				.setNation(pid.getPid10_Race(0).getCe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0)
				.getXad1_StreetAddress().getSad1_StreetOrMailingAddress()
				.getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City()
				.getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0)
				.getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0)
				.getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0)
				.getXtn1_TelephoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0)
				.getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus()
				.getCe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber()
				.getCx1_IDNumber().getValue());
		patientBaseInfo.setCertificateType(pid.getPid18_PatientAccountNumber()
				.getCx2_CheckDigit().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient()
				.getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());

		ca.uhn.hl7v2.model.v25.segment.NK1 nk1 = axx.getNK1();

		patientBaseInfo.setContactName(nk1.getNk12_NKName(0)
				.getXpn1_FamilyName().getFn1_Surname().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship()
				.getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0)
				.getXad1_StreetAddress().getSad1_StreetOrMailingAddress()
				.getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0)
				.getXtn1_TelephoneNumber().getValue());

		ca.uhn.hl7v2.model.v25.segment.AL1 al1 = axx.getAL1();
		patientBaseInfo.setAllergyHistory(al1
				.getAl13_AllergenCodeMnemonicDescription().getCe2_Text()
				.getValue());

		return patientBaseInfo;
	}

	private static PatientBaseInfo v251Parser(String message, Parser parser)
			throws HL7Exception {
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();

		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(
				ca.uhn.hl7v2.model.v251.message.ADT_AXX.class));

		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v251.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v251.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v251.segment.PID pid = axx.getPID();

		patientBaseInfo.setPatientId(pid.getPid3_PatientIdentifierList(0)
				.getCx1_IDNumber().getValue());
		patientBaseInfo.setPracticeNo(pid.getPid2_PatientID().getCx1_IDNumber()
				.getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName()
				.getFn1_Surname().getValue());
		try {
			patientBaseInfo.setBirthday(sdf.parse(pid.getPid7_DateTimeOfBirth()
					.getTs2_DegreeOfPrecision().getValue()));
		} catch (ParseException e) {
		}
		patientBaseInfo.setSex(pid.getPid8_AdministrativeSex().getValue());
		patientBaseInfo
				.setNation(pid.getPid10_Race(0).getCe2_Text().getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0)
				.getXad1_StreetAddress().getSad1_StreetOrMailingAddress()
				.getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City()
				.getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0)
				.getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0)
				.getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0)
				.getXtn1_TelephoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0)
				.getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus()
				.getCe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber()
				.getCx1_IDNumber().getValue());
		patientBaseInfo.setCertificateType(pid.getPid18_PatientAccountNumber()
				.getCx2_CheckDigit().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient()
				.getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());

		ca.uhn.hl7v2.model.v251.segment.NK1 nk1 = axx.getNK1();

		patientBaseInfo.setContactName(nk1.getNk12_Name(0).getXpn1_FamilyName()
				.getFn1_Surname().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship()
				.getCe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0)
				.getXad1_StreetAddress().getSad1_StreetOrMailingAddress()
				.getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0)
				.getXtn1_TelephoneNumber().getValue());

		ca.uhn.hl7v2.model.v251.segment.AL1 al1 = axx.getAL1();
		patientBaseInfo.setAllergyHistory(al1
				.getAl13_AllergenCodeMnemonicDescription().getCe2_Text()
				.getValue());

		return patientBaseInfo;
	}

	private static PatientBaseInfo v26Parser(String message, Parser parser)
			throws HL7Exception {
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();

		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(
				ca.uhn.hl7v2.model.v26.message.ADT_AXX.class));

		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v26.message.ADT_AXX axx = (ca.uhn.hl7v2.model.v26.message.ADT_AXX) msg;
		ca.uhn.hl7v2.model.v26.segment.PID pid = axx.getPID();

		patientBaseInfo.setPatientId(pid.getPid3_PatientIdentifierList(0)
				.getCx1_IDNumber().getValue());
		patientBaseInfo.setPracticeNo(pid.getPid2_PatientID().getCx1_IDNumber()
				.getValue());
		patientBaseInfo.setName(pid.getPid5_PatientName(0).getXpn1_FamilyName()
				.getFn1_Surname().getValue());
		patientBaseInfo.setBirthday(pid.getPid7_DateTimeOfBirth()
				.getValueAsDate());
		patientBaseInfo.setSex(pid.getPid8_AdministrativeSex().getValue());
		patientBaseInfo.setNation(pid.getPid10_Race(0).getCwe2_Text()
				.getValue());
		patientBaseInfo.setAddress(pid.getPid11_PatientAddress(0)
				.getXad1_StreetAddress().getSad1_StreetOrMailingAddress()
				.getValue());
		patientBaseInfo.setCity(pid.getPid11_PatientAddress(0).getXad3_City()
				.getValue());
		patientBaseInfo.setProvince(pid.getPid11_PatientAddress(0)
				.getXad4_StateOrProvince().getValue());
		patientBaseInfo.setNationlity(pid.getPid11_PatientAddress(0)
				.getXad6_Country().getValue());
		patientBaseInfo.setTelephone(pid.getPid13_PhoneNumberHome(0)
				.getXtn1_TelephoneNumber().getValue());
		patientBaseInfo.setWorkUnit(pid.getPid14_PhoneNumberBusiness(0)
				.getXtn9_AnyText().getValue());
		patientBaseInfo.setMaritalStatus(pid.getPid16_MaritalStatus()
				.getCwe2_Text().getValue());
		patientBaseInfo.setCertificateNo(pid.getPid18_PatientAccountNumber()
				.getCx1_IDNumber().getValue());
		patientBaseInfo.setCertificateType(pid.getPid18_PatientAccountNumber()
				.getCx2_IdentifierCheckDigit().getValue());
		patientBaseInfo.setMedicalAccount(pid.getPid19_SSNNumberPatient()
				.getValue());
		patientBaseInfo.setBirthPlace(pid.getPid23_BirthPlace().getValue());

		ca.uhn.hl7v2.model.v26.segment.NK1 nk1 = ((ca.uhn.hl7v2.model.v26.message.ADT_A01) msg)
				.getNK1();

		patientBaseInfo.setContactName(nk1.getNk12_Name(0).getXpn1_FamilyName()
				.getFn1_Surname().getValue());
		patientBaseInfo.setContactRelation(nk1.getNk13_Relationship()
				.getCwe2_Text().getValue());
		patientBaseInfo.setContactAddress(nk1.getNk14_Address(0)
				.getXad1_StreetAddress().getSad1_StreetOrMailingAddress()
				.getValue());
		patientBaseInfo.setContactTelephone(nk1.getNk15_PhoneNumber(0)
				.getXtn1_TelephoneNumber().getValue());

		ca.uhn.hl7v2.model.v26.segment.AL1 al1 = ((ca.uhn.hl7v2.model.v26.message.ADT_A01) msg)
				.getAL1();
		patientBaseInfo.setAllergyHistory(al1
				.getAl13_AllergenCodeMnemonicDescription().getCwe2_Text()
				.getValue());

		return patientBaseInfo;
	}
}
