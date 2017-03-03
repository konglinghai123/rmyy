package com.ewcms.empi.card.manage.service;

import java.io.IOException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import com.ewcms.empi.card.manage.entity.MessageLog;

/**
 *@author zhoudongchu
 */
@Service
@Aspect
public class MessageLogFilterHandler {
	@Autowired
	private MessageLogService messageLogService;
	
	@Pointcut("@annotation(aMessageLogFilter)")
	private void save(MessageLogFilter aMessageLogFilter) {
		
		
	}

	@After(value = "save(aMessageLogFilter)", argNames = "aMessageLogFilter")
	public void saveCompress(JoinPoint pjp, MessageLogFilter aMessageLogFilter) throws Throwable {
		System.out.print(aMessageLogFilter.ip());
	}
}
