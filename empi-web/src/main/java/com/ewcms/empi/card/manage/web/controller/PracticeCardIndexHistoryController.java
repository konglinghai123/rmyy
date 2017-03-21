package com.ewcms.empi.card.manage.web.controller;

import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.empi.card.manage.entity.PracticeCardIndexHistory;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/practicecardindexhistory")
public class PracticeCardIndexHistoryController extends	BaseCRUDController<PracticeCardIndexHistory, Long> {
	@RequestMapping(value = "{practiceNo}/detail")
	public String index(@PathVariable(value = "practiceNo")String practiceNo, Model model){
		return viewName("detail");
	}
	
	@RequestMapping(value = "{practiceNo}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "practiceNo")String practiceNo, Model model){
		searchParameter.getParameters().put("EQ_practiceNo", practiceNo);
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}
}
