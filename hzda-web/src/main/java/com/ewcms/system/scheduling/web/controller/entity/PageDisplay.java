package com.ewcms.system.scheduling.web.controller.entity;

import java.io.Serializable;
import java.util.List;

import com.ewcms.system.report.generate.entity.PageShowParam;
import com.google.common.collect.Lists;

/**
 * @author 吴智俊
 */
public class PageDisplay implements Serializable {

	private static final long serialVersionUID = 5593933725156531605L;
	
	// Job(任务)信息 //
	private Long jobId;
	private Integer jobVersion;
	private String label;
	private String userName = "";
	private String description;
	private Long jobClassId;
	private String state;
	// 调度计划 //
	private Long triggerId;
	private Integer triggerVersion;
	private Integer start = 1;
	private Integer mode = 0;
	private Integer occur;
	private Integer days;
	private String startDate;
	private String endDateSimple;
	private String endDateCalendar;
	private Integer occurrenceCount;
	private Integer recurrenceInterval;
	private Integer recurrenceIntervalUnit;
	private String minutes;
	private String hours;
	private Integer[] weekDays;
	private Integer[] monthDays;
	private Integer[] months;
	//是否是频道
	private Boolean isJobChannel = false;
	private Long channelId;
	private Boolean subChannel = false;
	//是否是采集器
	private Boolean isJobCrawler = false;
	//是否是报表
	private Boolean isJobReport = false;
	// 报表信息 //	
	private Long reportId;
	private String reportName;
	private String reportType;
	private List<PageShowParam> pageShowParams = Lists.newArrayList();
	private Integer[] outputFormats;
	
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Integer getJobVersion() {
		return jobVersion;
	}

	public void setJobVersion(Integer jobVersion) {
		this.jobVersion = jobVersion;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(Long triggerId) {
		this.triggerId = triggerId;
	}

	public Integer getTriggerVersion() {
		return triggerVersion;
	}

	public void setTriggerVersion(Integer triggerVersion) {
		this.triggerVersion = triggerVersion;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getOccur() {
		return occur;
	}

	public void setOccur(Integer occur) {
		this.occur = occur;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDateSimple() {
		return endDateSimple;
	}

	public void setEndDateSimple(String endDateSimple) {
		this.endDateSimple = endDateSimple;
	}

	public String getEndDateCalendar() {
		return endDateCalendar;
	}

	public void setEndDateCalendar(String endDateCalendar) {
		this.endDateCalendar = endDateCalendar;
	}

	public Integer getOccurrenceCount() {
		return occurrenceCount;
	}

	public void setOccurrenceCount(Integer occurrenceCount) {
		this.occurrenceCount = occurrenceCount;
	}

	public Integer getRecurrenceInterval() {
		return recurrenceInterval;
	}

	public void setRecurrenceInterval(Integer recurrenceInterval) {
		this.recurrenceInterval = recurrenceInterval;
	}

	public Integer getRecurrenceIntervalUnit() {
		return recurrenceIntervalUnit;
	}

	public void setRecurrenceIntervalUnit(Integer recurrenceIntervalUnit) {
		this.recurrenceIntervalUnit = recurrenceIntervalUnit;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public Integer[] getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(Integer[] weekDays) {
		this.weekDays = weekDays;
	}

	public Integer[] getMonthDays() {
		return monthDays;
	}

	public void setMonthDays(Integer[] monthDays) {
		this.monthDays = monthDays;
	}

	public Integer[] getMonths() {
		return months;
	}

	public void setMonths(Integer[] months) {
		this.months = months;
	}

	public Boolean getIsJobChannel() {
		return isJobChannel;
	}

	public void setIsJobChannel(Boolean isJobChannel) {
		this.isJobChannel = isJobChannel;
	}

	public Long getJobClassId() {
		return jobClassId;
	}

	public void setJobClassId(Long jobClassId) {
		this.jobClassId = jobClassId;
	}

	public Boolean getIsJobReport() {
		return isJobReport;
	}

	public void setIsJobReport(Boolean isJobReport) {
		this.isJobReport = isJobReport;
	}

	public Boolean getIsJobCrawler() {
		return isJobCrawler;
	}

	public void setIsJobCrawler(Boolean isJobCrawler) {
		this.isJobCrawler = isJobCrawler;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<PageShowParam> getPageShowParams() {
		return pageShowParams;
	}

	public void setPageShowParams(List<PageShowParam> pageShowParams) {
		this.pageShowParams = pageShowParams;
	}

	public Integer[] getOutputFormats() {
		return outputFormats;
	}

	public void setOutputFormats(Integer[] outputFormats) {
		this.outputFormats = outputFormats;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Boolean getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(Boolean subChannel) {
		this.subChannel = subChannel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageDisplay other = (PageDisplay) obj;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		return true;
	}
}
