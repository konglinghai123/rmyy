package com.ewcms.security.dictionary.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.dictionary.entity.TechnicalTitle;
import com.ewcms.security.dictionary.repository.TechnicalTitleRepository;

@Service
public class TechnicalTitleService extends BaseService<TechnicalTitle, Long> {
	
	private TechnicalTitleRepository getTechnicalTitleRepository() {
		return (TechnicalTitleRepository) baseRepository;
	}
	
	public TechnicalTitle findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return getTechnicalTitleRepository().findByName(name);
	}
}
