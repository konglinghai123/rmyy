package com.ewcms.system.scheduling.generate.job.sp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.system.scheduling.generate.job.BaseExecutionJob;
import com.ewcms.yjk.sp.service.SystemParameterService;

/**
 * 执行系统参数
 * 
 * @author 吴智俊
 */
public class ExecutionJobSystemParameter extends BaseExecutionJob {
	
    public static final String SYSTEM_PARAMETER_SERVICE = "systemParameterService";

    protected void jobExecute(Long jobId) throws Exception {
    	System.out.println("---1----");
       getSystemParameterService().isCloseDeclare();
    }

    private SystemParameterService getSystemParameterService(){
    	return (SystemParameterService) applicationContext.getBean(SYSTEM_PARAMETER_SERVICE);
    }

	@Override
	protected void jobClear() {
	}
}
