package com.ewcms.system.scheduling.generate.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.triggers.AbstractTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.ewcms.system.scheduling.entity.JobCalendarTrigger;
import com.ewcms.system.scheduling.entity.JobInfo;
import com.ewcms.system.scheduling.entity.JobInfoState;
import com.ewcms.system.scheduling.entity.JobSimpleTrigger;
import com.ewcms.system.scheduling.entity.JobTrigger;
import com.ewcms.system.scheduling.exception.SchedulingException;
import com.ewcms.system.scheduling.generate.common.ValidationError;
import com.ewcms.system.scheduling.generate.common.ValidationErrorsable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author 吴智俊
 */
public class JobsQuartzScheduler implements JobsQuartzSchedulerable, InitializingBean {

    protected static final Logger logger = LoggerFactory.getLogger(JobsQuartzScheduler.class);
    
    private static final String GROUP = "jobs";
    private static final String TRIGGER_LISTENER_NAME = "schedulerTriggerListener";
    private static final String JOB_DATA_KEY_DETAILS_ID = "jobDetailsID";
    
    private static final Long COEFFICIENT_MINUTE = 60L * 1000L;
    private static final Long COEFFICIENT_HOUR = 60L * COEFFICIENT_MINUTE;
    private static final Long COEFFICIENT_DAY = 24L * COEFFICIENT_HOUR;
    private static final Long COEFFICIENT_WEEK = 7L * COEFFICIENT_DAY;
    private static final Integer COUNT_WEEKDAYS = 7;
    private static final Integer COUNT_MONTHS = 12;
    
    private Scheduler scheduler;

    private final Set<SchedulerListenerable> listeners;
    private final SchedulerListener schedulerListener;
    private final TriggerListener triggerListener;

    public JobsQuartzScheduler() {
        listeners = Sets.newHashSet();

        schedulerListener = new SchedulerQuartzListener();
        triggerListener = new SchedulerTriggerListener(TRIGGER_LISTENER_NAME);
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void afterPropertiesSet() throws Exception {
        try {
            //注册为非全局的SchedulerListener
        	getScheduler().getListenerManager().addSchedulerListener(schedulerListener);
            //注册为非全局的TriggerListener
        	getScheduler().getListenerManager().addTriggerListener(triggerListener, (List<Matcher<TriggerKey>>)null);
        } catch (SchedulerException e) {
            logger.error("注册Quartz监听时出现错误", e);
            throw new SchedulingException("scheduling.register.error", null);
        }
    }

    public void scheduleJob(JobInfo job) {
        JobDetail jobDetail = createJobDetail(job);
        Trigger trigger = createTrigger(job);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (logger.isDebugEnabled()){
            	logger.debug("Created job " + jobDetail.getKey().getName() + " and trigger " + trigger.getKey().getName() + " for job " + job.getId());
            }
        } catch (SchedulerException e) {
            logger.error("调度Quartz任务时错误", e);
            throw new SchedulingException("scheduling.dispatch.error", null);
        }
    }

    /**
     * 建立调度器任务明细
     *
     * @param job 调度器任务
     * @return JobDetail
     */
    @SuppressWarnings("unchecked")
	protected JobDetail createJobDetail(JobInfo job) {
        String jobName = jobName(job.getId());
        String jobClassEntity = job.getJobClass().getClassName().trim();
        try {
            if (jobClassEntity != null && jobClassEntity.length() > 0) {
            	//TODO 未测试
            	//ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
            	//Class<Job> classEntity = (Class<Job>) ClassUtils.forName(jobClassEntity, beanClassLoader);
                Class<Job> classEntity = (Class<Job>) Class.forName(jobClassEntity);
                JobDetail jobDetail = newJob(classEntity).
                		withIdentity(jobName, GROUP).
                		requestRecovery(true).
                		build();
                return jobDetail;
            }else{
            	logger.info("必须选择一个执行的调度器作业任务");
                throw new SchedulingException("scheduling.select.error", null);
            }
        } catch (ClassNotFoundException e) {
            logger.error("调度器作业不是一个有效的作业任务",e);
            throw new SchedulingException("scheduling.effective.error", new Object[]{jobClassEntity});
        }
    }

    public void rescheduleJob(JobInfo job) {
        try {
            Trigger oldTrigger = getJobTrigger(job.getId());
            Trigger trigger = createTrigger(job);

            if (oldTrigger == null) {
                JobDetail jobDetail = createJobDetail(job);
                scheduler.scheduleJob(jobDetail, trigger);
                if (logger.isDebugEnabled()) {
					logger.debug("Scheduled trigger " + trigger.getKey().getName() + " for job " + job.getId());
				}
            } else {
                scheduler.rescheduleJob(oldTrigger.getKey(), trigger);
                if (logger.isDebugEnabled()) {
					logger.debug("Trigger " + oldTrigger.getKey().getName() + " rescheduled by " + trigger.getKey().getName() + " for job " + job.getId());
				}
            }
        } catch (SchedulerException e) {
            logger.error("调度Quartz任务错误", e);
            throw new SchedulingException("scheduling.dispatch.error", null);
        }
    }

    /**
     * 查询调度器任务的触发器
     *
     * @param jobId 调度器任务编号
     * @return Trigger
     * @throws SchedulerException
     */
    public Trigger getJobTrigger(long jobId) throws SchedulerException {
        Trigger trigger;
        String jobName = jobName(jobId);
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(new JobKey(jobName, GROUP));
        
        List<Trigger> filteredTriggersList = Lists.newArrayList();
        for (Trigger currTrigger : triggers) {
            if (GROUP.equals(currTrigger.getKey().getGroup())) {
                filteredTriggersList.add(currTrigger);
            }
        }
        
        triggers = filteredTriggersList;
        
        if (triggers == null || triggers.isEmpty()) {
            trigger = null;
            if (logger.isDebugEnabled()) {
				logger.debug("No trigger found for job " + jobId);
			}
        } else if (triggers.size() == 1) {
            trigger = triggers.get(0);
        	if (logger.isDebugEnabled()) {
				logger.debug("Trigger " + trigger.getKey().getName() + " found for job " + jobId);
			}
        } else {
        	logger.error("任务有一个以上的触发器", new Object[] {new Long(jobId)});
            throw new SchedulingException("scheduling.trigger.morethanone.error", new Object[] {jobName});
        }
        return trigger;
    }

    /**
     * 设置调度器任务名称
     *
     * @param jobId 调度器任务编号
     * @return String
     */
    protected String jobName(long jobId) {
        return "job_" + jobId;
    }

    /**
     * 设置触发器名称
     *
     * @param jobTrigger 触发器
     * @return String
     */
    protected String triggerName(JobTrigger jobTrigger) {
        return "trigger_" + jobTrigger.getId() + "_" + jobTrigger.getVersion();
    }

    /**
     * 建立触发器
     *
     * @param jobInfo 触发器
     * @return Trigger
     */
    protected Trigger createTrigger(JobInfo jobInfo) {
        Trigger trigger;
        JobTrigger jobTrigger = jobInfo.getTrigger();
        if (jobTrigger instanceof JobSimpleTrigger) {
            trigger = createTrigger((JobSimpleTrigger) jobTrigger);
        } else if (jobTrigger instanceof JobCalendarTrigger) {
            trigger = createTrigger((JobCalendarTrigger) jobTrigger);
        } else {
        	String quotedJobTrigger = "\"" + jobTrigger.getClass().getName() + "\"";
        	logger.error("任务触发类型没有命名", new Object[] {quotedJobTrigger});
            throw new SchedulingException("scheduling.trigger.type.error", new Object[] {quotedJobTrigger});
        }

        JobDataMap jobDataMap = trigger.getJobDataMap();
        jobDataMap.put(JOB_DATA_KEY_DETAILS_ID, jobInfo.getId());

        TriggerKey tk = getTriggerKey(jobTrigger);
        Matcher<TriggerKey> matcher = KeyMatcher.keyEquals(tk);
        try {
            getScheduler().getListenerManager().addTriggerListener(triggerListener, matcher);
        } catch (SchedulerException e) {
        	logger.error("增加Quartz触发监听器错误", e.toString());
        	throw new SchedulingException("scheduling.trigger.add.error", null);
        }
        
        return trigger;
    }

    //---------------------------------------简单型触发器---------------------------------------------------
    /**
     * 建立触发器
     *
     * @param jobTrigger 简单型触发器
     * @return Trigger
     */
    protected Trigger createTrigger(JobSimpleTrigger jobTrigger) {
        String triggerName = triggerName(jobTrigger);
        Date startDate = getStartDate(jobTrigger);
        Date endDate = getEndDate(jobTrigger);

        int repeatCount = repeatCount(jobTrigger);
        SimpleTrigger trigger;
        if (repeatCount == 0) {
            trigger = newTrigger().withIdentity(triggerName, GROUP).
            		startAt(startDate).
            		withSchedule(simpleSchedule().
            				withRepeatCount(0).
            				withMisfireHandlingInstructionNextWithRemainingCount()).
            		build();
        } else {
            int recurrenceInterval = jobTrigger.getRecurrenceInterval().intValue();
            long unitCoefficient = getIntervalUnitCoefficient(jobTrigger);
            long interval = recurrenceInterval * unitCoefficient;
            trigger = newTrigger().withIdentity(triggerName, GROUP).
            		startAt(startDate).
            		endAt(endDate).
            		withSchedule(simpleSchedule().
            				withIntervalInMilliseconds(interval).
            				withRepeatCount(repeatCount).
            				withMisfireHandlingInstructionNextWithRemainingCount()).
            		build();
        }
        return trigger;
    }

    protected long getIntervalUnitCoefficient(JobSimpleTrigger jobTrigger) {
		long coefficient;
		int riu = jobTrigger.getRecurrenceIntervalUnit().intValue();
		if (riu == JobSimpleTrigger.INTERVAL_MINUTE.intValue()){
			coefficient = COEFFICIENT_MINUTE;
		}else if (riu == JobSimpleTrigger.INTERVAL_HOUR.intValue()){
			coefficient = COEFFICIENT_HOUR;
		}else if (riu == JobSimpleTrigger.INTERVAL_DAY.intValue()){
			coefficient = COEFFICIENT_DAY;
		}else if (riu == JobSimpleTrigger.INTERVAL_WEEK.intValue()){
			coefficient = COEFFICIENT_WEEK;
		}else {
			logger.error("不能识别任务执行的间隔数", new Object[] {jobTrigger.getRecurrenceIntervalUnit()});
			throw new SchedulingException("scheduling.trigger.cofficient.error", new Object[] {jobTrigger.getRecurrenceIntervalUnit()});
		}
		return coefficient;
	}
    
    /**
     * 查询结束时间
     *
     * @param jobTrigger 触发器
     * @return Date
     */
    protected Date getEndDate(JobTrigger jobTrigger)  {
        return translateFromTriggerTimeZone(jobTrigger, jobTrigger.getEndDate());
    }

    protected Date translateFromTriggerTimeZone(JobTrigger jobTrigger, Date date) {
        if (date != null) {
            TimeZone tz = getTriggerTimeZone(jobTrigger);
            if (tz != null) {
            	date = DateBuilder.translateTime(date, TimeZone.getDefault(), tz);
            }
        }
        return date;
    }

    /**
     * 查询开始时间
     *
     * @param jobTrigger 触发器
     * @return Date
     */
    protected Date getStartDate(JobTrigger jobTrigger) {
        Date startDate;

        if (jobTrigger.getStartType().intValue() == JobTrigger.START_TYPE_NOW.intValue()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            startDate = calendar.getTime();
        } else if (jobTrigger.getStartType().intValue() == JobTrigger.START_TYPE_SCHEDULE.intValue()) {
            startDate = translateFromTriggerTimeZone(jobTrigger, jobTrigger.getStartDate());
        } else {
        	logger.error("任务开始执行时间没有赋值", new Object[] {jobTrigger.getStartType()});
            throw new SchedulingException("scheduling.trigger.startdate.error", new Object[] {jobTrigger.getStartType()});
        }
        return startDate;
    }

    /**
     * 执行次数
     *
     * @param jobTrigger
     * @return int
     */
    protected int repeatCount(JobSimpleTrigger jobTrigger) {
        Integer recurrenceCount = jobTrigger.getOccurrenceCount();
        Integer repeatCount;
        if (recurrenceCount.intValue() == JobSimpleTrigger.RECUR_INDEFINITELY.intValue()) {
            repeatCount = SimpleTrigger.REPEAT_INDEFINITELY;
        } else {
            repeatCount = recurrenceCount - 1;
        }
        return repeatCount;
    }
    //---------------------------------------简单型触发器---------------------------------------------------

    //---------------------------------------复杂型触发器---------------------------------------------------
    /**
     * 建立触发器
     *
     * @param jobTrigger 复杂型触发器
     * @return Trigger
     */
    protected Trigger createTrigger(JobCalendarTrigger jobTrigger) {
        String triggerName = triggerName(jobTrigger);
        Date startDate = getStartDate(jobTrigger);
        Date endDate = getEndDate(jobTrigger);

        String cronExpression = getCronExpression(jobTrigger);

        try {
            Trigger trigger = newTrigger().withIdentity(triggerName, GROUP).
                    startAt(startDate).
                    endAt(endDate).
                    withSchedule(cronSchedule(cronExpression).
                            inTimeZone(getTriggerTimeZone(jobTrigger)).     //  NULL input is OK
                            withMisfireHandlingInstructionDoNothing()).
                    build();
  			return trigger;
        } catch (Exception e) {
            logger.error("建立Quartz复杂触发器错误", e);
            throw new SchedulingException("scheduling.trigger.create.error", null);
        }
    }

    /**
     * 查询触发器时区
     *
     * @param jobTrigger 触发器
     * @return TimeZone
     */
    protected TimeZone getTriggerTimeZone(JobTrigger jobTrigger){
        String tzId = jobTrigger.getTimeZone();
        TimeZone tz;
        if (tzId == null || tzId.length() == 0) {
            tz = null;
        } else {
            tz = TimeZone.getTimeZone(tzId);
            if (tz == null) {
            	String quotedTzId = "\"" + tzId + "\"";
            	logger.error("时区(TimeZone)没有赋值", quotedTzId);
                throw new SchedulingException("scheduling.trigger.timezone.error", new Object[] {quotedTzId});
            }
        }
        return tz;
    }

    /**
     * 查询触发器表达式
     *
     * @param jobTrigger 复杂型触发器
     * @return String
     */
    protected String getCronExpression(JobCalendarTrigger jobTrigger) {
        String minutes = jobTrigger.getMinutes();
        String hours = jobTrigger.getHours();
        String weekDays;
        String monthDays;
        if (jobTrigger.getDaysType().intValue() == JobCalendarTrigger.DAYS_TYPE_ALL.intValue()) {
            weekDays = "?";
            monthDays = "*";
        } else if (jobTrigger.getDaysType().intValue() == JobCalendarTrigger.DAYS_TYPE_WEEK.intValue()) {
            weekDays = enumerateCronVals(jobTrigger.getWeekDays(), COUNT_WEEKDAYS);
            monthDays = "?";
        } else if (jobTrigger.getDaysType().intValue() == JobCalendarTrigger.DAYS_TYPE_MONTH.intValue()) {
            weekDays = "?";
            monthDays = jobTrigger.getMonthDays();
        } else {
        	logger.error("未知的任务触发天数类型",  new Object[] {jobTrigger.getDaysType()});
            throw new SchedulingException("shceduling.trigger.days.error", new Object[] {jobTrigger.getDaysType()});
        }
        String months = enumerateCronVals(jobTrigger.getMonths(), COUNT_MONTHS);

        StringBuffer cronExpression = new StringBuffer();
        cronExpression.append("0 ");
        cronExpression.append(minutes);
        cronExpression.append(' ');
        cronExpression.append(hours);
        cronExpression.append(' ');
        cronExpression.append(monthDays);
        cronExpression.append(' ');
        cronExpression.append(months);
        cronExpression.append(' ');
        cronExpression.append(weekDays);

        return cronExpression.toString();
    }

    protected String enumerateCronVals(String vals, int totalCount) {
        if (vals == null || vals.length() == 0) {
        	logger.error("未选择值");
            throw new SchedulingException("scheduling.trigger.select.error", null);
        }
        StringTokenizer tokenizer = new StringTokenizer(vals, ",", false);
        if (tokenizer.countTokens() == totalCount) {
            return "*";
        } else {
            return vals;
        }
    }
    //---------------------------------------复杂型触发器---------------------------------------------------

    public void removeScheduledJob(Long jobId) {
        try {
            String jobName = jobName(jobId);
            if (scheduler.deleteJob(new JobKey(jobName, GROUP))) {
                logger.info("JobsQuartzScheduler：任务 " + jobName + " 被删除");
            } else {
                logger.info("JobsQuartzScheduler：Quartz任务 " + jobId + " 未查到不能删除");
            }
        } catch (SchedulerException e) {
            logger.error("删除Quartz任务时错误", e);
            throw new SchedulingException("scheduling.delete.error", new Object[]{jobId});
        }
    }

//    public List<JobInfo> getJobsRuntimeInformation(List<JobInfo> jobInfos) {
//    	
//        if (EmptyUtil.isCollectionEmpty(jobInfos)) return Lists.newArrayList();
//        
//        try {
//            Set<String> executingJobNames = getExecutingJobNames();
//            for (JobInfo jobInfo : jobInfos) {
//                convertJobInfo(jobInfo, executingJobNames);
//            }
//            return jobInfos;
//        } catch (SchedulerException e) {
//            logger.error("获得Quartz运行时信息错误", e);
//            throw new SchedulingException("scheduling.runtime.error", null);
//        }
//    }

//    /**
//     * 获取调度器任务运行时的状态，提供给页面使用
//     *
//     * @param jobId 调度器任务编号
//     * @param executingJobNames 调度器任务名称
//     * @return JobRuntimeInformation
//     * @throws SchedulerException
//     */
//    protected void convertJobInfo(JobInfo jobInfo, Set<String> executingJobNames) throws SchedulerException {
//        Trigger trigger = getJobTrigger(jobInfo.getId());
//        if (trigger == null) {
//        	jobInfo.setState(JobInfo.STATE_UNKNOWN);
//        } else {
//        	jobInfo.setStartTime(trigger.getStartTime() != null ? trigger.getStartTime() : (jobInfo.getTrigger() != null ? jobInfo.getTrigger().getStartDate() : null));
//        	jobInfo.setEndTime(trigger.getEndTime() != null ? trigger.getEndTime() : (jobInfo.getTrigger() != null ? jobInfo.getTrigger().getEndDate() : null));
//        	jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
//            if (trigger.mayFireAgain()) {
//            	jobInfo.setNextFireTime(trigger.getNextFireTime());
//            }
//            String state = getJobState(trigger, executingJobNames);
//            jobInfo.setState(state);
//        }
//    }

    public JobInfoState getJobState(Trigger trigger, Set<String> executingJobNames) throws SchedulerException {
        JobInfoState state;
        Trigger.TriggerState quartzState = scheduler.getTriggerState(trigger.getKey());
        switch (quartzState) {
            case NORMAL:
            case BLOCKED:
                state = executingJobNames.contains(trigger.getKey().getName()) ? JobInfoState.STATE_EXECUTING : JobInfoState.STATE_NORMAL;
                break;
            case COMPLETE:
                state = JobInfoState.STATE_COMPLETE;
                break;
            case PAUSED:
                state = JobInfoState.STATE_PAUSED;
                break;
            case ERROR:
                state = JobInfoState.STATE_ERROR;
                break;
            default:
                state = JobInfoState.STATE_UNKNOWN;
                break;
        }
        return state;
    }

    public Set<String> getExecutingJobNames() throws SchedulerException {
        List<JobExecutionContext> executingJobs = (List<JobExecutionContext>) scheduler.getCurrentlyExecutingJobs();
        Set<String> executingJobNames = Sets.newHashSet();
        for (Iterator<JobExecutionContext> iter = executingJobs.iterator(); iter.hasNext();) {
            JobExecutionContext executionContext = (JobExecutionContext) iter.next();
            JobDetail jobDetail = executionContext.getJobDetail();
            if (jobDetail.getKey().getGroup().equals(GROUP)) {
                executingJobNames.add(jobDetail.getKey().getName());
            }
        }
        return executingJobNames;
    }

    public void addSchedulerListener(SchedulerListenerable listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public synchronized void removeSchedulerListener(SchedulerListenerable listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    protected void notifyListenersOfFinalizedJob(Long jobId) {
        synchronized (listeners) {
            for (Iterator<SchedulerListenerable> it = listeners.iterator(); it.hasNext();) {
                SchedulerListenerable listener = (SchedulerListenerable) it.next();
                listener.jobFinalized(jobId);
            }
        }
    }

    protected void getTriggerFinalized(Trigger trigger) {
    	try{
    		long jobId = trigger.getJobDataMap().getIntegerFromString(JOB_DATA_KEY_DETAILS_ID);
    		notifyListenersOfFinalizedJob(jobId);
    	}catch(ClassCastException e){
    	}
    }
    
    protected TriggerKey getTriggerKey(JobTrigger jobTrigger) {
        return new TriggerKey(triggerName(jobTrigger), GROUP);
    }

    protected class SchedulerQuartzListener implements SchedulerListener {

        public SchedulerQuartzListener() {
        }

		@Override
		public void jobScheduled(Trigger trigger) {
            if (logger.isDebugEnabled()) {
                logger.debug("Quartz job " + trigger.getKey() + " scheduled by trigger " + trigger.getKey());
            }
		}

		@Override
		public void jobUnscheduled(TriggerKey triggerKey) {
			if (logger.isDebugEnabled()) {
				logger.debug("Quartz job with triggerKey: " + triggerKey + " unscheduled");
	        }
		}

		@Override
		public void triggerFinalized(Trigger trigger) {
            if (logger.isDebugEnabled()) {
                logger.debug("Quartz trigger finalized " + trigger.getKey());
            }

            if (trigger.getKey().getGroup().equals(GROUP)) {
            	try {
					getTriggerFinalized(trigger);
				} catch (SchedulingException e) {
					e.printStackTrace();
				}
            }
		}

		@Override
		public void triggerPaused(TriggerKey triggerKey) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job trigger" + triggerKey + " paused");
	            }
		}

		@Override
		public void triggersPaused(String triggerGroup) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job trigger group " + triggerGroup + " paused ");
	            }
		}

		@Override
		public void triggerResumed(TriggerKey triggerKey) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job trigger " + triggerKey + " resumed ");
	            }
		}

		@Override
		public void triggersResumed(String triggerGroup) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job trigger group" + triggerGroup + " resumed ");
	            }
		}

		@Override
		public void jobAdded(JobDetail jobDetail) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job " + jobDetail.getKey() + " added. ");
	            }
		}

		@Override
		public void jobDeleted(JobKey jobKey) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job " + jobKey + " deleted ");
	            }
		}

		@Override
		public void jobPaused(JobKey jobKey) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job " + jobKey + " paused ");
	            }
		}

		@Override
		public void jobsPaused(String jobGroup) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job Group " + jobGroup + " paused ");
	            }
		}

		@Override
		public void jobResumed(JobKey jobKey) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job " + jobKey + " resumed  ");
	            }
		}

		@Override
		public void jobsResumed(String jobGroup) {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz job  Group " + jobGroup +" resumed  ");
	            }
		}

		@Override
		public void schedulerError(String msg, SchedulerException cause) {
            if (logger.isInfoEnabled()) {
                logger.info("Quartz scheduler error: " + msg, cause);
            }
		}

		@Override
		public void schedulerInStandbyMode() {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz Scheduler in standby mode ");
	            }
		}

		@Override
		public void schedulerStarted() {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz Scheduler started");
	            }
		}

		@Override
		public void schedulerShutdown() {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz Scheduler shutting down");
	            }
		}

		@Override
		public void schedulerShuttingdown() {
            if (logger.isInfoEnabled()) {
                logger.info("Quartz scheduler shutdown");
            }
		}

		@Override
		public void schedulingDataCleared() {
	          if (logger.isDebugEnabled()) {
	              logger.debug("Quartz Scheduler Data Cleared");
	            }
		}
    }

    protected class SchedulerTriggerListener implements TriggerListener {

        private final String name;

        public SchedulerTriggerListener(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

		@Override
		public void triggerFired(Trigger trigger, JobExecutionContext context) {
			if (logger.isDebugEnabled()) {
				logger.debug("Quartz trigger fired " + trigger.getKey());
			}
		}

		@Override
		public boolean vetoJobExecution(Trigger trigger,
				JobExecutionContext context) {
			return false;
		}

		@Override
		public void triggerMisfired(Trigger trigger) {
			if (logger.isDebugEnabled()) {
				logger.debug("Quartz trigger misfired " + trigger.getKey());
			}
			
			if (trigger.getKey().getGroup().equals(GROUP) && trigger.getFireTimeAfter(new Date()) == null) {
				try {
					getTriggerFinalized(trigger);
				} catch (SchedulingException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void triggerComplete(Trigger trigger,
				JobExecutionContext context,
				CompletedExecutionInstruction triggerInstructionCode) {
			if (logger.isDebugEnabled()) {
				logger.debug("Quartz trigger complete " + trigger.getKey() + " triggerInstructionCode=" + triggerInstructionCode);
			}
		}
    }

    /*
     * 校验Job
     */
    @SuppressWarnings("rawtypes")
	public void validate(JobInfo job, ValidationErrorsable errors) {
        Trigger quartzTrigger = createTrigger(job);
        AbstractTrigger abstrTrigger = (AbstractTrigger) quartzTrigger;
        Date firstFireTime = abstrTrigger.computeFirstFireTime(null);
        if (firstFireTime == null) {
            errors.add(new ValidationError("scheduling.trigger.no.fire", null, "设置的任务将不会执行，请重新设置"));
        }
    }

	@Override
	public void pauseJob(Long jobId) {
		try{
			Trigger trigger = getJobTrigger(jobId);
			TriggerState quartzState = scheduler.getTriggerState(trigger.getKey());
			if (quartzState == TriggerState.NORMAL){
				scheduler.pauseTrigger(trigger.getKey());
				if (logger.isDebugEnabled()){
					logger.debug("暂停工作任务 " + GROUP + "." + jobId);
				}
//			}else{
//				logger.error("暂停工作任务 " + GROUP + "." + jobId + " 必须处于正常状态");
//				throw new BaseException("任务必须处于正常状态才能暂停","任务必须处于正常状态才能暂停");
			}
		}catch (SchedulerException e) {
			logger.error("暂停Quartz任务时错误", e);
		    throw new SchedulingException("scheduling.pause.error", new Object[]{jobId});
        }
	}

	@Override
	public void resumedJob(Long jobId) {
		try{
			Trigger trigger = getJobTrigger(jobId);
			TriggerState quartzState = scheduler.getTriggerState(trigger.getKey());
			if (quartzState == Trigger.TriggerState.PAUSED){
				scheduler.resumeTrigger(trigger.getKey());
				if (logger.isDebugEnabled()){
					logger.info("恢复工作任务 " + GROUP + "." + jobId);
				}
//			}else{
//				logger.error("恢复工作任务 " + GROUP + "." + jobId + " 必须处于暂停状态");
//				throw new BaseException("任务必须处于暂停状态才能恢复" ,"任务必须处于暂停状态才能恢复");
			}
		}catch (SchedulerException e) {
			logger.error("恢复Quartz任务时错误", e);
		    throw new SchedulingException("scheduling.resume.error", new Object[]{jobId});
        }
	}
}
