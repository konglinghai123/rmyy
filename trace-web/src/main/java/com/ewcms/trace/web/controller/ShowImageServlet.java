package com.ewcms.trace.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.ewcms.common.utils.SpringUtils;
import com.ewcms.trace.service.TraceService;

/**
 *
 * @author wu_zhijun
 */
@WebServlet(urlPatterns = "/showImage/*", asyncSupported = true)
public class ShowImageServlet extends HttpServlet{
	
	private static final long serialVersionUID = 5476359894108203781L;
	
	private TraceService traceService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		traceService = SpringUtils.getBean("traceService");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.reset();
		response.setHeader("Content-Language", "zh-CN");
		response.setHeader("Cache-Control", "no-store");
		
		// set on cache
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/x-msdownload");
		
		OutputStream outputStream = null;
		InputStream in = null;
		try {
			byte[] data = traceService.lookImage(request.getParameter("cpid"), request.getParameter("field").replace("d", "b"));
	
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
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
