package com.ewcms.hzda.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.hzda.entity.FollowupTime;
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.repository.FollowupTimeRepository;
import com.ewcms.sms.SmsUtil;

@Service
public class FollowupTimeService extends BaseService<FollowupTime, Long>{
	
	private FollowupTimeRepository getFollowupTimeRepository() {
		return (FollowupTimeRepository) baseRepository;
	}
	
	@Autowired
	private GeneralInformationService generalInformationService;
	@Autowired
	private SmsUtil smsUtil;
	
	public Set<Long> findByLastMonth(){
		Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate); 
        
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); 
        Date endDate = calendar.getTime();
        
		return getFollowupTimeRepository().findByLastMonth(startDate, endDate);
	}
	
	public Set<FollowupTime> findSmsByLastMonth(){
		Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate); 
        
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); 
        Date endDate = calendar.getTime();
        
		return getFollowupTimeRepository().findSmsByLastMonth(startDate, endDate);
	}
	
	
	public void close(Long id) {
		FollowupTime followupTime = findOne(id);
		if (EmptyUtil.isNotNull(followupTime)) {
			followupTime.setTip(false);
			update(followupTime);
		}
	}
	
	public void updateSms(FollowupTime followupTime) {
		if (followupTime.getNextTime() == null) return;
		
		Date smsDate = new Date();
		if (followupTime.getNextTime().getTime() < (new Date()).getTime()) {
			followupTime.setCode("");
			followupTime.setMessage("提醒设置时间已过不能发送提醒短信");
			followupTime.setRequestId("");
			followupTime.setSms(true);
			followupTime.setSmsDate(smsDate);
			
			update(followupTime);
			return;
		}
		
		Long generalInformationId = followupTime.getGeneralInformationId();
		GeneralInformation generalInformation = generalInformationService.findOne(generalInformationId);
		if (generalInformation == null) return;
		String phone = generalInformation.getMobilePhoneNumber();
		String name = generalInformation.getName();
		String organizationName = generalInformation.getOrganizationName();
		String telephone = generalInformation.getTelephone();
		
		if (EmptyUtil.isStringEmpty(phone)) {
			followupTime.setCode("isv.MOBILE_NUMBER_ILLEGAL");
			followupTime.setMessage("无效号码");
			followupTime.setRequestId("27d7d838-9387-4f10-a12f-b8e398e3d2a7");
			followupTime.setSms(true);
			followupTime.setSmsDate(smsDate);
			
			update(followupTime);
		} else {
			String param = "{\"name\":\"" + name + "\", \"date\":\"" + followupTime.getNextTime() + "\" ,\"organizationName\":\"" + organizationName + "\"";
			Boolean isTelephone = Boolean.FALSE;
			if (EmptyUtil.isStringNotEmpty(telephone)) {
				isTelephone = Boolean.TRUE;
				param += ", \"telephone\":\"" + telephone + "\"";
			}
			param += "}";
			
			String data = smsUtil.sendSms(phone, param, isTelephone);
			if (EmptyUtil.isStringNotEmpty(data)) {
				JSONObject jsonObject = JSON.parseObject(data);
				followupTime.setCode(jsonObject.getString("Code"));
				followupTime.setMessage(jsonObject.getString("Message"));
				followupTime.setRequestId(jsonObject.getString("RequestId"));
				
				followupTime.setSms(true);
				followupTime.setSmsDate(smsDate);
				
				update(followupTime);
			}
		}
	}
}
