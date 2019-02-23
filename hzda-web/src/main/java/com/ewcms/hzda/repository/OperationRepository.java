package com.ewcms.hzda.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.Operation;

public interface OperationRepository extends BaseRepository<Operation, Long> {

	Operation findByGeneralInformationId(Long generalInformationId);
}
