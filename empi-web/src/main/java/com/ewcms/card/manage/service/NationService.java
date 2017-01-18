package com.ewcms.card.manage.service;

import org.springframework.stereotype.Service;

import com.ewcms.card.manage.entity.Nation;
import com.ewcms.card.manage.repository.NationRepository;
import com.ewcms.common.service.BaseService;

/**
 *@author zhoudongchu
 */
@Service
public class NationService extends BaseService<Nation, Long> {
	private NationRepository getNationRepository(){
		return (NationRepository)baseRepository;
	}
	
	public Nation findByName(String name){
		return getNationRepository().findByName(name);
	}
}
