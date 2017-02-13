package com.ewcms.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.hoh.hapi.server.HohServlet;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

/**
 * 接收HL7 V2 的MSH消息
 * 
 * @author wu_zhijun
 */
@WebServlet(urlPatterns = "/hl7v2", asyncSupported = false)
public class Hl7v2Servlet extends HohServlet {

	private static final long serialVersionUID = 8222293165976114636L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		/*
		 * 必须初始化ReceivingApplication实现类，才能处理传入的消息
		 */
		setApplication(new EmpiHL7V2Application());
	}
	
	/**
	 * 实际处理过程 
	 * 
	 * @author wu_zhijun
	 */
	private class EmpiHL7V2Application implements ReceivingApplication{

		private HapiContext context;

		@Override
		public boolean canProcess(Message theMessage) {
			return true;
		}

		/**
		 * 
		 */
		@Override
		public Message processMessage(Message theMessage, Map<String, Object> theMetadata) throws ReceivingApplicationException, HL7Exception {
			System.out.println("Received message:\n" + theMessage.encode());
			
			context = new DefaultHapiContext();
			
			//建立MCF，设置默认解析HL7消息的版本为2.5.1
			CanonicalModelClassFactory mcf = new CanonicalModelClassFactory("2.4");
			context.setModelClassFactory(mcf);
			
			//生成解析
			PipeParser parser = context.getPipeParser();
			
			ca.uhn.hl7v2.model.v23.message.ADT_A04 msg = (ca.uhn.hl7v2.model.v23.message.ADT_A04)parser.parse(theMessage.encode());
			
			String messageControlID = msg.getMSH().getMessageControlID().getValue();
			
			System.out.println("Message Control ID : " + messageControlID);
			
			//回复消息
			Message response;
			try{
				response = theMessage.generateACK();
			} catch (IOException e){
				throw new ReceivingApplicationException(e);
			}
			
			boolean somethingFailed = false;
			if (somethingFailed){
				throw new ReceivingApplicationException("");
			}
			
			if (somethingFailed){
				try{
					response = theMessage.generateACK(AcknowledgmentCode.AE,  new HL7Exception("The was a problem!!"));
				} catch (IOException e){
					throw new ReceivingApplicationException(e);
				}
			}
			return response;
		}
		
	}
}
