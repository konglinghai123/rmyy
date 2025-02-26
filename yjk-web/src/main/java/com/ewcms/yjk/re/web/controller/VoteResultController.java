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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.yjk.re.entity.ReviewExpert;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
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
		Boolean isOpenReview = false;
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			isOpenReview = true;
			model.addAttribute("reviewProcessesList", reviewMain.getReviewProcesses());
			model.addAttribute("currentReviewProcess", reviewProcessService.findCurrentReviewProcess(reviewMain.getId()));
		}
		
		model.addAttribute("isOpenReview", isOpenReview);
		
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

	@RequestMapping(value = "queryUserSubmitted")
	@ResponseBody
	public Map<String, Object> queryUserSubmitted(@ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return map;
		Long reviewMainId = reviewMain.getId();
		ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
		if (reviewProcess == null) return map;
		
		List<Long> submittedUserIds = voteRecordService.findSubmittedUserIds(reviewMainId, reviewProcess.getId());
		if (EmptyUtil.isCollectionEmpty(submittedUserIds)) submittedUserIds.add(-1L);
		
		List<VoteMonitor> users = voteResultService.findVoteResultMonitor(submittedUserIds, reviewMainId, reviewProcess.getId());

		map.put("total", users.size());
		map.put("rows", users);
		return map;
	}
	
	@RequestMapping(value = "userNoSubmitted")
	public String userNoSubmitted() {
		return viewName("userNoSubmitted");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "queryUserNoSubmitted")
	@ResponseBody
	public Map<String, Object> queryUserNoSubmitted(@ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return map;
		Long reviewMainId = reviewMain.getId();
		ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
		if (reviewProcess == null) return map;

		List<Long> submittedUserIds = voteRecordService.findSubmittedUserIds(reviewMainId, reviewProcess.getId());
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
	
	@RequestMapping(value = "{userId}/giveUp", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse giveUp(@PathVariable(value = "userId")Long userId) {
		return voteRecordService.giveUp(userId);
	}
 
	@RequestMapping(value = "{userId}/sign", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse sign(@PathVariable(value = "userId")Long userId) {
		return voteRecordService.sign(userId);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "voteResult")
	public String voteResult(Model model) {
		setCommonData(model);
		
		Boolean isNextEnable = true;
		Boolean isResult = false;
		Boolean isClose = false;
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess != null) {
				List<ReviewProcess> reviewProcesses = reviewProcessService.findByReviewMainIdAndFinishedFalseOrderByWeightDesc(reviewMainId);
				if (EmptyUtil.isCollectionNotEmpty(reviewProcesses) && reviewProcesses.get(0).equals(reviewProcess)) {
					isNextEnable = false;
				}

				Long countSinged = voteRecordService.findSubmittedAndSinged(reviewMainId, reviewProcess.getId());
				
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
				
				if (EmptyUtil.isCollectionNotEmpty(allUserIds) && allUserIds.size() == countSinged.intValue()){
					voteResultService.generateCurrentVoteResults(reviewMainId, reviewProcess.getId());
					isResult = true;
				}
				model.addAttribute("statisticalNotes", voteResultService.statisticalNotes(reviewProcess.getId()));
			} else {
				List<ReviewProcess> reviewProcesses = reviewMain.getReviewProcesses();
				if (EmptyUtil.isCollectionNotEmpty(reviewProcesses)) {
					model.addAttribute("currentReviewProcess", reviewProcesses.get(reviewProcesses.size() - 1));
					isResult = true;
					isClose = true;
				}
			}
			
		}
		model.addAttribute("isResult", isResult);
		model.addAttribute("isNextEnable", isNextEnable);
		model.addAttribute("isClose", isClose);
		
		return viewName("voteResult");
	}

	
	@RequestMapping(value = "queryVoteResult")
	@ResponseBody
	public Map<String, Object> queryVoteResult(@ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return map;
		Long reviewMainId = reviewMain.getId();
		
		ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
		List<ReviewProcess> reviewProcesses = reviewMain.getReviewProcesses();
		if (reviewProcess == null && EmptyUtil.isCollectionNotEmpty(reviewProcesses)) {
			String customShow = (String) searchParameter.getParameters().get("CUSTOM_show");
			List<VoteResult> voteResults = Lists.newArrayList();
			if (EmptyUtil.isStringEmpty(customShow) || customShow.equals("all")) {
				voteResults = voteResultService.findAllVoteResultLast(reviewMainId);
			} else {
				voteResults = voteResultService.findChosnResult(reviewMainId);
			}
			
			map.put("total", voteResults.size());
			map.put("rows", voteResults);
		}else{
			List<VoteResult> voteResults = voteResultService.findCurrentReviewProcessVoteResults(reviewMainId, reviewProcess.getId());
			map.put("total", voteResults.size());
			map.put("rows", voteResults);
		}
		return map;
	}
	
	@RequestMapping(value = "transferIn", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse transferIn(@RequestParam(required = false) List<Long> selections) {
		return voteResultService.transferIn(selections);
	}

	@RequestMapping(value = "callOut", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse callOut(@RequestParam(required = false) List<Long> selections) {
		return voteResultService.callOut(selections);
	}
	
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse cancel(@RequestParam(value = "voteResultId") Long voteResultId) {
		return voteResultService.cancel(voteResultId);
	}
	

	@RequestMapping(value = "affirm", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse affirm(@RequestParam(required = false) List<Long> selections) {
		return voteResultService.affirm(selections);
	}
	
	@RequestMapping(value = "next", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse next(@CurrentUser User user, @RequestParam(value = "reason") String reason, Model model) {
		return voteResultService.next(user.getId(), reason);
	}
	
	@RequestMapping(value = "process")
	public String process(Model model) {
		setCommonData(model);
		
		Boolean isClose = false;
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null && EmptyUtil.isCollectionNotEmpty(reviewMain.getReviewProcesses())) {
				isClose = true;
			}
		}
		model.addAttribute("isClose", isClose);
		
		return viewName("process");
	}
	
}
