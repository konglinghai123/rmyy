package com.ewcms.yjk.sb.entity;

/**
 *@author zhoudongchu
 */
public enum SbState {
	init("等待审批"),
	passed("审批通过"),
	un_passed("审批未通过");
	
	private final String info;
	
	private SbState(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
