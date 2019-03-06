package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.RiskEvaluation;
import com.ewcms.hzda.repository.RiskEvaluationRepository;

@Service
public class RiskEvaluationService extends BaseService<RiskEvaluation, Long> {

	private RiskEvaluationRepository getRiskEvaluationRepository() {
		return (RiskEvaluationRepository) baseRepository;
	}
	
	public RiskEvaluation findByGeneralInformationId(Long generalInformationId) {
		return getRiskEvaluationRepository().findByGeneralInformationId(generalInformationId);
	}
}
