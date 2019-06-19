package com.ewcms.yjk.re.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.re.entity.ReviewExpert;
import com.ewcms.yjk.re.repository.ReviewExpertRepository;

@Service
public class ReviewExpertService extends BaseSequenceMovableService<ReviewExpert, Long> {

	private ReviewExpertRepository getReviewExpertRepository() {
		return (ReviewExpertRepository) baseRepository;
	}
	
	public void changeStatus(List<Long> reviewExpertIds, Boolean newStatus) {
		getReviewExpertRepository().changeStatus(reviewExpertIds, newStatus);
	}
	
}
