package com.ewcms.yjk.sb.entity;

/**
 *@author zhoudongchu
 */
public enum AuditStatusEnum {
	nodeclare("未申报"),
	init("等待初审"),
	passed("初审通过"),
	un_passed("初审不通过");
	
	private final String info;
	
	private AuditStatusEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
