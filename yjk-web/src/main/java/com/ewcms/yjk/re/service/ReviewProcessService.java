package com.ewcms.yjk.re.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.repository.ReviewProcessRepository;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;

@Service
public class ReviewProcessService extends BaseSequenceMovableService<ReviewProcess, Long>{

	public ReviewProcessService() {
		super(1);
	}
	
	private ReviewProcessRepository getReviewProcessRepository() {
		return (ReviewProcessRepository) baseRepository;
	}
	
	public ReviewProcess findByReviewMainAndReviewBaseRule(ReviewMain reviewMain, ReviewBaseRule reviewBaseRule) {
		return getReviewProcessRepository().findByReviewMainAndReviewBaseRule(reviewMain, reviewBaseRule);
	}
	
	public ReviewProcess findByReviewMainIdAndReviewBaseRuleId(Long reviewMainId, Long reviewBaseRuleId) {
		return getReviewProcessRepository().findByReviewMainIdAndReviewBaseRuleId(reviewMainId, reviewBaseRuleId);
	}
	
	@Override
	public ReviewProcess save(ReviewProcess m) {
		ReviewProcess dbReviewProcess = findByReviewMainAndReviewBaseRule(m.getReviewMain(), m.getReviewBaseRule());
		return (dbReviewProcess == null) ? super.save(m) : null;
	}
	
	@Override
	public ReviewProcess update(ReviewProcess m) {
		ReviewProcess dbReviewProcess = findByReviewMainAndReviewBaseRule(m.getReviewMain(), m.getReviewBaseRule());
		return (dbReviewProcess != null && m.getId().equals(dbReviewProcess.getId())) ? super.update(m) : null;
		
	}
}
