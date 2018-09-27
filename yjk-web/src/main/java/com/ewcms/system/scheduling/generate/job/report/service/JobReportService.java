package com.ewcms.system.scheduling.generate.job.report.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.generate.factory.ChartFactory;
import com.ewcms.system.report.generate.factory.TextFactory;
import com.ewcms.system.report.service.ChartReportService;
import com.ewcms.system.report.service.TextReportService;
import com.ewcms.system.scheduling.entity.JobClass;
import com.ewcms.system.scheduling.entity.JobInfo;
import com.ewcms.system.scheduling.generate.SchedulingFactory;
import com.ewcms.system.scheduling.generate.job.JobClassEntity;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReport;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReportParameter;
import com.ewcms.system.scheduling.generate.job.report.repository.JobReportRepository;
import com.ewcms.system.scheduling.service.JobClassService;
import com.ewcms.system.scheduling.service.JobInfoService;
import com.ewcms.system.scheduling.util.SchedulingConversionUtil;
import com.ewcms.system.scheduling.web.controller.entity.PageDisplay;
import com.google.common.collect.Sets;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class JobReportService extends BaseService<JobReport, Long>{

	private JobReportRepository getJobReportRepository(){
		return (JobReportRepository) baseRepository;
	}
	
	@Autowired
	private ChartReportService chartReportService;
	@Autowired
	private TextReportService textReportService;
	@Autowired
	private JobInfoService jobInfoService;
	@Autowired
	private JobClassService jobClassService;
	@Autowired
	private SchedulingFactory schedulingFactory;
	@Autowired
	private TextFactory textFactory;
	@Autowired
	private ChartFactory chartFactory;
	
	public Long saveOrUpdateJobReport(PageDisplay pageDisplay, HttpServletRequest request) {
		String reportType = pageDisplay.getReportType();
		Long reportId = pageDisplay.getReportId();
		
		TextReport textReport = null;
		ChartReport chartReport = null;
		
		Set<JobReportParameter> ewcmsJobParameters = Sets.newLinkedHashSet();
		if (reportType.equals("text")) {
			textReport = textReportService.findOne(reportId);
			SchedulingConversionUtil.pageToJob(ewcmsJobParameters, textReport.getParameters(), request);
		} else if (reportType.equals("chart")) {
			chartReport = chartReportService.findOne(reportId);
			SchedulingConversionUtil.pageToJob(ewcmsJobParameters, chartReport.getParameters(), request);
		}
		
		if (EmptyUtil.isNotNull(textReport) || EmptyUtil.isNotNull(chartReport)){
			
			JobInfo jobInfo = null;
			if (EmptyUtil.isNotNull(pageDisplay.getJobId())){
				jobInfo = jobInfoService.findOne(pageDisplay.getJobId());
			} else {
				jobInfo = new JobInfo();
			}
			
//			if (EmptyUtil.isNull(jobInfo)) {
//				throw new SchedulingException("scheduling.not.exists", null);
//			}
			
			jobInfo = SchedulingConversionUtil.constructJobInfo(jobInfo, pageDisplay);

			JobReport jobReport = new JobReport();
			if (pageDisplay.getJobId() != null && pageDisplay.getJobId().intValue() > 0) {
				jobReport.setId(pageDisplay.getJobId());
				jobReport.setJobClass(jobInfo.getJobClass());
			}else{
				JobClass jobClass = jobClassService.findByClassName(JobClassEntity.JOB_REPORT);
				if (jobClass == null) {
					jobClass = new JobClass();
					jobClass.setClassName(JobClassEntity.JOB_REPORT);
					jobClass.setName("报表定时器类");
					jobClass.setDescription("报表定时器类");
					jobClassService.save(jobClass);
				}
				jobReport.setJobClass(jobClass);
			}
			
			if (reportType.equals("text")){
				if (pageDisplay.getOutputFormats() != null && pageDisplay.getOutputFormats().length > 0){
					jobReport.setOutputFormat(SchedulingConversionUtil.arrayToString(pageDisplay.getOutputFormats()));
				}else{
					jobReport.setOutputFormat(String.valueOf(JobReport.OUTPUT_FORMAT_PDF));
				}
			}
			
			jobReport.setJobReportParameters(ewcmsJobParameters);
			jobReport.setDescription(jobInfo.getDescription());
			jobReport.setLabel(jobInfo.getLabel());
			jobReport.setNextFireTime(jobInfo.getNextFireTime());
			jobReport.setOutputLocale(jobInfo.getOutputLocale());
			jobReport.setPreviousFireTime(jobInfo.getPreviousFireTime());
			jobReport.setState(jobInfo.getState());
			jobReport.setTrigger(jobInfo.getTrigger());
			jobReport.setUserName(jobInfo.getUserName());
			jobReport.setVersion(jobInfo.getVersion());
			jobReport.setTextReport(textReport);
			jobReport.setChartReport(chartReport);
			
			if (jobReport.getId() == null) {
				return schedulingFactory.saveScheduleJob(jobReport);
			} else {
				return schedulingFactory.updateScheduledJob(jobReport);
			}
		}
		return null;
	}

	public JobReport findJobReportByReportIdAndReportType(Long reportId, String reportType) {
		return getJobReportRepository().findJobReportByReportIdAndReportType(reportId, reportType);
	}

	public List<JobReportParameter> findByJobReportParameterById(Long jobReportId) {
		return getJobReportRepository().findByJobReportParameterById(jobReportId);
	}
	
	public PageDisplay convertPageUse(Long reportId, String reportType){
		PageDisplay pageDisplay = new PageDisplay();

		JobReport jobReport = getJobReportRepository().findJobReportByReportIdAndReportType(reportId, reportType);
		
		if (jobReport != null) {
			
			pageDisplay = SchedulingConversionUtil.constructPage(jobReport);
			
			pageDisplay.setState(jobInfoService.findJobInfoState(jobReport.getId()));
			
			if (reportType.equals("text")) {
				TextReport text = jobReport.getTextReport();
				pageDisplay.setReportId(text.getId());
				pageDisplay.setReportName(text.getName());
				pageDisplay.setPageShowParams(SchedulingConversionUtil.coversionParameterFromPage(findByJobReportParameterById(jobReport.getId()), textFactory.textParameters(text)));
				pageDisplay.setOutputFormats(SchedulingConversionUtil.stringToArray(((JobReport) jobReport).getOutputFormat()));
			} else if (reportType.equals("chart")) {
				ChartReport chart = jobReport.getChartReport();
				pageDisplay.setReportId(chart.getId());
				pageDisplay.setReportName(chart.getName());
				pageDisplay.setPageShowParams(SchedulingConversionUtil.coversionParameterFromPage(findByJobReportParameterById(jobReport.getId()), chartFactory.chartParameters(chart)));
			}
		} else {
			if (reportType.equals("text")) {
				TextReport text = textReportService.findOne(reportId);
				pageDisplay.setLabel(text.getName());
				pageDisplay.setReportName(text.getName());
				pageDisplay.setPageShowParams(textFactory.textParameters(text));
			} else if (reportType.equals("chart")) {
				ChartReport chart = chartReportService.findOne(reportId);
				pageDisplay.setLabel(chart.getName());
				pageDisplay.setReportName(chart.getName());
				pageDisplay.setPageShowParams(chartFactory.chartParameters(chart));
			}
		}
		pageDisplay.setReportId(reportId);
		pageDisplay.setReportType(reportType);
		pageDisplay.setIsJobReport(true);
		
		return pageDisplay;
	}
}
