package com.ewcms.yjk.re.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.yjk.re.entity.ReviewProcessRecord;
import com.ewcms.yjk.re.service.ReviewProcessRecordService;

@Controller
@RequestMapping(value = "/yjk/re/reviewprocessrecord")
public class ReviewProcessRecordController extends BaseController<ReviewProcessRecord, Long>{

	@Autowired
	private ReviewProcessRecordService reviewProcessRecordService;
	
	@RequestMapping(value = "{reviewProcessId}/index")
	public String index(@PathVariable(value = "reviewProcessId") Long reviewProcessId, Model model) {
		model.addAttribute("reviewProcessId", reviewProcessId);
		return viewName("index");
	}
	
	@RequestMapping(value = "{reviewProcessId}/query")
	@ResponseBody
	public Map<String, Object> query(@PathVariable(value = "reviewProcessId") Long reviewProcessId, @ModelAttribute SearchParameter<Long> searchParameter){
		searchParameter.getParameters().put("EQ_reviewProcessId", reviewProcessId);
		
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, ReviewProcessRecord.class);
		
		searchable.addSort(Direction.DESC, "operateDate");
		
		Page<ReviewProcessRecord> pages = reviewProcessRecordService.findAll(searchable);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", pages.getTotalElements());
		resultMap.put("rows", pages.getContent());
		return resultMap;
	}
}
