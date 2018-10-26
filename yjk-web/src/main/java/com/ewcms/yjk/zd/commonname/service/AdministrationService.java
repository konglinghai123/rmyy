package com.ewcms.yjk.zd.commonname.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.repository.AdministrationRepository;

@Service
public class AdministrationService extends BaseService<Administration, Long> {
	
    private AdministrationRepository getAdministrationRepository() {
        return (AdministrationRepository) baseRepository;
    }

    public List<Administration> findByName(String name) {
    	return getAdministrationRepository().findByName(name);
    }
}
