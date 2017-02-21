package com.ewcms.empi.card.manage.entity;

/**
 *@author zhoudongchu
 */
public enum HapiVersion {
	v1("v2.1"), v2("v2.2"), v3("v2.3"), v3_1("v2.3.1"), v4("v2.4"), v5("v2.5"), v5_1("v2.5.1"), v6("v2.6");

	private final String info;

	private HapiVersion(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
