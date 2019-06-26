package com.ewcms.yjk.re.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.repository.VoteResultRepository;

@Service
public class VoteResultService extends BaseService<VoteResult, Long> {
	private VoteResultRepository getVoteResultRepository(){
		return (VoteResultRepository)baseRepository;
	}
	
	
	public Long countByReviewProcessId(Long reviewProcessId){
		return getVoteResultRepository().countByReviewProcessId(reviewProcessId);
	}
	
	public VoteResult findByDrugFormIdAndReviewProcessId(Long drugFormId, Long reviewProcessId){
		return getVoteResultRepository().findByDrugFormIdAndReviewProcessId(drugFormId, reviewProcessId);
	}
}
