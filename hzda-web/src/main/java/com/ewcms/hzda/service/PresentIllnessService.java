package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.PresentIllness;
import com.ewcms.hzda.repository.PresentIllnessRepository;

@Service
public class PresentIllnessService extends BaseService<PresentIllness, Long> {

	private PresentIllnessRepository getPresentIllnessRepository() {
		return (PresentIllnessRepository) baseRepository;
	}
	
	public PresentIllness findByGeneralInformationId(Long generalInformationId) {
		return getPresentIllnessRepository().findByGeneralInformationId(generalInformationId);
	}
}
