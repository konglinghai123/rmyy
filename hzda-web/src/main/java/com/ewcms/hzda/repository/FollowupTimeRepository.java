package com.ewcms.hzda.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.FollowupTime;

public interface FollowupTimeRepository extends BaseRepository<FollowupTime, Long>{
	
	@Query("select f.generalInformationId from FollowupTime f where (f.nextTime>=?1 and f.nextTime<=?2) and f.tip=true order by f.nextTime desc")
	Set<Long> findByLastMonth(Date startDate, Date endDate);
}
