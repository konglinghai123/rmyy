package com.ewcms.empi.card.manage.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.card.manage.entity.ClientEnroll;

/**
 *@author zhoudongchu
 */
public interface ClientEnrollRepository extends	BaseRepository<ClientEnroll, Long> {
	
	ClientEnroll findByIp(String ip);
	
	ClientEnroll findByUserName(String userName);

	ClientEnroll findByIpAndUserName(String ip, String userName);
}