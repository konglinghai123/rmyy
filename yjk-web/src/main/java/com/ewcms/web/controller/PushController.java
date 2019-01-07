package com.ewcms.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.extra.push.PushService;
import com.ewcms.personal.message.service.MessageApi;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.system.notice.entity.Notice;
import com.ewcms.system.notice.service.NoticeService;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.google.common.collect.Maps;

@Controller
public class PushController {
	
	@Autowired
	private MessageApi messageApi;
    @Autowired
    private PushService pushService;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private NoticeService noticeService;
    /**
     * 获取页面的提示信息
     * @return
     */
    @RequestMapping(value = "polling")
    @ResponseBody
    public Object polling(@CurrentUser User user, HttpServletResponse resp) {
        resp.setHeader("Connection", "Keep-Alive");
        resp.addHeader("Cache-Control", "private");
        resp.addHeader("Pragma", "no-cache");

        Long userId = user.getId();
        if(userId == null) {
            return null;
        }
        
        //如果用户第一次来 立即返回
        if(!pushService.isOnline(userId)) {
            Long unreadMessageCount = messageApi.countUnread(userId);
            
            SystemParameter systemParameter = systemParameterService.findByEnabledTrue();

//            String notice = (systemParameter == null) ? "申报新药还未开启，请等待!!!" : "请大家在 " + systemParameter.getApplyStartDate() + " 至 " + systemParameter.getApplyEndDate() + " 之间申报新药！！！";
            Map<String, Object> data = Maps.newHashMap();
            data.put("unreadMessageCount", unreadMessageCount);
//            data.put("drugForm_notice", notice);
            
            List<Notice> notices = noticeService.findByIndexShow();
            data.put("notices", notices);
            
            if (systemParameter != null) {
                data.put("drugForm_nodeclare", systemParameter.getNodeclareNumber());
                data.put("drugForm_init", systemParameter.getInitNumber());
                data.put("drugForm_passed", systemParameter.getPassedNumber());
                data.put("drugForm_unPassed", systemParameter.getUnPassedNumber());
            }
            
            
            
            pushService.online(userId);
            return data;
        } else {
            //长轮询
            return pushService.newDeferredResult(userId);
        }
    }
}
