package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;

/**
 *@author zhoudongchu
 */
public interface HospitalContentsRepository extends BaseRepository<HospitalContents, Long> {
	List<HospitalContents> findByCommonIdAndDeletedFalse(Long commonId);
	
	Page<HospitalContents> findByCommonIdInAndDeletedFalse(List<Long> commonIds, Pageable pageable);
}
