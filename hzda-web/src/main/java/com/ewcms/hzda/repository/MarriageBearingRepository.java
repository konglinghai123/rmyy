package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.MarriageBearing;

public interface MarriageBearingRepository extends BaseRepository<MarriageBearing, Long> {

	MarriageBearing findByGeneralInformationId(Long generalInformationId);
}
