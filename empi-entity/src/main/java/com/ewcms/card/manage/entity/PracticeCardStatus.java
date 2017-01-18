package com.ewcms.card.manage.entity;

/**
 * 诊疗卡状态
 *@author zhoudongchu
 */
public enum PracticeCardStatus {
	normal("正常"),loss("挂失"), close("销户"),invalid("作废");

	private final String info;

	private PracticeCardStatus(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
