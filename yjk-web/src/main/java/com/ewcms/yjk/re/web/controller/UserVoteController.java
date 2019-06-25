package com.ewcms.yjk.re.web.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseController;
import com.ewcms.security.user.entity.User;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.VoteRecordService;
import com.ewcms.yjk.sb.entity.DrugForm;

@Controller
@RequestMapping(value = "/yjk/re/uservote")
@RequiresPermissions("re:uservote:*")
public class UserVoteController extends BaseController<User, Long>{

	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private VoteRecordService voteRecordService;
	
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
	
	@RequestMapping(value = "{userId}/query")
	@ResponseBody
	public Map<DrugForm, Map<String, String>> queryUserVote(@PathVariable(value = "userId") Long userId){
		return voteRecordService.findUserVote(userId);
	}
}
