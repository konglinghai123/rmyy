package com.ewcms.yjk.re.zd.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;

public interface ReviewBaseRuleRepository extends BaseRepository<ReviewBaseRule, Long> {
	
	List<ReviewBaseRule> findByRuleName(String ruleName);
	
	List<ReviewBaseRule> findByRuleCnName(String ruleCnName);
}
