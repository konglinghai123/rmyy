package com.ewcms.hl7v2.segment;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;

/**
 *
 * @author wu_zhijun
 */
public class PIDSegment {
	
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
	
	public PIDSegment(PatientBaseInfo patientBaseInfo, String practiceNo, Integer patientIdLen) {
		this.id = String.format("%0" + patientIdLen + "d", patientBaseInfo.getId()); // 患者ID号
		this.practiceNo = practiceNo;
		this.name = patientBaseInfo.getName(); // 姓名
		this.sex = patientBaseInfo.getSex().getId(); // 性别
		this.birthday = patientBaseInfo.getBirthday(); // 出生日期
		// this.sourcePlace = patientBaseInfo.getSourcePlace(); // 来源地
		this.certificateType = patientBaseInfo.getCertificateType(); // 证件类别
		this.certificateNo = patientBaseInfo.getCertificateNo(); // 证件号码
		this.telephone = patientBaseInfo.getTelephone(); // 联系电话
		this.workUnit = patientBaseInfo.getWorkUnit(); // 工作单位
		this.address = patientBaseInfo.getAddress(); // 地址
		this.province = patientBaseInfo.getProvince(); // 省
		this.city = patientBaseInfo.getCity(); // 市
		this.birthPlace = patientBaseInfo.getBirthPlace(); // 出生地
		this.nation = patientBaseInfo.getNation(); // 民族
		this.nationlity = patientBaseInfo.getCountryCode().getId(); // 国籍
		// this.profession = patientBaseInfo.getProfession(); // 职业
		this.maritalStatus = patientBaseInfo.getMarital().getId(); // 婚姻状况
		// this.medicalType = patientBaseInfo.getMedicalType(); // 医保类别
		// this.medicalOrganize = patientBaseInfo.getMedicalOrganize(); //医保机构
		this.medicalAccount = patientBaseInfo.getMedicalAccount(); // 医保账号
		// this.patientType = patientBaseInfo.getPatientType(); // 病人类别
		// this.familyHistory = patientBaseInfo.getFamilyHistory(); // 家族史
	}

	public void setPidSegment(AbstractSegment pid) throws HL7Exception{
		if (pid instanceof ca.uhn.hl7v2.model.v21.segment.PID){
			setPid_V21((ca.uhn.hl7v2.model.v21.segment.PID)pid);
		}else if (pid instanceof ca.uhn.hl7v2.model.v22.segment.PID){
			setPid_V22((ca.uhn.hl7v2.model.v22.segment.PID)pid);
		}else if (pid instanceof ca.uhn.hl7v2.model.v23.segment.PID){
			setPid_V23((ca.uhn.hl7v2.model.v23.segment.PID)pid);
		}else if (pid instanceof ca.uhn.hl7v2.model.v231.segment.PID){
			setPid_V231((ca.uhn.hl7v2.model.v231.segment.PID)pid);
		}else if (pid instanceof ca.uhn.hl7v2.model.v24.segment.PID){
			setPid_V24((ca.uhn.hl7v2.model.v24.segment.PID)pid);
		}else if (pid instanceof ca.uhn.hl7v2.model.v25.segment.PID){
			setPid_V25((ca.uhn.hl7v2.model.v25.segment.PID)pid);
		}else if (pid instanceof ca.uhn.hl7v2.model.v251.segment.PID){
			setPid_V251((ca.uhn.hl7v2.model.v251.segment.PID)pid);
		}else if (pid instanceof ca.uhn.hl7v2.model.v26.segment.PID){
			setPid_V26((ca.uhn.hl7v2.model.v26.segment.PID)pid);
		}
	}

	/****************************************************************v2.1版本*********************************************************/
	private void setPid_V21(ca.uhn.hl7v2.model.v21.segment.PID pid) throws HL7Exception {
		pid.getPid2_PATIENTIDEXTERNALEXTERNALID().getCk1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PATIENTIDINTERNALINTERNALID().getCk1_IDNumber().setValue(getId());
		pid.getPid5_PATIENTNAME().getPn1_FamilyName().setValue(getName());
		pid.getPid7_DATEOFBIRTH().setValue(DateFormatUtils.format(getBirthday(), "yyyy-MM-dd"));
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
	}

	/****************************************************************v2.2版本*********************************************************/
	private void setPid_V22(ca.uhn.hl7v2.model.v22.segment.PID pid) throws HL7Exception {
		pid.getPid2_PatientIDExternalID().getCk1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIDInternalID(0).getCm_pat_id1_IDNumber().setValue(getId());
		pid.getPid5_PatientName().getPn1_FamilyName().setValue(getName());
		pid.getPid7_DateOfBirth().getTs1_TimeOfAnEvent().setValue(getBirthday());
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
	}
	
	/****************************************************************v2.3版本*********************************************************/
	private void setPid_V23(ca.uhn.hl7v2.model.v23.segment.PID pid) throws HL7Exception {
		pid.getPid2_PatientIDExternalID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIDInternalID(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().setValue(getName());
		pid.getPid7_DateOfBirth().getTs1_TimeOfAnEvent().setValue(getBirthday());
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
	}
	
	/****************************************************************v2.3.1版本*********************************************************/
	private void setPid_V231(ca.uhn.hl7v2.model.v231.segment.PID pid) throws HL7Exception {
		pid.getPid2_PatientID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyLastName().getFn1_FamilyName().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs1_TimeOfAnEvent().setValue(getBirthday());
		pid.getPid8_Sex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCe1_Identifier().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_ID().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());
	}
	
	/****************************************************************v2.4版本*********************************************************/
	private void setPid_V24(ca.uhn.hl7v2.model.v24.segment.PID pid) throws HL7Exception {
		pid.getPid2_PatientID().getCx1_ID().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_ID().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs1_TimeOfAnEvent().setValue(getBirthday());
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn7_PhoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCe1_Identifier().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_ID().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());
	}
	
	/****************************************************************v2.5版本*********************************************************/
	private void setPid_V25(ca.uhn.hl7v2.model.v25.segment.PID pid) throws HL7Exception {
		pid.getPid2_PatientID().getCx1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs1_Time().setValue(getBirthday());
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCe1_Identifier().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());
	}
	
	/****************************************************************v2.5.1版本*********************************************************/
	private void setPid_V251(ca.uhn.hl7v2.model.v251.segment.PID pid) throws HL7Exception {
		pid.getPid2_PatientID().getCx1_IDNumber().setValue(getPracticeNo());
		pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(getId());
		pid.getPid5_PatientName(0).getXpn1_FamilyName().getFn1_Surname().setValue(getName());
		pid.getPid7_DateTimeOfBirth().getTs1_Time().setValue(getBirthday());
		pid.getPid8_AdministrativeSex().setValue(getSex());
		pid.getPid10_Race(0).getCe2_Text().setValue(getNation());
		pid.getPid11_PatientAddress(0).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().setValue(getAddress());
		pid.getPid11_PatientAddress(0).getXad3_City().setValue(getCity());
		pid.getPid11_PatientAddress(0).getXad4_StateOrProvince().setValue(getProvince());
		pid.getPid11_PatientAddress(0).getXad6_Country().setValue(getNationlity());
		pid.getPid13_PhoneNumberHome(0).getXtn1_TelephoneNumber().setValue(getTelephone());
		pid.getPid14_PhoneNumberBusiness(0).getXtn9_AnyText().setValue(getWorkUnit());
		pid.getPid16_MaritalStatus().getCe1_Identifier().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_CheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());
	}
	
	/****************************************************************v2.6版本*********************************************************/
	private void setPid_V26(ca.uhn.hl7v2.model.v26.segment.PID pid) throws HL7Exception{
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
		pid.getPid16_MaritalStatus().getCwe1_Identifier().setValue(getMaritalStatus());
		pid.getPid18_PatientAccountNumber().getCx1_IDNumber().setValue(getCertificateNo());
		pid.getPid18_PatientAccountNumber().getCx2_IdentifierCheckDigit().setValue(getCertificateType());
		pid.getPid19_SSNNumberPatient().setValue(getMedicalAccount());
		pid.getPid23_BirthPlace().setValue(getBirthPlace());
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
}
