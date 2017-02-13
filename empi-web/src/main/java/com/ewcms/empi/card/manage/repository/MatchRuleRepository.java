package com.ewcms.empi.card.manage.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.card.manage.entity.MatchRule;

/**
 *@author zhoudongchu
 */
public interface MatchRuleRepository extends BaseRepository<MatchRule, Long> {
	MatchRule findByFieldName(String fieldName);
}
