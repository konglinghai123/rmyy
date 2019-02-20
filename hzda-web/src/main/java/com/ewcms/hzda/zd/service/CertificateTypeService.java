package com.ewcms.hzda.zd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.zd.entity.CertificateType;
import com.ewcms.hzda.zd.repository.CertificateTypeRepository;

/**
 * 
 * @author wuzhijun
 *
 */
@Service
public class CertificateTypeService extends BaseService<CertificateType, Long> {

	private CertificateTypeRepository getCertificateTypeRepository() {
		return (CertificateTypeRepository) baseRepository;
	}
	
	public List<CertificateType> findByName(String name){
		return getCertificateTypeRepository().findByName(name);
	}
}
