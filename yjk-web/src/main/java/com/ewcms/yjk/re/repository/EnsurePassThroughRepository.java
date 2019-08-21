package com.ewcms.yjk.re.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.EnsurePassThrough;

public interface EnsurePassThroughRepository extends BaseRepository<EnsurePassThrough, Long>{
	
	@Modifying
	@Query("update EnsurePassThrough o set enabled=?2 where o.id in (?1)")
	void changeStatus(List<Long> ensurePassThroughIds, Boolean newStatus);
	
	List<EnsurePassThrough> findByReviewProcessIdAndEnabledTrueOrderByWeightAsc(Long reviewProcessId);
}
