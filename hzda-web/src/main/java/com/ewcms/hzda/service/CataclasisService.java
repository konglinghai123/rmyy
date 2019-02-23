package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Cataclasis;
import com.ewcms.hzda.repository.CataclasisRepository;

@Service
public class CataclasisService extends BaseService<Cataclasis, Long> {

	private CataclasisRepository getCataclasisRepository() {
		return (CataclasisRepository) baseRepository;
	}
	
	public Cataclasis findByGeneralInformationId(Long generalInformationId) {
		return getCataclasisRepository().findByGeneralInformationId(generalInformationId);
	}
}
