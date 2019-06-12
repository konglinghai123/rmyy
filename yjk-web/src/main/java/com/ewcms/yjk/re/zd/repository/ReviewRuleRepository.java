package com.ewcms.yjk.re.zd.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.zd.entity.ReviewRule;

public interface ReviewRuleRepository extends BaseRepository<ReviewRule, Long> {
	
	List<ReviewRule> findByEnabledTrueOrderByWeightAsc();
	
	List<ReviewRule> findByRuleName(String ruleName);
	
	List<ReviewRule> findByRuleCnName(String ruleCnName);
}
