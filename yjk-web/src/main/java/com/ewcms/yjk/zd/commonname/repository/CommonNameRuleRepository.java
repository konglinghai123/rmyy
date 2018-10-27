package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;

public interface CommonNameRuleRepository extends BaseRepository<CommonNameRule, Long> {
	@Query("select ruleName from CommonNameRule order by weight ")
	List<String> findRuleNameOrderByWeight();
	
	@Query("from CommonNameRule where deleted is false order by weight ")
	List<CommonNameRule> findRuleNameByDeleted();
	
	List<CommonNameRule> findByRuleName(String ruleName);
	
	List<CommonNameRule> findByRuleCnName(String ruleCnName);
}
