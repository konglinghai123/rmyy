package com.ewcms.rongzhi.web;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.rongzhi.web.vo.AngularGauge;
import com.ewcms.rongzhi.web.vo.Color;
import com.ewcms.rongzhi.web.vo.ColorRange;
import com.ewcms.rongzhi.web.vo.Column3D;
import com.ewcms.rongzhi.web.vo.Dial;
import com.ewcms.rongzhi.web.vo.Dials;
import com.ewcms.rongzhi.web.vo.Graph;
import com.ewcms.rongzhi.web.vo.Set;

@Controller
@ResponseBody
@RequestMapping(value = "/fcf")
public class FcfController {

	/**
	 * @return
	 */
	@RequestMapping(value = "/column3d")
	public Object getColumn3dJsonData(){
		Random r = new Random();
		Column3D testChart = new Column3D();
		Graph graphVo = new Graph();
		graphVo.setxAxisName("季度");
		graphVo.setyAxisName("Units");
		testChart.setGraph(graphVo);
		Set setVo1 = new Set();
		setVo1.setName("一季度");
		setVo1.setValue(String.valueOf(r.nextInt(999)));
		setVo1.setColor("AFD8F8");
		
		Set setVo2 = new Set();
		setVo2.setName("二季度");
		setVo2.setValue(String.valueOf(r.nextInt(999)));
		setVo2.setColor("F6BD0F");
		
		Set setVo3 = new Set();
		setVo3.setName("三季度");
		setVo3.setValue(String.valueOf(r.nextInt(999)));
		setVo3.setColor("8BBA00");
		
		Set setVo4 = new Set();
		setVo4.setName("四季度");
		setVo4.setValue(String.valueOf(r.nextInt(999)));
		setVo4.setColor("EEBBA00");
		
		testChart.getSet().add(setVo1);
		testChart.getSet().add(setVo2);
		testChart.getSet().add(setVo3);
		testChart.getSet().add(setVo4);
		return testChart;
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/angulargauge")
	public Object getAngularGaugeJsonData(){
		Random r = new Random();
		AngularGauge testChart = new AngularGauge();
		
		Graph graphVo = new Graph();
		graphVo.setLowerLimit("0");
		graphVo.setUpperLimit("100");
		graphVo.setShowValue("1");
		testChart.setGraph(graphVo);
		
		ColorRange colorRangeVo = new ColorRange();
		Color colorVo1 = new Color();
		colorVo1.setMinValue("0");
		colorVo1.setMaxValue("50");
		colorVo1.setCode("#e44a00");
		
		Color colorVo2 = new Color();
		colorVo2.setMinValue("50");
		colorVo2.setMaxValue("75");
		colorVo2.setCode("#f8bd19");
		
		Color colorVo3 = new Color();
		colorVo3.setMinValue("75");
		colorVo3.setMaxValue("100");
		colorVo3.setCode("#6baa01");
		
		colorRangeVo.getColor().add(colorVo1);
		colorRangeVo.getColor().add(colorVo2);
		colorRangeVo.getColor().add(colorVo3);
		testChart.setColorRange(colorRangeVo);
		
		Dials dialsVo = new Dials();
		Dial dialVo = new Dial();
		dialVo.setValue(String.valueOf(r.nextInt(99)));
		dialsVo.getDial().add(dialVo);
		testChart.setDials(dialsVo);
		
		return testChart;
	}	
}
