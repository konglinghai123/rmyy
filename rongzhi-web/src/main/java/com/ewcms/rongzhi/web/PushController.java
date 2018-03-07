package com.ewcms.rongzhi.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Maps;

@Controller
public class PushController {
	/**
	 * 获取页面的提示信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "polling")
	@ResponseBody
	public Object polling(HttpServletResponse resp) {
		resp.setHeader("Connection", "Keep-Alive");
		resp.addHeader("Cache-Control", "private");
		resp.addHeader("Pragma", "no-cache");
		Map<String, Object> data = Maps.newHashMap();
		data.put("isFlush", true);
		return data;
	}
}
