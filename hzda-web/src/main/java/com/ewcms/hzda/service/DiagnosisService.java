package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;
import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Diagnosis;
import com.ewcms.hzda.repository.DiagnosisRepository;


@Service
public class DiagnosisService extends BaseService<Diagnosis, Long> {

	private DiagnosisRepository getDiagnosisRepository() {
		return (DiagnosisRepository) baseRepository;
	}
	
	public Diagnosis findByGeneralInformationId(Long generalInformationId) {
		return getDiagnosisRepository().findByGeneralInformationId(generalInformationId);
	}
}