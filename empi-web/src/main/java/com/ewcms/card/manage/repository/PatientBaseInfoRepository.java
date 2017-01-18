package com.ewcms.card.manage.repository;

import com.ewcms.card.manage.entity.PatientBaseInfo;
import com.ewcms.common.repository.BaseRepository;

/**
 *@author zhoudongchu
 */
public interface PatientBaseInfoRepository extends BaseRepository<PatientBaseInfo, Long> {
	PatientBaseInfo findByCertificateNo(String certificateNo);
	PatientBaseInfo findByCertificateNoAndCertificateTypeId(String certificateNo,Long certificateTypeId);
}
