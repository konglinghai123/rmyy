package com.ewcms.rongzhi.web.vo;

import java.io.Serializable;

public class Color implements Serializable {
	private static final long serialVersionUID = 5789829379645690798L;

	private String minValue;
	private String maxValue;
	private String code;
	
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
