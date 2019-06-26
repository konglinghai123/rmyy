package com.ewcms.yjk.re.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.ewcms.yjk.re.entity.ReviewExpert;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.ReviewProcessService;
import com.ewcms.yjk.re.service.VoteRecordService;
import com.ewcms.yjk.re.service.VoteResultService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/yjk/re/voteresult")
@RequiresPermissions("yjk:voteresult:*")
public class VoteResultController extends BaseController<VoteResult, Long> {

	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private ReviewProcessService reviewProcessService;
	@Autowired
	private VoteRecordService voteRecordService;
	@Autowired
	private UserService userService;
	@Autowired
	private VoteResultService voteResultService;

	@Override
	protected void setCommonData(Model model) {
		model.addAttribute("isOpenReview", reviewMainService.isOpenReview());
		Long reviewMainId = 0L;
		if (reviewMainService.isOpenReview()) {
			ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
			model.addAttribute("reviewProcessesList", reviewMain.getReviewProcesses());
			model.addAttribute("currentReviewProcess",
					reviewProcessService.findCurrentReviewProcess(reviewMain.getId()));
			reviewMainId = reviewMain.getId();
		}
		model.addAttribute("reviewMainId", reviewMainId);
		super.setCommonData(model);
	}

	@RequestMapping(value = "index")
	public String index(Model model) {
		setCommonData(model);
		return viewName("index");
	}

	@RequestMapping(value = "userSubmitted")
	public String userSubmitted(Model model) {
		setCommonData(model);
		return viewName("userSubmitted");
	}

	@RequestMapping(value = "{reviewProcessId}/queryUserSubmitted")
	@ResponseBody
	public Map<String, Object> queryUserSubmitted(@PathVariable("reviewProcessId") Long reviewProcessId,
			@ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null)
			return map;

		List<Long> submittedUserIds = voteRecordService.findUserSubmitted(reviewMain.getId(), reviewProcessId);
		List<VoteMonitor> users = voteResultService.findVoteMonitor(submittedUserIds);

		map.put("total", users.size());
		map.put("rows", users);
		return map;
	}

	@RequestMapping(value = "userNoSubmitted")
	public String userNoSubmitted(Model model) {
		setCommonData(model);
		return viewName("userNoSubmitted");
	}

	@RequestMapping(value = "{reviewProcessId}/queryUserNoSubmitted")
	@ResponseBody
	public Map<String, Object> queryUserNoSubmitted(@PathVariable("reviewProcessId") Long reviewProcessId,
			@ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null)
			return map;

		List<Long> submittedUserIds = voteRecordService.findUserSubmitted(reviewMain.getId(), reviewProcessId);
		List<Long> allUserIds = Lists.newArrayList();

		List<User> reviewMainUsers = reviewMain.getUsers();
		if (EmptyUtil.isCollectionNotEmpty(reviewMainUsers)) {
			allUserIds.addAll(Collections3.extractToList(reviewMainUsers, "id"));
		}

		List<ReviewExpert> reviewExperts = reviewMain.getReviewExperts();
		if (EmptyUtil.isCollectionNotEmpty(reviewExperts)) {
			for (ReviewExpert reviewExpert : reviewExperts) {
				allUserIds.addAll(Collections3.extractToList(reviewExpert.getUsers(), "id"));
			}
		}
		List<Long> notSubmittedUserIds = Collections3.subtract(allUserIds, submittedUserIds);

		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, User.class);
		if (EmptyUtil.isCollectionNotEmpty(notSubmittedUserIds)) {
			searchable.addSearchFilter("id", SearchOperator.IN, notSubmittedUserIds);
		} else {
			searchable.addSearchFilter("id", SearchOperator.EQ, -1L);
		}

		searchable.addSort(Direction.ASC, "id");

		List<User> users = userService.findAllWithSort(searchable);

		map.put("total", users.size());
		map.put("rows", users);
		return map;
	}
}
