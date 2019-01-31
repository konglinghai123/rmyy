package com.ewcms.security.dictionary.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.dictionary.entity.DepartmentAttribute;
import com.ewcms.security.dictionary.repository.DepartmentAttributeRepository;

@Service
public class DepartmentAttributeService extends BaseService<DepartmentAttribute, Long> {
	
	private DepartmentAttributeRepository getDepartmentAttributeRepository() {
		return (DepartmentAttributeRepository) baseRepository;
	}
	
	public DepartmentAttribute findByName(String name){
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return getDepartmentAttributeRepository().findByName(name);
	}
}
