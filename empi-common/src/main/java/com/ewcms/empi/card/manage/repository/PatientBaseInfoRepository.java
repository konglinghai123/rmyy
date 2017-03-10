package com.ewcms.empi.card.manage.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;

/**
 *@author zhoudongchu
 */
public interface PatientBaseInfoRepository extends BaseRepository<PatientBaseInfo, Long> {
	List<PatientBaseInfo> findByCertificateNo(String certificateNo);
	List<PatientBaseInfo> findByCertificateNoAndCertificateTypeOrderByUpdateDateDesc(String certificateNo,String certificateType);
	List<PatientBaseInfo> findRepeatByMatchRule(List<MatchRule> matchRuleList);
	List<PatientBaseInfo> findNoRepeatByMatchRule(List<MatchRule> matchRuleList);
}
