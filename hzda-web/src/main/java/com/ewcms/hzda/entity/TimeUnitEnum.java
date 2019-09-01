package com.ewcms.hzda.entity;

/**
 * 时间单位
 * 
 * @author wu_zhijun
 *
 */

public enum TimeUnitEnum {
	day("天"),week("周"),month("月"),season("季"),year("年");
	
	private final String info;
	
	private TimeUnitEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
