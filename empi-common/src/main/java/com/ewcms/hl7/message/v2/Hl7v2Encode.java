package com.ewcms.hl7.message.v2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.parser.Parser;

/**
 * HL7V2解码
 * 
 * <ul>
 * <li>practiceNo:患者卡号(PID-2)</li>
 * <li>id:患者ID号(PID-3)</li>
 * <li>name:患者姓名(PID-5)</li>
 * <li>birthday:出生日期/时间(PID-7)</li>
 * <li>sex:性别(PID-8)</li>
 * <li>nation:民族(PID-10)</li>
 * <li>address:患者地址(PID-11-1)</li>
 * <li>city:市(PID-11-3)</li>
 * <li>province:省(PID-11-4)</li>
 * <li>nationlity:国籍(PID-11-6)</li>
 * <li>telephone:电话(PID-13)</li>
 * <li>workUnit:工作单位(PID-14-9)</li>
 * <li>maritalStatus:婚姻状况(PID-16)</li>
 * <li>certificateNo:患者身份证号(PID-18)</li>
 * <li>medicalAccount:患者社保号(PID-19)</li>
 * <li>birthPlace:出生地(PID-23)
 * <li>contactName:联系人姓名(NK1-2)</li>
 * <li>contactRelation:联系人关系(NK1-3)</li>
 * <li>contactAddress:联系人地址(NK1-4)</li>
 * <li>contactTelephone:联系人电话(NK1-5)</li>
 * <li>allergyHistory:过敏史(AL1-3-2)</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
public class Hl7v2Encode {

	private Parser parser = new DefaultHapiContext().getXMLParser();

	private String practiceNo;
	private String id;
	private String name;
	private Date birthday;
	private String sex;
	private String nation;
	private String address;
	private String province;
	private String city;
	private String birthPlace;
	private String nationlity;
	private String telephone;
	private String workUnit;
	private String maritalStatus;
	private String certificateNo;
	private String certificateType;
	private String medicalAccount;
	private String contactName;
	private String contactTelephone;
	private String contactRelation;
	private String contactAddress;
	private String allergyHistory;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Hl7v2Encode(String practiceNo, String id, String name,
			Date birthday, String sex, String nation, String address,
			String province, String city, String birthPlace,
			String nationlity, String telephone, String workUnit, String maritalStatus,
			String certificateNo, String certificateType, String medicalAccount,
			String contactName, String contactTelephone, String contactRelation, 
			String contactAddress, String allergyHistory) {
		super();

		this.practiceNo = practiceNo;
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.sex = sex;
		this.nation = nation;
		this.address = address;
		this.province = province;
		this.city = city;
		this.birthPlace = birthPlace;
		this.nationlity = nationlity;
		this.telephone = telephone;
		this.workUnit = workUnit;
		this.maritalStatus = maritalStatus;
		this.certificateNo = certificateNo;
		this.certificateType = certificateType;
		this.medicalAccount = medicalAccount;
		this.contactName = contactName;
		this.contactTelephone = contactTelephone;
		this.contactRelation = contactRelation;
		this.contactAddress = contactAddress;
		this.allergyHistory = allergyHistory;
	}

	/**
	 * v2.1的版本解码
	 * 
	 * @return
	 * @throws HL7Exception
	 * @throws IOException
	 */
	public String v21Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v21.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v21.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
//		ca.uhn.hl7v2.model.v21.segment.MSH mshSegment = adt.getMSH();
//		mshSegment.getSENDINGAPPLICATION().setValue("EMPI");
//		mshSegment.getSEQUENCENUMBER().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v21.segment.PID pid = adt.getPID();

		pid.getPid2_PATIENTIDEXTERNALEXTERNALID().getCk1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PATIENTIDINTERNALINTERNALID().getCk1_IDNumber().setValue(getId());
		pid.getPid5_PATIENTNAME().getPn1_FamilyName().setValue(getName());
		pid.getPid7_DATEOFBIRTH().setValue(sdf.format(getBirthday()));
		pid.getPid8_SEX().setValue(getSex());
		pid.getPid10_ETHNICGROUP().setValue(getNation());
		pid.getPid11_PATIENTADDRESS().getAd1_StreetAddress().setValue(getAddress());
		pid.getPid11_PATIENTADDRESS().getAd3_City().setValue(getCity());
		pid.getPid11_PATIENTADDRESS().getAd4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PATIENTADDRESS().getAd6_Country().setValue(getNationlity());
		pid.getPid13_PHONENUMBERHOME(0).setValue(getTelephone());
		pid.getPid14_PHONENUMBERBUSINESS(0).setValue(getWorkUnit());
		pid.getPid16_MARITALSTATUS().setValue(getMaritalStatus());
		pid.getPid18_PATIENTACCOUNTNUMBER().getCk1_IDNumber().setValue(getCertificateNo());
		pid.getPid18_PATIENTACCOUNTNUMBER().getCk2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNUMBERPATIENT().setValue(getMedicalAccount());
		//TODO birthPlace未找到定义的PID
		
		ca.uhn.hl7v2.model.v21.segment.NK1 nk1 = adt.getNK1();
		
		nk1.getNk12_NEXTOFKINNAME().getPn1_FamilyName().setValue(getContactName());
		nk1.getNk13_NEXTOFKINRELATIONSHIP().setValue(getContactRelation());
		nk1.getNk14_NEXTOFKINADDRESS().getAd1_StreetAddress().setValue(getContactAddress());
		nk1.getNk15_NEXTOFKINPHONENUMBER(0).setValue(getContactTelephone());
		
		//TODO allergyHistory过敏史未设置
		//TOTO familyHistory家族史未设置

		return parser.encode(adt);
	}

	/**
	 * v2.2的版本解码
	 * 
	 * @return
	 * @throws HL7Exception
	 * @throws IOException
	 */
	public String v22Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v22.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v22.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
//		ca.uhn.hl7v2.model.v22.segment.MSH mshSegment = adt.getMSH();
//		mshSegment.getSendingApplication().setValue("EMPI");
//		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v22.segment.PID pid = adt.getPID();

		pid.getPid2_PatientIDExternalID().getCk1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIDInternalID(0).getCm_pat_id1_IDNumber().setValue(getId());
		pid.getPid5_PatientName().getPn1_FamilyName().setValue(getName());
		pid.getPid7_DateOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_Sex().setValue(getSex());
		pid.getPid10_Race().setValue(getNation());
		pid.getPid11_PatientAddress(0).getAd1_StreetAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getAd3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getAd4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getAd6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCk1_IDNumber().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCk2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SocialSecurityNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());

		ca.uhn.hl7v2.model.v22.segment.NK1 nk1 = adt.getNK1();
		
		nk1.getNk12_NKName().getPn1_FamilyName().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address().getAd1_StreetAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).setValue(getContactTelephone());

		ca.uhn.hl7v2.model.v22.segment.AL1 al1 = adt.getAL1();
		
		al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());

		return parser.encode(adt);
	}

	public String v23Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v23.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v23.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
//		ca.uhn.hl7v2.model.v23.segment.MSH mshSegment = adt.getMSH();
//		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
//		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v23.segment.PID pid = adt.getPID();

		pid.getPid2_PatientIDExternalID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIDInternalID(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().setValue(getName());
		pid.getPid7_DateOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_Sex().setValue(getSex());
		pid.getPid10_Race().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus(0).setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_ID().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());

		ca.uhn.hl7v2.model.v23.segment.NK1 nk1 = adt.getNK1();
		
		nk1.getNk12_NKName(0).getXpn1_FamilyName().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().setValue(getContactTelephone());

		ca.uhn.hl7v2.model.v23.segment.AL1 al1 = adt.getAL1();
		
		al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());
		
		return parser.encode(adt);
	}

	public String v231Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v231.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v231.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");
//		ca.uhn.hl7v2.model.v231.message.ORU_R01 oru = new ca.uhn.hl7v2.model.v231.message.ORU_R01();
//		oru.initQuickstart("ORU", "R01", "p");

		// 填充MSH段
//		ca.uhn.hl7v2.model.v231.segment.MSH mshSegment = adt.getMSH();
//		ca.uhn.hl7v2.model.v231.segment.MSH mshSegment = oru.getMSH();
//		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
//		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v231.segment.PID pid = adt.getPID();
//		ca.uhn.hl7v2.model.v231.segment.PID pid = oru.getPIDPD1NK1NTEPV1PV2ORCOBRNTEOBXNTECTI().getPIDPD1NK1NTEPV1PV2().getPID();

		pid.getPid2_PatientID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyLastName().getFn1_FamilyName().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_Sex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_ID().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());

//		ca.uhn.hl7v2.model.v231.segment.NK1 nk1 = oru.getPIDPD1NK1NTEPV1PV2ORCOBRNTEOBXNTECTI().getPIDPD1NK1NTEPV1PV2().getNK1();
		ca.uhn.hl7v2.model.v231.segment.NK1 nk1 = adt.getNK1();
		
		nk1.getNk12_NKName(0).getXpn1_FamilyLastName().getFn1_FamilyName().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().setValue(getContactTelephone());

		ca.uhn.hl7v2.model.v231.segment.AL1 al1 = adt.getAL1();
		
		al1.getAl13_AllergyCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());

		return parser.encode(adt);
	}

	public String v24Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v24.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v24.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
//		ca.uhn.hl7v2.model.v24.segment.MSH mshSegment = adt.getMSH();
//		mshSegment.getMsh3_SendingApplication().getHd1_NamespaceID().setValue("HIS");
//		mshSegment.getMsh4_SendingFacility().getHd1_NamespaceID().setValue("RIH");
//		mshSegment.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue("EKG");
//		mshSegment.getMsh6_ReceivingFacility().getHd1_NamespaceID().setValue("EKG");
		
		// 填充PID段
		ca.uhn.hl7v2.model.v24.segment.PID pid = adt.getPID();

		pid.getPid2_PatientID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(nation);
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_ID().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());
		
		ca.uhn.hl7v2.model.v24.segment.NK1 nk1 = adt.getNK1();
		
		nk1.getNk12_NKName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn7_PhoneNumber().setValue(getContactTelephone());

		ca.uhn.hl7v2.model.v24.segment.AL1 al1 = adt.getAL1();
		al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());

		return parser.encode(adt);
	}

	public String v25Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v25.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v25.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
//		ca.uhn.hl7v2.model.v25.segment.MSH mshSegment = adt.getMSH();
//		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
//		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v25.segment.PID pid = adt.getPID();

		pid.getPid2_PatientID().getCx1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());

		ca.uhn.hl7v2.model.v25.segment.NK1 nk1 = adt.getNK1();
		
		nk1.getNk12_NKName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().setValue(getContactTelephone());

		ca.uhn.hl7v2.model.v25.segment.AL1 al1 = adt.getAL1();
		al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());

		return parser.encode(adt);
	}

	public String v251Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v251.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v251.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
//		ca.uhn.hl7v2.model.v251.segment.MSH mshSegment = adt.getMSH();
//		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
//		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v251.segment.PID pid = adt.getPID();

		pid.getPid2_PatientID().getCx1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());
		
		ca.uhn.hl7v2.model.v251.segment.NK1 nk1 = adt.getNK1();
		
		nk1.getNk12_Name(0).getXpn1_FamilyName().getFn1_Surname().setValue(getContactName());
		nk1.getNk13_Relationship().getCe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().setValue(getContactTelephone());

		ca.uhn.hl7v2.model.v251.segment.AL1 al1 = adt.getAL1();
		al1.getAl13_AllergenCodeMnemonicDescription().getCe2_Text().setValue(getAllergyHistory());

		return parser.encode(adt);
	}

	public String v26Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v26.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v26.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");
		
		// 填充MSH段
//		ca.uhn.hl7v2.model.v26.segment.MSH mshSegment = adt.getMSH();
//		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
//		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v26.segment.PID pid = adt.getPID();
		
		pid.getPid2_PatientID().getCx1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().setValue(getBirthday());
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCwe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCwe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_IdentifierCheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());
		
		ca.uhn.hl7v2.model.v26.segment.NK1 nk1 = adt.getNK1();
		
		nk1.getNk12_Name(0).getXpn1_FamilyName().getFn1_Surname().setValue(getContactName());
		nk1.getNk13_Relationship().getCwe2_Text().setValue(getContactRelation());
		nk1.getNk14_Address(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getContactAddress());
		nk1.getNk15_PhoneNumber(0).getXtn1_TelephoneNumber().setValue(getContactTelephone());
		
		ca.uhn.hl7v2.model.v26.segment.AL1 al1 = adt.getAL1();
		al1.getAl13_AllergenCodeMnemonicDescription().getCwe2_Text().setValue(getAllergyHistory());

		return parser.encode(adt);
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public String getPracticeNo() {
		return practiceNo;
	}

	public void setPracticeNo(String practiceNo) {
		this.practiceNo = practiceNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getNationlity() {
		return nationlity;
	}

	public void setNationlity(String nationlity) {
		this.nationlity = nationlity;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getMedicalAccount() {
		return medicalAccount;
	}

	public void setMedicalAccount(String medicalAccount) {
		this.medicalAccount = medicalAccount;
	}
	
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
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
	
	public String getAllergyHistory() {
		return allergyHistory;
	}

	public void setAllergyHistory(String allergyHistory) {
		this.allergyHistory = allergyHistory;
	}

	@Override
	public String toString() {
		return "Hl7v2Encode [practiceNo=" + practiceNo + ", id=" + id
				+ ", name=" + name + ", birthday=" + birthday + ", sex=" + sex
				+ ", nation=" + nation + ", address=" + address + ", province="
				+ province + ", city=" + city + ", birthPlace=" + birthPlace
				+ ", nationlity=" + nationlity + ", telephone=" + telephone
				+ ", workUnit=" + workUnit + ", maritalStatus=" + maritalStatus
				+ ", certificateNo=" + certificateNo + ", certificateType="
				+ certificateType + ", medicalAccount=" + medicalAccount
				+ ", contactName=" + contactName + ", contactTelephone="
				+ contactTelephone + ", contactRelation=" + contactRelation
				+ ", contactAddress=" + contactAddress + ", allergyHistory="
				+ allergyHistory + "]";
	}
}
