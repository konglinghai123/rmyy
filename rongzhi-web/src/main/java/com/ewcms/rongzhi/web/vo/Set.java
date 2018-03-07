package com.ewcms.rongzhi.web.vo;

import java.io.Serializable;

public class Set implements Serializable {
	private static final long serialVersionUID = 1427303430848198215L;
	
	private String name;
	private String value;
	private String color;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	

}
