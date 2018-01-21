package com.ewcms.personal.message.entity;

/**
 * 消息类型
 *
 * @author wu_zhijun
 */
public enum MessageType {
	user_message("普通消息"),
	system_message("系统消息");
	
	private final String info;
	
	private MessageType(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
