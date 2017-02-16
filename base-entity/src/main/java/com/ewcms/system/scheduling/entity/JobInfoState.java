package com.ewcms.system.scheduling.entity;

/**
 *
 * @author 吴智俊
 */
public enum JobInfoState {
	
    STATE_UNKNOWN("已执行完"),
    STATE_NORMAL("正常"),
    STATE_EXECUTING("正在执行"),
    STATE_PAUSED("暂停"),
    STATE_COMPLETE("完成"),
    STATE_ERROR("错误");

	
	private String info;

	private JobInfoState(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

}
