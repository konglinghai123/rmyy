package com.ewcms.yjk.sp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.yjk.sp.entity.SystemExpert;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.repository.SystemExpertRepository;

@Service
public class SystemExpertService extends BaseSequenceMovableService<SystemExpert, Long> {

	private SystemExpertRepository getSystemExpertRepository() {
		return (SystemExpertRepository) baseRepository;
	}
	
	public SystemExpertService() {
		super(1);
	}
	
	@Override
	public SystemExpert save(SystemExpert m) {
		SystemExpert dbSystemExpert = findBySystemParameterAndId(m.getSystemParameter(), m.getId());
		if (dbSystemExpert != null) {
			m.setId(dbSystemExpert.getId());
		}
		//setupSystemExpert(m);
		
		return super.save(m);
	}
	
	public SystemExpert findBySystemParameterAndId(SystemParameter systemParameter, Long systemExpertId) {
		return getSystemExpertRepository().findBySystemParameterAndId(systemParameter, systemExpertId);
	}
	
	@Override
	public SystemExpert update(SystemExpert m) {
		SystemExpert dbSystemExpert = findBySystemParameterAndId(m.getSystemParameter(), m.getId());
		if (dbSystemExpert != null) {
			m.setId(dbSystemExpert.getId());
		}
		//setupSystemExpert(m);
		return super.update(m);
	}
	
	public void changeStatus(List<Long> systemExpertIds, Boolean newStatus) {
		getSystemExpertRepository().changeStatus(systemExpertIds, newStatus);
	}
	
}
