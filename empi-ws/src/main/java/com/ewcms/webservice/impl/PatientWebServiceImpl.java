package com.ewcms.webservice.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.service.PracticeCardService;
import com.ewcms.webservice.IPatientWebService;

/**
 *
 * @author wu_zhijun
 */
@Service("patientWebService")
@WebService(endpointInterface= "com.ewcms.webservice.IPatientWebService", targetNamespace = "http://ewcms.com/patient")
public class PatientWebServiceImpl implements IPatientWebService {

	@Autowired
	private PracticeCardService practiceCardService;
	
	@Override
	public String sayHello(String name) {
        return "欢迎 " + name + "，调用WebService服务....";
   }

	@Override
	public Long findPatientId(String practiceNo, Boolean deleted) {
		return practiceCardService.findPatientIdByPracticeNo(practiceNo, deleted);
	}

}
