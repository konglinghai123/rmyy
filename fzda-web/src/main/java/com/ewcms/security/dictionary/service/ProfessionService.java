package com.ewcms.security.dictionary.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.dictionary.entity.Profession;
import com.ewcms.security.dictionary.repository.ProfessionRepository;

@Service
public class ProfessionService extends BaseService<Profession, Long> {

	private ProfessionRepository getProfessionRepository() {
		return (ProfessionRepository) baseRepository;
	}

	public Profession findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return getProfessionRepository().findByName(name);
	}
}
