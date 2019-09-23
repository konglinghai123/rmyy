package com.ewcms.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.extra.push.PushService;
import com.ewcms.hzda.entity.FollowupTime;
import com.ewcms.hzda.service.FollowupTimeService;
import com.ewcms.personal.calendar.service.CalendarService;
import com.ewcms.security.resource.entity.Menu;
import com.ewcms.security.resource.service.ResourceService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
public class HomeController {
	
	@Autowired
	private ResourceService resourceService;
    @Autowired
    private PushService pushApiService;
    @Autowired
    private CalendarService calendarService;
    @Autowired
    private FollowupTimeService followupTimeService;

	@RequestMapping(value = "home")
	public String home(@CurrentUser User user, Model model){
        List<Menu> menus = resourceService.findMenus(user);
        model.addAttribute("menus", menus);
        
        pushApiService.offline(user.getId());
        
		Subject subject = SecurityUtils.getSubject();
		model.addAttribute("isRunas", subject.isRunAs());
		if (subject.isRunAs()) {
			String previousUsername = (String) subject.getPreviousPrincipals().getPrimaryPrincipal();
			model.addAttribute("previousUsername", previousUsername);
		}

        //未读消息
        //Long messageUnreadCount = messageService.countUnread(loginUser.getId());
        //model.addAttribute("messageUnreadCount", messageUnreadCount);
		
        //最近3天的日历
        model.addAttribute("calendarCount", calendarService.countRecentlyCalendar(user.getId(), 2));

		return "home";
	}
	
	@RequestMapping(value = "/followupTime/query")
	@ResponseBody
	public Map<String, Object> followupTimeQuery(@CurrentUser User user, @ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		Long userId = null;
		if (!user.getAdmin()) {
			userId = user.getId();
		}
		
		Page<FollowupTime> pages = followupTimeService.findFollowupTime(userId, searchParameter);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		
		resultMap.put("total", pages.getTotalElements());
		resultMap.put("rows", pages.getContent());
		
		return resultMap;
	}

	@RequestMapping(value = "/followupTime/close")
	@ResponseBody
	public AjaxResponse closeFollowup(@RequestParam(value = "followupTimeId")Long followupTimeId){
        AjaxResponse ajaxResponse = new AjaxResponse("关闭提醒成功！");
        followupTimeService.close(followupTimeId);
		return ajaxResponse;
	}
}
