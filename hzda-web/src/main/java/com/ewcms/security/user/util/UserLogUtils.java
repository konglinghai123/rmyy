package com.ewcms.security.user.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.utils.IpUtils;
import com.ewcms.common.utils.LogUtils;

/**
 *
 * @author 吴智俊
 */
public class UserLogUtils {

	private static final Logger SYS_USER_LOGGER = LoggerFactory.getLogger("hzda-sys-user");
	
	private static Logger getSysUserLog(){
		return SYS_USER_LOGGER;
	}
	
	/**
	 * 记录格式[ip][用户名][操作][错误消息]
	 * 注意操作如下:
	 * loginError 登录失败
	 * loginSuccess 登录成功
	 * passwordError 密码错误
	 * changePassword 修改密码
	 * changeStatus 修改状态
	 * 
	 * @param username
	 * @param op
	 * @param msg
	 * @param args
	 */
	public static void log(String username, String op, String msg, Object... args){
		StringBuilder s = new StringBuilder();
		s.append(LogUtils.getBlock(IpUtils.getIp()));
		s.append(LogUtils.getBlock(username));
		s.append(LogUtils.getBlock(op));
		s.append(LogUtils.getBlock(msg));
		
		getSysUserLog().info(s.toString(), args);
	}
	
}
