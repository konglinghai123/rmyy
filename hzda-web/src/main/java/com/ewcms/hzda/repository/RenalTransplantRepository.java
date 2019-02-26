package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.RenalTransplant;

public interface RenalTransplantRepository extends BaseRepository<RenalTransplant, Long> {
	RenalTransplant findByGeneralInformationId(Long generalInformationId);
}