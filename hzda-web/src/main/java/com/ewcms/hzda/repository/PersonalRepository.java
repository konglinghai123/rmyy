package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.Personal;

public interface PersonalRepository extends BaseRepository<Personal, Long> {
	
	Personal findByGeneralInformationId(Long generalInformationId);
}
