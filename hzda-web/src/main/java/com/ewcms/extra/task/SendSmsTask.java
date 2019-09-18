package com.ewcms.extra.task;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.hzda.entity.FollowupTime;
import com.ewcms.hzda.service.FollowupTimeService;

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
	
	public void autoSendSms() {
		Set<FollowupTime> followupTimes = followupTimeService.findSmsByLastMonth();
		if (EmptyUtil.isCollectionNotEmpty(followupTimes)) {
			for (FollowupTime followupTime : followupTimes) {
				followupTimeService.updateSms(followupTime);
			}
		}
	}
}
