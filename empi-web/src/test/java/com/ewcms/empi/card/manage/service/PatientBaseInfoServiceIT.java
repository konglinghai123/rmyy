package com.ewcms.empi.card.manage.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.BaseIT;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.google.common.collect.Lists;

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
	
	@Test
	public void testFindByMatchRule(){
		List<MatchRule> matchRuleList = Lists.newArrayList();
		MatchRule matchRule1 = new MatchRule();
		matchRule1.setFieldName("name");
		matchRuleList.add(matchRule1);
		MatchRule matchRule2 = new MatchRule();
		matchRule2.setFieldName("telephone");
		matchRuleList.add(matchRule2);	
		List<PatientBaseInfo> patientBaseInfoList = patientBaseInfoService.findRepeatByMatchRule(matchRuleList);
	}
}
