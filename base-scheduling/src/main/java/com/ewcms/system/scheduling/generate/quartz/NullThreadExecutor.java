package com.ewcms.system.scheduling.generate.quartz;

import org.quartz.spi.ThreadExecutor;
import org.springframework.stereotype.Service;

/**
 * 
 * @author wuzhijun
 *
 */
@Service
public class NullThreadExecutor implements ThreadExecutor {

	@Override
	public void initialize() {
		throw new UnsupportedOperationException("This class can only be used as a placeholder for null");
	}

	@Override
	public void execute(Thread thread) {
		throw new UnsupportedOperationException("This class can only be used as a placeholder for null");
	}

}
