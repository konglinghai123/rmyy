package com.ewcms.empi.card.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.repository.MatchRuleRepository;
import com.google.common.collect.Lists;

/**
 *@author zhoudongchu
 */
@Service
public class MatchRuleService extends BaseService<MatchRule, Long> {
	private MatchRuleRepository getMatchRuleRepository(){
		return (MatchRuleRepository)baseRepository;
	}
	
	public MatchRule findByFieldName(String fieldName){
		return getMatchRuleRepository().findByFieldName(fieldName);
	}
	
	public List<MatchRule> findMatchRuleByMatched(){
		List<MatchRule> matchRuleList = getMatchRuleRepository().findMatchRuleByMatched();
		if(EmptyUtil.isCollectionEmpty(matchRuleList)){//默认证件号码、证件类型为匹配项
			matchRuleList = Lists.newArrayList();
			MatchRule matchRule = new MatchRule();
			matchRule.setFieldName("certificateNo");
			matchRule.setCnName("证件号码");
			MatchRule matchRule1 = new MatchRule();
			matchRule1.setFieldName("certificateType");
			matchRule1.setCnName("证件类型");
			matchRuleList.add(matchRule);
			matchRuleList.add(matchRule1);
		}
		return matchRuleList;
	}
	
	public void matchFields(List<Long> matchFields){
		Iterable<MatchRule> matchRules = findAll();
		if (matchRules != null) {
			for (MatchRule matchRule : matchRules) {
				matchRule.setMatched(false);
				if(EmptyUtil.isNotNull(matchFields)){
					for(Long matchFieldId:matchFields){
						if(matchFieldId.equals(matchRule.getId())){
							matchRule.setMatched(true);
							break;
						}
					}
				}
				super.update(matchRule);
			}
		}		
		
	}
}
