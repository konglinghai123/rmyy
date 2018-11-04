package com.ewcms.yjk.zd.commonname.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;
import com.ewcms.yjk.zd.commonname.repository.CommonNameRuleRepository;

@Service
public class CommonNameRuleService extends BaseSequenceMovableService<CommonNameRule, Long> {

	public CommonNameRuleService() {
		super(1);
	}
	
    private CommonNameRuleRepository getCommonNameRuleRepository() {
        return (CommonNameRuleRepository) baseRepository;
    }
	
    
	public List<CommonNameRule> findByDeletedFalseAndEnabledTrueOrderByWeightAsc(){
		return getCommonNameRuleRepository().findByDeletedFalseAndEnabledTrueOrderByWeightAsc();
	}
	
	public List<CommonNameRule> findByRuleName(String ruleName){
		return getCommonNameRuleRepository().findByRuleName(ruleName);
	}
	
	public List<CommonNameRule> findByRuleCnName(String ruleCnName){
		return getCommonNameRuleRepository().findByRuleCnName(ruleCnName);
	}
	
	public CommonNameRule open(Long commonNameRuleId){
		CommonNameRule m = baseRepository.findOne(commonNameRuleId);
		m.setEnabled(Boolean.TRUE);
		return super.update(m);
	}
	
	public CommonNameRule close(Long commonNameRuleId){
		CommonNameRule m = baseRepository.findOne(commonNameRuleId);
		m.setEnabled(Boolean.FALSE);
		return super.update(m);
	}
}
