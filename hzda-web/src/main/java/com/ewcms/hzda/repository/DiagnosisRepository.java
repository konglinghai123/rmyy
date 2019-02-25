package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.Diagnosis;

public interface DiagnosisRepository  extends BaseRepository<Diagnosis, Long> {
	Diagnosis findByGeneralInformationId(Long generalInformationId);
}
