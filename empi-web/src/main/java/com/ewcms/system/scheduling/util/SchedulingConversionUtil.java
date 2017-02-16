package com.ewcms.system.scheduling.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.generate.entity.PageShowParam;
import com.ewcms.system.scheduling.entity.JobCalendarTrigger;
import com.ewcms.system.scheduling.entity.JobInfo;
import com.ewcms.system.scheduling.entity.JobSimpleTrigger;
import com.ewcms.system.scheduling.entity.JobTrigger;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReportParameter;
import com.ewcms.system.scheduling.web.controller.entity.PageDisplay;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author 吴智俊
 */
public class SchedulingConversionUtil {
	/**
	 * 字符串转换成整型数组
	 * 
	 * @param value 字符串
	 * @return 整型数组
	 */
	public static Integer[] stringToArray(String value) {
		if (EmptyUtil.isStringEmpty(value)) return new Integer[0];
		
		StringTokenizer tokenizer = new StringTokenizer(value, ",", false);
		Integer[] values = new Integer[tokenizer.countTokens()];
		for (int i = 0; tokenizer.hasMoreElements(); i++) {
			values[i] = Integer.valueOf(tokenizer.nextToken());
		}
		return values;
	}

	/**
	 * 整型数组转换成字符串
	 * 
	 * @param values 整型数组
	 * @return 字符串
	 */
	public static String arrayToString(Integer[] values) {
		if (EmptyUtil.isArrayEmpty(values)) return "";
		return Collections3.convertToString(Lists.newArrayList(values), ",");
	}
	
//	public static PageDisplay constructPage(JobInfo jobInfo) {
//		if (EmptyUtil.isNull(jobInfo)) return new PageDisplay();
//		
//		PageDisplay pageDisplay = new PageDisplay();
//		// 任务(Job)信息 //
//		pageDisplay.setJobId(jobInfo.getId());
//		pageDisplay.setJobVersion(jobInfo.getVersion());
//		pageDisplay.setLabel(jobInfo.getLabel());
//		pageDisplay.setUserName(jobInfo.getUserName());
//		pageDisplay.setDescription(jobInfo.getDescription());
//		if (EmptyUtil.isNotNull(jobInfo.getClass())) pageDisplay.setJobClassId(jobInfo.getJobClass().getId());
//		
//		// 开始状态 //
//		if (jobInfo.getTrigger().getStartType().intValue() == 1) {// 立刻执行 //
//			pageDisplay.setStart(1);
//		} else {// 开始时间 //
//			pageDisplay.setStart(2);
//			Date startDate = jobInfo.getTrigger().getStartDate();
//			if (EmptyUtil.isNotNull(startDate))	pageDisplay.setStartDate(startDate);
//		}
//		// 调度计划 //
//		JobTrigger trigger = jobInfo.getTrigger();
//		if (EmptyUtil.isNotNull(trigger)) {
//			pageDisplay.setTriggerId(trigger.getId());
//			pageDisplay.setTriggerVersion(trigger.getVersion());
//			if (trigger instanceof JobSimpleTrigger) {
//				Integer recurrenceInterval = ((JobSimpleTrigger) trigger).getRecurrenceInterval();
//				Integer recurrenceIntervalUnit = ((JobSimpleTrigger) trigger).getRecurrenceIntervalUnit();
//				Integer occurenceCount = ((JobSimpleTrigger) trigger).getOccurrenceCount();
//				if (EmptyUtil.isNull(recurrenceInterval) && EmptyUtil.isNull(recurrenceIntervalUnit) && occurenceCount == 1) {
//					pageDisplay.setMode(0);
//					pageDisplay.setOccurrenceCount(occurenceCount);
//				} else {// 简单 //
//					pageDisplay.setMode(1);
//
//					pageDisplay.setRecurrenceInterval(((JobSimpleTrigger) trigger).getRecurrenceInterval());
//					pageDisplay.setRecurrenceIntervalUnit(((JobSimpleTrigger) trigger).getRecurrenceIntervalUnit());
//
//					Date endDate = ((JobSimpleTrigger) trigger).getEndDate();
//
//					if (EmptyUtil.isNull(endDate) && occurenceCount == -1) {// 无限期 //
//						pageDisplay.setOccur(1);
//						pageDisplay.setEndDateSimple(null);
//						pageDisplay.setOccurrenceCount(null);
//					} else if (EmptyUtil.isNotNull(endDate) && occurenceCount == -1) {// 结束日期 //
//						pageDisplay.setOccur(2);
//						pageDisplay.setEndDateSimple(endDate);
//						pageDisplay.setOccurrenceCount(null);
//					} else if (EmptyUtil.isNull(endDate) && occurenceCount > 0) {// 次数 //
//						pageDisplay.setOccur(3);
//						pageDisplay.setEndDateSimple(null);
//						pageDisplay.setOccurrenceCount(occurenceCount);
//					}
//				}
//			} else if (trigger instanceof JobCalendarTrigger) {
//				pageDisplay.setMode(2);// 复杂 //
//				pageDisplay.setMinutes(((JobCalendarTrigger) trigger).getMinutes());// 分钟 //
//				pageDisplay.setHours(((JobCalendarTrigger) trigger).getHours());// 小时 //
//				pageDisplay.setMonths(SchedulingConversionUtil.stringToArray(((JobCalendarTrigger) trigger).getMonths()));// 月份 //
//
//				Date endDate = ((JobCalendarTrigger) trigger).getEndDate();
//				if (EmptyUtil.isNotNull(endDate)) pageDisplay.setEndDateCalendar(endDate);
//				
//				String weekDays = ((JobCalendarTrigger) trigger).getWeekDays();
//				String monthDays = ((JobCalendarTrigger) trigger).getMonthDays();
//				if (EmptyUtil.isStringNotEmpty(weekDays)) {// 一周之内 //
//					pageDisplay.setDays(2);
//					pageDisplay.setWeekDays(SchedulingConversionUtil.stringToArray(weekDays));
//					pageDisplay.setMonthDays(null);
//				} else if (EmptyUtil.isStringNotEmpty(monthDays)) {// 一月之内 //
//					pageDisplay.setDays(3);
//					pageDisplay.setWeekDays(null);
//					pageDisplay.setMonthDays(SchedulingConversionUtil.stringToArray(monthDays));
//				} else {// 每一天 //
//					pageDisplay.setDays(1);
//					pageDisplay.setWeekDays(null);
//					pageDisplay.setMonthDays(null);
//				}
//
//			} else {// 无 //
//				pageDisplay.setMode(0);
//				pageDisplay.setOccurrenceCount(1);
//			}
//		}
//		return pageDisplay;
//	}
//	
//	public static JobInfo constructJobInfo(JobInfo jobInfo, PageDisplay pageDisplay) {
//		jobInfo.setVersion(pageDisplay.getJobVersion());
//		jobInfo.setLabel(pageDisplay.getLabel());
//		jobInfo.setUserName(pageDisplay.getUserName());
//		jobInfo.setDescription(pageDisplay.getDescription());
//
//		if (pageDisplay.getMode() == 0) {// 无 //
//			JobSimpleTrigger t = new JobSimpleTrigger();
//			t.setId(pageDisplay.getTriggerId());
//			t.setVersion(pageDisplay.getTriggerVersion());
//
//			if (pageDisplay.getStart() == 1) {// 立刻执行 //
//				t.setStartType(1);
//				t.setStartDate(null);
//			} else {// 开始时间 //
//				t.setStartType(2);
//				if (EmptyUtil.isNotNull(pageDisplay)) {
//					t.setStartDate(pageDisplay.getStartDate());
//				}
//			}
//			t.setOccurrenceCount(1);
//
//			jobInfo.setTrigger(t);
//		} else if (pageDisplay.getMode() == 1) {// SimpleTrigger //
//			JobSimpleTrigger st = new JobSimpleTrigger();
//
//			if (pageDisplay.getStart() == 1) {// 立刻执行 //
//				st.setStartType(1);
//				st.setStartDate(null);
//			} else {// 开始时间 //
//				st.setStartType(2);
//				if (EmptyUtil.isNotNull(pageDisplay.getStartDate())) {
//					st.setStartDate(pageDisplay.getStartDate());
//				}
//			}
//			st.setId(pageDisplay.getTriggerId());
//			st.setVersion(pageDisplay.getTriggerVersion());
//			st.setRecurrenceInterval(pageDisplay.getRecurrenceInterval());
//			st.setRecurrenceIntervalUnit(pageDisplay.getRecurrenceIntervalUnit());
//
//			if (pageDisplay.getOccur() == 1) {// 无限期 //
//				st.setOccurrenceCount(-1);
//				st.setEndDate(null);
//			} else if (pageDisplay.getOccur() == 2) {// 结束日期 //
//				st.setOccurrenceCount(-1);
//				if (EmptyUtil.isNotNull(pageDisplay.getEndDateSimple())) {
//					st.setEndDate(pageDisplay.getEndDateSimple());
//				}
//			} else if (pageDisplay.getOccur() == 3) {// 次数 //
//				st.setOccurrenceCount(pageDisplay.getOccurrenceCount());
//				st.setEndDate(null);
//			}
//			jobInfo.setTrigger(st);
//		} else if (pageDisplay.getMode() == 2) {// CalendarTrigger
//			JobCalendarTrigger ct = new JobCalendarTrigger();
//			if (pageDisplay.getStart() == 1) {// 立刻执行 //
//				ct.setStartType(1);
//				ct.setStartDate(null);
//			} else {// 开始时间 //
//				ct.setStartType(2);
//				if (EmptyUtil.isNotNull(pageDisplay.getStartDate())) {
//					ct.setStartDate(pageDisplay.getStartDate());
//				}
//			}
//			ct.setId(pageDisplay.getTriggerId());
//			ct.setVersion(pageDisplay.getTriggerVersion());
//			if (EmptyUtil.isNotNull(pageDisplay.getEndDateCalendar())) {
//				ct.setEndDate(pageDisplay.getEndDateCalendar());
//			}
//			ct.setMinutes(pageDisplay.getMinutes());
//			ct.setHours(pageDisplay.getHours());
//			ct.setDaysType(pageDisplay.getDays());
//			ct.setMonths(SchedulingConversionUtil.arrayToString(pageDisplay.getMonths()));
//
//			if (pageDisplay.getDays() == 1) {// 每天 //
//				ct.setWeekDays(null);
//				ct.setMonthDays(null);
//			} else if (pageDisplay.getDays() == 2) {// 一周内 //
//				ct.setWeekDays(SchedulingConversionUtil.arrayToString(pageDisplay.getWeekDays()));
//				ct.setMonthDays(null);
//			} else if (pageDisplay.getDays() == 3) {// 一个月内 //
//				ct.setWeekDays(null);
//				ct.setMonthDays(SchedulingConversionUtil.arrayToString(pageDisplay.getMonthDays()));
//			}
//			jobInfo.setTrigger(ct);
//		}
//		return jobInfo;
//	}
	
	public static PageDisplay constructPage(JobInfo jobInfo) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		PageDisplay pageDisplay = new PageDisplay();
		
		if (EmptyUtil.isNull(jobInfo) || EmptyUtil.isNull(jobInfo.getId())) return pageDisplay;
		
		// 任务(Job)信息 //
		pageDisplay.setJobId(jobInfo.getId());
		pageDisplay.setJobVersion(jobInfo.getVersion());
		pageDisplay.setLabel(jobInfo.getLabel());
		pageDisplay.setUserName(jobInfo.getUserName());
		pageDisplay.setDescription(jobInfo.getDescription());
		if (jobInfo.getJobClass() != null){
			pageDisplay.setJobClassId(jobInfo.getJobClass().getId());
		}
		// 开始状态 //
		if (jobInfo.getTrigger().getStartType().intValue() == 1) {// 立刻执行 //
			pageDisplay.setStart(1);
		} else {// 开始时间 //
			pageDisplay.setStart(2);
			Date startDate = jobInfo.getTrigger().getStartDate();
			if (startDate != null){
				pageDisplay.setStartDate(bartDateFormat.format(startDate));
			}
		}
		// 调度计划 //
		JobTrigger trigger = jobInfo.getTrigger();
		if (trigger != null) {
			pageDisplay.setTriggerId(trigger.getId());
			pageDisplay.setTriggerVersion(trigger.getVersion());
			if (trigger instanceof JobSimpleTrigger) {
				Integer recurrenceInterval = ((JobSimpleTrigger) trigger).getRecurrenceInterval();
				Integer recurrenceIntervalUnit = ((JobSimpleTrigger) trigger).getRecurrenceIntervalUnit();
				Integer occurenceCount = ((JobSimpleTrigger) trigger).getOccurrenceCount();
				if (recurrenceInterval == null && recurrenceIntervalUnit == null && occurenceCount == 1) {
					pageDisplay.setMode(0);
					pageDisplay.setOccurrenceCount(occurenceCount);
				} else {// 简单 //
					pageDisplay.setMode(1);

					pageDisplay.setRecurrenceInterval(((JobSimpleTrigger) trigger).getRecurrenceInterval());
					pageDisplay.setRecurrenceIntervalUnit(((JobSimpleTrigger) trigger).getRecurrenceIntervalUnit());

					Date endDate = ((JobSimpleTrigger) trigger).getEndDate();

					if (endDate == null && occurenceCount == -1) {// 无限期 //
						pageDisplay.setOccur(1);
						pageDisplay.setEndDateSimple(null);
						pageDisplay.setOccurrenceCount(null);
					} else if (endDate != null && occurenceCount == -1) {// 结束日期 //
						pageDisplay.setOccur(2);
						pageDisplay.setEndDateSimple(bartDateFormat.format(endDate));
						pageDisplay.setOccurrenceCount(null);
					} else if (endDate == null && occurenceCount > 0) {// 次数 //
						pageDisplay.setOccur(3);
						pageDisplay.setEndDateSimple(null);
						pageDisplay.setOccurrenceCount(occurenceCount);
					}
				}
			} else if (trigger instanceof JobCalendarTrigger) {
				pageDisplay.setMode(2);// 复杂 //
				pageDisplay.setMinutes(((JobCalendarTrigger) trigger).getMinutes());// 分钟 //
				pageDisplay.setHours(((JobCalendarTrigger) trigger).getHours());// 小时 //
				pageDisplay.setMonths(stringToArray(((JobCalendarTrigger) trigger).getMonths()));// 月份 //

				Date endDate = ((JobCalendarTrigger) trigger).getEndDate();
				if (endDate != null){
					pageDisplay.setEndDateCalendar(bartDateFormat.format(endDate));
				}
				
				String weekDays = ((JobCalendarTrigger) trigger).getWeekDays();
				String monthDays = ((JobCalendarTrigger) trigger).getMonthDays();
				if (weekDays != null && weekDays.length() > 0) {// 一周之内 //
					pageDisplay.setDays(2);
					pageDisplay.setWeekDays(stringToArray(weekDays));
					pageDisplay.setMonthDays(null);
				} else if (monthDays != null && monthDays.length() > 0) {// 一月之内 //
					pageDisplay.setDays(3);
					pageDisplay.setWeekDays(null);
					pageDisplay.setMonthDays(stringToArray(monthDays));
				} else {// 每一天 //
					pageDisplay.setDays(1);
					pageDisplay.setWeekDays(null);
					pageDisplay.setMonthDays(null);
				}

			} else {// 无 //
				pageDisplay.setMode(0);
				pageDisplay.setOccurrenceCount(1);
			}
		}
		return pageDisplay;
	}
	
	public static JobInfo constructJobInfo(JobInfo jobInfo, PageDisplay pageDisplay) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			jobInfo.setVersion(pageDisplay.getJobVersion());
			jobInfo.setLabel(pageDisplay.getLabel());
			jobInfo.setUserName(pageDisplay.getUserName());
			jobInfo.setDescription(pageDisplay.getDescription());

			if (pageDisplay.getMode() == 0) {// 无 //
				JobSimpleTrigger t = new JobSimpleTrigger();
				t.setId(pageDisplay.getTriggerId());
				t.setVersion(pageDisplay.getTriggerVersion());

				if (pageDisplay.getStart() == 1) {// 立刻执行 //
					t.setStartType(1);
					t.setStartDate(null);
				} else {// 开始时间 //
					t.setStartType(2);
					if (pageDisplay.getStartDate() != null && !pageDisplay.getStartDate().equals("")) {
						t.setStartDate(bartDateFormat.parse(pageDisplay.getStartDate()));
					}
				}
				t.setOccurrenceCount(1);

				jobInfo.setTrigger(t);
			} else if (pageDisplay.getMode() == 1) {// SimpleTrigger //
				JobSimpleTrigger st = new JobSimpleTrigger();

				if (pageDisplay.getStart() == 1) {// 立刻执行 //
					st.setStartType(1);
					st.setStartDate(null);
				} else {// 开始时间 //
					st.setStartType(2);
					if (pageDisplay.getStartDate() != null && !pageDisplay.getStartDate().equals("")) {
						st.setStartDate(bartDateFormat.parse(pageDisplay.getStartDate()));
					}
				}
				st.setId(pageDisplay.getTriggerId());
				st.setVersion(pageDisplay.getTriggerVersion());
				st.setRecurrenceInterval(pageDisplay.getRecurrenceInterval());
				st.setRecurrenceIntervalUnit(pageDisplay.getRecurrenceIntervalUnit());

				if (pageDisplay.getOccur() == 1) {// 无限期 //
					st.setOccurrenceCount(-1);
					st.setEndDate(null);
				} else if (pageDisplay.getOccur() == 2) {// 结束日期 //
					st.setOccurrenceCount(-1);
					if (pageDisplay.getEndDateSimple() != null && !pageDisplay.getEndDateSimple().equals("")) {
						st.setEndDate(bartDateFormat.parse(pageDisplay.getEndDateSimple()));
					}
				} else if (pageDisplay.getOccur() == 3) {// 次数 //
					st.setOccurrenceCount(pageDisplay.getOccurrenceCount());
					st.setEndDate(null);
				}
				jobInfo.setTrigger(st);
			} else if (pageDisplay.getMode() == 2) {// CalendarTrigger
				JobCalendarTrigger ct = new JobCalendarTrigger();
				if (pageDisplay.getStart() == 1) {// 立刻执行 //
					ct.setStartType(1);
					ct.setStartDate(null);
				} else {// 开始时间 //
					ct.setStartType(2);
					if (pageDisplay.getStartDate() != null && !pageDisplay.getStartDate().equals("")) {
						ct.setStartDate(bartDateFormat.parse(pageDisplay.getStartDate()));
					}
				}
				ct.setId(pageDisplay.getTriggerId());
				ct.setVersion(pageDisplay.getTriggerVersion());
				if (pageDisplay.getEndDateCalendar() != null && !pageDisplay.getEndDateCalendar().equals("")) {
					ct.setEndDate(bartDateFormat.parse(pageDisplay.getEndDateCalendar()));
				}
				ct.setMinutes(pageDisplay.getMinutes());
				ct.setHours(pageDisplay.getHours());
				ct.setDaysType(pageDisplay.getDays());
				ct.setMonths(arrayToString(pageDisplay.getMonths()));

				if (pageDisplay.getDays() == 1) {// 每天 //
					ct.setWeekDays(null);
					ct.setMonthDays(null);
				} else if (pageDisplay.getDays() == 2) {// 一周内 //
					ct.setWeekDays(arrayToString(pageDisplay.getWeekDays()));
					ct.setMonthDays(null);
				} else if (pageDisplay.getDays() == 3) {// 一个月内 //
					ct.setWeekDays(null);
					ct.setMonthDays(arrayToString(pageDisplay.getMonthDays()));
				}
				jobInfo.setTrigger(ct);
			}
			return jobInfo;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JobInfo();
	}

	/**
	 * 如果调度器中有参数值与原参数的默认值不相等,则使用调度器中的参数值
	 * 
	 * @param ewcmsJobParameters
	 *            调度器参数集合
	 * @param pageShowParams
	 *            页面原参数集合
	 * @return 页面原参数集合
	 */
	public static List<PageShowParam> coversionParameterFromPage(List<JobReportParameter> ewcmsJobParameters, List<PageShowParam> pageShowParams) {
		for (PageShowParam pageShowParam : pageShowParams) {
			Long paramId = pageShowParam.getId();
			String enName = pageShowParam.getEnName();
			String pageValue = pageShowParam.getDefaultValue();
			for (JobReportParameter ewcmsJobParameter : ewcmsJobParameters) {
				if (paramId.intValue() == ewcmsJobParameter.getParameter().getId().intValue() && enName.equals(ewcmsJobParameter.getParameter().getEnName())) {
					String value = ewcmsJobParameter.getParameterValue();
					if (value != null && !value.equals(pageValue)) {
						pageShowParam.setDefaultValue(value);
						break;
					}
				}
			}
		}
		return pageShowParams;
	}
	
	/**
	 * 
	 * @param ewcmsJobParameters
	 * @param parameters
	 * @return
	 */
	public static void pageToJob(Set<JobReportParameter> ewcmsJobParameters,Set<Parameter> parameters,HttpServletRequest request){
		JobReportParameter jobParameter = null;
		if (!parameters.isEmpty()) {
			if (ewcmsJobParameters.isEmpty()){
				ewcmsJobParameters = Sets.newLinkedHashSet();
				for (Parameter parameter : parameters) {
					String value = (String) request.getParameter(parameter.getEnName());
	
					jobParameter = new JobReportParameter();
	
					jobParameter.setParameter(parameter);
					jobParameter.setParameterValue(value);
	
					ewcmsJobParameters.add(jobParameter);
				}
			}else{
				for (Parameter parameter : parameters){
					String enName = parameter.getEnName();
					String value = (String) request.getParameter(parameter.getEnName());
					Boolean mate = false;
					for (JobReportParameter ewcmsJobParameter : ewcmsJobParameters){
						String jobEnName = ewcmsJobParameter.getParameter().getEnName();
						if (enName.equals(jobEnName)){
							ewcmsJobParameter.setParameterValue(value);
							mate = true;
							break;
						}
					}
					if (!mate){
						jobParameter = new JobReportParameter();
						
						jobParameter.setParameter(parameter);
						jobParameter.setParameterValue(value);
		
						ewcmsJobParameters.add(jobParameter);
					}
				}
			}
		}else{
			ewcmsJobParameters = Sets.newLinkedHashSet();
		}
	}

}
