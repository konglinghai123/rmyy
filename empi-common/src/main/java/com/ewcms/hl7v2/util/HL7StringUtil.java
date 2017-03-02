package com.ewcms.hl7v2.util;

/**
 *
 * @author wu_zhijun
 */
public class HL7StringUtil {

	public static String format(String sHL7){
		StringBuffer result = new StringBuffer();
		
		String[] sHL7Lines = sHL7.split("\n");
		for (String sHL7Line : sHL7Lines){
			result.append(sHL7Line + "\r");
		}
		
		return result.toString();
	}
}
