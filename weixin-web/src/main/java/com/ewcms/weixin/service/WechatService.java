package com.ewcms.weixin.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ewcms.weixin.util.MessageUtil;

/**
 *
 * @author wu_zhijun
 */
@Service
public class WechatService {
	
	private static final Logger log = LoggerFactory.getLogger(WechatService.class);

	public String weixinPost(HttpServletRequest request){
		String respMessage = null;
		
		try{
			//xml请求解析
			Map<String, String> requestMap = MessageUtil.xmlToMap(request);
			
			//发送方帐号(open_id)
			String fromUserName = requestMap.get("FromUserName");
			
			//公众帐号
			String toUserName = requestMap.get("ToUserName");
			
			//消息类型
			String msgType = requestMap.get("MsgType");
			
			//消息内容
			String content = requestMap.get("Content");
			
			log.info("FromUserName is : " + fromUserName + ", ToUserName is : " + toUserName + ", MsgType is : " + msgType);
			
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){//文本消息
				//这里根据关键字执行相应的逻辑
				if (content.equals("xxx")){
					
				}
				
				//自动回复
				TextMessage text = new TextMessage();
				text.setContent("the text is " + content);
				text.setToUserName(fromUserName);
				text.setFromUserName(toUserName);
				text.setCreateTime((new Date()).getTime());
				text.setMsgType(msgType);
				
				respMessage = MessageUtil.textMessageToXml(text);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){//事件推送
				//事件类型
				String eventType = requestMap.get("Event");
				
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){//订阅
					TextMessage text = new TextMessage();
					text.setContent("欢迎关注, XXX");
					text.setToUserName(fromUserName);
					text.setFromUserName(toUserName);
					text.setCreateTime((new Date()).getTime());
					text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					
					respMessage = MessageUtil.textMessageToXml(text);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){//取消订阅 
					//TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){//自定义菜单点击事件
					//事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					log.info("eventKey is : " + eventKey);
					if (eventKey.equals("customer_telephone")){
						TextMessage text = new TextMessage();
						text.setContent("0791-123456789");
						text.setToUserName(fromUserName);
						text.setFromUserName(toUserName);
						text.setCreateTime((new Date()).getTime());
						text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						
						respMessage = MessageUtil.textMessageToXml(text);
					}
				}
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){//声音
//				String recvMessage = requestMap.get("Recognition");
//				if (recvMessage != null){
//					respMessage = TulingApiProcess.getTulingResult(recvMessage);
//				} else {
//					respMessage = "您说的太模糊了，能不能重新说下呢?";
//				}
//				return MessageResponse.getTextMessage(fromUserName, toUserName, respMessage);
			} else if (msgType.equals("pic_sysphoto")){//拍照功能
			}
		} catch (Exception e){
			log.error("error " + e.toString());
		}
		
		return respMessage;
	}
}
