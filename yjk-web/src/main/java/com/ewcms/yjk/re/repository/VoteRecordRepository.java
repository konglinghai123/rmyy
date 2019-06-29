package com.ewcms.yjk.re.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteRecord;

public interface VoteRecordRepository extends BaseRepository<VoteRecord, Long> {
	
	Long countByUserIdAndReviewProcessId(Long userId, Long reviewProcessId);
	
	Long countByUserIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewProcessId);
	
	VoteRecord findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewMainId, Long drugFormId, Long reviewProcessId);
	
	@Query("select distinct v.userId from VoteRecord v where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.submitted=true")
	List<Long> findSubmittedUserIds(Long reviewMainId, Long reviewProcessId);
	
	//查询中止投票
	List<VoteRecord> findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIdIsNullAndCommonNameContentsIdIsNull(Long userId, Long reviewMainId, Long reviewProcessId);
	
	//查询当前用户未签字数量
	Long countByUserIdAndReviewMainIdAndReviewProcessIdAndSignedFalse(Long userId, Long reviewMainId, Long reviewProcessId);
	
	@Modifying
	@Query("update VoteRecord v set v.signed=true where v.userId=?1 and v.reviewMainId=?2 and v.reviewProcessId=?3")
	void updateSigned(Long userId, Long reviewMainId, Long reviewProcessId);

	//查询当前用户投票结果
	List<VoteRecord> findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIsNotNull(Long userId, Long reviewMainId, Long reviewProcessId);
	
}
