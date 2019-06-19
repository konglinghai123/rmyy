package com.ewcms.yjk.sp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.sp.entity.SystemExpert;

public interface SystemExpertRepository extends BaseRepository<SystemExpert, Long>{
	
//	SystemExpert findBySystemParameterAndId(SystemParameter systemParameter, Long systemExpertId);
	
    @Modifying
    @Query("update SystemExpert o set enabled=?2 where o.id in (?1)")
	void changeStatus(List<Long> systemExpertIds, Boolean newStatus);

}
