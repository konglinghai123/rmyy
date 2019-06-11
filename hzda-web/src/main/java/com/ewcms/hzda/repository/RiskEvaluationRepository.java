package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.RiskEvaluation;

public interface RiskEvaluationRepository extends BaseRepository<RiskEvaluation, Long> {

	RiskEvaluation findByGeneralInformationId(Long generalInformationId);
	
}
