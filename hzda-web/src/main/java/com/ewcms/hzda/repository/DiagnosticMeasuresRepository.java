package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.DiagnosticMeasures;


public interface DiagnosticMeasuresRepository extends BaseRepository<DiagnosticMeasures, Long> {
	DiagnosticMeasures findByGeneralInformationId(Long generalInformationId);
}
