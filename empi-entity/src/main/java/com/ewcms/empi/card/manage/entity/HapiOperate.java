package com.ewcms.empi.card.manage.entity;

/**
 *@author zhoudongchu
 */
public enum HapiOperate {
	receive("接收"), send("发送");

	private final String info;

	private HapiOperate(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
