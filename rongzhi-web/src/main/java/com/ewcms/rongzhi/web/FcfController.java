package com.ewcms.rongzhi.web;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.rongzhi.web.vo.Chart;
import com.ewcms.rongzhi.web.vo.Graph;
import com.ewcms.rongzhi.web.vo.Set;

@Controller
@ResponseBody
@RequestMapping(value = "/fcf")
public class FcfController {

	/**
	 * @return
	 */
	@RequestMapping(value = "/test")
	public Object getTestJsonData(){
		Random r = new Random();
		Chart testChart = new Chart();
		Graph graphVo = new Graph();
		graphVo.setCaption("每月销售额柱形图");
		graphVo.setxAxisName("月份");
		graphVo.setyAxisName("Units");
		testChart.setGraph(graphVo);
		Set setVo1 = new Set();
		setVo1.setName("一月");
		setVo1.setValue(String.valueOf(r.nextInt(999)));
		setVo1.setColor("AFD8F8");
		
		Set setVo2 = new Set();
		setVo2.setName("二月");
		setVo2.setValue(String.valueOf(r.nextInt(999)));
		setVo2.setColor("F6BD0F");
		
		Set setVo3 = new Set();
		setVo3.setName("三月");
		setVo3.setValue(String.valueOf(r.nextInt(999)));
		setVo3.setColor("8BBA00");
		testChart.getSet().add(setVo1);
		testChart.getSet().add(setVo2);
		testChart.getSet().add(setVo3);
		return testChart;
	}
}
