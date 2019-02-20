package com.ewcms.personal.calendar.web.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.personal.calendar.entity.Calendar;
import com.ewcms.personal.calendar.service.CalendarService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 * @author wu_zhijun
 */
@Controller
@RequestMapping(value = "/personal/calendar")
public class CalendarController extends BaseController<Calendar, Long> {

	private static final long oneDayMillis = 24L * 60 * 60 * 1000;
	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private CalendarService calendarService;
	
	@RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
	public String index() throws IOException{
		return viewName("index");
	}

	@RequestMapping(value = "load")
	public @ResponseBody Collection<Map<String, Object>> ajaxLoad(@CurrentUser User loginUser){
		Searchable searchable = Searchable.newSearchable();
		
		searchable.addSearchParam("EQ_userId", loginUser.getId());
		List<Calendar> calendars = calendarService.findAllWithNoPageNoSort(searchable);
		
		return Lists.<Calendar, Map<String, Object>>transform(calendars, new Function<Calendar, Map<String, Object>>(){

			@SuppressWarnings("deprecation")
			@Override
			public Map<String, Object> apply(Calendar c) {
				Map<String, Object> m = Maps.newHashMap();
				
				Date startDate = new Date(c.getStartDate().getTime());
				Date endDate = DateUtils.addDays(startDate, c.getLength() - 1);
				boolean allDays = c.getStartTime() == null && c.getEndTime() == null;
				
				if (!allDays){
					startDate.setHours(c.getStartTime().getHours());
					startDate.setMinutes(c.getStartTime().getMinutes());
					startDate.setSeconds(c.getStartTime().getSeconds());
					endDate.setHours(c.getEndTime().getHours());
					endDate.setMinutes(c.getEndTime().getMinutes());
					endDate.setSeconds(c.getEndTime().getSeconds());
				}
				
				m.put("id", c.getId());
				m.put("start", DateFormatUtils.format(startDate, dateFormat));
				m.put("end", DateFormatUtils.format(endDate, dateFormat));
				m.put("allDay", allDays);
				m.put("title", c.getTitle());
				m.put("details", c.getDetails());
				
				if (StringUtils.isNotEmpty(c.getBackgroundColor())){
					m.put("backgroundColor", c.getBackgroundColor());
					m.put("borderColor", c.getBackgroundColor());
				}
				
				if (StringUtils.isNotEmpty(c.getTextColor())){
					m.put("textColor", c.getTextColor());
				}
				
				return m;
			}
			
		});
	}
	
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String viewCalendar(@PathVariable(value = "id") Calendar calendar, Model model) {
		model.addAttribute("calendar", calendar);
		return viewName("view");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.GET)
	public String showForm(@RequestParam(value = "start", required = false) @DateTimeFormat(pattern = dateFormat) Date start, @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = dateFormat) Date end, Model model) {
		setColorList(model);
		
		Calendar calendar = new Calendar();
		calendar.setLength(1);
		if (start != null){
			calendar.setStartDate(start);
			calendar.setLength((int)Math.ceil(1.0 * (end.getTime() - start.getTime()) / oneDayMillis));
			if (DateUtils.isSameDay(start, end)){
				calendar.setLength(1);
			}
			if (!"00:00:00".equals(DateFormatUtils.format(start, "HH:mm:ss"))){
				calendar.setStartTime(start);
			}
			if (!"00:00:00".equals(DateFormatUtils.format(end, "HH:mm:ss"))){
				calendar.setEndTime(end);
			}
		}
		model.addAttribute("m", calendar);
		return viewName("edit");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse newCalendar(@ModelAttribute("m") Calendar calendar, @CurrentUser User loginUser){
		AjaxResponse ajaxResponse = new AjaxResponse();
		
		try{
			calendar.setUserId(loginUser.getId());
			calendarService.save(calendar);
		} catch (Exception e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}

		return ajaxResponse;
	}
	
	@RequestMapping(value = "move", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse moveCalendar(@RequestParam(value = "id") Long id, @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = dateFormat) Date start, @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = dateFormat) Date end) {
		AjaxResponse ajaxResponse = new AjaxResponse();

		Calendar calendar = calendarService.findOne(id);
		
		if (end == null) end = start;
		
		calendar.setStartDate(start);
		calendar.setLength((int)Math.ceil(1.0 * (end.getTime() - start.getTime()) / oneDayMillis));
		if (DateUtils.isSameDay(start, end)){
			calendar.setLength(1);
		}
		if (!"00:00:00".equals(DateFormatUtils.format(start, "HH:mm:ss"))){
			calendar.setStartTime(start);
		}
		if (!"00:00:00".equals(DateFormatUtils.format(end, "HH:mm:ss"))){
			calendar.setEndTime(end);
		}
		calendarService.copyAndRemove(calendar);
		
		return ajaxResponse;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse deleteCalendar(@RequestParam("id") Long id){
		AjaxResponse ajaxResponse = new AjaxResponse();
		
		try{
			calendarService.delete(id);
		}catch(Exception e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}
	
	private void setColorList(Model model){
		List<String> backgroundColors = Lists.newArrayList();
		backgroundColors.add("#3a87ad");
		backgroundColors.add("#0d7813");
		backgroundColors.add("#f2a640");
		backgroundColors.add("#b373b3");
		backgroundColors.add("#f2a640");
		backgroundColors.add("#668cb3");
		backgroundColors.add("#28754e");
		backgroundColors.add("#8c66d9");
		
		model.addAttribute("backgroundColorList", backgroundColors);
	}
}
