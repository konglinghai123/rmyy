package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Examined;
import com.ewcms.hzda.repository.ExaminedRepository;

@Service
public class ExaminedService extends BaseService<Examined, Long> {

	private ExaminedRepository getExaminedRepository() {
		return (ExaminedRepository) baseRepository;
	}
	
	public Examined findByGeneralInformationId(Long generalInformationId) {
		return getExaminedRepository().findByGeneralInformationId(generalInformationId);
	}
}