package com.ewcms.yjk.re.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.ReviewProcessService;
import com.ewcms.yjk.re.service.VoteRecordService;

@Controller
@RequestMapping(value = "/yjk/re/voterecord")
public class VoteRecordController extends BaseCRUDController<VoteRecord, Long> {
	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private ReviewProcessService reviewProcessService;
	
	private VoteRecordService getVoteRecordService() {
		return (VoteRecordService) baseService;
	}
	
	public VoteRecordController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("re:voterecord");
	}
	
	@Override
	protected void setCommonData(Model model) {
		model.addAttribute("isOpenReview", reviewMainService.isOpenReview());
		if(reviewMainService.isOpenReview()){
			ReviewMain reviewMain =  reviewMainService.findByEnabledTrue();
			model.addAttribute("reviewProcessesList", reviewMain.getReviewProcesses());
			model.addAttribute("currentReviewProcess", reviewProcessService.findCurrentReviewProcess(reviewMain.getId()));
		}
		super.setCommonData(model);
	}
	
	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}


	/**
	 * 投票流程处理
	 * 
	 */
	@RequestMapping(value = "index")
	public String index(@CurrentUser User user,Model model) {
		ReviewMain reviewMain =  reviewMainService.findByEnabledTrue();
		Boolean isReivewProcess = false;
		if(reviewMain != null){
			Boolean isExpertSubmitCurrentReview = false;
			ReviewProcess currentReviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMain.getId());
			if (currentReviewProcess != null) { 
				isReivewProcess = true;
				isExpertSubmitCurrentReview = getVoteRecordService().expertSubmitCurrentReview(user.getId(), currentReviewProcess.getId());
				model.addAttribute("reviewProcessId", currentReviewProcess.getId());
				if(!isExpertSubmitCurrentReview){
					if(currentReviewProcess.getReviewBaseRule().getRuleName().equals("addCommonName")){
						getVoteRecordService().expertStartVoteAddCommonName(user.getId(),currentReviewProcess.getId());
					}
					if(currentReviewProcess.getReviewBaseRule().getRuleName().equals("addSpecificationsAndPill")){
						getVoteRecordService().expertStartVoteAddSpecificationsAndPill(user.getId(),currentReviewProcess.getId());
					}	
					if(currentReviewProcess.getReviewBaseRule().getRuleName().equals("addCommonNameManufacturer")){
						getVoteRecordService().expertStartVoteAddCommonNameManufacturer(user.getId(),currentReviewProcess.getId());
					}	
					if(currentReviewProcess.getReviewBaseRule().getRuleName().equals("addSpecificationsAndPillManufacturer")){
						getVoteRecordService().expertStartVoteAddSpecificationsAndPillManufacturer(user.getId(),currentReviewProcess.getId());
					}	
				}
			}
			
			model.addAttribute("isExpertSubmitCurrentReview", isExpertSubmitCurrentReview);
		}
		model.addAttribute("isReivewProcess", isReivewProcess);
		return super.index(model);
	}


	/**
	 * 查询需要投票的申报药品
	 * 
	 */
	@RequestMapping(value = "{reviewProcessId}/query")
	@ResponseBody
	public Map<String, Object> query(@CurrentUser User user,SearchParameter<Long> searchParameter,	Model model,@PathVariable(value = "reviewProcessId") Long reviewProcessId) {
		searchParameter.getParameters().put("EQ_userId", user.getId());
		searchParameter.getParameters().put("EQ_reviewProcessId",reviewProcessId);
		searchParameter.getSorts().put("drugForm.commonNameContents.common.drugCategory", Direction.ASC);
		searchParameter.getSorts().put("id", Direction.ASC);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "{userId}/{reviewProcessId}/query")
	@ResponseBody
	public Map<String, Object> query(@PathVariable(value = "userId") Long userId,SearchParameter<Long> searchParameter,	Model model, @PathVariable(value = "reviewProcessId") Long reviewProcessId) {
		searchParameter.getParameters().put("EQ_userId", userId);
		searchParameter.getParameters().put("EQ_reviewProcessId",reviewProcessId);
		searchParameter.getSorts().put("drugForm.commonNameContents.common.drugCategory", Direction.ASC);
		searchParameter.getSorts().put("id", Direction.ASC);
		return super.query(searchParameter, model);
	}

	/**
	 * 获取投票类型记录集
	 * 
	 */
//	@RequestMapping(value = "canVoteType")
//	@ResponseBody
//	public Object canVoteType(Model model) {
//		
//		return VoteTypeEnum.values();
//	}
	
	/**
	 * 保存专家投票结果
	 * 
	 */
	@RequestMapping(value = "savevote", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveVote(@CurrentUser User user, @RequestParam(required = false) List<String> selections) {
		AjaxResponse ajaxResponse = new AjaxResponse("保存成功！");
		try {
			getVoteRecordService().saveVote(user.getId(), selections);
		} catch (Exception e) {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("保存失败！");
		}
		return ajaxResponse;
	}
	
	/**
	 * 提交专家投票结果
	 * 
	 */
	@RequestMapping(value = "submitvote", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse submitVote(@CurrentUser User user, @RequestParam(required = false) List<String> selections) {
		AjaxResponse ajaxResponse = new AjaxResponse("提交成功！");
		try {
			getVoteRecordService().submitVote(user.getId(), selections);
		} catch (Exception e) {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("提交失败！");
		}
		return ajaxResponse;
	}
	
}
