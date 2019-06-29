package com.ewcms.yjk.re.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.re.repository.VoteResultRepository;
import com.ewcms.yjk.sb.entity.DrugForm;

@Service
public class VoteResultService extends BaseService<VoteResult, Long> {

	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private ReviewProcessService reviewProcessService;
	@Autowired
	private VoteRecordService voteRecordService;
	
	private VoteResultRepository getVoteResultRepository(){
		return (VoteResultRepository)baseRepository;
	}
	
	public Long countByReviewProcessId(Long reviewProcessId){
		return getVoteResultRepository().countByReviewProcessId(reviewProcessId);
	}
	
	public VoteResult findByDrugFormIdAndReviewProcessId(Long drugFormId, Long reviewProcessId){
		return getVoteResultRepository().findByDrugFormIdAndReviewProcessId(drugFormId, reviewProcessId);
	}
	
	public List<VoteMonitor> findVoteMonitor(List<Long> userIds){
		return getVoteResultRepository().findVoteMonitor(userIds);
	}
	
	public List<DrugForm> findSelectedDrugForm(Long reviewMainId,String declareCategory){
		return getVoteResultRepository().findSelectedDrugForm(reviewMainId, declareCategory);
	}
	
	public List<VoteResult> findCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId){
		return getVoteResultRepository().findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId);
	}
	
	public List<VoteResult> findByReviewProcessId(Long reviewProcessId){
		return getVoteResultRepository().findByReviewProcessId(reviewProcessId);
	}
	
	public VoteResult findByCommonNameContentsIdAndReviewProcessId(Long commonNameContentsId, Long reviewProcessId){
		return getVoteResultRepository().findByCommonNameContentsIdAndReviewProcessId(commonNameContentsId, reviewProcessId);
	}
	
	/**
	 * 调整入围
	 * 
	 * @param reviewMainId
	 */
	public AjaxResponse adjust(List<Long> voteResultIds) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String message = "调整入围操作成功！";
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			message = "评审未开启，不能操作！";
			ajaxResponse.setSuccess(Boolean.FALSE);
		} else {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null) {
				message = "没有流程可使用或当前流程已结束！";
				ajaxResponse.setSuccess(Boolean.FALSE);
			} else {
				Long reviewProcessId = reviewProcess.getId();
				getVoteResultRepository().adjust(reviewMainId, reviewProcessId, voteResultIds);
			}
		}
		ajaxResponse.setMessage(message);
		return ajaxResponse;
	}
	
	/**
	 * 调整出围
	 * 
	 * @param voteResultIds
	 */
	public AjaxResponse cancel(List<Long> voteResultIds) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String message = "调整出围操作成功！";
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			message = "评审未开启，不能操作！";
			ajaxResponse.setSuccess(Boolean.FALSE);
		} else {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null) {
				message = "没有流程可使用或当前流程已结束！";
				ajaxResponse.setSuccess(Boolean.FALSE);
			} else {
				Long reviewProcessId = reviewProcess.getId();
				getVoteResultRepository().cancel(reviewMainId, reviewProcessId, voteResultIds);
			}
		}
		ajaxResponse.setMessage(message);
		return ajaxResponse;
	}
	
	/**
	 * 最终确认
	 * @param voteResultIds
	 */
	public AjaxResponse affirm(List<Long> voteResultIds) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String message = "最终结果操作成功！";
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			message = "评审未开启，不能操作！";
			ajaxResponse.setSuccess(Boolean.FALSE);
		} else {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null) {
				message = "没有流程可使用或当前流程已结束！";
				ajaxResponse.setSuccess(Boolean.FALSE);
			} else {
				Long reviewProcessId = reviewProcess.getId();
				List<Long> submittedUserIds = voteRecordService.findSubmittedUserIds(reviewMainId, reviewProcessId);
				List<Long> reviewUserIds = reviewMainService.findReviewUserIds(reviewMain);
				if (EmptyUtil.isCollectionNotEmpty(submittedUserIds) && EmptyUtil.isCollectionNotEmpty(reviewUserIds) && submittedUserIds.size() == reviewUserIds.size()) {
					getVoteResultRepository().affirm(reviewMainId, reviewProcessId, voteResultIds);
				} else {
					message = "本轮投票中有专家还没有结束投票，请等全部都投完后，再最终确认！";
					ajaxResponse.setSuccess(Boolean.FALSE);
				}
			}
		}
		ajaxResponse.setMessage(message);
		return ajaxResponse;
	}
	
	/**
	 * 进入下一轮
	 * @param opUserId 操作用户
	 * @return
	 */
	public AjaxResponse next(Long opUserId) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String message = "进入下一轮操作成功！";
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			message = "评审未开启，不能操作！";
			ajaxResponse.setSuccess(Boolean.FALSE);
		} else {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null) {
				message = "本轮已是最后投票结果！";
				ajaxResponse.setSuccess(Boolean.FALSE);
			} else {
				Long reviewProcessId = reviewProcess.getId();
				List<Long> submittedUserIds = voteRecordService.findSubmittedUserIds(reviewMainId, reviewProcessId);
				List<Long> reviewUserIds = reviewMainService.findReviewUserIds(reviewMain);
				if (EmptyUtil.isCollectionNotEmpty(submittedUserIds) && EmptyUtil.isCollectionNotEmpty(reviewUserIds) && submittedUserIds.size() == reviewUserIds.size()) {
					Long countNoAffirm = getVoteResultRepository().countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedFalse(reviewMainId, reviewProcessId);
					if (countNoAffirm.longValue() == 0) {
						Long countBeSelectedNext = getVoteResultRepository().countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedTrueAndSelectedTrue(reviewMainId, reviewProcessId);
						if (countBeSelectedNext.longValue() > 0) {
							reviewProcess.setFinished(Boolean.TRUE);
							reviewProcessService.update(reviewProcess, opUserId, "在 最终结果 窗口中操作了 进入下一轮 按钮");
						} else {
							message = "在本轮未有入选的结果，不能进行下一轮！";
							ajaxResponse.setSuccess(Boolean.FALSE);
						}
					} else {
						message = "还有未确认最终结果的记录！";
						ajaxResponse.setSuccess(Boolean.FALSE);
					}
				} else {
					message = "本轮投票中有专家还没有结束投票，请等全部都投完后进行完最终确认后再进入下一轮！";
					ajaxResponse.setSuccess(Boolean.FALSE);
				}
			}
		}
		
		ajaxResponse.setMessage(message);
		return ajaxResponse;
	}
}
