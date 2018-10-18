package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.Pill;

/**
 *@author zhoudongchu
 */
public interface PillRepository extends BaseRepository<Pill, Long> {
	@Query("from Pill where deleted is ?1 order by id Asc")
	List<Pill> findPillByDeleted(Boolean isDeleted);
	
	@Query("from Pill where pillName = ?1")
	List<Pill> findPillByName(String pillName);
}
