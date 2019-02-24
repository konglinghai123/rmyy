package com.ewcms.system.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.system.notice.entity.Notice;

public interface NoticeRepository extends BaseRepository<Notice, Long> {

	List<Notice> findByReleaseTrueOrderByWeightAsc();
	
    @Modifying
    @Query("update Notice o set release=?2 where o.id in (?1)")
	void changeStatus(List<Long> noticeIds, Boolean newStatus);
    
    Notice findByTitle(String title);
}
