package com.ewcms.personal.calendar.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personal.calendar.entity.Calendar;

/**
 *
 * @author wu_zhijun
 */
public interface CalendarRepository extends BaseRepository<Calendar, Long> {

//	/**
//	 * SQLServer数据库，在使用日期和时间对象做为条件查询时会产生SQL语句错误，所以使用SQLServer本地查询语句
//	 * 
//	 * @param userId
//	 * @param nowDate
//	 * @param nowTime
//	 * @param interval
//	 * @return
//	 */
//	@Query(value = "select count(id) from per_calendar "
//			+ "where user_id=?1 and ("
//			+ "(start_date=left(?2, 10) and (start_time is null or start_time<right(?3, 12))) or "
//			+ "(start_date>left(?2, 10) and start_date<=left(dateadd(day, ?4, ?2), 10)) or "
//			+ "(start_date<left(?2, 10) and start_date>left(dateadd(day, -?4, ?2), 10)) or "
//			+ "(start_date=left(dateadd(day, -?4, ?2), 10) and (end_time is null or end_time>right(?3, 12))))", nativeQuery = true)
//	Long countRecentlyCalendarUseSqlServer(Long userId, Date nowDate, Date nowTime, Integer interval);
	
	 @Query("select count(id) from Calendar where userId=?1 and ((startDate=?2 and (startTime is null or startTime<?3)) or (startDate > ?2 and startDate<=(?2+?4)) or (startDate<?2 and (startDate+length)>?2) or ((startDate+length)=?2 and (endTime is null or endTime>?3)))")
	 Long countRecentlyCalendar(Long userId, Date nowDate, Date nowTime, Integer interval);
}
