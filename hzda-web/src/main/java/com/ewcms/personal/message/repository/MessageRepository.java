package com.ewcms.personal.message.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personal.message.entity.Message;
import com.ewcms.personal.message.entity.MessageState;

/**
 *
 * @author wu_zhijun
 */
public interface MessageRepository extends BaseRepository<Message, Long>{

	@Modifying
	@Query("update Message set senderState=?3, senderStateChangeDate=?4 where senderId=?1 and senderState=?2")
	int changeSenderState(Long senderId, MessageState oldState, MessageState newState, Date changeDate);
	
	@Modifying
	@Query("update Message set receiverState=?3, receiverStateChangeDate=?4 where receiverId=?1 and receiverState=?2")
	int changeReceiverState(Long receiverId, MessageState oldState, MessageState newState, Date changeDate);
	
	@Modifying
	@Query("update Message set senderState=?2, senderStateChangeDate=?3 where senderState in (?1) and senderStateChangeDate<?4")
	int changeSenderState(List<MessageState> oldStates, MessageState newState, Date changeDate, Date expirDate);
	
	@Modifying
	@Query("update Message set receiverState=?2, receiverStateChangeDate=?3 where receiverState in (?1) and receiverStateChangeDate<?4")
	int changeReceiverState(List<MessageState> oldStates, MessageState newState, Date changeDate, Date expireDate);
	
	@Modifying
	@Query("delete from Message where senderState=?1 and receiverState=?1")
	int clearDeletedMessage(MessageState deletedState);
	
	@Query("select count(o) from Message o where receiverId=?1 and receiverState=?2 and read=false")
	Long countUnread(Long userId, MessageState receiverSate);
	
	@Modifying
	@Query("update Message set read=true where receiverId=?1 and id in (?2)")
	void markRead(Long userId, List<Long> ids);
	
	@Query("from Message o where (o.senderId=?1 and o.senderState=?2) or (o.receiverId=?1 and o.receiverState=?2)")
	Page<Message> findStoreOrTrash(Long userId, MessageState state, Pageable pageable);
}
