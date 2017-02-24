package com.ewcms.hl7.message.v2;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import ca.uhn.hl7v2.HL7Exception;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

/**
 *
 * @author wu_zhijun
 */
public class PatientMessageTest {

	@Test
	public void testCreateHl7v2(){
		PatientBaseInfo patientBaseInfo = init();
		String practiceNo = "000000001";
		String version = "v2.3";
		Integer patientIdLen = 10;
		
		try {
			String message = PatientMessage.createHl7v2(patientBaseInfo, practiceNo, version, patientIdLen);
			System.out.println("HL7V2 : " + message);
		} catch (HL7Exception | IOException e) {
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void testParserHl7v2(){
		PatientBaseInfo patientBaseInfo = init();
		String practiceNo = "000000001";
		String version = "v2.3";
		Integer patientIdLen = 10;
		
		try {
			String message = PatientMessage.createHl7v2(patientBaseInfo, practiceNo, version, patientIdLen);
			PatientBaseInfo vo = PatientMessage.parserHl7v2(message, version);
			System.out.println(vo.getPatientId());
			System.out.println(vo.getName());
		} catch (HL7Exception | IOException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	private PatientBaseInfo init(){
		PatientBaseInfo patientBaseInfo = new PatientBaseInfo();
		
		patientBaseInfo.setId(3L);
		patientBaseInfo.setName("测试数据");
		patientBaseInfo.setSex("男");
		patientBaseInfo.setBirthday(new Date());
		patientBaseInfo.setSourcePlace("南昌");
		patientBaseInfo.setCertificateNo("360102198002195312");
		patientBaseInfo.setTelephone("13177840351");
		patientBaseInfo.setContactName("数据测试");
		patientBaseInfo.setWorkUnit("测试工作");
		patientBaseInfo.setAddress("地址测试");
		patientBaseInfo.setProvince("江西省");
		patientBaseInfo.setCity("南昌");
		patientBaseInfo.setBirthPlace("南昌");
		patientBaseInfo.setNation("汉族");
		patientBaseInfo.setNationlity("中国");
		patientBaseInfo.setProfession("IT工程师");
		patientBaseInfo.setMaritalStatus("已婚");
		patientBaseInfo.setMedicalAccount("000000000002");
		patientBaseInfo.setPatientType("省医保");
		patientBaseInfo.setContactTelephone("13007910001");
		patientBaseInfo.setContactRelation("父子");
		patientBaseInfo.setContactAddress("江西南昌");
		patientBaseInfo.setAllergyHistory("无");
		patientBaseInfo.setFamilyHistory("无");
		
		return patientBaseInfo;
	}
}
