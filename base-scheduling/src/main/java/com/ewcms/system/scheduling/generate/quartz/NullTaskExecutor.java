package com.ewcms.system.scheduling.generate.quartz;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author wuzhijun
 */
@Service
public class NullTaskExecutor implements TaskExecutor {

	@Override
	public void execute(Runnable task) {
		throw new UnsupportedOperationException("This class can only be used as a placeholder for null");
	}

}
