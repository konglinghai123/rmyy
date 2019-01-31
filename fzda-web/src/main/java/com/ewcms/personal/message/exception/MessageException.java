package com.ewcms.personal.message.exception;

import com.ewcms.common.exception.BaseException;

/**
 *
 * @author wu_zhijun
 */
public class MessageException extends BaseException{

	private static final long serialVersionUID = -442715888773407005L;

	public MessageException(String code, Object[] args){
		super("personal", code, args);
	}
}
