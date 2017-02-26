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
	 * 查询患者ID号
	 * 
	 * @param practiceNo
	 *            卡号
	 * @param deleted
	 *            是否删除
	 * @return 患者ID号
	 */
	@WebMethod(operationName = "findPatientId")
	@WebResult(name = "patientId")
	public String findPatientId(
			@WebParam(name = "practiceNo", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String practiceNo);

	/**
	 * 查询患者ID号（组合成HL7消息）
	 * 
	 * @param hl7Message
	 *            传入的HL7消息
	 * @return HL7消息
	 */
	@WebMethod(operationName = "doPatientIdToHl7")
	@WebResult(name = "patientId")
	public String doPatientIdToHl7(
			@WebParam(name = "hl7Message", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String hl7Message,
			@WebParam(name = "version", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String version);

	/**
	 * 查询患者信息
	 * 
	 * @param practiceNo 卡号
	 * @param deleted 是否删除
	 * @param version 版本号(2.2, 2.3, 2.31, 2.4, 2.5, 2.5.1, 2.6)
	 * @return HL7患者信息
	 */
	@WebMethod(operationName = "findPatientIdHl7v2")
	@WebResult(name = "patientIdHl7v2")
	public String findPatientIdHl7v2(
			@WebParam(name = "practiceNo", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String practiceNo,
			@WebParam(name = "version", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String version,
			@WebParam(name = "messageTriggerEvent", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String messageTriggerEvent,
			@WebParam(name = "processingId", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String processingId,
			@WebParam(name = "style", targetNamespace = WebServiceConstants.PATIENT_TARGETNAMESPACE) String style);
}
