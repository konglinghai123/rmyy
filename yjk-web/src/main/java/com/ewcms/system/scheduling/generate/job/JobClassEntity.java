package com.ewcms.system.scheduling.generate.job;

import com.ewcms.system.scheduling.generate.job.report.ExecutionJobReport;
import com.ewcms.system.scheduling.generate.job.sp.ExecutionJobSystemParameter;

/**
 * 执行定时工作任务实体类路径
 * 
 * @author 吴智俊
 */
public final class JobClassEntity {
	//报表执行JOB
	public static final String JOB_REPORT = ExecutionJobReport.class.getName().toString();
	
	public static final String JOB_SYSTEM_PARAMETER = ExecutionJobSystemParameter.class.getName().toString();
}
