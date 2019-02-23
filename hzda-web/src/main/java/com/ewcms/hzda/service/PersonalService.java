package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Personal;
import com.ewcms.hzda.repository.PersonalRepository;

@Service
public class PersonalService extends BaseService<Personal, Long> {

	private PersonalRepository getPersonalRepository() {
		return (PersonalRepository) baseRepository;
	}
	
	public Personal findByGeneralInformationId(Long generalInformationId) {
		return getPersonalRepository().findByGeneralInformationId(generalInformationId);
	}
}
