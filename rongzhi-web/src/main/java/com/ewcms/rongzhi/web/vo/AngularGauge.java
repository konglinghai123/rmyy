package com.ewcms.rongzhi.web.vo;

import java.io.Serializable;

public class AngularGauge implements Serializable {
	private static final long serialVersionUID = -3350059629846451979L;
	private Graph graph;
	private ColorRange colorRange;
	private Dials dials;
	public Graph getGraph() {
		return graph;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	public ColorRange getColorRange() {
		return colorRange;
	}
	public void setColorRange(ColorRange colorRange) {
		this.colorRange = colorRange;
	}
	public Dials getDials() {
		return dials;
	}
	public void setDials(Dials dials) {
		this.dials = dials;
	}
	
}
