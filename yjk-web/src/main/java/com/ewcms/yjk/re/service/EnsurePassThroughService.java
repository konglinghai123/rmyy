package com.ewcms.yjk.re.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.re.entity.EnsurePassThrough;
import com.ewcms.yjk.re.repository.EnsurePassThroughRepository;

@Service
public class EnsurePassThroughService extends BaseSequenceMovableService<EnsurePassThrough, Long>{
	
	private EnsurePassThroughRepository getEnsurePassThroughRepository() {
		return (EnsurePassThroughRepository) baseRepository;
	}
	
	public List<EnsurePassThrough> findByReviewProcessIdAndEnabledTrueOrderByWeightAsc(Long reviewProcessId){
		return getEnsurePassThroughRepository().findByReviewProcessIdAndEnabledTrueOrderByWeightAsc(reviewProcessId);
	}
	
	public void changeStatus(List<Long> ensurePassThroughIds, Boolean newStatus) {
		getEnsurePassThroughRepository().changeStatus(ensurePassThroughIds, newStatus);
	}
}
