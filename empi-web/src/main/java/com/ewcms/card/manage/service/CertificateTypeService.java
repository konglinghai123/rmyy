package com.ewcms.card.manage.service;

import org.springframework.stereotype.Service;

import com.ewcms.card.manage.entity.CertificateType;
import com.ewcms.card.manage.repository.CertificateTypeRepository;
import com.ewcms.common.service.BaseService;

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
