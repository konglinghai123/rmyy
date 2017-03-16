package com.ewcms.hl7v2.message;

import java.util.Date;

import org.junit.Test;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;

import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.hl7v2.defined.MessageTriggerEvent;
import com.ewcms.hl7v2.message.ADTMessage;
import com.ewcms.hl7v2.model.ADTEntity;

/**
 *
 * @author wu_zhijun
 */
public class ADTUtilTest {
	static HapiContext hapiContext = new DefaultHapiContext();

//	@Test
//	public void testHL7(){
//		//DefaultModelClassFactory dmc = new DefaultModelClassFactory();
//		
//		try {
//			Class<? extends Message> clazz = hapiContext.getModelClassFactory().getMessageClass("ADT_A01", "2.4", false);
//			
//			Message message = clazz.newInstance();
//			
//			ADT_A01 adt = (ADT_A01)message;
//			
//			System.out.println(clazz.getSimpleName());
//			System.out.println(AcknowledgmentCode.AA.name());
//		} catch (HL7Exception | InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testADTXML(){
		PatientBaseInfo patientBaseInfo = init();
		String practiceNo = "000000001";
		String version = "v2.4";
		Integer patientIdLen = 10;
		String messageTriggerEvent = MessageTriggerEvent.A04.getTriggerEvent();
		String processingId = "P";
		String style = "xml";

		patientBaseInfo.setPracticeNo(practiceNo);
		
		ADTEntity adtEntity = new ADTEntity();
		adtEntity.setPatientBaseInfo(patientBaseInfo);
		adtEntity.setPracticeNo(practiceNo);
		adtEntity.setMessageTriggerEvent(messageTriggerEvent);
		adtEntity.setProcessingId(processingId);
		adtEntity.setVersion(version);
		adtEntity.setStyle(style);
		adtEntity.setPatientIdLen(patientIdLen);
		
		String message =  ADTMessage.encode(adtEntity);
		System.out.println("XML : " + message);
	}
	
	@Test
	public void testADTER7(){
		PatientBaseInfo patientBaseInfo = init();
		String practiceNo = "000000001";
		String version = "v2.4";
		Integer patientIdLen = 10;
		String messageTriggerEvent = MessageTriggerEvent.A04.getTriggerEvent();
		String processingId = "P";
		String style = "ER7";
		
		patientBaseInfo.setPracticeNo(practiceNo);
		
		ADTEntity adtEntity = new ADTEntity();
		adtEntity.setPatientBaseInfo(patientBaseInfo);
		adtEntity.setPracticeNo(practiceNo);
		adtEntity.setMessageTriggerEvent(messageTriggerEvent);
		adtEntity.setProcessingId(processingId);
		adtEntity.setVersion(version);
		adtEntity.setStyle(style);
		adtEntity.setPatientIdLen(patientIdLen);

		try {
			String message =  ADTMessage.encode(adtEntity);
			System.out.println("ER7 : " + message);
			ADTEntity adtEntityParser = ADTMessage.parser(message, version, style);
			System.out.println(adtEntityParser.getPatientBaseInfo().getPatientId());
			System.out.println(adtEntityParser.getPatientBaseInfo().getName());
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
		patientBaseInfo.setCertificateType("身份证");
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
