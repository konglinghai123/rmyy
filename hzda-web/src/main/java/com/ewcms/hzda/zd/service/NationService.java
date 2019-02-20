package com.ewcms.hzda.zd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.zd.entity.Nation;
import com.ewcms.hzda.zd.repository.NationRepository;

@Service
public class NationService extends BaseService<Nation, Long> {

	private NationRepository getNationRepository() {
		return (NationRepository) baseRepository;
	}
	
	public List<Nation> findByName(String name){
		return getNationRepository().findByName(name);
	}
}
