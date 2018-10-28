package com.ewcms.yjk.zd.commonname.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.repository.HospitalContentsRepository;

/**
 *@author zhoudongchu
 */
@Service
public class HospitalContentsService extends BaseService<HospitalContents, Long> {
    private HospitalContentsRepository getHospitalContentsRepository() {
        return (HospitalContentsRepository) baseRepository;
    }
	
	public List<HospitalContents> findByExtractCommonNameAndDeletedFalse(String extractCommonName){
		return getHospitalContentsRepository().findByExtractCommonNameAndDeletedFalse(extractCommonName);
	}
}
