package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;

public interface CommonNameRuleRepository extends BaseRepository<CommonNameRule, Long> {

	List<String> findRuleNameOrderByWeight();
}
