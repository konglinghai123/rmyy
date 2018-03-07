package com.ewcms.rongzhi.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chart implements Serializable {
	private static final long serialVersionUID = -3644443964553560992L;
	
	private Graph graph;
	
	private List<Set> set = new ArrayList<Set>();

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public List<Set> getSet() {
		return set;
	}

	public void setSet(List<Set> set) {
		this.set = set;
	}
}
