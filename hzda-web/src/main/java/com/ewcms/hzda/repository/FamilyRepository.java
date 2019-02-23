package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.Family;

public interface FamilyRepository extends BaseRepository<Family, Long> {
	
	Family findByGeneralInformationId(Long generalInformationId);
}
