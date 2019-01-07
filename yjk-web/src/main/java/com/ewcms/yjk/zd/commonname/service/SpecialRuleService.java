package com.ewcms.yjk.zd.commonname.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.SpecialRule;
import com.ewcms.yjk.zd.commonname.repository.SpecialRuleRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service
public class SpecialRuleService extends BaseService<SpecialRule, Long> {

	private SpecialRuleRepository getSpecialRuleRepository() {
		return (SpecialRuleRepository) baseRepository;
	}
	
	@Override
	public SpecialRule update(SpecialRule m) {
		SpecialRule dbSpecialRule = findOne(m.getId());
		m.setCommonNames(dbSpecialRule.getCommonNames());
		
		return super.update(m);
	}
	
	public SpecialRule findByName(String name) {
		return getSpecialRuleRepository().findByName(name);
	}
	
	public Long findExist(Long id, Long commonNameId) {
		return getSpecialRuleRepository().findExist(id, commonNameId);
	}
	
	public void changeStatus(List<Long> ids, Boolean newStatus) {
		for (Long id : ids) {
			SpecialRule specialRule = findOne(id);
			specialRule.setEnabled(newStatus);
			update(specialRule);
		}
	}

	public List<BigInteger> findSpecialRules(Set<Long> commonNameIds,Long administrationId){
		return getSpecialRuleRepository().findSpecialRules(commonNameIds,administrationId);
	}
	
	public Map<Long, SpecialRule> findMaxLimitNumber(Long commonNameId,Long administrationId) {
		Map<Long, SpecialRule> map = Maps.newHashMap();
		
		Set<Long> sets = Sets.newHashSet();
		sets.add(commonNameId);
		
		List<BigInteger> specialRuleIds = findSpecialRules(sets,administrationId);
		
		Long maxNumber = 0L;
		SpecialRule specialRule = null;
		
		if (EmptyUtil.isCollectionNotEmpty(specialRuleIds)) {
			for (BigInteger specialRuleId : specialRuleIds) {
				SpecialRule tempSpecialRule = findOne(specialRuleId.longValue());
				if (maxNumber <= tempSpecialRule.getLimitNumber()) {
					maxNumber = tempSpecialRule.getLimitNumber();
					specialRule = tempSpecialRule;
				}
			}
		}
		map.put(maxNumber, specialRule);
		
		return map;
	}
	
	public void deleteSpecialRuleLink(Long specialRuleId, List<Long> selections) {
		SpecialRule specialRule = findOne(specialRuleId);
		if (EmptyUtil.isNotNull(specialRule)) {
			List<CommonName> commonNames = specialRule.getCommonNames();
			List<CommonName> lists = Lists.newArrayList(commonNames);
			
			for (Long commonNameId : selections) {
				for (CommonName commonName : commonNames) {
					if (commonName.getId().equals(commonNameId)) {
						lists.remove(commonName);
						break;
					}
				}
			}
			
			specialRule.setCommonNames(lists);
			
			super.update(specialRule);
		}
	}
}
