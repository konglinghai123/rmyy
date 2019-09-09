package com.ewcms.extra.task;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.hzda.entity.FollowupTime;
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.service.FollowupTimeService;
import com.ewcms.hzda.service.GeneralInformationService;
import com.ewcms.sms.SmsUtil;

/**
 * 定时发送短信服务
 * 
 * @author wuzhijun
 *
 */
@Service("sendSmsTask")
public class SendSmsTask {
	
	@Autowired
	private FollowupTimeService followupTimeService;
	@Autowired
	private GeneralInformationService generalInformationService;
	@Autowired
	private SmsUtil smsUtil;
	
	public void autoSendSms() {
		Set<FollowupTime> followupTimes = followupTimeService.findSmsByLastMonth();
		if (EmptyUtil.isCollectionNotEmpty(followupTimes)) {
			GeneralInformation generalInformation = null;
			for (FollowupTime followupTime : followupTimes) {
				Long generalInformationId = followupTime.getGeneralInformationId();
				generalInformation = generalInformationService.findOne(generalInformationId);
				if (generalInformation == null) continue;
				String phone = generalInformation.getMobilePhoneNumber();
				String name = generalInformation.getName();
				String organizationName = generalInformation.getOrganizationName();
				if (EmptyUtil.isStringEmpty(phone)) {
					followupTime.setCode("isv.MOBILE_NUMBER_ILLEGAL");
					followupTime.setMessage("无效号码");
					followupTime.setRequestId("27d7d838-9387-4f10-a12f-b8e398e3d2a7");
					followupTime.setSms(true);
					followupTime.setSmsDate(new Date());
					
					followupTimeService.update(followupTime);
				} else {
					String param = "{\"name\":\"" + name + "\", \"organizationName\":\"" + organizationName + "\"}";
								
					String data = smsUtil.sendSms(phone, param);
					if (EmptyUtil.isStringNotEmpty(data)) {
						JSONObject jsonObject = JSON.parseObject(data);
						followupTime.setCode(jsonObject.getString("code"));
						followupTime.setMessage(jsonObject.getString("message"));
						followupTime.setRequestId(jsonObject.getString("RequestId"));
						
						followupTime.setSms(true);
						followupTime.setSmsDate(new Date());
						
						followupTimeService.update(followupTime);
					}
				}
			}
		}
	}
}
