package com.ewcms.webservice.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.utils.LogUtils;

/**
 *
 * @author wu_zhijun
 */
public class WebServiceUtil {
	
	public static String getClientIpCxf() {
		try{
			Message message = PhaseInterceptorChain.getCurrentMessage();
	        HttpServletRequest request = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);
	        
	        return request.getRemoteAddr(); 
		} catch (Exception e){
			return "unknow";
		}
     }
	
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
		s.append(LogUtils.getBlock(op));
		s.append(LogUtils.getBlock(msg));
		
		getWsAccessLog().info(s.toString(), args);
	}

}
