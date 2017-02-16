package com.ewcms.system.scheduling.generate.job.report.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.scheduling.entity.JobInfo;
import com.google.common.collect.Sets;

/**
 * 报表调度器任务
 * 
 * <ul>
 * <li>mail:AlqcJobMail对象</li>
 * <li>outputFormat:输出格式</li>
 * <li>ewcmsJobParameters:EwcmsJobParameter集合</li>
 * <li>report:Report对象</li>
 * <li>chart:Chart对象</li>
 * </ul>
 *
 * @author wu_zhijun
 */
@Entity
@Table(name = "sys_job_report")
@PrimaryKeyJoinColumn(name = "info_id")
public class JobReport extends JobInfo {

	private static final long serialVersionUID = -6943912221507320883L;
	//输出格式
    public static final Integer OUTPUT_FORMAT_HTML = 0;
    public static final Integer OUTPUT_FORMAT_PDF = 1;
    public static final Integer OUTPUT_FORMAT_XLS = 2;
    public static final Integer OUTPUT_FORMAT_RTF = 3;
    public static final Integer OUTPUT_FORMAT_XML = 4;
    
    @Column(name = "outputformat", length = 20)
    private String outputFormat;
    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = JobReportParameter.class, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "job_report_id")
    private Set<JobReportParameter> jobReportParameters = Sets.newLinkedHashSet();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "report_id")
    private TextReport textReport;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "chart_id")
    private ChartReport chartReport;

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    @JSONField(serialize = false)
    public Set<JobReportParameter> getJobReportParameters() {
        return jobReportParameters;
    }

    public void setJobReportParameters(Set<JobReportParameter> jobReportParameters) {
        this.jobReportParameters = jobReportParameters;
    }

    @JSONField(serialize = false)
    public TextReport getTextReport() {
        return textReport;
    }

    public void setTextReport(TextReport textReport) {
        this.textReport = textReport;
    }

    @JSONField(serialize = false)
    public ChartReport getChartReport() {
        return chartReport;
    }

    public void setChartReport(ChartReport chartReport) {
        this.chartReport = chartReport;
    }
}
