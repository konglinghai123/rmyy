package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Family;
import com.ewcms.hzda.repository.FamilyRepository;

@Service
public class FamilyService extends BaseService<Family, Long> {

	private FamilyRepository getFamilyRepository() {
		return (FamilyRepository) baseRepository;
	}
	
	public Family findByGeneralInformationId(Long generalInformationId) {
		return getFamilyRepository().findByGeneralInformationId(generalInformationId);
	}
}
