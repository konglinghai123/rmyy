package com.ewcms.rongzhi.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ColorRange implements Serializable {
	private static final long serialVersionUID = 1447383605878828092L;

	private List<Color> color = new ArrayList<Color>();

	public List<Color> getColor() {
		return color;
	}

	public void setColor(List<Color> color) {
		this.color = color;
	}
}
