package com.ewcms.system.scheduling.generate.job.report;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.entity.Repository;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.entity.TextReport.Type;
import com.ewcms.system.report.generate.factory.ChartFactory;
import com.ewcms.system.report.generate.factory.TextFactory;
import com.ewcms.system.report.service.RepositoryService;
import com.ewcms.system.scheduling.generate.job.BaseExecutionJob;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReport;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReportParameter;
import com.ewcms.system.scheduling.generate.job.report.service.JobReportService;

/**
 * 执行报表Job
 * 
 * @author 吴智俊
 */
public class ExecutionJobReport extends BaseExecutionJob {

	private static final Logger logger = LoggerFactory.getLogger(ExecutionJobReport.class);
    
    public static final String JOB_REPORT_SERVICE = "jobReportService";
    public static final String REPOSITORY_SERVICE = "repositoryService";
    public static final String CHART_FACTORY = "chartFactory";
    public static final String TEXT_FACTORY = "textFactory";

    private JobReport ewcmsJobReport;

    protected void jobExecute(Long jobId) throws Exception {
        ewcmsJobReport = getJobReportService().findOne(jobId);
        if (ewcmsJobReport != null){
	        String outputFormat = ewcmsJobReport.getOutputFormat();
	        String label = ewcmsJobReport.getLabel();
	        String description = ewcmsJobReport.getDescription();
	        
	        logger.info("生成报表开始...");
	        if (outputFormat != null && outputFormat.length() > 0) {
	            StringTokenizer tokenizerOutput = new StringTokenizer(outputFormat, ",", false);
	            while (tokenizerOutput.hasMoreElements()) {
	                Integer iOutput = Integer.valueOf(tokenizerOutput.nextToken());
	                attachOutput(iOutput, label, description);
	            }
	        }else{
	        	attachOutput(null, label, description);
	        }
	        logger.info("生成报表结束.");
        }
    }

	protected void jobClear() {
		ewcmsJobReport = null;		
	}
	
	private void attachOutput(Integer iOutput, String label, String description) throws Exception {
        Set<JobReportParameter> jobParams = ewcmsJobReport.getJobReportParameters();
        Map<String, String> parameters = new LinkedHashMap<String, String>();
        for (JobReportParameter jobParameter : jobParams) {
            String enName = jobParameter.getParameter().getEnName();
            String value = jobParameter.getParameterValue();
            parameters.put(enName, value);
        }

        TextReport textReport = ewcmsJobReport.getTextReport();
        ChartReport chartReport = ewcmsJobReport.getChartReport();

        String fileType = "";
        byte[] bytes = null;
        if (textReport != null) {
            TextFactory reportEngine = getTextFactory();
            Type textEnum = conversionToEnum(iOutput);
            bytes = reportEngine.export(parameters, textReport, textEnum, null);
            fileType = textEnum.getDescription();
        }

        if (chartReport != null) {
            fileType = "PNG";
            ChartFactory chartEngine = getChartFactory();
            bytes = chartEngine.export(chartReport, parameters);
        }

        if (bytes != null){
        	RepositoryService repositoryService = getRepositoryService();
	        Repository repository = new Repository();
	        
	        repository.setEntity(bytes);
	        repository.setType(fileType);
	        repository.setName(label);
	        repository.setDescription(description);
	        repository.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
	        repositoryService.save(repository);
        }
    }

    private JobReportService getJobReportService(){
    	return (JobReportService) applicationContext.getBean(JOB_REPORT_SERVICE);
    }
    
    private RepositoryService getRepositoryService(){
    	return (RepositoryService) applicationContext.getBean(REPOSITORY_SERVICE);
    }
    
    private ChartFactory getChartFactory() {
        return (ChartFactory) applicationContext.getBean(CHART_FACTORY);
    }

    private TextFactory getTextFactory() {
        return (TextFactory) applicationContext.getBean(TEXT_FACTORY);
    }
    
    private Type conversionToEnum(Integer iOutput) {
        if (iOutput.intValue() == Type.HTML.ordinal()) {
            return Type.HTML;
        } else if (iOutput.intValue() == Type.PDF.ordinal()) {
            return Type.PDF;
        } else if (iOutput.intValue() == Type.XLS.ordinal()) {
            return Type.XLS;
        } else if (iOutput.intValue() == Type.RTF.ordinal()) {
            return Type.RTF;
        } else if (iOutput.intValue() == Type.XML.ordinal()) {
            return Type.XML;
        } else {
            return Type.PDF;
        }
    }
}
