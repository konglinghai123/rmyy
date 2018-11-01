package com.ewcms.yjk.zd.commonname.entity;

/**
 *@author zhoudongchu
 */
public enum DrugCategoryEnum {
	Z("中药"),
	H("西药"),
	Q("其它");
	
	private final String info;
	
	private DrugCategoryEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
