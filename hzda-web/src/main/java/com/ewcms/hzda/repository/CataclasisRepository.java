package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.Cataclasis;

public interface CataclasisRepository extends BaseRepository<Cataclasis, Long> {

	Cataclasis findByGeneralInformationId(Long generalInformationId);
}
