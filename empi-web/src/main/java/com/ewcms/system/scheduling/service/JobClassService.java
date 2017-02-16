package com.ewcms.system.scheduling.service;

import java.lang.Class;

import org.quartz.Job;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.system.scheduling.entity.JobClass;
import com.ewcms.system.scheduling.exception.SchedulingException;
import com.ewcms.system.scheduling.repository.JobClassRepository;

/**
 * 
 * @author 吴智俊
 */
@Service
public class JobClassService extends BaseService<JobClass, Long>{

	private JobClassRepository getJobClassRepository(){
		return (JobClassRepository) baseRepository;
	}
	
	@Override
	public JobClass save(JobClass jobClass){
		if (validator(jobClass)) return super.save(jobClass);
		return null;
	}

	@Override
	public JobClass update(JobClass jobClass) {
		return save(jobClass);
	}

	protected Boolean validator(JobClass jobClass){
		String className = jobClass.getClassName().trim();
		if (className != null && className.length() > 0) {
			try {
				Class<?> classEntity = Class.forName(className);
				if (Job.class.isAssignableFrom(classEntity)) {
					return true;
				} else {
					throw new SchedulingException("scheduling.job.class.validator", null);
				}
			} catch (ClassNotFoundException ex) {
				throw new SchedulingException("scheduling.job.class.found", null);
			}
		} else {
			throw new SchedulingException("scheduling.job.class.set", null);
		}

	}

	public JobClass findByClassName(String className) {
		return getJobClassRepository().findByClassName(className);
	}
}
