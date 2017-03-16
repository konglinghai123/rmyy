package com.ewcms.empi.card.manage.defined;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * HL7定义的婚姻状态
 *
 * @author wu_zhijun
 */
public enum Marital {
	A("Separated", "分居"), 
	D("Divorced", "离婚"), 
	M("Married", "已婚"), 
	S("Single", "独身"), 
	W("Widowed", "丧偶"), 
	C("Common law", "普通法律"), 
	G("Living together", "同居"),
	P("Domestic partner", "同性恋"),
	R("Registered domestic partner", "已登记同性恋"),
	E("Legally Separated", "合法分居"),
	N("Annulled", "废止"),
	I("Interlocutory", "中间"),
	B("Unmarried", "未婚"),
	U("Unknown", "未知"),
	O("Other", "其他"),
	T("Unreported", "未报告")
	;
	
	private final String enName;
	private final String cnName;
	
	Marital(final String enName, final String cnName){
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
	
	public static Marital valueByCnName(String cnName){
		cnName = formatCnName(cnName);
		for (Marital event : values()){
			if (event.getCnName().equals(cnName)){
				return event;
			}
		}
		
		return Marital.U;
	}
	
	private static String formatCnName(String cnName){
		if (StringUtils.isBlank(cnName)){
			return cnName;
		}
		return cnName.trim().toLowerCase().replace("   ", "  ");
	}

	public static Marital valueByEnName(String enName) {
		enName = formatEnName(enName);
		for (Marital event : values()){
			if (event.getEnName().equals(enName)){
				return event;
			}
		}
		
		return Marital.U;
	}
	
	private static String formatEnName(String enName){
		if (StringUtils.isBlank(enName)){
			return enName;
		}
		return enName.trim().toLowerCase().replace("   ", "  ");
	}
}
