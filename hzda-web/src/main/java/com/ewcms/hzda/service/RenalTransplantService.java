package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.RenalTransplant;
import com.ewcms.hzda.repository.RenalTransplantRepository;


@Service
public class RenalTransplantService extends BaseService<RenalTransplant, Long> {

	private RenalTransplantRepository getRenalTransplantRepository() {
		return (RenalTransplantRepository) baseRepository;
	}
	
	public RenalTransplant findByGeneralInformationId(Long generalInformationId) {
		return getRenalTransplantRepository().findByGeneralInformationId(generalInformationId);
	}
}