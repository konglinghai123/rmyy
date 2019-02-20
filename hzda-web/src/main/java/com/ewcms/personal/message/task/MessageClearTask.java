package com.ewcms.personal.message.task;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.personal.message.entity.MessageState;
import com.ewcms.personal.message.service.MessageService;
import com.google.common.collect.Lists;

/**
 * 清理过期的消息
 *
 * @author wu_zhijun
 */
@Service
public class MessageClearTask {
	public static final int EXPIRE_DAYS_OF_ONE_YEAR = 366;
	public static final int EXPIRE_DAYS_OF_ONE_MONTH = 31;
	
	@Autowired
	private MessageService messageService;
	
	public void autoClearExpiredOrDeletedMessage(){
		MessageClearTask messageClearTask = (MessageClearTask) AopContext.currentProxy();
		messageClearTask.doClearInOrOutBox();
		messageClearTask.doClearTrashBox();
		messageClearTask.doClearDeletedMessage();
	}
	
	public void doClearInOrOutBox(){
		messageService.changeState(Lists.newArrayList(MessageState.in_box, MessageState.out_box), MessageState.trash_box, EXPIRE_DAYS_OF_ONE_YEAR);
	}
	
	public void doClearTrashBox(){
		messageService.changeState(Lists.newArrayList(MessageState.trash_box), MessageState.delete_box, EXPIRE_DAYS_OF_ONE_MONTH);
	}
	
	public void doClearDeletedMessage(){
		messageService.clearDeletedMessage(MessageState.delete_box);
	}
}
