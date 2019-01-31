package com.ewcms.extra.push;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

/**
 *
 * @author wu_zhijun
 */
@Service
public class PushApiImpl implements PushApi{

	@Autowired
	private PushService pushService;

	@Override
	public void pushUnreadMessage(Long userId, Long unreadMessageCount) {
		Map<String, Object> data = Maps.newHashMap();
		data.put("unreadMessageCount", unreadMessageCount);
		pushService.push(userId, data);
	}

	@Override
	public void pushNewNotification(Long userId, List<Map<String, Object>> notifiations) {
		Map<String, Object> data = Maps.newHashMap();
		data.put("notifications", notifiations);
		pushService.push(userId, data);
	}

	@Override
	public void offline(Long userId) {
		pushService.offline(userId);
	}
}
