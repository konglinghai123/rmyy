package com.ewcms.yjk.sp.repository;

import java.util.Date;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.sp.entity.SystemParameter;

/**
 * 
 * @author wuzhijun
 *
 */
public interface SystemParameterRepository extends BaseRepository<SystemParameter, Long> {

	SystemParameter findByApplyStartDateAfterAndApplyEndDateBefore(Date applyStartDate, Date applyEndDate);
}
