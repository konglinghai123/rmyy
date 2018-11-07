package com.ewcms.yjk.zd.commonname.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.SpecialRule;

public interface SpecialRuleRepository extends BaseRepository<SpecialRule, Long> {

	SpecialRule findByName(String name);
	
	@Query(value = "select count(sc.special_rule_id) from zd_special_rule_common_name sc left join zd_special_rule r on sc.special_rule_id=r.id where sc.special_rule_id=?1 and sc.common_name_id=?2 and r.is_enabled=true", nativeQuery = true)
	Long findExist(Long id, Long commonNameId);
	
	@Query(value = "select sc.special_rule_id from zd_special_rule_common_name sc left join zd_special_rule r on sc.special_rule_id=r.id where common_name_id in (?1) and r.is_enabled=true", nativeQuery = true)
	List<BigInteger> findSpecialRules(Set<Long> commonNameIds);
}
