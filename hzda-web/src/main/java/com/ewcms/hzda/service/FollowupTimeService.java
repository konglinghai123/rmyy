package com.ewcms.hzda.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.hzda.entity.FollowupTime;
import com.ewcms.hzda.repository.FollowupTimeRepository;

@Service
public class FollowupTimeService extends BaseService<FollowupTime, Long>{
	
	private FollowupTimeRepository getFollowupTimeRepository() {
		return (FollowupTimeRepository) baseRepository;
	}
	
	public Set<Long> findByLastMonth(){
		Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate); 
        
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); 
        Date endDate = calendar.getTime();
        
		return getFollowupTimeRepository().findByLastMonth(startDate, endDate);
	}
	
	public void close(Long id) {
		FollowupTime followupTime = findOne(id);
		if (EmptyUtil.isNotNull(followupTime)) {
			followupTime.setTip(false);
			update(followupTime);
		}
	}
	
}
