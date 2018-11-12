package com.ewcms.extra.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.yjk.sp.service.SystemParameterService;

/**
 * 系统申报达到结束时间自动关闭
 * @author wuzhijun
 *
 */
@Service("declareCloseTask")
public class DeclareCloseTask {
	
	@Autowired
	private SystemParameterService systemParameterService;
	
	public void autoCloseDeclare() {
		systemParameterService.isCloseDeclare();
	}
}
