package com.ewcms.yjk.re.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.re.entity.ReviewExpert;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.repository.ReviewExpertRepository;

@Service
public class ReviewExpertService extends BaseSequenceMovableService<ReviewExpert, Long> {

	private ReviewExpertRepository getReviewExpertRepository() {
		return (ReviewExpertRepository) baseRepository;
	}
	
	@Override
	public ReviewExpert save(ReviewExpert m) {
		ReviewExpert dbReviewExpert = findByReviewMainAndId(m.getReviewMain(), m.getId());
		if (dbReviewExpert != null) {
			m.setId(dbReviewExpert.getId());
		}
		return super.save(m);
	}
	
	public ReviewExpert findByReviewMainAndId(ReviewMain reviewMain, Long reviewExpertId) {
		return getReviewExpertRepository().findByReviewMainAndId(reviewMain, reviewExpertId);
	}
	
	@Override
	public ReviewExpert update(ReviewExpert m) {
		ReviewExpert dbReviewExpert = findByReviewMainAndId(m.getReviewMain(), m.getId());
		if (dbReviewExpert != null) {
			m.setId(dbReviewExpert.getId());
		}
		return super.update(m);
	}
	
	public void changeStatus(List<Long> reviewExpertIds, Boolean newStatus) {
		getReviewExpertRepository().changeStatus(reviewExpertIds, newStatus);
	}
	
}
