package com.ewcms.yjk.re.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/yjk/re/uservote")
@RequiresPermissions("yjk:uservote:*")
public class UserVoteController extends BaseController<User, Long>{

	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "index")
	public String index(Model model) {
		List<ReviewMain> reviewMainList = reviewMainService.findAll();
		model.addAttribute("reviewMainList", reviewMainList);
		
		model.addAttribute("istbEnable", reviewMainService.isOpenReview());
		
		return viewName("index");
	}
	
	@RequestMapping(value = "{userId}/{reviewMainId}/tabs")
	public String tabs(@PathVariable(value = "userId") Long userId, @PathVariable(value = "reviewMainId") Long reviewMainId, Model model) {
		ReviewMain reviewMain =  reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			reviewMain = reviewMainService.findOne(reviewMainId);
		}
		model.addAttribute("reviewProcessesList", reviewMain.getReviewProcesses());
		return viewName("tabs");
	}
	
	@RequestMapping(value = "{userId}/{reviewProcessId}/record")
	public String record(@PathVariable(value = "userId") User user, @PathVariable(value = "reviewProcessId") ReviewProcess reviewProcess, Model model) {
		model.addAttribute("reviewProcess", reviewProcess);
		model.addAttribute("user", user);
		return viewName("record");
	}
	
	@RequestMapping(value = "queryUser")
	@ResponseBody
	public Map<String, Object> queryUser(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		Map<String, Object> map = Maps.newHashMap();

		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			String eqReviewMainId = (String) searchParameter.getParameters().get("EQ_reviewMainId");
			if (EmptyUtil.isStringNotEmpty(eqReviewMainId)) {
				reviewMain = reviewMainService.findOne(Long.valueOf(eqReviewMainId));
			}
		}
		searchParameter.getParameters().remove("EQ_reviewMainId");
		
		List<Long> allUserIds = reviewMainService.findReviewUserIds(reviewMain);

		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, User.class);
		if (EmptyUtil.isCollectionNotEmpty(allUserIds)) {
			searchable.addSearchFilter("id", SearchOperator.IN, allUserIds);
		} else {
			searchable.addSearchFilter("id", SearchOperator.EQ, -1L);
		}
		
		searchable.addSort(Direction.ASC, "id");

		Page<User> users = userService.findAll(searchable);

		map.put("total", users.getTotalElements());
		map.put("rows", users.getContent());
		return map;
	}

}
