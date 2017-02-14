package com.ewcms.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 *
 * @author wu_zhijun
 */
@WebService(targetNamespace = "http://ewcms.com/patient")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public interface IPatientWebService {
	
	@WebMethod(operationName = "sayHello")
	@WebResult(name = "out")
	public String sayHello(@WebParam(name = "name", targetNamespace = "http://ewcms.com/patient")String name);
	
	@WebMethod(operationName = "findPatientId")
	@WebResult(name = "patientId")
	public Long findPatientId(@WebParam(name = "practiceNo", targetNamespace = "http://ewcms.com/patient")String practiceNo, @WebParam(name = "deleted")Boolean deleted);
}
