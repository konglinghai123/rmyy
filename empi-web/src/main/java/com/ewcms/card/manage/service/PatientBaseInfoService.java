package com.ewcms.card.manage.service;

import org.springframework.stereotype.Service;

import com.ewcms.card.manage.entity.PatientBaseInfo;
import com.ewcms.card.manage.repository.PatientBaseInfoRepository;
import com.ewcms.common.service.BaseService;

/**
 *@author zhoudongchu
 */
@Service
public class PatientBaseInfoService extends BaseService<PatientBaseInfo, Long> {

	private PatientBaseInfoRepository getPatientBaseInfoRepository() {
		return (PatientBaseInfoRepository) baseRepository;
	}
	
	@Override
	public PatientBaseInfo save(PatientBaseInfo m) {
		PatientBaseInfo patientBaseInfo = super.save(m);
		patientBaseInfo.setPatientId(String.format("%0"+ PatientBaseInfo.patientIdlength +"d", patientBaseInfo.getId()));
		return super.update(patientBaseInfo);
	}

	@Override
	public PatientBaseInfo update(PatientBaseInfo m) {
		PatientBaseInfo patientBaseInfo = findOne(m.getId());
		m.setPatientId(patientBaseInfo.getPatientId());
		return super.update(m);
	}
	
	public PatientBaseInfo findByCertificateNo(String certificateNo){
		return getPatientBaseInfoRepository().findByCertificateNo(certificateNo);
	}
	public PatientBaseInfo findByCertificateNoAndCertificateTypeId(String certificateNo,Long certificateTypeId){
		return getPatientBaseInfoRepository().findByCertificateNoAndCertificateTypeId(certificateNo, certificateTypeId);
	}
}
