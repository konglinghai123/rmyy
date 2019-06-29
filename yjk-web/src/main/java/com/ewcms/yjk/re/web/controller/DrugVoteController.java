package com.ewcms.yjk.re.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.VoteRecordService;
import com.ewcms.yjk.re.service.VoteResultService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/yjk/re/drugvote")
@RequiresPermissions("yjk:drugvote:*")
public class DrugVoteController extends BaseController<VoteRecord, Long>{
	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private VoteRecordService voteRecordService;
	@Autowired
	private VoteResultService voteResultService;
	
	@RequestMapping(value = "index")
	public String tabs(Model model) {
		if(reviewMainService.isOpenReview()){
			ReviewMain reviewMain =  reviewMainService.findByEnabledTrue();
			model.addAttribute("reviewProcessesList", reviewMain.getReviewProcesses());
		}
		return viewName("index");
	}
	
	@RequestMapping(value = "{reviewProcessId}/record")
	public String record(@PathVariable(value = "reviewProcessId") ReviewProcess reviewProcess, Model model) {
		model.addAttribute("reviewProcess", reviewProcess);
		return viewName("record");
	}

	@RequestMapping(value = "{reviewProcessId}/queryVoteResult")
	@ResponseBody
	public Map<String, Object> queryVoteResult(@PathVariable(value = "reviewProcessId") Long reviewProcessId, @ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return map;
		Long reviewMainId = reviewMain.getId();
		
		List<VoteResult> voteResults = voteResultService.findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId);
		map.put("total", voteResults.size());
		map.put("rows", voteResults);
		return map;
	}
	
	@RequestMapping(value = "{reviewProcessId}/{voteResultId}/user")
	public String user(@PathVariable(value = "reviewProcessId") Long reviewProcessId, @PathVariable(value = "voteResultId")Long voteResultId, Model model) {
		model.addAttribute("reviewProcessId", reviewProcessId);
		model.addAttribute("voteResultId", voteResultId);
		return viewName("user");
	}
	
	@RequestMapping(value = "{reviewProcessId}/{voteResultId}/queryVoteUser")
	@ResponseBody
	public Map<String, Object> queryVoteUser(@PathVariable(value = "reviewProcessId") ReviewProcess reviewProcess, @PathVariable(value = "voteResultId")VoteResult voteResult){
		Map<String, Object> map = Maps.newHashMap();
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return map;
		Long reviewMainId = reviewMain.getId();
		
		if (reviewProcess != null && reviewProcess.getReviewBaseRule() != null){
			List<VoteMonitor> voteMonitors = Lists.newArrayList();
			if (reviewProcess.getReviewBaseRule().getRuleName().equals("addCommonName") || reviewProcess.getReviewBaseRule().getRuleName().equals("addSpecificationsAndPill")) {
				voteMonitors = voteRecordService.findVoteUserMonitorDrugForm(reviewMainId, reviewProcess.getId(), voteResult.getDrugForm().getId());
				
			} else if (reviewProcess.getReviewBaseRule().getRuleName().equals("addCommonNameManufacturer") || reviewProcess.getReviewBaseRule().getRuleName().equals("addSpecificationsAndPillManufacturer")) {
				voteMonitors = voteRecordService.findVoteUserMonitorCommonNameContents(reviewMainId, reviewProcess.getId(), voteResult.getCommonNameContents().getId());
			}
			map.put("total", voteMonitors.size());
			map.put("rows", voteMonitors);
		}
		return map;
	}
}
