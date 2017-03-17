package com.ewcms.empi.dictionary.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.dictionary.entity.CountryCode;
import com.ewcms.empi.dictionary.repository.CountryCodeRepository;

/**
 *@author zhoudongchu
 */
@Service
public class CountryCodeService extends BaseService<CountryCode, String> {
	private CountryCodeRepository getCountryCodeRepository(){
		return (CountryCodeRepository)baseRepository;
	}
}
