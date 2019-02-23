package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.MarriageBearing;
import com.ewcms.hzda.repository.MarriageBearingRepository;

@Service
public class MarriageBearingService extends BaseService<MarriageBearing, Long> {
	
	private MarriageBearingRepository getMarriageBearingRepository() {
		return (MarriageBearingRepository) baseRepository;
	}
	
	public MarriageBearing findByGeneralInformationId(Long generalInformationId) {
		return getMarriageBearingRepository().findByGeneralInformationId(generalInformationId);
	}
}
