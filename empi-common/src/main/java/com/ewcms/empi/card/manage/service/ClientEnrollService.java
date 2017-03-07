package com.ewcms.empi.card.manage.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.card.manage.entity.ClientEnroll;
import com.ewcms.empi.card.manage.repository.ClientEnrollRepository;

/**
 *@author zhoudongchu
 */
@Service
public class ClientEnrollService extends BaseService<ClientEnroll, Long> {
	private ClientEnrollRepository getClientEnrollRepository() {
		return (ClientEnrollRepository) baseRepository;
	}
	
	public ClientEnroll findByIp(String ip){
		return getClientEnrollRepository().findByIp(ip);
	}
	
	public ClientEnroll findByUserName(String userName){
		return getClientEnrollRepository().findByUserName(userName);
	}
	
	public ClientEnroll findByIpAndUserName(String ip, String userName){
		return getClientEnrollRepository().findByIpAndUserName(ip, userName);
	}
}
