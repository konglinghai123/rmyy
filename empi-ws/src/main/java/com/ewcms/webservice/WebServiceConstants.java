package com.ewcms.webservice;

/**
 *
 * @author wu_zhijun
 */
public interface WebServiceConstants {
	
	String PATIENT_TARGETNAMESPACE = "http://webservice.ewcms.com/patient";
	
	String PATIENT_ENDPOINTINTERFACE = "com.ewcms.webservice.service.IPatientWebService";

	String SOAP_Header = "soap:Header";
	
	String AUTH_TARGETNAMESPACE_URI = "http://webservice.ewcms.com/authentication";
	
	String AUTH_AUTHENTICATION = "auth:authentication";
	
	String AUTH_USERNAME = "auth:userName";
	
	String AUTH_NONCE = "auth:nonce";
	
	String AUTH_CREATED = "auth:created";
	
	String AUTH_PASSWORDDIGEST = "auth:password";
	
	
}
