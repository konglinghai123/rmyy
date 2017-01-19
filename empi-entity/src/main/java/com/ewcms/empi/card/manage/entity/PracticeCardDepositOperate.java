package com.ewcms.empi.card.manage.entity;

/**
 *@author zhoudongchu
 */
public enum PracticeCardDepositOperate {
	paydeposit("收取押金"),nodeposit("不收押金"), redeposit("已退押金");

	private final String info;

	private PracticeCardDepositOperate(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
