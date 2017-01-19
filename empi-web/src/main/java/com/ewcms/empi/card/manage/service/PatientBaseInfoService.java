package com.ewcms.empi.card.manage.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.repository.PatientBaseInfoRepository;

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
		PatientBaseInfo dbPatientBaseInfo = findByCertificateNo(m.getCertificateNo());
		if (EmptyUtil.isNotNull(dbPatientBaseInfo))throw new BaseException("证件号码已存在");
		
		PatientBaseInfo patientBaseInfo = super.save(m);
		patientBaseInfo.setPatientId(String.format("%0"+ PatientBaseInfo.patientIdlength +"d", patientBaseInfo.getId()));
		return super.update(patientBaseInfo);
	}

	@Override
	public PatientBaseInfo update(PatientBaseInfo m) {
		PatientBaseInfo dbPatientBaseInfo = null;
		dbPatientBaseInfo = findByCertificateNo(m.getCertificateNo());
		if (EmptyUtil.isNotNull(dbPatientBaseInfo))throw new BaseException("证件号码已存在");
		
		dbPatientBaseInfo = findOne(m.getId());
		if (EmptyUtil.isNull(dbPatientBaseInfo))throw new BaseException("患者信息不存在");
		
		m.setPatientId(dbPatientBaseInfo.getPatientId());
		return super.update(m);
	}
	
	public PatientBaseInfo findByCertificateNo(String certificateNo){
		return getPatientBaseInfoRepository().findByCertificateNo(certificateNo);
	}
	public PatientBaseInfo findByCertificateNoAndCertificateTypeId(String certificateNo,Long certificateTypeId){
		return getPatientBaseInfoRepository().findByCertificateNoAndCertificateTypeId(certificateNo, certificateTypeId);
	}
}
