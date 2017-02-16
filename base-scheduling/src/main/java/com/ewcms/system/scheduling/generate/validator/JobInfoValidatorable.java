/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.system.scheduling.generate.validator;

import com.ewcms.system.scheduling.entity.JobInfo;
import com.ewcms.system.scheduling.generate.common.ValidationErrorsable;

/**
 * 调度任务时间表达式效验接口
 *
 * @author 吴智俊
 */
public interface JobInfoValidatorable {

    public ValidationErrorsable validateJob(JobInfo job);
}
