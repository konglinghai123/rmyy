package com.ewcms.yjk.re.zd.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;
import com.ewcms.yjk.re.zd.repository.ReviewBaseRuleRepository;

@Service
public class ReviewBaseRuleService extends BaseService<ReviewBaseRule, Long> {

	
    private ReviewBaseRuleRepository getReviewRuleRepository() {
        return (ReviewBaseRuleRepository) baseRepository;
    }

	public List<ReviewBaseRule> findByRuleName(String ruleName){
		return getReviewRuleRepository().findByRuleName(ruleName);
	}
	
	public List<ReviewBaseRule> findByRuleCnName(String ruleCnName){
		return getReviewRuleRepository().findByRuleCnName(ruleCnName);
	}
	
	@Override
	public ReviewBaseRule save(ReviewBaseRule m) {
		return super.save(m);
	}

}
