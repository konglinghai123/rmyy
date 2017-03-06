package com.ewcms.empi.card.manage.service;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
/**
 *@author zhoudongchu
 */
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageLogFilter {
	int modelObjectIndex() default -1;
}
