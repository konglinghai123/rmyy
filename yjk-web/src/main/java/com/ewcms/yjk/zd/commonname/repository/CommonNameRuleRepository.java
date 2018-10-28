package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;
import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;

public interface CommonNameRuleRepository extends BaseRepository<CommonNameRule, Long> {
//	@Query("select ruleName from CommonNameRule order by weight ")
//	List<String> findRuleNameOrderByWeight();
	
	List<CommonNameRule> findByDeletedFalseOrderByWeightAsc();
	
	List<CommonNameRule> findByRuleName(String ruleName);
	
	List<CommonNameRule> findByRuleCnName(String ruleCnName);
}
