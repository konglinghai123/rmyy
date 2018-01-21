package com.ewcms.weixin.util;

import java.security.MessageDigest;

/**
 *
 * @author wu_zhijun
 */
public final class SHA1 {
	
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	private static String getFormattedText(byte[] bytes){
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int i = 0; i < len; i++){
			buf.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return buf.toString();
	}
	
	public static String encode(String str){
		if (str == null) return null;
		
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}