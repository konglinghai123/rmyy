package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Complained;
import com.ewcms.hzda.repository.ComplainedRepository;

@Service
public class ComplainedService extends BaseService<Complained, Long> {

	private ComplainedRepository getComplainedRepository() {
		return (ComplainedRepository) baseRepository;
	}
	
	public Complained findByGeneralInformationId(Long generalInformationId) {
		return getComplainedRepository().findByGeneralInformationId(generalInformationId);
	}
}
