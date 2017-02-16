package com.ewcms.system.scheduling.generate.quartz;

import java.util.Set;

import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.ewcms.system.scheduling.entity.JobInfo;
import com.ewcms.system.scheduling.entity.JobInfoState;
import com.ewcms.system.scheduling.generate.common.ValidationErrorsable;

/**
 * 
 * @author 吴智俊
 */
public interface JobsQuartzSchedulerable {

    /**
     * 新建调度任务
     *
     * @param job 调度器任务
     */
    public void scheduleJob(JobInfo job);

    /**
     * 修改调度任务
     *
     * @param job 调度器任务
     */
    public void rescheduleJob(JobInfo job);

    /**
     * 删除调度任务
     *
     * @param jobId 调度任务编号
     */
    public void removeScheduledJob(Long jobId);
    
    /**
     * 暂停调度任务
     * 
     * @param jobId 调度任务编号
     */
    public void pauseJob(Long jobId);
    
    /**
     * 从暂停恢复调度任务
     * @param jobId 调度任务编号
     */
    public void resumedJob(Long jobId);

    /**
     * 新增调度器监听接口
     *
     * @param listener 监听接口
     */
    public void addSchedulerListener(SchedulerListenerable listener);

    /**
     * 删除调度器监听接口
     *
     * @param listener 监听接口
     */
    public void removeSchedulerListener(SchedulerListenerable listener);

    /**
     * 校验调度器任务
     *
     * @param job 调度器任务
     * @param errors 错误信息
     */
    public void validate(JobInfo job, ValidationErrorsable errors);
    
    public Set<String> getExecutingJobNames() throws SchedulerException;
    
    public JobInfoState getJobState(Trigger trigger, Set<String> executingJobNames) throws SchedulerException;
    
    public Trigger getJobTrigger(long jobId) throws SchedulerException;
}
