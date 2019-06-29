package com.ewcms.yjk.re.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseController;
import com.ewcms.security.user.entity.User;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.service.ReviewMainService;

@Controller
@RequestMapping(value = "/yjk/re/uservote")
@RequiresPermissions("yjk:uservote:*")
public class UserVoteController extends BaseController<User, Long>{

	@Autowired
	private ReviewMainService reviewMainService;
	
	@RequestMapping(value = "index")
	public String index(Model model) {
		Long reviewMainId = null;
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			reviewMainId = reviewMain.getId();
		}
		model.addAttribute("reviewMainId", reviewMainId);
		
		return viewName("index");
	}
	
	@RequestMapping(value = "{userId}/tabs")
	public String tabs(@PathVariable(value = "userId") Long userId, Model model) {
		if(reviewMainService.isOpenReview()){
			ReviewMain reviewMain =  reviewMainService.findByEnabledTrue();
			model.addAttribute("reviewProcessesList", reviewMain.getReviewProcesses());
		}
		return viewName("tabs");
	}
	
	@RequestMapping(value = "{userId}/{reviewProcessId}/record")
	public String record(@PathVariable(value = "userId") User user, @PathVariable(value = "reviewProcessId") ReviewProcess reviewProcess, Model model) {
		model.addAttribute("reviewProcess", reviewProcess);
		model.addAttribute("user", user);
		return viewName("record");
	}
}
