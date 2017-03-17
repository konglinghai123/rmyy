package com.ewcms.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 通过Shiro Session,根据属性名获取参数值
 * 
 * @author 吴智俊
 */
public final class ShiroSessionUtil {
	
	private static Subject getShiroSubject(){
		return SecurityUtils.getSubject();
	}
	
	public static String getCurrentUsername(){
		return (String)getShiroSubject().getPrincipal();
	}
}
