package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Anamnesis;
import com.ewcms.hzda.repository.AnamnesisRepository;

@Service
public class AnamnesisService extends BaseService<Anamnesis, Long> {
	
	private AnamnesisRepository getAnamnesisRepository() {
		return (AnamnesisRepository) baseRepository;
	}
	
	public Anamnesis findByGeneralInformationId(Long generalInformationId) {
		return getAnamnesisRepository().findByGeneralInformationId(generalInformationId);
	}
}
