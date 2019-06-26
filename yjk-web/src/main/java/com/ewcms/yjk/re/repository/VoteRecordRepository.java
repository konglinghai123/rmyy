package com.ewcms.yjk.re.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteRecord;

public interface VoteRecordRepository extends BaseRepository<VoteRecord, Long> {
	
	Long countByUserIdAndReviewProcessId(Long userId, Long reviewProcessId);
	Long countByUserIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewProcessId);
	
	VoteRecord findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewMainId, Long drugFormId, Long reviewProcessId);
	
	@Query("select distinct v.userId from VoteRecord v where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.submitted=true")
	List<Long> findUserSubmitted(Long reviewMainId, Long reviewProcessId);
}
