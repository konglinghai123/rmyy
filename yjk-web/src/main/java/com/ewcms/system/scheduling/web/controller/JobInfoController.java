package com.ewcms.system.scheduling.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.generate.factory.ChartFactory;
import com.ewcms.system.report.generate.factory.TextFactory;
import com.ewcms.system.scheduling.entity.JobClass;
import com.ewcms.system.scheduling.entity.JobInfo;
import com.ewcms.system.scheduling.entity.JobInfoState;
import com.ewcms.system.scheduling.generate.SchedulingFactory;
import com.ewcms.system.scheduling.generate.common.ValidationErrorable;
import com.ewcms.system.scheduling.generate.common.ValidationErrorsable;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReport;
import com.ewcms.system.scheduling.generate.job.report.service.JobReportService;
import com.ewcms.system.scheduling.service.JobClassService;
import com.ewcms.system.scheduling.service.JobInfoService;
import com.ewcms.system.scheduling.util.SchedulingConversionUtil;
import com.ewcms.system.scheduling.web.controller.entity.PageDisplay;
import com.google.common.collect.Lists;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/scheduling/jobinfo")
public class JobInfoController extends BaseCRUDController<JobInfo, Long>{

	private JobInfoService getJobInfoService(){
		return (JobInfoService) baseService;
	}
	
	@Autowired
	private SchedulingFactory schedulingFactory;
	@Autowired
	private JobClassService jobClassService;
	@Autowired
	private JobReportService jobReportService;
	@Autowired
	private TextFactory textFactory;
	@Autowired
	private ChartFactory chartFactory;
	@Autowired
	private MessageSource messageSource;

	public JobInfoController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("system:schedulingjobinfo");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("jobClasses", jobClassService.findAll());
		model.addAttribute("jobInfoStates", JobInfoState.values());
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
		
	@RequestMapping(value = "{isClose}/edit", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable("isClose") Boolean isClose, 
			@RequestParam(required = false) List<Long> selections, 
			@RequestParam(required = false) String className, 
			@RequestParam(required = false) Long objectId, 
			@RequestParam(required = false) String reportType,
			Model model) {
		
		if (permissionList != null) {
            this.permissionList.assertHasUpdatePermission();
        }
		
		setCommonData(model);
		
		PageDisplay pageDisplay = null;
		if (EmptyUtil.isCollectionNotEmpty(selections)){
			JobInfo jobInfo = getJobInfoService().findOne(selections.get(0));
			pageDisplay = convertPageUse(jobInfo);
			pageDisplay.setState(getJobInfoService().findJobInfoState(selections.get(0)));
		} else if (EmptyUtil.isNotNull(objectId)){
//			if (className.toLowerCase().equals(JobChannel.class.getCanonicalName().toLowerCase())){
//				pageDisplay = jobChannelService.convertPageUser(objectId);
//			} else 
			if (className.toLowerCase().equals(JobReport.class.getCanonicalName().toLowerCase())){
				pageDisplay = jobReportService.convertPageUse(objectId, reportType);
			}
		}
		
		model.addAttribute("selections", selections);
		model.addAttribute("m", pageDisplay);
		model.addAttribute("className", className);
		model.addAttribute("objectId", objectId);
		
		return viewName("edit");
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, JobInfo m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{isClose}/save", method = RequestMethod.POST)
	public String save(@PathVariable("isClose") Boolean isClose, 
			@ModelAttribute("m") PageDisplay m, BindingResult result,
			@RequestParam(required = false) List<Long> selections, 
			@RequestParam(required = false) String className,
			@RequestParam(required = false) Long objectId,
			HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		
		JobInfo jobInfo = new JobInfo();
		if (m.getJobId() != null && m.getJobId().intValue() > 0) {
			jobInfo = getJobInfoService().findOne(m.getJobId());
		}
		if (m.getJobClassId() != null && m.getJobClassId().intValue() > 0) {
			JobClass alqcJobClass = jobClassService.findOne(m.getJobClassId());
			jobInfo.setJobClass(alqcJobClass);
		}
		jobInfo = SchedulingConversionUtil.constructJobInfo(jobInfo, m);

		ValidationErrorsable validate = schedulingFactory.validateJob(jobInfo);
		if (validate.isError()){
			List<ValidationErrorable> errors = validate.getErrors();
			model.addAttribute(Constants.ERROR, messageSource.getMessage("scheduling.validate.error", new Object[]{errors}, null));
			return showSaveForm(isClose, selections, className, objectId, m.getReportType(), model);
		}
		
		if (isClose){
			Boolean close = Boolean.FALSE;
			if (jobInfo.getId() != null && StringUtils.hasText(jobInfo.getId().toString())) {
				schedulingFactory.updateScheduledJob(jobInfo);
				selections.remove(0);
				if (selections == null || selections.isEmpty()) {
					close = Boolean.TRUE;
				} else {
					jobInfo = getJobInfoService().findOne(selections.get(0));
					m = convertPageUse(jobInfo);
					model.addAttribute("m", m);
					model.addAttribute("selections", selections);
				}
			} else {
				schedulingFactory.saveScheduleJob(jobInfo);
				selections = selections == null ? Lists.<Long>newArrayList() : selections;
				selections.add(0, jobInfo.getId());
				model.addAttribute("m", new PageDisplay());
				model.addAttribute("selections", selections);
			}
			model.addAttribute("close", close);
			return viewName("edit");
		} else {
//			if (className.toLowerCase().equals(JobChannel.class.getCanonicalName().toLowerCase())){
//				jobChannelService.saveOrUpdateJobChannel(m);
//			} else 
			if (className.toLowerCase().equals(JobReport.class.getCanonicalName().toLowerCase())){
				jobReportService.saveOrUpdateJobReport(m, request);
			}
		    redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改成功");
		    return redirectToUrl(viewName("false/edit?className=" + className + "&objectId=" + objectId + "&reportType=" + m.getReportType()));
		}
	}
	
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections){
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("删除成功！");
        
        try{
        	for (Long id : selections) {
    			schedulingFactory.deletedScheduledJob(id);
    		}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("删除失败了！");
        }
		return ajaxResponse;
	}
	
	@RequestMapping(value = "{jobId}/pause")
	@ResponseBody
	public AjaxResponse pauseJob(@PathVariable(value = "jobId") Long jobId) {
		AjaxResponse ajaxResponse = new AjaxResponse("暂停任务成功");
		try{
			schedulingFactory.pauseJob(jobId);
		} catch(IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("暂停任务失败");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "{jobId}/resumed")
	@ResponseBody
	public AjaxResponse resumedJob(@PathVariable(value = "jobId") Long jobId) {
		AjaxResponse ajaxResponse = new AjaxResponse("启动任务成功");
		try{
			schedulingFactory.resumedJob(jobId);
		} catch(IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("启动任务失败");
		}
		return ajaxResponse;
	}

	private PageDisplay convertPageUse(JobInfo jobInfo) {
		PageDisplay pageDisplay = SchedulingConversionUtil.constructPage(jobInfo);

//		if (jobInfo instanceof JobChannel) {
//			pageDisplay.setSubChannel(((JobChannel) jobInfo).getSubChannel());
//			pageDisplay.setIsJobChannel(true);
//		} else 
		if (jobInfo instanceof JobReport) {
			TextReport textReport = ((JobReport) jobInfo).getTextReport();
			ChartReport chartReport = ((JobReport) jobInfo).getChartReport();
			pageDisplay = SchedulingConversionUtil.constructPage((JobReport) jobInfo);
			if (textReport != null) {
				pageDisplay.setReportId(textReport.getId());
				pageDisplay.setReportName(textReport.getName());
				pageDisplay.setReportType("text");
				pageDisplay.setPageShowParams(SchedulingConversionUtil.coversionParameterFromPage(jobReportService.findByJobReportParameterById(jobInfo.getId()), textFactory.textParameters(textReport)));
				pageDisplay.setOutputFormats(SchedulingConversionUtil.stringToArray(((JobReport) jobInfo).getOutputFormat()));
			} else if (chartReport != null) {
				pageDisplay.setReportId(chartReport.getId());
				pageDisplay.setReportName(chartReport.getName());
				pageDisplay.setReportType("chart");
				pageDisplay.setPageShowParams(SchedulingConversionUtil.coversionParameterFromPage(jobReportService.findByJobReportParameterById(jobInfo.getId()), chartFactory.chartParameters(chartReport)));
			}
			pageDisplay.setIsJobReport(true);
//			model.addAttribute("pageShowParams", pageDisplay.getPageShowParams());
//		} else if (jobInfo instanceof EwcmsJobCrawler) {
//			pageDisplay.setIsJobCrawler(true);
		}

//		List<JobClass> jobClassList = schedulingFactory.findByAllJobClass();
//		if (!pageDisplay.getIsJobChannel()) {
//			JobClass jobClass = schedulingFactory.findByJobClassByClassEntity(JobClassEntity.JOB_CHANNEL);
//			jobClassList.remove(jobClass);
//		}
//		if (!pageDisplay.getIsJobReport()) {
//			JobClass jobClass = schedulingFactory.findByJobClassByClassEntity(JobClassEntity.JOB_REPORT);
//			jobClassList.remove(jobClass);
//		}
////		if (!pageDisplay.getIsJobCrawler()) {
////			 JobClass jobClass =
////			 schedulingFac.findByJobClassByClassEntity(JobClassEntity.JOB_CRAWLER);
////			 jobClassList.remove(jobClass);
////		}
//		model.addAttribute("jobClassList", jobClassList);
		return pageDisplay;
	}

}
