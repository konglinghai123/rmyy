package com.ewcms.empi.card.manage.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.Reflections;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.repository.PatientBaseInfoRepository;

/**
 *@author zhoudongchu
 */
@Service
public class PatientBaseInfoService extends BaseService<PatientBaseInfo, Long> {
	@Autowired
	private MatchRuleService matchRuleService;
	
	private PatientBaseInfoRepository getPatientBaseInfoRepository() {
		return (PatientBaseInfoRepository) baseRepository;
	}
	
	
	@Override
	public PatientBaseInfo save(PatientBaseInfo m) {
		PatientBaseInfo dbPatientBaseInfo = findByCertificateNo(m.getCertificateNo());
		if (EmptyUtil.isNotNull(dbPatientBaseInfo))throw new BaseException("证件号码已存在");		
		return super.save(m);
	}

	@Override
	public PatientBaseInfo update(PatientBaseInfo m) {
		PatientBaseInfo dbPatientBaseInfo = null;
		dbPatientBaseInfo = findByCertificateNo(m.getCertificateNo());
		if (EmptyUtil.isNotNull(dbPatientBaseInfo)&&!dbPatientBaseInfo.getId().equals(m.getId()))throw new BaseException("证件号码已存在");
		
		dbPatientBaseInfo = findOne(m.getId());
		if (EmptyUtil.isNull(dbPatientBaseInfo))throw new BaseException("患者信息不存在");
		m.setUpdateDate(Calendar.getInstance().getTime());
		return super.update(m);
	}
	
	public PatientBaseInfo findByCertificateNo(String certificateNo){
		return getPatientBaseInfoRepository().findByCertificateNo(certificateNo);
	}
	
	public PatientBaseInfo findByCertificateNoAndCertificateType(String certificateNo,String certificateType){
		return getPatientBaseInfoRepository().findByCertificateNoAndCertificateType(certificateNo, certificateType);
	}
	
	public List<PatientBaseInfo> findByMatchRule(List<MatchRule> matchRuleList){
		return getPatientBaseInfoRepository().findByMatchRule(matchRuleList);
	}	
	
	public List<PatientBaseInfo> match(PatientBaseInfo m){
		List<MatchRule> matchRuleList = matchRuleService.findMatchRuleByMatched();//获取需要匹配的字段
		Searchable searchable = Searchable.newSearchable();
		
		for(MatchRule matchRule:matchRuleList){
			searchable.addSearchFilter(matchRule.getFieldName(), SearchOperator.EQ, Reflections.getFieldValue(m, matchRule.getFieldName()));
		}
		searchable.addSort(Direction.DESC, "updateDate");
		return findAllWithSort(searchable);
	}
}
