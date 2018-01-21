package com.ewcms.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.weixin.service.WechatService;
import com.ewcms.weixin.util.SignUtil;

/**
 *
 * @author wu_zhijun
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

	private static final Logger log = LoggerFactory.getLogger(WechatController.class);
	
	private String DNBX_TOKEN = "rmyyweixin";
	
	@Autowired
	private WechatService wechatService;
	
	/**
	 * 微信接入
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/connect", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void connectWeixin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		/*
		 * 将请求、响应的编码均设置为UTF-8，防止中文乱码
		 */
		//微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码
		request.setCharacterEncoding("UTF-8");
		//在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上
		response.setCharacterEncoding("UTF-8");
		
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		
		PrintWriter out = response.getWriter();
		
		
		try{
			if (isGet){
				//微信加密签名
				String signature = request.getParameter("signature");
				//时间戳
				String timestamp = request.getParameter("timestamp");
				//随机数
				String nonce = request.getParameter("nonce");
				//随机字符串
				String echostr = request.getParameter("echostr");
				
				//退过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
				if (SignUtil.checkSignature(DNBX_TOKEN, signature, timestamp, nonce)){
					log.info("Connect the weixin server is successful.");
					response.getWriter().write(echostr);
				} else {
					log.info("Failed to verify the signature!");
				}
			} else {
				String respMessage = "异常消息!";
				
				try{
					respMessage = wechatService.weixinPost(request);
					out.write(respMessage);
					log.info("The request completed successfully to weixin service " + respMessage);
				} catch (Exception e){
					log.error("Failed to convert the message from weixin!");
				}
			}
		} catch (Exception e){
			log.error("Connect the weixin server is error.");
		} finally{
			IOUtils.closeQuietly(out);
		}
	}
}
