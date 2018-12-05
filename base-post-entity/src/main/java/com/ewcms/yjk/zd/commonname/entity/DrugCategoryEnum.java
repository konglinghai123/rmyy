package com.ewcms.yjk.zd.commonname.entity;

/**
 *@author zhoudongchu
 */
public enum DrugCategoryEnum {
	Z("中成药"),
	H("西药"),
	Q("其他");
	
	private final String info;
	
	private DrugCategoryEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
