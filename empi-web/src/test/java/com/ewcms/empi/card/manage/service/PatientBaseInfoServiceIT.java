package com.ewcms.empi.card.manage.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.BaseIT;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

/**
 *@author zhoudongchu
 */
public class PatientBaseInfoServiceIT extends BaseIT {
	@Autowired
	protected PatientBaseInfoService patientBaseInfoService;
	
	@Test
	public void testMatch(){
		PatientBaseInfo m = new PatientBaseInfo();
		m.setTelephone("18970986851");
		m.setCertificateNo("362222197911084335");
		List<PatientBaseInfo> patientBaseInfoList = patientBaseInfoService.match(m);
	}
}
