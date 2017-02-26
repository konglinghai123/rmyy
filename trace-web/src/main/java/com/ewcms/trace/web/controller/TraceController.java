package com.ewcms.trace.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.trace.entity.Trace;
import com.ewcms.trace.service.TraceService;

/**
 *
 * @author wu_zhijun
 */
@Controller
@RequestMapping(value = "/trace")
public class TraceController extends BaseCRUDController<Trace, String> {

	private TraceService getTraceService() {
		return (TraceService) baseService;
	}

	@RequestMapping("lookImage")
	public void lookImage(@RequestParam("cpid") String cpid,
			@RequestParam("field") String field, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		OutputStream outputStream = null;
		InputStream in = null;
		try {
			byte[] data = getTraceService().lookImage(cpid, field.replace("d", "b"));
	
			response.setContentType("image/jpeg;charset=utf-8");
			response.setCharacterEncoding("utf-8");
		
			outputStream = response.getOutputStream();
			in = new ByteArrayInputStream(data);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
		} catch (IOException e) {
		} catch (Exception e){
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(outputStream);
		}
	}
}
