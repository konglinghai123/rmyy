package com.ewcms.yjk.re.zd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.re.zd.entity.ReviewRule;
import com.ewcms.yjk.re.zd.repository.ReviewRuleRepository;

@Service
public class ReviewRuleService extends BaseSequenceMovableService<ReviewRule, Long> {

	public ReviewRuleService() {
		super(1);
	}
	
    private ReviewRuleRepository getReviewRuleRepository() {
        return (ReviewRuleRepository) baseRepository;
    }
	
    
	public List<ReviewRule> findByDeletedFalseAndEnabledTrueOrderByWeightAsc(){
		return getReviewRuleRepository().findByDeletedFalseAndEnabledTrueOrderByWeightAsc();
	}
	
	public List<ReviewRule> findByRuleName(String ruleName){
		return getReviewRuleRepository().findByRuleName(ruleName);
	}
	
	public List<ReviewRule> findByRuleCnName(String ruleCnName){
		return getReviewRuleRepository().findByRuleCnName(ruleCnName);
	}
	
	public ReviewRule open(Long reviewRuleId){
		ReviewRule m = baseRepository.findOne(reviewRuleId);
		m.setEnabled(Boolean.TRUE);
		return super.update(m);
	}
	
	public ReviewRule close(Long reviewRuleId){
		ReviewRule m = baseRepository.findOne(reviewRuleId);
		m.setEnabled(Boolean.FALSE);
		return super.update(m);
	}
}
