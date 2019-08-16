package com.ewcms.yjk.re.entity;

/**
 * 
 * @author wu_zhijun
 *
 */
public enum AdjustedEnum {

	transferIn("调入"),
	callOut("调出"),
	notAdjusted("");
	
	private final String info;
	
	private AdjustedEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
