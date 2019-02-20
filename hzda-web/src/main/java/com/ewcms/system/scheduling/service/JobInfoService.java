package com.ewcms.system.scheduling.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.system.scheduling.entity.JobInfo;
import com.ewcms.system.scheduling.entity.JobInfoState;
import com.ewcms.system.scheduling.exception.SchedulingException;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReport;
import com.ewcms.system.scheduling.generate.quartz.JobsQuartzSchedulerable;
import com.google.common.collect.Lists;

/**
 * @author 吴智俊
 */
@Service
public class JobInfoService extends BaseService<JobInfo, Long> implements JobInfoServiceable{
	
	@Autowired
	private JobTriggerService jobTriggerService;
	@Autowired
	private JobsQuartzSchedulerable jobsQuartzScheduler;
	
	@Override
	public JobInfo update(JobInfo jobInfo) {
		if (jobInfo.getTrigger() != null){
			Long triggerId = jobInfo.getTrigger().getId();
			if (triggerId != null){
				jobTriggerService.delete(triggerId);
				jobInfo.getTrigger().setId(null);
				jobInfo.getTrigger().setVersion(-1);
			}
		}
		return super.update(jobInfo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter){
		Map<String, Object> searchMap = super.query(searchParameter);
		List<JobInfo> jobInfos = (List<JobInfo>) searchMap.get("rows");
		
		if (EmptyUtil.isCollectionEmpty(jobInfos)) return searchMap;
        
		Set<String> executingJobNames = null;
        try {
            executingJobNames = jobsQuartzScheduler.getExecutingJobNames();
        } catch (SchedulerException e) {
            throw new SchedulingException("scheduling.runtime.error", null);
        }
        
        String searchState = (String)searchParameter.getParameters().get("CUSTOM_state");
        List<JobInfo> reject = Lists.newArrayList();
        for (JobInfo jobInfo : jobInfos){
        	jobInfo.setJobClassName(jobInfo.getJobClass().getName());
        	
        	String remark = "";
//        	if (jobInfo instanceof JobChannel){
//        		JobChannel jobChannel = (JobChannel) jobInfo;
//        		Channel channel = jobChannel.getChannel();
//        		remark = channelService.findNames(channel.getName(), channel.getParentIds(), true, " &gt;&gt; ");
//        	} else 
        	if (jobInfo instanceof JobReport){
        		JobReport jobReport = (JobReport) jobInfo;
        		if (EmptyUtil.isNotNull(jobReport.getTextReport())){
        			remark = "文字报表";
        		} else if (EmptyUtil.isNotNull(jobReport.getChartReport())){
        			remark = "图形报表";
        		}
        	}
        	jobInfo.setRemark(remark);
        	
        	Trigger trigger = null;
        	try{
            	trigger = jobsQuartzScheduler.getJobTrigger(jobInfo.getId());
            } catch (SchedulerException e){
            }
            JobInfoState jobInfoState = JobInfoState.STATE_UNKNOWN;
            if (EmptyUtil.isNotNull(trigger)){
                try{
                	jobInfoState = jobsQuartzScheduler.getJobState(trigger, executingJobNames);
                } catch (SchedulerException e){
                	jobInfoState = JobInfoState.STATE_ERROR;
                }

                jobInfo.setState(jobInfoState.getInfo());
                jobInfo.setStartTime(trigger.getStartTime() != null ? trigger.getStartTime() : (jobInfo.getTrigger() != null ? jobInfo.getTrigger().getStartDate() : null));
                jobInfo.setEndTime(trigger.getEndTime() != null ? trigger.getEndTime() : (jobInfo.getTrigger() != null ? jobInfo.getTrigger().getEndDate() : null));
                jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
                if (trigger.mayFireAgain()) {
                	jobInfo.setNextFireTime(trigger.getNextFireTime());
                }
            }
            if (EmptyUtil.isStringNotEmpty(searchState) && !searchState.equals(jobInfoState.name())){
            	reject.add(jobInfo);
            }
        }
        
		searchMap.put("rows", Collections3.subtract(jobInfos, reject));
		return searchMap;
	}
	
	public String findJobInfoState(Long jobInfoId){
		JobInfoState jobInfoState = JobInfoState.STATE_UNKNOWN;
		try{
			Set<String> executingJobNames = jobsQuartzScheduler.getExecutingJobNames();
			Trigger trigger = jobsQuartzScheduler.getJobTrigger(jobInfoId);
			
            if (EmptyUtil.isNotNull(trigger)){
            	jobInfoState = jobsQuartzScheduler.getJobState(trigger, executingJobNames);
            }
		}catch(SchedulerException e){
			jobInfoState = JobInfoState.STATE_ERROR;
		}
		return jobInfoState.getInfo();
	}
}
