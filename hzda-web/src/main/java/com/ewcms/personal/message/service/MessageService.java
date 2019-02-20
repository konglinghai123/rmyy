package com.ewcms.personal.message.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.personal.message.entity.Message;
import com.ewcms.personal.message.entity.MessageState;
import com.ewcms.personal.message.repository.MessageRepository;

/**
 *
 * @author wu_zhijun
 */
@Service
public class MessageService extends BaseService<Message, Long>{

	private MessageRepository getMessageRepository(){
		return (MessageRepository) baseRepository;
	}
	
	/**
	 * 改变发件人，消息的原状态为目标状态
	 * 
	 * @param senderId
	 * @param oldState
	 * @param newState
	 * @return
	 */
	public Integer changeSenderState(Long senderId, MessageState oldState, MessageState newState){
		Date changeDate = new Date();
		return getMessageRepository().changeSenderState(senderId, oldState, newState, changeDate);
	}
	
	/**
	 * 改变收件人，消息的原状态为目标状态
	 * 
	 * @param receiverId
	 * @param oldState
	 * @param newState
	 * @return
	 */
	public Integer changeReceiverSate(Long receiverId, MessageState oldState, MessageState newState){
		Date changeDate = new Date();
		return getMessageRepository().changeReceiverState(receiverId, oldState, newState, changeDate);
	}
	
	/**
	 * 物理删除已做为删除标记的（即收件人和发件人同时都标记为删除的）
	 * 
	 * @param deletedState
	 * @return
	 */
	public Integer clearDeletedMessage(MessageState deletedState){
		return getMessageRepository().clearDeletedMessage(deletedState);
	}
	
	/**
	 * 更改状态
	 * 
	 * @param oldStates
	 * @param newState
	 * @param expireDays
	 * @return
	 */
	public Integer changeState(List<MessageState> oldStates, MessageState newState, int expireDays){
		Date changeDate = new Date();
		Integer count = getMessageRepository().changeSenderState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
		count += getMessageRepository().changeReceiverState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
		return count;
	}
	
	public Long countUnread(Long userId){
		return getMessageRepository().countUnread(userId, MessageState.in_box);
	}
	
	public void markRead(final Long userId, final Long[] ids){
		if (ArrayUtils.isEmpty(ids)) return;
		getMessageRepository().markRead(userId, Arrays.asList(ids));
	}
	
	public Page<Message> findStoreOrTrash(Long userId, MessageState state, Pageable pageable){
		return getMessageRepository().findStoreOrTrash(userId, state, pageable);
	}
}
