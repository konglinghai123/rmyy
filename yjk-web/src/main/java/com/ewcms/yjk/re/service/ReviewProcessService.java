package com.ewcms.yjk.re.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.repository.ReviewProcessRepository;

@Service
public class ReviewProcessService extends BaseSequenceMovableService<ReviewProcess, Long>{

	private ReviewProcessRepository getReviewProcessRepository() {
		return (ReviewProcessRepository) baseRepository;
	}
	
	@Override
	public ReviewProcess save(ReviewProcess m) {
		ReviewProcess dbReviewProcess = findByReviewMainAndId(m.getReviewMain(), m.getId());
		if (dbReviewProcess != null) {
			m.setId(dbReviewProcess.getId());
		}
		return super.save(m);
	}
	
	public ReviewProcess findByReviewMainAndId(ReviewMain reviewMain, Long ReviewProcessId) {
		return getReviewProcessRepository().findByReviewMainAndId(reviewMain, ReviewProcessId);
	}
	
	@Override
	public ReviewProcess update(ReviewProcess m) {
		ReviewProcess dbReviewProcess = findByReviewMainAndId(m.getReviewMain(), m.getId());
		if (dbReviewProcess != null) {
			m.setId(dbReviewProcess.getId());
		}
		return super.update(m);
	}
}
