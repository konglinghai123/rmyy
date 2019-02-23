package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Operation;
import com.ewcms.hzda.repository.OperationRepository;

@Service
public class OperationService extends BaseService<Operation, Long> {

	private OperationRepository getOperationRepository() {
		return (OperationRepository) baseRepository;
	}
	
	public Operation findByGeneralInformationId(Long generalInformationId) {
		return getOperationRepository().findByGeneralInformationId(generalInformationId);
	}
}
