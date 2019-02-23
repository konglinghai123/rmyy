package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.FractureOther;
import com.ewcms.hzda.repository.FractureOtherRepository;

@Service
public class FractureOtherService extends BaseService<FractureOther, Long> {
	private FractureOtherRepository getFractureOtherRepository() {
		return (FractureOtherRepository) baseRepository;
	}
	public FractureOther findByGeneralInformationId(Long generalInformationId){
		return getFractureOtherRepository().findByGeneralInformationId(generalInformationId);
	}
}
