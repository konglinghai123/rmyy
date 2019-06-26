package com.ewcms.yjk.re.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteResult;

public interface VoteResultRepository extends BaseRepository<VoteResult, Long> {
	Long countByReviewProcessId(Long reviewProcessId);
	
	VoteResult findByDrugFormIdAndReviewProcessId(Long drugFormId, Long reviewProcessId);
}
