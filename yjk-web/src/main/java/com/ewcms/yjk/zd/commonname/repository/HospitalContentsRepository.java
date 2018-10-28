package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;

/**
 *@author zhoudongchu
 */
public interface HospitalContentsRepository extends BaseRepository<HospitalContents, Long> {
	List<HospitalContents> findByExtractCommonNameAndDeletedFalse(String extractCommonName);
}
