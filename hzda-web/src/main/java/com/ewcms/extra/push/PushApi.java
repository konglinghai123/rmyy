package com.ewcms.extra.push;

import java.util.List;
import java.util.Map;

/**
 *
 * @author wu_zhijun
 */
public interface PushApi {
	
	/**
	 * 推送未读消息
	 * 
	 * @param userId
	 * @param unreadMessageCount
	 */
	public void pushUnreadMessage(final Long userId, Long unreadMessageCount);
	
	/**
	 * 推送未读通知
	 * 
	 * @param userId
	 * @param notifiations
	 */
	public void pushNewNotification(final Long userId, List<Map<String, Object>> notifiations);
	
	/**
	 * 离线用户，即清空用户的DeferredResult，这样就是新用户，可以即时得到通知
	 * 
	 * @param userId
	 */
	void offline(Long userId);
}
