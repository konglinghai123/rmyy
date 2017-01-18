package com.ewcms.card.manage.repository;

import com.ewcms.card.manage.entity.CertificateType;
import com.ewcms.common.repository.BaseRepository;

/**
 *@author zhoudongchu
 */
public interface CertificateTypeRepository extends BaseRepository<CertificateType, Long> {
	CertificateType findByName(String name);
}
