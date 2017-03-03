package com.ewcms.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import com.ewcms.WebServiceConstants;

/**
 *
 * @author wu_zhijun
 */
@WebService(targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE)
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public interface IPatientWebService {

	/**
	 * 根据患者卡号组合成HL7格式的QRY消息
	 * 
	 * @param practiceNo 患者卡号
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param processingId 消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param style 样式(ER7或XML)
	 * @return QRY消息
	 */
	@WebMethod(operationName = "compositePracticeNo")
	@WebResult(name = "hl7Result")
	public String compositePracticeNo(@WebParam(name = "practiceNo", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String practiceNo,
			@WebParam(name = "version", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String version, 
			@WebParam(name = "processingId", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String processingId, 
			@WebParam(name = "style", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String style);
	
	/**
	 * 根据QRY消息查询患者索引号
	 * 
	 * @param qryMessage QRY消息
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param processingId 消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param style 样式(ER7或XML)
	 * @return ADR消息
	 */
	@WebMethod(operationName = "queryPatient")
	@WebResult(name = "hl7Result")
	public String queryPatient(@WebParam(name = "qryMessage", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String qryMessage,
			@WebParam(name = "version", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String version,
			@WebParam(name = "processingId", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String processingId, 
			@WebParam(name = "style", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String style);
	
	/**
	 * 注册患者信息
	 * 
	 * @param qryMessage ADT消息
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param processingId 消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param style 样式(ER7或XML)
	 * @return ACK消息
	 */
	@WebMethod(operationName = "registerPatient")
	@WebResult(name = "hl7Result")
	public String registerPatient(
			@WebParam(name = "adtMessage", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String adtMessage,
			@WebParam(name = "version", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String version,
			@WebParam(name = "processingId", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String processingId, 
			@WebParam(name = "style", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String style);
}
