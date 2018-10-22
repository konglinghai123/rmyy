package com.ewcms.yjk.zd.commonname.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;
import com.ewcms.yjk.zd.commonname.repository.CommonNameRuleRepository;

@Service
public class CommonNameRuleService extends BaseSequenceMovableService<CommonNameRule, Long> {

    private CommonNameRuleRepository getCommonNameRuleRepository() {
        return (CommonNameRuleRepository) baseRepository;
    }
	
	public List<String> findRuleNameOrderByWeight(){
    return getCommonNameRuleRepository().findRuleNameOrderByWeight();
	}
}
