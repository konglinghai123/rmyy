package com.ewcms.hzda.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.FollowupTime;

public interface FollowupTimeRepository extends BaseRepository<FollowupTime, Long>{
	
	@Query("from FollowupTime f where ((f.nextTime>=?1 and f.nextTime<=?2 and f.tip=true) or (f.nextTime<=?1 and f.tip=true)) and f.generalInformation.userId=?3 order by f.nextTime desc, f.id desc")
	Page<FollowupTime> findByLastMonth(Date startDate, Date endDate, Long userId, Pageable pageable);
	
	@Query("from FollowupTime f where f.nextTime>=?1 and f.nextTime<=?2 and f.tip=true and f.sms=false and f.smsDate is null order by f.nextTime desc, f.id desc")
	Set<FollowupTime> findSmsByLastMonth(Date startDate, Date endDate);
}
