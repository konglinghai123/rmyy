package com.ewcms.empi.card.manage.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.card.manage.entity.MatchRule;

/**
 *@author zhoudongchu
 */
public interface MatchRuleRepository extends BaseRepository<MatchRule, Long> {
	MatchRule findByFieldName(String fieldName);
	
	@Query("from MatchRule where matched=true")
	List<MatchRule> findMatchRuleByMatched();
}
