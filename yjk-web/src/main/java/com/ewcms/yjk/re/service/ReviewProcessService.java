package com.ewcms.yjk.re.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.security.user.entity.User;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.ReviewProcessRecord;
import com.ewcms.yjk.re.repository.ReviewProcessRepository;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;

@Service
public class ReviewProcessService extends BaseSequenceMovableService<ReviewProcess, Long>{

	public ReviewProcessService() {
		super(1);
	}
	
	@Autowired
	private ReviewProcessRecordService reviewProcessRecordService;
	
	private ReviewProcessRepository getReviewProcessRepository() {
		return (ReviewProcessRepository) baseRepository;
	}
	
	public ReviewProcess findByReviewMainAndReviewBaseRule(ReviewMain reviewMain, ReviewBaseRule reviewBaseRule) {
		return getReviewProcessRepository().findByReviewMainAndReviewBaseRule(reviewMain, reviewBaseRule);
	}
	
	public ReviewProcess findByReviewMainIdAndReviewBaseRuleId(Long reviewMainId, Long reviewBaseRuleId) {
		return getReviewProcessRepository().findByReviewMainIdAndReviewBaseRuleId(reviewMainId, reviewBaseRuleId);
	}
	
	public ReviewProcess findByReviewBaseRuleRuleNameAndReviewMainId(String ruleName, Long reviewMainId) {
		return getReviewProcessRepository().findByReviewBaseRuleRuleNameAndReviewMainId(ruleName, reviewMainId);
	}
	
	@Override
	public ReviewProcess save(ReviewProcess m) {
		throw new RuntimeException("discarded method");
	}
	
	public ReviewProcess save(ReviewProcess m, Long userId) {
		ReviewProcess dbReviewProcess = findByReviewMainAndReviewBaseRule(m.getReviewMain(), m.getReviewBaseRule());
		
		ReviewProcess newReviewProcess = null;
		if (dbReviewProcess == null) {
			newReviewProcess = super.save(m);
			
			ReviewProcessRecord reviewProcessRecord = new ReviewProcessRecord(newReviewProcess.getId(), userId, "新增 评审流程");
			reviewProcessRecordService.save(reviewProcessRecord);
		}
		return newReviewProcess;
	}
	
	@Override
	public ReviewProcess update(ReviewProcess m) {
		throw new RuntimeException("discarded method");
	}
	
	public ReviewProcess update(ReviewProcess m, Long userId) {
		ReviewProcess dbReviewProcess = findByReviewMainAndReviewBaseRule(m.getReviewMain(), m.getReviewBaseRule());
		
		if (dbReviewProcess != null && m.getId().equals(dbReviewProcess.getId())) {
			ReviewProcessRecord reviewProcessRecord = new ReviewProcessRecord(m.getId(), userId, "修改 评审流程");
			reviewProcessRecordService.save(reviewProcessRecord);
			
			return super.update(m);
		} else {
			return null;
		}
	}
	
	@Override
	public void down(Long fromId, Long toId) {
		throw new RuntimeException("discarded method");
	}
	
	public void down(Long fromId, Long toId, Long userId) {
		super.down(fromId, toId);
		
		ReviewProcessRecord reviewProcessRecord = null;
		if (fromId.longValue() > toId.longValue()) {
			
		}
		
	}
}
