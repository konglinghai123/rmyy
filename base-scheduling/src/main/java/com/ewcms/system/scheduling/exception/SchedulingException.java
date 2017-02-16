package com.ewcms.system.scheduling.exception;

import com.ewcms.common.exception.BaseException;

/**
 *
 * @author 吴智俊
 */
public class SchedulingException extends BaseException {

	private static final long serialVersionUID = -1153311318717256864L;

	public SchedulingException(String code, Object[] args) {
        super("scheduling", code, args, null);
    }

}