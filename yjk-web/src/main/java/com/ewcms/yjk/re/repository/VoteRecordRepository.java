package com.ewcms.yjk.re.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteRecord;

public interface VoteRecordRepository extends BaseRepository<VoteRecord, Long> {
	
	Long countByUserIdAndReviewProcessId(Long userId, Long reviewProcessId);
	Long countByUserIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewProcessId);
	
	VoteRecord findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewMainId, Long drugFormId, Long reviewProcessId);
}
