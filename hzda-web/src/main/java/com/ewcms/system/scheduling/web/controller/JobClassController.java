package com.ewcms.system.scheduling.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.system.scheduling.entity.JobClass;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/scheduling/jobclass")
public class JobClassController extends BaseCRUDController<JobClass, Long>{
	
	public JobClassController() {
		setResourceIdentity("system:schedulingjobclass");
	}
}
