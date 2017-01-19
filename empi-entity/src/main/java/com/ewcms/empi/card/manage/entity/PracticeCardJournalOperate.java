package com.ewcms.empi.card.manage.entity;

/**
 * 流水操作状态
 *@author zhoudongchu
 */
public enum PracticeCardJournalOperate {
	pay("缴费"),refund("退费"), recharge("充值"),hedging("对冲");

	private final String info;

	private PracticeCardJournalOperate(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
