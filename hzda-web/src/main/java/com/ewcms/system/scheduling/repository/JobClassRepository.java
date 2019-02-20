package com.ewcms.system.scheduling.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.system.scheduling.entity.JobClass;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface JobClassRepository extends BaseRepository<JobClass, Long>{
	
	JobClass findByClassName(String className);
}
