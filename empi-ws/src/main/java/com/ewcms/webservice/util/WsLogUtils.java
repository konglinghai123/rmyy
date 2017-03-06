package com.ewcms.webservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.utils.IpUtils;
import com.ewcms.common.utils.LogUtils;

/**
 *
 * @author 吴智俊
 */
public class WsLogUtils {

	private static final Logger WS_ACCESS_LOGGER = LoggerFactory.getLogger("empi-ws");
	
	private static Logger getWsAccessLog(){
		return WS_ACCESS_LOGGER;
	}
	
	/**
	 * 记录格式[ip][操作][错误消息]
	 * @param op
	 * @param msg
	 * @param args
	 */
	public static void log(String op, String msg, Object... args){
		StringBuilder s = new StringBuilder();
		s.append(LogUtils.getBlock(IpUtils.getIp()));
		s.append(LogUtils.getBlock(op));
		s.append(LogUtils.getBlock(msg));
		
		getWsAccessLog().info(s.toString(), args);
	}
}
