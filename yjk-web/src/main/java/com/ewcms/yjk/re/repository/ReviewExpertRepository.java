package com.ewcms.yjk.re.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.ReviewExpert;

public interface ReviewExpertRepository extends BaseRepository<ReviewExpert, Long> {

    @Modifying
    @Query("update ReviewExpert o set enabled=?2 where o.id in (?1)")
	void changeStatus(List<Long> reviewExpertIds, Boolean newStatus);
}
