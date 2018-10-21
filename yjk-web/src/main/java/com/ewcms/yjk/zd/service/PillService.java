package com.ewcms.yjk.zd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.zd.entity.Pill;
import com.ewcms.yjk.zd.repository.PillRepository;

/**
 *@author zhoudongchu
 */
@Service
public class PillService extends BaseService<Pill, Long> {
    private PillRepository getPillRepository() {
        return (PillRepository) baseRepository;
    }
    
    public List<Pill> findPillByDeleted(Boolean isDeleted){
    	return getPillRepository().findPillByDeleted(isDeleted);
    }
    public List<Pill> findPillByName(String pillName){
    	return getPillRepository().findPillByName(pillName);
    }
}
