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
	
//	 @Query("select count(id) from Calendar "
//	 		+ "where userId=?1 and ("
//	 		+ "(startDate=?2 and (startTime is null or startTime<?3)) or "
//	 		+ "(startDate > ?2 and startDate<=(?2+?4)) or "
//	 		+ "(startDate<?2 and (startDate+length)>?2) or "
//	 		+ "((startDate+length)=?2 and (endTime is null or endTime>?3)))")
//	@Query(value = "select count(id) from per_calendar "
//			+ "where user_id=?1 and ("
//			+ "(start_date=?2 and (start_time is null or start_time<?3)) or "
//			+ "(start_date>?2 and start_date<=(date ?2 + integer ?4)) or "
//			+ "(start_date<?2 and start_date>(date ?2 - integer ?4)) or "
//			+ "(start_date=(date ?2 - integer ?4) and (end_time is null or end_time>?3))"
//			+ ")", nativeQuery = true)
	@Query(value = "select count(id) from per_calendar " + 
			"where user_id=?1 and (" + 
			"(start_date=to_date(?2, 'yyyy-MM-dd') and (start_time is null or start_time<?3)) or " + 
			"(start_date>to_date(?2, 'yyyy-MM-dd') and start_date<=to_date(?4, 'yyyy-MM-dd')) or " + 
			"(start_date<to_date(?2, 'yyyy-MM-dd') and start_date>to_date(?5,'yyyy-MM-dd')) or " + 
			"(start_date=to_date(?5, 'yyyy-MM-dd') and (end_time is null or end_time>?3))" + 
			")", nativeQuery = true)
	Long countRecentlyCalendar(Long userId, String nowDate, Date nowTime, String futureDate, String pastDate);
}
