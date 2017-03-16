package com.ewcms.empi.card.manage.defined;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * HL7定义性别
 * 
 * @author wu_zhijun
 */
public enum Sex {
	F("Femal", "男"), 
	M("Male", "女"), 
	O("Other", "其他"), 
	U("Unknown", "未知"), 
	A("Ambiguous", "不明确"), 
	N("Not applicable", "不适用")
	;
	
	private final String enName;
	private final String cnName;
	
	Sex(final String enName, final String cnName){
		this.enName = enName;
		this.cnName = cnName;
	}
	
	public String getEnName(){
		return enName;
	}
	
	public String getCnName(){
		return cnName;
	}
	
	public static String toStringAllSex(){
		return Arrays.toString(Sex.values());
	}
	
	public static Sex valueByCnName(String cnName){
		cnName = formatCnName(cnName);
		for (Sex event : values()){
			if (event.getCnName().equals(cnName)){
				return event;
			}
		}
		
		return Sex.U;
	}
	
	private static String formatCnName(String cnName){
		if (StringUtils.isBlank(cnName)){
			return cnName;
		}
		return cnName.trim().toLowerCase().replace("   ", "  ");
	}

	public static Sex valueByEnName(String enName) {
		enName = formatEnName(enName);
		for (Sex event : values()){
			if (event.getEnName().equals(enName)){
				return event;
			}
		}
		
		return Sex.U;
	}
	
	private static String formatEnName(String enName){
		if (StringUtils.isBlank(enName)){
			return enName;
		}
		return enName.trim().toLowerCase().replace("   ", "  ");
	}
}
