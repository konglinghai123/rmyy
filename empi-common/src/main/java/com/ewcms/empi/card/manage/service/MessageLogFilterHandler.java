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
		int modelObjectIndex = aMessageLogFilter.modelObjectIndex();
		if (modelObjectIndex <= -1){
			return;
		}
		Object[] args = pjp.getArgs();
		try {
			MessageLog messageLog = (MessageLog)args[modelObjectIndex];
			messageLogService.save(messageLog);
//			String packageName = args[modelObjectIndex].getClass().getPackage().getName();
//			String modelName = args[modelObjectIndex].getClass().getSimpleName();
//			historyModel.setClassName(packageName + "." + modelName);
//			historyModel.setUserName(EwcmsContextUtil.getUserName());
//
//			historyModel.setMethodName(pjp.getSignature().getName());
//
//			out = new ByteArrayOutputStream();
//			outputStream = new ObjectOutputStream(out);
//			outputStream.writeObject(args[modelObjectIndex]);
//			byte[] bytes = out.toByteArray();
//			historyModel.setModelObject(bytes);
//
//			Field[] fields = args[modelObjectIndex].getClass().getDeclaredFields();
//			Boolean isId = false;
//			for (int j = 0; j < fields.length; j++) {
//				Annotation[] annotations = fields[j].getAnnotations();
//				for (int k = 0; k < annotations.length; k++) {
//					String annotationsName = annotations[k].toString();
//					if (annotationsName.equals("@javax.persistence.Id()")) {
//						isId = true;
//						fields[j].setAccessible(true);
//						historyModel.setIdName(fields[j].getName());
//						historyModel.setIdType(fields[j].getType().getName());
//						historyModel.setIdValue((fields[j].get(args[modelObjectIndex])).toString());
//						break;
//					}
//				}
//				if (isId) {
//					break;
//				}
//			}
//			historyModelDAO.persist(historyModel);
//			historyModelDAO.flush(historyModel);
		} catch (Exception e) {
			
		}
	}
}
