package com.ewcms.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.XMLUtil;
import com.ewcms.extra.push.PushService;
import com.ewcms.personal.calendar.service.CalendarService;
import com.ewcms.security.resource.entity.Menu;
import com.ewcms.security.resource.service.ResourceService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.yjk.sb.service.DrugFormService;

@Controller
public class HomeController {
	
	@Autowired
	private ResourceService resourceService;
    @Autowired
    private PushService pushApiService;
    @Autowired
    private CalendarService calendarService;
    @Autowired
    private DrugFormService drugFormService;

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
	
	@RequestMapping(value = "drugFormCountReport")
	@ResponseBody
	public Map<String, Object> findDrugFormCountReport(@CurrentUser User user, @ModelAttribute SearchParameter<Long> searchParameter){
		return drugFormService.findDrugFormCount(user, searchParameter);
	}
	
	@RequestMapping(value = "drugFormCountChart")
	@ResponseBody
	public String drugFormCountChart(){
		XMLUtil xml = new XMLUtil();
		Element graph = xml.addRoot("graph");
		xml.addAttribute(graph, "basefontsize", "12");
		xml.addAttribute(graph, "showNames", "1");
		xml.addAttribute(graph, "decimalPrecision", "0");
		xml.addAttribute(graph, "formatNumberScale", "0");
		Map<String, Long> map = drugFormService.drupFromCountChart();
		Iterator<Entry<String, Long>> it = map.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, Long> m = (Map.Entry<String, Long>)it.next();
			String key = m.getKey();
			Long total = (Long)map.get(key);
			Element set = xml.addNode(graph, "set");
			set.addAttribute("name", key);
			set.addAttribute("value", total.toString());
		}
		return xml.getXML();
	}
}
