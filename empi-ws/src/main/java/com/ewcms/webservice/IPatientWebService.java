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
	 * 查询患者(普通方法)
	 * 
	 * @param practiceNo 卡号
	 * @return 患者ID号
	 */
	@WebMethod(operationName = "queryPatient")
	@WebResult(name = "patientId")
	public String queryPatient(@WebParam(name = "practiceNo", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String practiceNo);

	/**
	 * 根据患者卡号组合成HL7格式的QRY
	 * 
	 * @param practiceNo 患者卡号
	 * @param version 版本号
	 * @param style 样式
	 * @return
	 */
	@WebMethod(operationName = "combinationQRY")
	@WebResult(name = "hl7Result")
	public String combinationQRY(@WebParam(name = "practiceNo", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String practiceNo,
			@WebParam(name = "version", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String version, 
			@WebParam(name = "processingId", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String processingId, 
			@WebParam(name = "style", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String style);
	
	/**
	 * 查询患者(HL7格式)
	 * 
	 * @param qryMessage HL7格式，使用QRY_A19做为消息传递 
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param processingId 消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param style 样式(ER7或XML)
	 * @return
	 */
	@WebMethod(operationName = "queryPatientToHl7")
	@WebResult(name = "hl7Result")
	public String queryPatientToHl7(@WebParam(name = "qryMessage", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String qryMessage,
			@WebParam(name = "version", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String version,
			@WebParam(name = "processingId", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String processingId, 
			@WebParam(name = "style", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String style);
	
	/**
	 * 患者登记（组合成HL7消息）
	 * 
	 * @param adtMessage 传入的HL7消息
	 * @return HL7消息
	 */
	@WebMethod(operationName = "registerPatient")
	@WebResult(name = "hl7Result")
	public String registerPatient(
			@WebParam(name = "adtMessage", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String adtMessage,
			@WebParam(name = "version", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String version, 
			@WebParam(name = "style", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String style);
}
