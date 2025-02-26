package com.ewcms.personal.message.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.entity.search.filter.SearchFilter;
import com.ewcms.common.entity.search.filter.SearchFilterHelper;
import com.ewcms.common.utils.LogUtils;
import com.ewcms.extra.push.PushApi;
import com.ewcms.personal.message.entity.Message;
import com.ewcms.personal.message.entity.MessageContent;
import com.ewcms.personal.message.entity.MessageState;
import com.ewcms.personal.message.entity.MessageType;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.UserService;
import com.google.common.collect.Lists;

/**
 *
 * @author wu_zhijun
 */
@Service
public class MessageApiImpl implements MessageApi{
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	@Autowired
	private PushApi pushApi;

	@Override
	public Page<Message> findUserMessage(Long userId, MessageState state, Pageable pageable) {
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(pageable);
		
		switch(state){
			//发送的信息
			case draft_box:
			case out_box:
				searchable.addSearchFilter("senderId", SearchOperator.EQ, userId);
				searchable.addSearchFilter("senderState", SearchOperator.EQ, state);
				break;
			//接收的信息
			case in_box:
				searchable.addSearchFilter("receiverId", SearchOperator.EQ, userId);
				searchable.addSearchFilter("receiverState", SearchOperator.EQ, state);
				break;
			//发送或接收的信息
			case store_box:
			case trash_box:
//				//发送
//				SearchFilter senderFilter = SearchFilterHelper.newCondition("senderId", SearchOperator.EQ, userId);
//				SearchFilter senderStateFilter = SearchFilterHelper.newCondition("senderState", SearchOperator.EQ, state);
//				SearchFilter andSender = SearchFilterHelper.and(senderFilter, senderStateFilter);
//				
//				//接收
//				SearchFilter receiverFilter = SearchFilterHelper.newCondition("receiverId", SearchOperator.EQ, userId);
//				SearchFilter receiverStateFilter = SearchFilterHelper.newCondition("receiverState", SearchOperator.EQ, state);
//				SearchFilter andReceiver = SearchFilterHelper.and(receiverFilter, receiverStateFilter);
//				
//				searchable.or(andSender, andReceiver);
				return messageService.findStoreOrTrash(userId, state, pageable);
			default:
				break;
		}
		return messageService.findAll(searchable);
	}

	@Override
	public List<Message> findAncestorsAndDescendants(Message message) {
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(Sort.Direction.ASC, "id");
		SearchFilter filter = null;
		if (!StringUtils.isEmpty(message.getParentIds())){
			String ancestorsId = message.getParentIds().split("/")[0];
			filter = SearchFilterHelper.or(SearchFilterHelper.newCondition("parentId", SearchOperator.PREFIXLIKE, ancestorsId + "/"), SearchFilterHelper.newCondition("id", SearchOperator.EQ, ancestorsId));
		} else {
			String descendantsParentIds = message.makeSelfAsParentIds();
			filter = SearchFilterHelper.newCondition("parentIds", SearchOperator.PREFIXLIKE, descendantsParentIds);
		}
		searchable.addSearchFilter(filter);
		List<Message> result = messageService.findAllWithSort(searchable);
		result.remove(message);
		
		for (int i = result.size() - 1; i >=0; i--){
			Message m = result.get(i);
			
			if (m.getSenderId() == message.getSenderId() && (m.getSenderState() == MessageState.trash_box || m.getSenderState() == MessageState.delete_box)){
				result.remove(i);
			}
			
			if (m.getReceiverId() == message.getSenderId() && (m.getSenderState() == MessageState.trash_box || m.getSenderState() == MessageState.delete_box)){
				result.remove(i);
			}
		}
		return result;
	}

	@Override
	public void saveDraft(Message message) {
		message.setSenderState(MessageState.draft_box);
		message.setReceiverState(null);
		if (message.getContent() != null){
			message.getContent().setMessage(message);
		}
		messageService.save(message);
	}
	
	@Override
	public void send(Message message) {
		Date now = new Date();
		message.setSendDate(now);
		message.setSenderState(MessageState.out_box);
		message.setSenderStateChangeDate(now);
		message.setReceiverState(MessageState.in_box);
		message.setReceiverStateChangeDate(now);
		
		message.getContent().setMessage(message);
		
		if (message.getParentId() != null){
			Message parent = messageService.findOne(message.getParentId());
			if (parent != null){
				message.setParentIds(parent.makeSelfAsParentIds());
			}
		}
		
		messageService.save(message);
		
		pushApi.pushUnreadMessage(message.getReceiverId(), countUnread(message.getReceiverId()));
	}

	@Override
	public void sendSystemMessage(Long[] receiverIds, Message message) {
		message.setType(MessageType.system_message);
		for (Long receiverId : receiverIds){
			if (receiverId == null) continue;
			Message copyMessage = new Message();
			MessageContent copyMessageContent = new MessageContent();
			copyMessageContent.setContent(message.getContent().getContent());
			
			BeanUtils.copyProperties(message, copyMessage);
			
			copyMessage.setContent(copyMessageContent);
			copyMessageContent.setMessage(copyMessage);
			
			copyMessage.setReceiverId(receiverId);
			
			send(copyMessage);
		}
	}

	@Async
	@Override
	public void sendSystemMessageToAllUser(Message message) {
		int pageRow = 0, pageSize = 100;
		
		Pageable pageable = null;
		Page<User> page = null;
		
		do {
			pageable = new PageRequest(pageRow++, pageSize);
			page = userService.findAll(pageable);
			
			try{
				((MessageApiImpl)AopContext.currentProxy()).doSendSystemMessage(page.getContent(), message);
			} catch (Exception e){
				LogUtils.logError("send system message to all user error", e);
			}
		} while (page.hasNext());
	}
	
	public void doSendSystemMessage(List<User> receivers, Message message){
		List<Long> receiverIds = Lists.newArrayList();
		
		for (User receiver : receivers){
			if (Boolean.TRUE.equals(receiver.getDeleted()) || receiver.getStatus() != UserStatus.normal){
				continue;
			}
			receiverIds.add(receiver.getId());
		}
		sendSystemMessage(receiverIds.toArray(new Long[0]), message);
	}

	@Override
	public void recycle(Long userId, Long messageId) {
		changeState(userId, messageId, MessageState.trash_box);
	}

	@Override
	public void recycle(Long userId, Long[] messageIds) {
		for (Long messageId : messageIds){
			recycle(userId, messageId);
		}
	}

	@Override
	public void store(Long userId, Long messageId) {
		changeState(userId, messageId, MessageState.store_box);
	}

	@Override
	public void store(Long userId, Long[] messageIds) {
		for (Long messageId : messageIds){
			store(userId, messageId);
		}
	}

	@Override
	public void delete(Long userId, Long messageId) {
		changeState(userId, messageId, MessageState.delete_box);
	}

	@Override
	public void delete(Long userId, Long[] messageIds) {
		for (Long messageId : messageIds){
			delete(userId, messageId);
		}
	}

	/**
	 * 变更状态
	 * 
	 * @param userId
	 * @param messageId
	 * @param state
	 */
	private void changeState(Long userId, Long messageId, MessageState state){
		Message message = messageService.findOne(messageId);
		if (message == null) return;
		
		if (userId.equals(message.getSenderId())){
			changeSenderState(message, state);
		} else {
			changeReceiverState(message, state);
		}
		messageService.update(message);
	}
	
	@Override
	public void clearBox(Long userId, MessageState state) {
		switch (state){
			case draft_box:
				clearBox(userId, MessageState.draft_box, MessageState.trash_box);
				break;
			case in_box:
				clearBox(userId, MessageState.in_box, MessageState.trash_box);
				break;
			case out_box:
				clearBox(userId, MessageState.out_box, MessageState.trash_box);
				break;
			case store_box:
				clearBox(userId, MessageState.store_box, MessageState.trash_box);
				break;
			case trash_box:
				clearBox(userId, MessageState.trash_box, MessageState.delete_box);
				break;
			default:
				break;
		}
	}

	@Override
	public void clearDraftBox(Long userId) {
		clearBox(userId, MessageState.draft_box);
	}

	@Override
	public void clearInBox(Long userId) {
		clearBox(userId, MessageState.in_box);
	}

	@Override
	public void clearOutBox(Long userId) {
		clearBox(userId, MessageState.out_box);
	}

	@Override
	public void clearStoreBox(Long userId) {
		clearBox(userId, MessageState.store_box);
	}

	@Override
	public void clearTrashBox(Long userId) {
		clearBox(userId, MessageState.trash_box);
	}
	
	private void clearBox(Long userId, MessageState oldState, MessageState newState){
		if (oldState == MessageState.draft_box || oldState == MessageState.out_box || oldState == MessageState.store_box || oldState == MessageState.trash_box){
			messageService.changeSenderState(userId, oldState, newState);
		}
		if (oldState == MessageState.in_box || oldState == MessageState.store_box || oldState == MessageState.trash_box){
			messageService.changeReceiverSate(userId, oldState, newState);
		}
	}

	@Override
	public Long countUnread(Long userId) {
		return messageService.countUnread(userId);
	}

	@Override
	public void markRead(Message message) {
		if (Boolean.TRUE.equals(message.getRead())) return;
		message.setRead(Boolean.TRUE);
		messageService.update(message);
	}

	@Override
	public void markReplied(Message message) {
		if (Boolean.TRUE.equals(message.getReplied())) return;
		message.setReplied(Boolean.TRUE);
		messageService.update(message);
	}

	@Override
	public void markRead(Long userId, Long[] ids) {
		messageService.markRead(userId, ids);
	}
	
	private void changeSenderState(Message message, MessageState state){
		message.setSenderState(state);
		message.setSenderStateChangeDate(new Date());
	}
	
	private void changeReceiverState(Message message, MessageState state){
		message.setReceiverState(state);
		message.setReceiverStateChangeDate(new Date());
	}

}
