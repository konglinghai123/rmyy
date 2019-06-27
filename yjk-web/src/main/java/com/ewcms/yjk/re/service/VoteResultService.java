package com.ewcms.yjk.re.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.re.repository.VoteResultRepository;
import com.ewcms.yjk.sb.entity.DrugForm;

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
	
	public List<VoteMonitor> findVoteMonitor(List<Long> userIds){
		return getVoteResultRepository().findVoteMonitor(userIds);
	}
	
	public List<DrugForm> findSelectedDrugForm(Long reviewMainId,String declareCategory){
		return getVoteResultRepository().findSelectedDrugForm(reviewMainId, declareCategory);
	}
	
	public List<VoteResult> findByReviewMainIdAndReviewProcessIdOrderByPassSumDesc(Long reviewMainId, Long reviewProcessId){
		return getVoteResultRepository().findByReviewMainIdAndReviewProcessIdOrderByPassSumDesc(reviewMainId, reviewProcessId);
	}
}
