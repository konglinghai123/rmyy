package com.ewcms.web.servlet;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.webservice.service.IPatientWebService;

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
 * 查询患者基本信息Servlet
 * 
 * @author wu_zhijun
 */
@WebServlet(urlPatterns = "/servlet/queryPatient", asyncSupported = false)
public class PatientQueryServlet extends HohServlet {

	private static final long serialVersionUID = 8222293165976114636L;
	
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PatientQueryServlet.class);
			
	@Autowired
	private IPatientWebService patientWebService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		/*
		 * 必须初始化ReceivingApplication实现类，才能处理传入的消息
		 */
		setApplication(new PatientQueryApplication());
	}
	
	/**
	 * 实际处理过程 
	 * 
	 * @author wu_zhijun
	 */
	private class PatientQueryApplication implements ReceivingApplication{

		private HapiContext hapiContext;

		@Override
		public boolean canProcess(Message theMessage) {
			return true;
		}

		@Override
		public Message processMessage(Message theMessage, Map<String, Object> theMetadata) throws ReceivingApplicationException, HL7Exception {
			if (LOG.isDebugEnabled()){
				LOG.debug("PatientQueryServlet : " + theMessage.encode());
			}
			
			hapiContext = new DefaultHapiContext();
			
			String version = theMessage.getVersion();
			
			String adrStr = patientWebService.queryPatient(theMessage.encode(), version, "T", "er7");
			
			//建立MCF，设置HL7消息的版本
			CanonicalModelClassFactory mcf = new CanonicalModelClassFactory(version);
			hapiContext.setModelClassFactory(mcf);
			
			//生成解析
			PipeParser parser = hapiContext.getPipeParser();
			Message response = parser.parse(adrStr);
			
			return response;
		}
		
	}
}
