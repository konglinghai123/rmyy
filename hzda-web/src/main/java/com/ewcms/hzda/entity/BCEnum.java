package com.ewcms.hzda.entity;

/**
 * 血浓度
 * 
 * @author wu_zhijun
 *
 */
public enum BCEnum {
	ngml("ng/ml");
	
	private final String info;
	
	private BCEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
