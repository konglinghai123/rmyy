package com.ewcms.empi.card.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.repository.MatchRuleRepository;

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
		return getMatchRuleRepository().findMatchRuleByMatched();
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
