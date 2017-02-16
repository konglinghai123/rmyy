package com.ewcms.hl7.createmessage;

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
 * <li>address:患者地址(PID-11)</li>
 * <li>nationlity:国籍(PID-12)</li>
 * <li>telephone:电话(PID-13)</li>
 * <li>maritalStatus:婚姻状况(PID-16)</li>
 * <li>certificateNo:患者身份证号(PID-18)</li>
 * <li>medicalAccount:患者社保号(PID-19)</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
public class Hl7v2Encode {

	private Parser parser = new DefaultHapiContext().getPipeParser();

	private String practiceNo;
	private String id;
	private String name;
	private Date birthday;
	private String sex;
	private String nation;
	private String address;
	private String nationlity;
	private String telephone;
	private String maritalStatus;
	private String certificateNo;
	private String medicalAccount;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Hl7v2Encode(String practiceNo, String id, String name,
			Date birthday, String sex, String nation, String address,
			String nationlity, String telephone, String maritalStatus,
			String certificateNo, String medicalAccount) {
		super();

		this.practiceNo = practiceNo;
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.sex = sex;
		this.nation = nation;
		this.address = address;
		this.nationlity = nationlity;
		this.telephone = telephone;
		this.maritalStatus = maritalStatus;
		this.certificateNo = certificateNo;
		this.medicalAccount = medicalAccount;
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
		ca.uhn.hl7v2.model.v21.segment.MSH mshSegment = adt.getMSH();
		mshSegment.getSENDINGAPPLICATION().setValue("EMPI");
		mshSegment.getSEQUENCENUMBER().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v21.segment.PID pid = adt.getPID();

		pid.getPid2_PATIENTIDEXTERNALEXTERNALID().getCk1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PATIENTIDINTERNALINTERNALID().getCk1_IDNumber().setValue(getId());
		pid.getPid5_PATIENTNAME().getPn1_FamilyName().setValue(getName());
		pid.getPid7_DATEOFBIRTH().setValue(sdf.format(getBirthday()));
		pid.getPid8_SEX().setValue(getSex());
		pid.getPid10_ETHNICGROUP().setValue(getNation());
		pid.getPid11_PATIENTADDRESS().getAd1_StreetAddress().setValue(getAddress());
		pid.getPid12_COUNTYCODE().setValue(getNationlity());
		pid.getPid13_PHONENUMBERHOME(0).setValue(getTelephone());
		pid.getPid16_MARITALSTATUS().setValue(getMaritalStatus());
		pid.getPid18_PATIENTACCOUNTNUMBER().getCk1_IDNumber().setValue(getCertificateNo());
		pid.getPid19_SSNNUMBERPATIENT().setValue(getMedicalAccount());

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
		ca.uhn.hl7v2.model.v22.segment.MSH mshSegment = adt.getMSH();
		mshSegment.getSendingApplication().setValue("EMPI");
		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v22.segment.PID pid = adt.getPID();

		pid.getPid2_PatientIDExternalID().getCk1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIDInternalID(0).getCm_pat_id1_IDNumber().setValue(getId());
		pid.getPid5_PatientName().getPn1_FamilyName().setValue(getName());
		pid.getPid7_DateOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_Sex().setValue(getSex());
		pid.getPid10_Race().setValue(getNation());
		pid.getPid11_PatientAddress(0).getAd1_StreetAddress().setValue(getAddress());
		pid.getPid12_CountyCode().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).setValue(getTelephone());
		pid.getPid16_MaritalStatus().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCk1_IDNumber().setValue(getCertificateNo());
		pid.getPid19_SocialSecurityNumberPatient().setValue(getMedicalAccount());

		return parser.encode(adt);
	}

	public String v23Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v23.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v23.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
		ca.uhn.hl7v2.model.v23.segment.MSH mshSegment = adt.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v23.segment.PID pid = adt.getPID();

		pid.getPid2_PatientIDExternalID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIDInternalID(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().setValue(getName());
		pid.getPid7_DateOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_Sex().setValue(getSex());
		pid.getPid10_Race().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().setValue(getAddress());
		pid.getPid12_CountyCode().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().setValue(getTelephone());
		pid.getPid16_MaritalStatus(0).setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_ID().setValue(getCertificateNo());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());

		return parser.encode(adt);
	}

	public String v231Encode() throws HL7Exception, IOException {
//		ca.uhn.hl7v2.model.v231.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v231.message.ADT_A01();
//		adt.initQuickstart("ADT", "A01", "P");
		ca.uhn.hl7v2.model.v231.message.ORU_R01 oru = new ca.uhn.hl7v2.model.v231.message.ORU_R01();
		oru.initQuickstart("ORU", "R01", "p");

		// 填充MSH段
//		ca.uhn.hl7v2.model.v231.segment.MSH mshSegment = adt.getMSH();
		ca.uhn.hl7v2.model.v231.segment.MSH mshSegment = oru.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
//		ca.uhn.hl7v2.model.v231.segment.PID pid = adt.getPID();
		ca.uhn.hl7v2.model.v231.segment.PID pid = oru.getPIDPD1NK1NTEPV1PV2ORCOBRNTEOBXNTECTI().getPIDPD1NK1NTEPV1PV2().getPID();

		pid.getPid2_PatientID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyLastName().getFamilyName().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_Sex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().setValue(getAddress());
		pid.getPid12_CountyCode().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().setValue(getTelephone());
		pid.getPid16_MaritalStatus().getCe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getID().setValue(getCertificateNo());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());

		return parser.encode(oru);
	}

	public String v24Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v24.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v24.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
		ca.uhn.hl7v2.model.v24.segment.MSH mshSegment = adt.getMSH();
		
		mshSegment.getMsh3_SendingApplication().getHd1_NamespaceID().setValue("EMPI");
		mshSegment.getMsh3_SendingApplication().getHd2_UniversalID().setValue("1.0");
		mshSegment.getMsh3_SendingApplication().getHd3_UniversalIDType().setValue("JAVA");
		mshSegment.getMsh13_SequenceNumber().setValue("1");

		// 填充PID段
		ca.uhn.hl7v2.model.v24.segment.PID pid = adt.getPID();

		pid.getPid2_PatientID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(nation);
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid12_CountyCode().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().setValue(getTelephone());
		pid.getPid16_MaritalStatus().getCe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_ID().setValue(getCertificateNo());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());

		return parser.encode(adt);
	}

	public String v25Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v25.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v25.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
		ca.uhn.hl7v2.model.v25.segment.MSH mshSegment = adt.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v25.segment.PID pid = adt.getPID();

		pid.getPid2_PatientID().getCx1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid12_CountyCode().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().setValue(getTelephone());
		pid.getPid16_MaritalStatus().getCe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());

		return parser.encode(adt);
	}

	public String v251Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v251.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v251.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
		ca.uhn.hl7v2.model.v251.segment.MSH mshSegment = adt.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v251.segment.PID pid = adt.getPID();

		pid.getPid2_PatientID().getCx1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue(sdf.format(getBirthday()));
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid12_CountyCode().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().setValue(getTelephone());
		pid.getPid16_MaritalStatus().getCe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());

		return parser.encode(adt);
	}

	public String v26Encode() throws HL7Exception, IOException {
		ca.uhn.hl7v2.model.v26.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v26.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");

		// 填充MSH段
		ca.uhn.hl7v2.model.v26.segment.MSH mshSegment = adt.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("EMPI");
		mshSegment.getSequenceNumber().setValue("empi-1.0");

		// 填充PID段
		ca.uhn.hl7v2.model.v26.segment.PID pid = adt.getPID();

		pid.getPid2_PatientID().getCx1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().setValue(getBirthday());
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCwe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid12_CountyCode().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().setValue(getTelephone());
		pid.getPid16_MaritalStatus().getCwe2_Text().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());

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

	public String getMedicalAccount() {
		return medicalAccount;
	}

	public void setMedicalAccount(String medicalAccount) {
		this.medicalAccount = medicalAccount;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
