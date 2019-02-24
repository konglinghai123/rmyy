package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.DiagnosticMeasures;
import com.ewcms.hzda.repository.DiagnosticMeasuresRepository;

@Service
public class DiagnosticMeasuresService extends BaseService<DiagnosticMeasures, Long> {
	private DiagnosticMeasuresRepository getDiagnosticMeasuresRepository() {
		return (DiagnosticMeasuresRepository) baseRepository;
	}
	public DiagnosticMeasures findByGeneralInformationId(Long generalInformationId){
		return getDiagnosticMeasuresRepository().findByGeneralInformationId(generalInformationId);
	}
}