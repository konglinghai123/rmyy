package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.FractureOther;

public interface FractureOtherRepository extends BaseRepository<FractureOther, Long> {
	FractureOther findByGeneralInformationId(Long generalInformationId);
}
