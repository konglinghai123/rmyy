package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.PresentIllness;

public interface PresentIllnessRepository extends BaseRepository<PresentIllness, Long> {

	PresentIllness findByGeneralInformationId(Long generalInformationId);
}
