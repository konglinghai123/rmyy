package com.ewcms.card.manage.web.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.card.manage.entity.PracticeCardHistory;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/card/manage/practicecardhistory")
public class PracticeCardHistoryController extends BaseCRUDController<PracticeCardHistory, Long> {
	@RequestMapping(value = "{practiceCardId}/detail")
	public String index(@PathVariable(value = "practiceCardId")Long practiceCardId, Model model){
		return this.viewName("detail");
	}
	
	@RequestMapping(value = "{practiceCardId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "practiceCardId")Long practiceCardId, Model model){
		searchParameter.getParameters().put("EQ_practiceCard.id", practiceCardId);
		
		return super.query(searchParameter, model);
	}
}
