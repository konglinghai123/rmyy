package com.ewcms.personal.calendar.service;

import java.sql.Time;
import java.text.SimpleDateFormat;
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
	
	public Long countRecentlyCalendar(Long userId, Integer interval){
		Date now = new Date();
		
        Date nowTime = new Time(now.getHours(), now.getMinutes(), now.getSeconds());
//        nowDate.setHours(0);
//        nowDate.setMinutes(0);
//        nowDate.setSeconds(0);
		
		SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = formatterDate.format(now);
		
		java.util.Calendar futureCalendar = java.util.Calendar.getInstance();
		futureCalendar.setTime(now);
		futureCalendar.add(java.util.Calendar.DAY_OF_YEAR, interval);
		String futureDate = formatterDate.format(futureCalendar.getTime());
        
		java.util.Calendar pastCalendar = java.util.Calendar.getInstance();
		pastCalendar.setTime(now);
		pastCalendar.add(java.util.Calendar.DAY_OF_YEAR, -interval);
		String pastDate = formatterDate.format(futureCalendar.getTime());
        
		
        return getCalendarRepository().countRecentlyCalendar(userId, nowDate, nowTime, futureDate, pastDate);
	}
}
