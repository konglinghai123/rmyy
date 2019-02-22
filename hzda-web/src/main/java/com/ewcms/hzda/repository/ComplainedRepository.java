package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.Complained;

public interface ComplainedRepository extends BaseRepository<Complained, Long> {

	Complained findByGeneralInformationId(Long generalInformationId);
}
