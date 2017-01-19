package com.ewcms.empi.dictionary.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.dictionary.entity.CertificateType;

/**
 *@author zhoudongchu
 */
public interface CertificateTypeRepository extends BaseRepository<CertificateType, Long> {
	CertificateType findByName(String name);
}
