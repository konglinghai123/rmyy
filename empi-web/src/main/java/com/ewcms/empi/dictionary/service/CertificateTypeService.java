package com.ewcms.empi.dictionary.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.dictionary.entity.CertificateType;
import com.ewcms.empi.dictionary.repository.CertificateTypeRepository;

/**
 *@author zhoudongchu
 */
@Service
public class CertificateTypeService extends BaseService<CertificateType, Long> {
	private CertificateTypeRepository getCertificateTypeRepository(){
		return (CertificateTypeRepository) baseRepository;
	}

	public CertificateType findByName(String name){
		return getCertificateTypeRepository().findByName(name);
	}
}
