package com.ewcms.empi.card.manage.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

/**
 *@author zhoudongchu
 */
public interface PatientBaseInfoRepository extends BaseRepository<PatientBaseInfo, Long> {
	PatientBaseInfo findByCertificateNo(String certificateNo);
	PatientBaseInfo findByCertificateNoAndCertificateTypeId(String certificateNo,Long certificateTypeId);
}
