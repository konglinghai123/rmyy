package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.Examined;

public interface ExaminedRepository extends BaseRepository<Examined, Long> {
	Examined findByGeneralInformationId(Long generalInformationId);
}
