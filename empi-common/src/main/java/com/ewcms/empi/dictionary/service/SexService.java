package com.ewcms.empi.dictionary.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.dictionary.entity.Sex;
import com.ewcms.empi.dictionary.repository.SexRepository;

/**
 *@author zhoudongchu
 */
@Service
public class SexService extends BaseService<Sex, String> {
	private SexRepository getSexRepository(){
		return (SexRepository)baseRepository;
	}

}
