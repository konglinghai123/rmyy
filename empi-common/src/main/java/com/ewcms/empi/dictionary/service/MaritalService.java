package com.ewcms.empi.dictionary.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.dictionary.entity.Marital;
import com.ewcms.empi.dictionary.repository.MaritalRepository;

/**
 *@author zhoudongchu
 */
@Service
public class MaritalService extends BaseService<Marital, String> {
	private MaritalRepository getMaritalRepository(){
		return (MaritalRepository)baseRepository;
	}

}
