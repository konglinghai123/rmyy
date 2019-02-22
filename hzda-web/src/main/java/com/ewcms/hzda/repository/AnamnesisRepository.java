package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.Anamnesis;

public interface AnamnesisRepository extends BaseRepository<Anamnesis, Long> {

	Anamnesis findByGeneralInformationId(Long generalInformationId);
}
