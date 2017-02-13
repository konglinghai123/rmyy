package com.ewcms.webservice.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.ewcms.webservice.IPatientService;

/**
 *
 * @author wu_zhijun
 */
@Service("patientService")
@WebService(endpointInterface= "com.ewcms.webservice.IPatientService", targetNamespace = "http://ewcms.com/patient")
public class PatientServiceImpl implements IPatientService {

	@Override
	public String sayHello(String name) {
        return "欢迎 " + name + "，调用WebService服务....";
   }

	@Override
	public Long findByCardId(String cardId) {
		if ("001".equals(cardId)){
			return 1L;
		} else {
			return 0L;
		}
	}

}
