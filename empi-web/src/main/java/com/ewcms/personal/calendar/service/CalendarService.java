package com.ewcms.personal.calendar.service;

import java.sql.Time;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.personal.calendar.entity.Calendar;
import com.ewcms.personal.calendar.repository.CalendarRepository;

/**
 *
 * @author wu_zhijun
 */
@Service
public class CalendarService extends BaseService<Calendar, Long>{

	private CalendarRepository getCalendarRepository(){
		return (CalendarRepository) baseRepository;
	}
	
	public void copyAndRemove(Calendar calendar){
		delete(calendar);
		
		Calendar copyCalendar = new Calendar();
		BeanUtils.copyProperties(calendar, copyCalendar);
		copyCalendar.setId(null);
		save(copyCalendar);
	}
	
	@SuppressWarnings("deprecation")
	public Long countRecentlyCalendar(Long userId, Integer interval){
		Date nowDate = new Date();
        Date nowTime = new Time(nowDate.getHours(), nowDate.getMinutes(), nowDate.getSeconds());
        nowDate.setHours(0);
        nowDate.setMinutes(0);
        nowDate.setSeconds(0);

        return getCalendarRepository().countRecentlyCalendarUseSqlServer(userId, nowDate, nowTime, interval);
	}
}
