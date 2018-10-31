package com.ewcms.security.dictionary.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.dictionary.entity.Technical;
import com.ewcms.security.dictionary.repository.TechnicalRepository;

@Service
public class TechnicalService extends BaseService<Technical, Long> {
	
	private TechnicalRepository getTechnicalRepository() {
		return (TechnicalRepository) baseRepository;
	}
	
	public Technical findByNameAndLevel(String name, String level) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(level)) {
			return null;
		}
		return getTechnicalRepository().findByNameAndLevel(name, level);
	}
}
