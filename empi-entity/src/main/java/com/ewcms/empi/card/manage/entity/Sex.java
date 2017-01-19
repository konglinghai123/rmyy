package com.ewcms.empi.card.manage.entity;

public enum Sex {
	male("男"), female("女");

	private final String info;

	private Sex(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}