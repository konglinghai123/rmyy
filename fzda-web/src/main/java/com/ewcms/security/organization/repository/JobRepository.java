package com.ewcms.security.organization.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.organization.entity.Job;

/**
 * @author wu_zhijun
 */
public interface JobRepository extends BaseRepository<Job, Long> {
	
	List<Job> findByName(String name);
}
