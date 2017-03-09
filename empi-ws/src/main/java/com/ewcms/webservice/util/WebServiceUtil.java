package com.ewcms.webservice.util;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.exception.BaseException;
import com.ewcms.common.utils.LogUtils;

/**
 *
 * @author wu_zhijun
 */
public final class WebServiceUtil {
	
	private WebServiceUtil(){
	}
	
	private static MessageDigest digest;
	
	public static synchronized byte[] generateDigest(byte[] inputBytes) throws BaseException {
        try {
            if (digest == null) {
                digest = MessageDigest.getInstance("SHA-1");
            }
            return digest.digest(inputBytes);
        } catch (Exception e) {
            throw new BaseException("Error in generating digest");
        }
    }
	
//	public static synchronized String generateDigestStr(byte[] inputBytes) throws BaseException {
//        try {
//            if (digest == null) {
//                digest = MessageDigest.getInstance("SHA-1");
//            }
//            return HexBinaryAdapter().marshal(digest.digest(inputBytes));
//        } catch (Exception e) {
//            throw new BaseException("Error in generating digest");
//        }
//    }
	
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
