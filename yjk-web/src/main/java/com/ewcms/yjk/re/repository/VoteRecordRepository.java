package com.ewcms.yjk.re.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteRecord;

public interface VoteRecordRepository extends BaseRepository<VoteRecord, Long> {
	
	Long countByUserIdAndReviewProcessId(Long userId, Long reviewProcessId);
	Long countByUserIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewProcessId);
	
//	@Query("select new com.ewcms.yjk.re.model.UserVote("
//			+ "v.drugForm,"
//			+ "count("
//			+ ") "
//			+ "from VoteRecord v "
//			+ "where v.submitted=true and "
//			+ "group by v.")
//	Page<UserVote> findUserVote(Pageable pageRequest);
}
