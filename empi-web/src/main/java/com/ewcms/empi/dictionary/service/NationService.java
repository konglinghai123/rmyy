package com.ewcms.empi.dictionary.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.dictionary.entity.Nation;
import com.ewcms.empi.dictionary.repository.NationRepository;

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
