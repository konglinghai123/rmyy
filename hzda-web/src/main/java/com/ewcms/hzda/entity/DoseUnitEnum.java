package com.ewcms.hzda.entity;

/**
 * 剂量单位
 * 
 * @author wu_zhijun
 *
 */
public enum DoseUnitEnum {
	mg("mg"), g("g"), slice("片");
	
	private final String info;
	
	private DoseUnitEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
