package com.ewcms.hl7.message.v2;

import java.io.IOException;
import java.util.Date;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

import ca.uhn.hl7v2.HL7Exception;

/**
 *
 * @author wu_zhijun
 */
public class CreatePatientMessage {

	public static String createPatient(PatientBaseInfo patientBaseInfo, String practiceNo, String version) throws HL7Exception, IOException {
		if (patientBaseInfo == null)
			return "";

		String id = String.format("%020d", patientBaseInfo.getId()); // 患者ID号
		String name = patientBaseInfo.getName(); // 姓名
		String sex = patientBaseInfo.getSex(); // 性别
		Date birthday = patientBaseInfo.getBirthday(); // 出生日期
		String sourcePlace = patientBaseInfo.getSourcePlace(); // 来源地
		String certificateType = patientBaseInfo.getCertificateType(); // 证件类别
		String certificateNo = patientBaseInfo.getCertificateNo(); // 证件号码
		String telephone = patientBaseInfo.getTelephone(); // 联系电话
		String contactName = patientBaseInfo.getContactName(); // 联系人姓名
		String workUnit = patientBaseInfo.getWorkUnit(); // 工作单位
		String address = patientBaseInfo.getAddress(); // 地址
		String province = patientBaseInfo.getProvince(); // 省
		String city = patientBaseInfo.getCity(); //市
		String birthPlace = patientBaseInfo.getBirthPlace(); // 出生地
		String nation = patientBaseInfo.getNation(); // 民族
		String nationlity = patientBaseInfo.getNationlity(); // 国籍
		String profession = patientBaseInfo.getProfession(); // 职业
		String maritalStatus = patientBaseInfo.getMaritalStatus(); // 婚姻状况
		String medicalType = patientBaseInfo.getMedicalType(); // 医保类别
		String medicalOrganize = patientBaseInfo.getMedicalOrganize(); // 医保机构
		String medicalAccount = patientBaseInfo.getMedicalAccount(); // 医保账号
		String patientType = patientBaseInfo.getPatientType(); // 病人类别
		String contactTelephone = patientBaseInfo.getContactTelephone(); // 联系人电话
		String contactRelation = patientBaseInfo.getContactRelation(); // 与联系人关系
		String contactAddress = patientBaseInfo.getContactAddress(); // 联系人地址
		String allergyHistory = patientBaseInfo.getAllergyHistory(); // 过敏史
		String familyHistory = patientBaseInfo.getFamilyHistory(); // 家族史

		Hl7v2Encode hl7v2Encode = new Hl7v2Encode(practiceNo, id, name,
				birthday, sex, nation, address, province, city, birthPlace, nationlity,
				telephone, workUnit, maritalStatus, certificateNo, medicalAccount,
				contactName, contactTelephone, contactRelation, contactAddress,
				allergyHistory);

		if ("v2.1".equals(version) || "2.1".equals(version)) {
			return hl7v2Encode.v21Encode();
		} else if ("v2.2".equals(version) || "2.2".equals(version)) {
			return hl7v2Encode.v22Encode();
		} else if ("v2.3".equals(version) || "2.3".equals(version)) {
			return hl7v2Encode.v23Encode();
		} else if ("v2.3.1".equals(version) || "2.3.1".equals(version)) {
			return hl7v2Encode.v231Encode();
		} else if ("v2.4".equals(version) || "2.4".equals(version)) {
			return hl7v2Encode.v24Encode();
		} else if ("v2.5".equals(version) || "2.5".equals(version)) {
			return hl7v2Encode.v25Encode();
		} else if ("v2.5.1".equals(version) || "2.5.1".equals(version)) {
			return hl7v2Encode.v251Encode();
		} else if ("v2.6".equals(version) || "2.6".equals(version)) {
			return hl7v2Encode.v26Encode();
		} else {
			return "";
		}
	}
}
