package com.ewcms.yjk.re.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.yjk.YjkConstants;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.re.repository.VoteResultRepository;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.google.common.collect.Lists;

@Service
public class VoteResultService extends BaseService<VoteResult, Long> {

	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private ReviewProcessService reviewProcessService;
	@Autowired
	private VoteRecordService voteRecordService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserOrganizationJobService userOrganizationJobService;
	@Autowired
	private EnsurePassThroughService ensurePassThroughService;
	
	private VoteResultRepository getVoteResultRepository(){
		return (VoteResultRepository)baseRepository;
	}
	
	public Long countByReviewProcessId(Long reviewProcessId){
		return getVoteResultRepository().countByReviewProcessId(reviewProcessId);
	}
	
	public VoteResult findByDrugFormIdAndReviewProcessId(Long drugFormId, Long reviewProcessId){
		return getVoteResultRepository().findByDrugFormIdAndReviewProcessId(drugFormId, reviewProcessId);
	}
	
	public List<VoteMonitor> findVoteResultMonitor(List<Long> userIds, Long reviewMainId, Long reviewProcessId){
		return voteRecordService.findVoteResultMonitor(userIds, reviewMainId, reviewProcessId);
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
	public AjaxResponse transferIn(List<Long> voteResultIds) {
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
				getVoteResultRepository().transferIn(reviewMainId, reviewProcessId, voteResultIds);
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
	public AjaxResponse callOut(List<Long> voteResultIds) {
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
				getVoteResultRepository().callOut(reviewMainId, reviewProcessId, voteResultIds);
			}
		}
		ajaxResponse.setMessage(message);
		return ajaxResponse;
	}
	
	/**
	 * 取肖调整
	 * 
	 * @param voteResultId 
	 * @return
	 */
	public AjaxResponse cancel(Long voteResultId) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String message = "取消调整操作成功！";
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
				VoteResult voteResult = findOne(voteResultId);
				if (voteResult.getAdjusted() != null) {
					if (voteResult.getAffirmVoteResulted()) {
						message = "此条记录结果已封存，不能取消调整操作！";
						ajaxResponse.setSuccess(Boolean.FALSE);
					} else {
						voteResult.setAdjusted(null);
						update(voteResult);
					}
				}
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
					getVoteResultRepository().result(reviewMainId, reviewProcessId, voteResultIds);
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
	 * @param reason 按钮说明
	 * @return
	 */
	public AjaxResponse next(Long opUserId, String reason) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String message = reason + "操作成功！";
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			message = "评审未开启，不能操作！";
			ajaxResponse.setSuccess(Boolean.FALSE);
		} else {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null) {
				message = "本轮流程有误！";
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
							reviewProcessService.update(reviewProcess, opUserId, "在 投票结果 窗口中操作了 " + reason + " 按钮");
						} else {
							message = "在本轮未有入选的结果，不能" + reason + "！";
							ajaxResponse.setSuccess(Boolean.FALSE);
						}
					} else {
						message = "还有未确认本轮结果的记录！";
						ajaxResponse.setSuccess(Boolean.FALSE);
					}
				} else {
					message = "本轮投票中有专家还没有结束投票，请等全部都投完后进行完最终确认后再" + reason + "！";
					ajaxResponse.setSuccess(Boolean.FALSE);
				}
				
			}
		}
		
		ajaxResponse.setMessage(message);
		return ajaxResponse;
	}
	
//	private void generateSelected(Long reviewMainId, Long reviewProcessId, Long number, DrugCategoryEnum drugCategoryEnum) {
//		List<VoteResult> voteResults = getVoteResultRepository().findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId, drugCategoryEnum);
//		if (EmptyUtil.isCollectionNotEmpty(voteResults)) {
//			long i = 0;
//			for (VoteResult voteResult : voteResults) {
//				if (i < number.longValue()) {
//					voteResult.setSelected(true);
//					update(voteResult);
//				} else {
//					break;
//				}
//				i++;
//			}
//		}
//	}
//	
//	public List<VoteResult> generateCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId){
//		if(getVoteResultRepository().countByReviewMainIdAndReviewProcessIdAndSelectedTrue(reviewMainId, reviewProcessId).longValue() == 0){
//			ReviewProcess reviewProcess = reviewProcessService.findOne(reviewProcessId);
//			if (reviewProcess == null) return Lists.newArrayList();
//			
//			String reuleName = reviewProcess.getReviewBaseRule().getRuleName();
//			if (reuleName.equals(YjkConstants.ACN) || reuleName.equals(YjkConstants.ACNM)) {
//				generateSelected(reviewMainId, reviewProcessId, reviewProcess.getGeneralNameChinese(), DrugCategoryEnum.Z);
//				generateSelected(reviewMainId, reviewProcessId, reviewProcess.getGeneralNameWestern(), DrugCategoryEnum.H);
//			} else if (reuleName.equals(YjkConstants.ASAP) || reuleName.equals(YjkConstants.ASAPM)) {
//				generateSelected(reviewMainId, reviewProcessId, reviewProcess.getFormulaChinese(), DrugCategoryEnum.Z);
//				generateSelected(reviewMainId, reviewProcessId, reviewProcess.getFormulaWestern(), DrugCategoryEnum.H);
//			} else {
//				return Lists.newArrayList();
//			}
//		}
//		
//		return findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId);
//	}
	
	@SuppressWarnings("unchecked")
	public List<VoteResult> generateCurrentVoteResults(Long reviewMainId, Long reviewProcessId){
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return Lists.newArrayList();
		SystemParameter systemParameter = reviewMain.getSystemParameter();
		if (systemParameter == null) return Lists.newArrayList();
		
		List<Long> organizationIds = Collections3.extractToList(organizationService.findAll(), "id");
		
		ReviewProcess currentReviewProcess = reviewProcessService.findOne(reviewProcessId);
		String reuleName = currentReviewProcess.getReviewBaseRule().getRuleName();
		
		for (DrugCategoryEnum drugCategoryEnum : DrugCategoryEnum.values()) {
			Long ensureOrganPassNumber = 0L;// 确保每科室通过数
			Long matchNumber = 0L;
			
			if (reuleName.equals(YjkConstants.ACN) || reuleName.equals(YjkConstants.ACNM)) {
				if (drugCategoryEnum.equals(DrugCategoryEnum.H)) {
					matchNumber = currentReviewProcess.getGeneralNameWestern();
				} else if (drugCategoryEnum.equals(DrugCategoryEnum.Z)) {
					matchNumber = currentReviewProcess.getGeneralNameChinese();
				}
			} else if (reuleName.equals(YjkConstants.ASAP) || reuleName.equals(YjkConstants.ASAPM)) {
				if (drugCategoryEnum.equals(DrugCategoryEnum.H)) {
					matchNumber = currentReviewProcess.getFormulaWestern();
				} else if (drugCategoryEnum.equals(DrugCategoryEnum.Z)) {
					matchNumber = currentReviewProcess.getFormulaChinese();
				}
			}
			
			if (drugCategoryEnum.equals(DrugCategoryEnum.H)) {
				ensureOrganPassNumber = currentReviewProcess.getEnsureOrganPassWesternNumber();
			} else if (drugCategoryEnum.equals(DrugCategoryEnum.Z)) {
				ensureOrganPassNumber = currentReviewProcess.getEnsureOrganPassChineseNumber();
			}
			
			if (ensureOrganPassNumber.longValue() > 0) {
				int ensureTotalNumber = 0;
				List<Long> excludeVoteResultIds = Lists.newArrayList();
				for (Long organizationId : organizationIds) {
					Set<Long> userIds = userOrganizationJobService.findUsersByOrganization(organizationId);
					if (EmptyUtil.isCollectionEmpty(userIds)) continue;
					
					List<Long> voteResultIds = getVoteResultRepository().findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId, drugCategoryEnum, userIds);
					if (EmptyUtil.isCollectionEmpty(voteResultIds)) continue;
					
					int ensureNumber = ensurePassThroughService.findEnsureNumber(reviewProcessId, organizationId, drugCategoryEnum);
					if (ensureNumber <= -1) {// 当特定科室为-1时，代表此编号的组织没有在特定科室定义
						ensureNumber = Math.min(voteResultIds.size(), ensureOrganPassNumber.intValue());
					} else {
						ensureNumber = Math.min(voteResultIds.size(), ensureNumber);
					}
					
//					int ensureNumber = Math.min(voteResultIds.size(), ensureOrganPassNumber.intValue());
					if (ensureNumber <= 0) continue;
					
					List<Long> newVoteResultIds = voteResultIds.subList(0, ensureNumber);
					getVoteResultRepository().ensure(newVoteResultIds);

					excludeVoteResultIds.addAll(newVoteResultIds);
					ensureTotalNumber += ensureNumber;
				}
				
				if (matchNumber.intValue() > ensureTotalNumber) {
					List<VoteResult> voteResults = getVoteResultRepository().findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId, drugCategoryEnum, excludeVoteResultIds);
					if (EmptyUtil.isCollectionNotEmpty(voteResults)) {
						int residueNumber = matchNumber.intValue() - ensureTotalNumber;
						int i = 0;
						for (VoteResult voteResult : voteResults) {
							if (i < residueNumber) {
								voteResult.setSelected(true);
								update(voteResult);
							} else {
								break;
							}
							i++;
						}
					}
				}
			} else {
				List<VoteResult> voteResults = getVoteResultRepository().findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId, drugCategoryEnum);
				if (EmptyUtil.isCollectionNotEmpty(voteResults)) {
					int i = 0;
					for (VoteResult voteResult : voteResults) {
						if (i < matchNumber.longValue()) {
							voteResult.setSelected(true);
							update(voteResult);
						} else {
							break;
						}
						i++;
					}
				}
			}
		}
		
		return findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId);
	}

	public Long countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedFalse(Long reviewMainId, Long reviewProcessId){
		return getVoteResultRepository().countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedFalse(reviewMainId, reviewProcessId);
	}
	
	/**
	 * 评审统计结果
	 * 
	 * @param reviewMainId
	 * @param reviewProcessId
	 * @param drugCategoryEnum
	 * @return
	 */
	public Long countResult(Long reviewMainId, Long reviewProcessId, DrugCategoryEnum drugCategoryEnum) {
		return getVoteResultRepository().countByReviewMainIdAndReviewProcessIdAndDrugFormCommonNameContentsCommonDrugCategoryAndSelectedTrueAndAffirmVoteResultedTrue(reviewMainId, reviewProcessId, drugCategoryEnum);
	}
	
	public List<VoteResult> findAllVoteResultLast(Long reviewMainId){
		return getVoteResultRepository().findAllVoteResultLast(reviewMainId);
	}
	
	public List<VoteResult> findChosnResult(Long reviewMainId){
		return getVoteResultRepository().findChosnResult(reviewMainId);
	}
}
