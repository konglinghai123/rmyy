package com.ewcms.yjk.zd.commonname.entity;

/**
 *@author zhoudongchu
 */
public enum DrugCategoryEnum {
	z("中药"),
	x("西药"),
	h("其它");
	
	private final String info;
	
	private DrugCategoryEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
