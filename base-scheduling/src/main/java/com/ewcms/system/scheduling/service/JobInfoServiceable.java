package com.ewcms.system.scheduling.service;

import com.ewcms.system.scheduling.entity.JobInfo;

public interface JobInfoServiceable {
	
	JobInfo save(JobInfo jobInfo);
	
	JobInfo update(JobInfo jobInfo);
	
	void delete(Long jobInfoId);
}
