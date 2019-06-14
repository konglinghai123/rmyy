package com.ewcms.yjk.re.entity;

public enum VoteTypeEnum {
	pass("通过"),
	oppose("反对"),
	abstain("弃权");
	
	private final String info;
	
	private VoteTypeEnum(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
