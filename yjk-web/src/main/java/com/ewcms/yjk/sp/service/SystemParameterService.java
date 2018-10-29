package com.ewcms.yjk.sp.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.repository.SystemParameterRepository;

/**
 * 
 * @author wuzhijun
 *
 */
@Service
public class SystemParameterService extends BaseService<SystemParameter, Long> {
    private SystemParameterRepository getSystemParameterRepository() {
        return (SystemParameterRepository) baseRepository;
    }
	public SystemParameter findByEnabledTrue(){
		return getSystemParameterRepository().findByEnabledTrue();
	}
	
	public SystemParameter openDeclare(Long systemParameterId){
		SystemParameter vo = findOne(systemParameterId);
		 if(vo.getApplyEndDate().after(new Date()) && vo.getApplyStartDate().before(new Date())){
				SystemParameter enabledvo = findByEnabledTrue();
				if(enabledvo !=null){
					enabledvo.setEnabled(Boolean.FALSE);
					update(enabledvo);
				}
				
				vo.setEnabled(Boolean.TRUE);
				return update(vo);
		 }
		 return null;
	}
	
	public boolean isOpenDrugDeclare(){
		return findByEnabledTrue()==null? false:true;
	}
}
