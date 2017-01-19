package com.ewcms.empi.card.manage.entity;

/**
 * 诊疗卡的操作
 *@author zhoudongchu
 */
public enum PracticeCardOperate {
	distribute("发诊疗卡"),loss("挂失"), cancelloss("取消挂失"),close("销户"), combine("合并"),invalid("作废"),init("初始化");

	private final String info;

	private PracticeCardOperate(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
