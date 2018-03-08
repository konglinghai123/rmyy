package com.ewcms.rongzhi.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dials implements Serializable {
	private static final long serialVersionUID = -8051077800357900504L;

	private List<Dial> dial = new ArrayList<Dial>();

	public List<Dial> getDial() {
		return dial;
	}

	public void setDial(List<Dial> dial) {
		this.dial = dial;
	}
	
	
}
