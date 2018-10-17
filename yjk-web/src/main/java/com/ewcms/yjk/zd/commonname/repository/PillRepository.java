package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.Pill;

/**
 *@author zhoudongchu
 */
public interface PillRepository extends BaseRepository<Pill, Long> {
	@Query("from Pill where deleted is false")
	List<Pill> findPillBydeleted();
}
