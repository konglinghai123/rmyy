package com.ewcms.system.scheduling.generate;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.ewcms.system.scheduling.entity.JobInfo;
import com.ewcms.system.scheduling.entity.JobTrigger;
import com.ewcms.system.scheduling.exception.SchedulingException;
import com.ewcms.system.scheduling.generate.common.ValidationErrorable;
import com.ewcms.system.scheduling.generate.common.ValidationErrorsable;
import com.ewcms.system.scheduling.generate.quartz.JobsQuartzSchedulerable;
import com.ewcms.system.scheduling.generate.quartz.SchedulerListenerable;
import com.ewcms.system.scheduling.generate.validator.JobInfoValidatorable;
import com.ewcms.system.scheduling.service.JobInfoServiceable;

/**
 * @author 吴智俊
 */
public class SchedulingFactory implements SchedulerListenerable, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(SchedulingFactory.class);

	private JobsQuartzSchedulerable scheduler;
	private JobInfoValidatorable validator;
	private JobInfoServiceable jobInfoService;

	public JobInfoServiceable getJobInfoService() {
		return jobInfoService;
	}

	public void setJobInfoService(JobInfoServiceable jobInfoService) {
		this.jobInfoService = jobInfoService;
	}

	public JobsQuartzSchedulerable getScheduler() {
		return scheduler;
	}

	public void setScheduler(JobsQuartzSchedulerable scheduler) {
		this.scheduler = scheduler;
	}

	public JobInfoValidatorable getValidator() {
		return validator;
	}

	public void setValidator(JobInfoValidatorable validator) {
		this.validator = validator;
	}

	public void afterPropertiesSet() throws Exception {
		getScheduler().addSchedulerListener(this);
	}

	public Long saveScheduleJob(JobInfo jobInfo) {
		validate(jobInfo);
		JobInfo savedJob = jobInfoService.save(jobInfo);
		scheduler.scheduleJob(savedJob);
		return savedJob.getId();
	}

	protected void validate(JobInfo jobInfo) {
		ValidationErrorsable errors = validator.validateJob(jobInfo);
		if (errors.isError()) {
			logger.debug("JobInfo Validate Error {}", errors);
			throw new SchedulingException("scheduling.validate.error", new Object[]{errors});
		}
	}

//	public List<JobInfo> getScheduledJobs() {
//		List<JobInfo> jobs = jobInfoService.findAll();
//		//setRuntimeInformation(jobs);
//		return removeDuplicateAndSort(jobs);
//	}

//	/*
//	 * 剔除重复记录,并根据JobInfo对象的id进行排序
//	 */
//	protected List<JobInfo> removeDuplicateAndSort(List<JobInfo> list) {
//		// 剔除重复记录
//		HashSet<JobInfo> h = new HashSet<JobInfo>(list);
//		list.clear();
//		list.addAll(h);
//		// 排序
//		Collections.sort(list, new Comparator<Object>() {
//			public int compare(Object o1, Object o2) {
//				JobInfo p1 = (JobInfo) o1;
//				JobInfo p2 = (JobInfo) o2;
//				if (p1.getId() > p2.getId())
//					return 1;
//				else
//					return 0;
//			}
//		});
//		return list;
//	}
//
//	protected void setRuntimeInformation(List<JobInfo> jobs) {
//		if (jobs != null && !jobs.isEmpty()) {
//			scheduler.getJobsRuntimeInformation(jobs);
//		}
//	}

	public void deletedScheduledJob(Long jobId) {
		deleteJob(jobId);
	}

	protected void unscheduleJobs(Long[] deletedJobIds) {
		if (deletedJobIds != null && deletedJobIds.length > 0) {
			for (int i = 0; i < deletedJobIds.length; i++) {
				Long jobId = deletedJobIds[i];
				scheduler.removeScheduledJob(jobId);
			}
		}
	}

	protected void deleteJob(Long jobId) {
		scheduler.removeScheduledJob(jobId);
		jobInfoService.delete(jobId);
	}

//	public JobInfo getScheduledJob(Long jobId) {
//		return jobInfoService.findOne(jobId);
//	}

	public void jobFinalized(Long jobId) {
		logger.info("任务 " + jobId + " 已完成,将删除数据");
		deleteJob(jobId);
	}

	public Long updateScheduledJob(JobInfo jobInfo) {
		validate(jobInfo);

		JobTrigger origTrigger = jobInfo.getTrigger();
		Long origTriggerId = origTrigger.getId();
		Integer origTriggerVersion = origTrigger.getVersion();

		JobInfo savedJob = jobInfoService.update(jobInfo);
		JobTrigger updatedTrigger = savedJob.getTrigger();

		if (updatedTrigger.getId() != origTriggerId || updatedTrigger.getVersion() != origTriggerVersion) {
			scheduler.rescheduleJob(savedJob);
		} else {
			logger.info("触发器属性没有改变 " + jobInfo.getId() + " 任务,任务将不会被改变");
		}
		return jobInfo.getId();
	}

	public ValidationErrorsable validateJob(JobInfo jobInfo) {
		ValidationErrorsable errors = validator.validateJob(jobInfo);
		if (!hasTriggerErrors(errors)) {
			scheduler.validate(jobInfo, errors);
		}
		return errors;
	}

//	public JobClass saveJobClass(JobClass jobClass) {
//		return jobClassService.save(jobClass);
//	}
//
//	public JobClass updateJobClass(JobClass jobClass) {
//		return jobClassService.update(jobClass);
//	}
//
//	public JobClass findByJobClass(Long id) {
//		return jobClassService.findOne(id);
//	}
//
//	public List<JobClass> findByAllJobClass() {
//		return jobClassService.findAll();
//	}
//
//	public void deletedJobClass(Long id) {
//		jobClassService.delete(id);
//	}

	protected boolean hasTriggerErrors(ValidationErrorsable errors) {
		boolean triggerError = false;
		for (Iterator<ValidationErrorable> it = errors.getErrors().iterator(); !triggerError && it.hasNext();) {
			ValidationErrorable error = (ValidationErrorable) it.next();
			String field = error.getField();
			if (field != null && (field.equals("trigger") || field.startsWith("trigger."))) {
				triggerError = true;
			}
		}
		return triggerError;
	}

//	public JobClass findByJobClassByClassEntity(String classEntity) {
//		return jobClassService.findByClassEntity(classEntity);
//	}

	public void pauseJob(Long id) {
		scheduler.pauseJob(id);
	}

	public void resumedJob(Long id) {
		scheduler.resumedJob(id);
	}
	
}
