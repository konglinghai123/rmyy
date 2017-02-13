package com.ewcms.hl7v2x.createmessage;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.parser.Parser;

/**
 *
 * @author wu_zhijun
 */
public class CreateAMessage {

	public static void main(String[] args) throws Exception{
		ca.uhn.hl7v2.model.v24.message.ADT_A01 adt = new ca.uhn.hl7v2.model.v24.message.ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");
		
		//填充MSH段
		ca.uhn.hl7v2.model.v24.segment.MSH mshSegment = adt.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("TestSendingSystem");
		mshSegment.getSequenceNumber().setValue("123");
		
		//填充PID段
		ca.uhn.hl7v2.model.v24.segment.PID pid = adt.getPID();
		pid.getPatientName(0).getFamilyName().getSurname().setValue("Doe");
		pid.getPatientName(0).getGivenName().setValue("John");
		pid.getPatientIdentifierList(0).getID().setValue("123456");
		
		//消息输出
		HapiContext context = new DefaultHapiContext();
		Parser parser = context.getPipeParser();
		String encodedMessage = parser.encode(adt);
		System.out.println("Printing ER7 Encoded Message:");
		System.out.println(encodedMessage);
		
		//解析成XML
		parser = context.getXMLParser();
		encodedMessage = parser.encode(adt);
		System.out.println("Printing XML Encoded Message:");
		System.out.println(encodedMessage);
		
	}
}
