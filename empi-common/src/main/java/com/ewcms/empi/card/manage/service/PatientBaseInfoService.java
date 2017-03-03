package com.ewcms.empi.card.manage.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Reflections;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
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
//		PatientBaseInfo dbPatientBaseInfo = findByCertificateNo(m.getCertificateNo());
//		if (EmptyUtil.isNotNull(dbPatientBaseInfo))throw new BaseException("证件号码已存在");		
		return super.save(m);
	}
	
	@Override
	public PatientBaseInfo saveAndFlush(PatientBaseInfo m) {
//		PatientBaseInfo dbPatientBaseInfo = findByCertificateNo(m.getCertificateNo());
//		if (EmptyUtil.isNotNull(dbPatientBaseInfo))throw new BaseException("证件号码已存在");		
		return super.saveAndFlush(m);
	}
	
	@Override
	public PatientBaseInfo update(PatientBaseInfo m) {
//		PatientBaseInfo dbPatientBaseInfo = null;
//		dbPatientBaseInfo = findByCertificateNo(m.getCertificateNo());
//		if (EmptyUtil.isNotNull(dbPatientBaseInfo)&&!dbPatientBaseInfo.getId().equals(m.getId()))throw new BaseException("证件号码已存在");
//		
//		dbPatientBaseInfo = findOne(m.getId());
//		if (EmptyUtil.isNull(dbPatientBaseInfo))throw new BaseException("患者信息不存在");
		m.setUpdateDate(Calendar.getInstance().getTime());
		return super.update(m);
	}
	
	/**
	 * 根据证件号码查询患者
	 * 
	 * @param certificateNo 证件号码
	 * @return PatientBaseInfo 患者信息
	 */
	public PatientBaseInfo findByCertificateNo(String certificateNo){
		return getPatientBaseInfoRepository().findByCertificateNo(certificateNo);
	}
	
	/**
	 * 根据证件类型和证件号码查询患者
	 * 
	 * @param certificateNo 证件号码
	 * @param certificateType 证件类型
	 * @return PatientBaseInfo 患者信息
	 */
	public PatientBaseInfo findByCertificateNoAndCertificateType(String certificateNo,String certificateType){
		return getPatientBaseInfoRepository().findByCertificateNoAndCertificateType(certificateNo, certificateType);
	}
	
	/**
	 * 根据匹配规则查询重复患者记录集
	 * 
	 * @param matchRuleList 匹配规则
	 * @return List<PatientBaseInfo> 重复患者信息记录集
	 */
	public List<PatientBaseInfo> findByMatchRule(List<MatchRule> matchRuleList){
		return getPatientBaseInfoRepository().findByMatchRule(matchRuleList);
	}	
	
	/**
	 * 根据一个患者信息和匹配规则去查询数据库相同患者的集合
	 * 
	 * @param m 患者信息
	 * @return List<PatientBaseInfo> 相同患者信息记录集
	 */
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
