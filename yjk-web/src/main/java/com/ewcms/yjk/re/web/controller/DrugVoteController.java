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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.yjk.YjkConstants;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.ReviewProcessService;
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
	private ReviewProcessService reviewProcessService;
	@Autowired
	private VoteRecordService voteRecordService;
	@Autowired
	private VoteResultService voteResultService;
	
	@RequestMapping(value = "index")
	public String index(Model model) {
		Boolean istbEnable = false;
		
		ReviewMain reviewMain =  reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			istbEnable = true;
			model.addAttribute("reviewProcessesList", reviewMain.getReviewProcesses());
		}
		
		List<ReviewMain> reviewMainList = reviewMainService.findAll();
		model.addAttribute("reviewMainList", reviewMainList);
		
		model.addAttribute("istbEnable", istbEnable);
		
		return viewName("index");
	}
	
	@RequestMapping(value = "addTabs")
	@ResponseBody
	public List<ReviewProcess> addTabs(@RequestParam(value = "reviewMainId", required = false)Long reviewMainId) {
		ReviewMain reviewMain =  reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			reviewMain = reviewMainService.findOne(reviewMainId);
		}
		
		if (reviewMain == null) return Lists.newArrayList();
		else return reviewMain.getReviewProcesses();
	}
	
	@RequestMapping(value = "{reviewProcessId}/record")
	public String record(@PathVariable(value = "reviewProcessId") ReviewProcess reviewProcess, @RequestParam(value = "reviewMainId", required = false) Long reviewMainId, Model model) {
		model.addAttribute("statisticalNotes", voteResultService.statisticalNotes(reviewProcess.getId()));
		model.addAttribute("reviewProcess", reviewProcess);
		model.addAttribute("reviewMainId", reviewMainId);
		return viewName("record");
	}
	
	@RequestMapping(value = "{reviewProcessId}/queryVoteResult")
	@ResponseBody
	public Map<String, Object> queryVoteResult(@PathVariable(value = "reviewProcessId") Long reviewProcessId, @RequestParam(value = "reviewMainId", required = false) Long reviewMainId, @ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			reviewMainId = reviewMain.getId();
		}
		
		List<VoteResult> voteResults = voteResultService.findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId);
		map.put("total", voteResults.size());
		map.put("rows", voteResults);
		return map;
	}
	
	@RequestMapping(value = "last")
	public String last(@RequestParam(value = "reviewMainId", required = false) Long reviewMainId, Model model) {
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			reviewMainId = reviewMain.getId();
		} else {
			reviewMain = reviewMainService.findOne(reviewMainId);
		}
		
		Boolean isLast = false;
		if (reviewMain != null) {
			model.addAttribute("reviewMainId", reviewMainId);
			
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null && EmptyUtil.isCollectionNotEmpty(reviewMain.getReviewProcesses())) {
				isLast = true;
				
				reviewProcess = reviewMain.getReviewProcesses().get(reviewMain.getReviewProcesses().size() - 1);
				
				model.addAttribute("currentReviewProcess", reviewProcess);
			}
		}
		model.addAttribute("isLast", isLast);
		
		return viewName("last");
	}

	@RequestMapping(value = "queryLastVoteResult")
	@ResponseBody
	public Map<String, Object> queryLastVoteResult(@RequestParam(value = "reviewMainId", required = false) Long reviewMainId, @ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			reviewMainId = reviewMain.getId();
		} else {
			reviewMain = reviewMainService.findOne(reviewMainId);
		}
		
		if (reviewMain == null) return map;
		
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
		}
		
		return map;
	}

	
	@RequestMapping(value = "{reviewProcessId}/{voteResultId}/user")
	public String user(@PathVariable(value = "reviewProcessId") Long reviewProcessId, @PathVariable(value = "voteResultId")Long voteResultId, @RequestParam(value = "reviewMainId", required = false) Long reviewMainId, Model model) {
		model.addAttribute("reviewProcessId", reviewProcessId);
		model.addAttribute("voteResultId", voteResultId);
		model.addAttribute("reviewMainId", reviewMainId);
		return viewName("user");
	}
	
	@RequestMapping(value = "{reviewProcessId}/{voteResultId}/queryVoteUser")
	@ResponseBody
	public Map<String, Object> queryVoteUser(@PathVariable(value = "reviewProcessId") ReviewProcess reviewProcess, @PathVariable(value = "voteResultId")VoteResult voteResult, @RequestParam(value = "reviewMainId", required = false) Long reviewMainId){
		Map<String, Object> map = Maps.newHashMap();
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) {
			reviewMainId = reviewMain.getId();
		}
		
		if (reviewProcess != null && reviewProcess.getReviewBaseRule() != null){
			List<VoteMonitor> voteMonitors = Lists.newArrayList();
			if (reviewProcess.getReviewBaseRule().getRuleName().equals(YjkConstants.ACN) || reviewProcess.getReviewBaseRule().getRuleName().equals(YjkConstants.ASAP)) {
				voteMonitors = voteRecordService.findVoteUserMonitorDrugForm(reviewMainId, reviewProcess.getId(), voteResult.getDrugForm().getId());
			} else if (reviewProcess.getReviewBaseRule().getRuleName().equals(YjkConstants.ACNM) || reviewProcess.getReviewBaseRule().getRuleName().equals(YjkConstants.ASAPM)) {
				voteMonitors = voteRecordService.findVoteUserMonitorCommonNameContents(reviewMainId, reviewProcess.getId(), voteResult.getCommonNameContents().getId());
			}
			map.put("total", voteMonitors.size());
			map.put("rows", voteMonitors);
		}
		return map;
	}
}
