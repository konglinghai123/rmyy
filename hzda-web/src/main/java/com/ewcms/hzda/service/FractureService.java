package com.ewcms.hzda.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.Fracture;
import com.ewcms.hzda.repository.FractureRepository;

@Service
public class FractureService extends BaseService<Fracture, Long> {
	private FractureRepository getFractureRepository() {
		return (FractureRepository) baseRepository;
	}
	
}
