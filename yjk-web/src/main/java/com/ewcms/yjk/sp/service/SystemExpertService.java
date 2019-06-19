package com.ewcms.yjk.sp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.yjk.sp.entity.SystemExpert;
import com.ewcms.yjk.sp.repository.SystemExpertRepository;

@Service
public class SystemExpertService extends BaseSequenceMovableService<SystemExpert, Long> {

	private SystemExpertRepository getSystemExpertRepository() {
		return (SystemExpertRepository) baseRepository;
	}
	
//	public SystemExpert findBySystemParameterAndId(SystemParameter systemParameter, Long systemExpertId) {
//		return getSystemExpertRepository().findBySystemParameterAndId(systemParameter, systemExpertId);
//	}
	
	public void changeStatus(List<Long> systemExpertIds, Boolean newStatus) {
		if (EmptyUtil.isCollectionEmpty(systemExpertIds)) return;
		getSystemExpertRepository().changeStatus(systemExpertIds, newStatus);
	}
	
}
