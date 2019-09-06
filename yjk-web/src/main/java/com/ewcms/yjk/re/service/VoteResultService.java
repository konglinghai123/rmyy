package com.ewcms.yjk.re.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.ConvertUtil;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.yjk.YjkConstants;
import com.ewcms.yjk.re.entity.AdjustedEnum;
import com.ewcms.yjk.re.entity.EnsurePassThrough;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.re.repository.VoteResultRepository;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.ewcms.yjk.zd.commonname.service.HospitalContentsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author admin
 *
 */
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
	@Autowired
	private CommonNameService commonNameService;
	@Autowired
	private HospitalContentsService hospitalContentsService;
	
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
	
	public Long countByReviewProcessIdAndDrugFormCommonNameContentsAdministrationIdAndDrugFormCommonNameContentsCommonCommonName(Long reviewProcessId,Long administrationId, String commonName){
		return getVoteResultRepository().countByReviewProcessIdAndDrugFormCommonNameContentsAdministrationIdAndDrugFormCommonNameContentsCommonCommonName(reviewProcessId, administrationId, commonName);
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
				VoteResult voteResult = null;
				for (Long voteResultId : voteResultIds) {
					voteResult = findOne(voteResultId);
					if (EmptyUtil.isNull(voteResult) || EmptyUtil.isNull(voteResult.getDrugForm()) || EmptyUtil.isNull(voteResult.getDrugForm().getCommonNameContents())) continue;
					String drugFormName = voteResult.getDrugForm().getCommonNameContents().getExtractCommonName() + "-" + voteResult.getDrugForm().getCommonNameContents().getAdministrationName() + "-" + voteResult.getDrugForm().getCommonNameContents().getDrugCategoryInfo();
					if (isMayTransferIn(voteResultId, reviewProcessId)) {
						if (!voteResult.getSelected() && !voteResult.getAffirmVoteResulted()) {
							voteResult.setAdjusted(AdjustedEnum.transferIn);
							update(voteResult);
						} else {
							if (voteResult.getSelected()) {
								message += "<br/>" + drugFormName + " 已是拟入围无需调入";
							} else if (voteResult.getAffirmVoteResulted()) {
								message += "<br/>" + drugFormName + " 已被封存不能调入";
							}
						}
					} else {
						int maxNumber = findMaxNumber(voteResult.getDrugForm().getCommonNameContents());
						message += "<br/>" + drugFormName + " 违反了一品" + ConvertUtil.int2chineseNum(maxNumber) +"规的限制";
					}
				}
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
	
	@SuppressWarnings("unchecked")
	public List<VoteResult> generateCurrentVoteResults(Long reviewMainId, Long reviewProcessId){
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return Lists.newArrayList();
		SystemParameter systemParameter = reviewMain.getSystemParameter();
		if (systemParameter == null) return Lists.newArrayList();
		
		if (getVoteResultRepository().countByReviewProcessIdAndSelectedTrue(reviewProcessId) == 0) {
			ReviewProcess currentReviewProcess = reviewProcessService.findOne(reviewProcessId);
			String reuleName = currentReviewProcess.getReviewBaseRule().getRuleName();
			
			List<Long> excludeVoteResultIds = Lists.newArrayList();// 有特定科室定义了需排除的投票结果编号记录集
			
			//int ensureTotalNumber = 0;
			
			List<EnsurePassThrough> ensurePassThroughs = ensurePassThroughService.findByReviewProcessIdAndEnabledTrueOrderByWeightAsc(reviewProcessId);
			if (EmptyUtil.isCollectionNotEmpty(ensurePassThroughs)) {
				Map<Long, Integer> organEnsureMap = Maps.newHashMap();
				for (EnsurePassThrough ensurePassThrough : ensurePassThroughs) {
					int ensureNumber = ensurePassThrough.getPassNumber();
					
					List<Long> organizationIds = ensurePassThrough.getOrganiztionIdsList();
					if (EmptyUtil.isCollectionEmpty(organizationIds)) {
						organizationIds = Collections3.extractToList(organizationService.findAll(), "id");
					}
					for (Long organizationId : organizationIds) {
						organEnsureMap.put(organizationId, ensureNumber);
					}
				}
				
				Iterator<Entry<Long, Integer>> iterator = organEnsureMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<Long, Integer> entry = iterator.next();
					int ensureNumber = entry.getValue();
					if (ensureNumber > 0) {
						Set<Long> userIds = userOrganizationJobService.findUsersByOrganization(entry.getKey());
						if (EmptyUtil.isCollectionEmpty(userIds)) continue;
						
						List<Long> voteResultIds = getVoteResultRepository().findCurrentReviewProcessUserIdsVoteResults(reviewMainId, reviewProcessId, userIds);
						if (EmptyUtil.isCollectionEmpty(voteResultIds)) continue;
						
						ensureNumber = Math.min(ensureNumber, voteResultIds.size());
						
						if (ensureNumber <= 0) continue;
						List<Long> newVoteResultIds = voteResultIds.subList(0, ensureNumber);
						getVoteResultRepository().ensure(newVoteResultIds);
	
						excludeVoteResultIds.addAll(newVoteResultIds);
						//ensureTotalNumber += ensureNumber;
						
						//TODO 未把中西药数量与设定数量进行比对
					}
				}
			}
			
			for (DrugCategoryEnum drugCategoryEnum : DrugCategoryEnum.values()) {
				Long ensureTotalNumber = getVoteResultRepository().countByReviewProcessIdAndSelectedTrueAndEnsureOrganTrueAndDrugFormCommonNameContentsCommonDrugCategory(reviewProcessId, drugCategoryEnum);
				int matchNumber = 0;
				
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
				
				if (matchNumber > ensureTotalNumber.intValue()) {
					List<VoteResult> voteResults = Lists.newArrayList();
					if (EmptyUtil.isCollectionEmpty(excludeVoteResultIds)) {
						voteResults = getVoteResultRepository().findCurrentReviewProcessVoteResults(reviewMainId, reviewProcessId, drugCategoryEnum);
					} else {
						voteResults = getVoteResultRepository().findCurrentReviewProcessExcludeIdsVoteResults(reviewMainId, reviewProcessId, drugCategoryEnum, excludeVoteResultIds);
					}
					
					if (EmptyUtil.isCollectionNotEmpty(voteResults)) {
						int residueNumber = matchNumber - ensureTotalNumber.intValue();
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
				
				//TODO 处理一品n规处理过程
				if (reuleName.equals(YjkConstants.ACNM) || reuleName.equals(YjkConstants.ASAPM)) {
					Boolean isAddPillAndSpecfication = currentReviewProcess.getReviewBaseRule().getRuleName().equals(YjkConstants.ASAPM) ? true : false;
					filterSelectUpperLimt(reviewProcessId, isAddPillAndSpecfication, drugCategoryEnum);
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
	
	/**
	 * 取消违反一品N规的入围记录并替补相应的新入围记录
	 * 
	 * @param reviewMainId
	 * @param reviewProcessId
	 * @return
	 */
	private void filterSelectUpperLimt(Long reviewProcessId,Boolean isAddPillAndSpecfication, DrugCategoryEnum drugCategoryEnum){
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return;
		int matchNumber = 0;
		ReviewProcess currentReviewProcess = reviewProcessService.findOne(reviewProcessId);
		String reuleName = currentReviewProcess.getReviewBaseRule().getRuleName();
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
		
		List<VoteResult> voteResults = Lists.newArrayList();
		voteResults = getVoteResultRepository().findByReviewMainIdAndReviewProcessIdAndDrugFormCommonNameContentsCommonDrugCategoryAndSelectedTrue(reviewMain.getId(), reviewProcessId, drugCategoryEnum);
		SystemParameter systemParameter = reviewMain.getSystemParameter();
		//定义一品N规的系统参数的限数
		int maxNumber = 0;
		CommonNameContents vo;
		//记录出围多余的一品N规记录
		List<VoteResult> outVoteResults = Lists.newArrayList();
		
		for (VoteResult voteResult : voteResults) {
			vo = voteResult.getDrugForm().getCommonNameContents();

			if (EmptyUtil.isNotNull(systemParameter)) {
				if(vo.getAdministration().getId()==1){//口服一品两规限数
					maxNumber = systemParameter.getOralDeclarationLimt().intValue();
				}else if(vo.getAdministration().getId()==2){//注射一品两规限数
					maxNumber = systemParameter.getInjectDeclarationLimt().intValue();
				}else if(vo.getAdministration().getId()==3){//外用及其他一品两规限数
					maxNumber = systemParameter.getOtherDeclarationLimt().intValue();
				}
			}
			List<VoteResult> sameVoteResults = matchNumberByVoteResult(vo, reviewProcessId);

			List<HospitalContents> hospitalContentsList = matchNumberByHospital(vo);
			
			int selectMaxNumber = maxNumber - hospitalContentsList.size();
			
			int outNumber = sameVoteResults.size() - selectMaxNumber;
			if(maxNumber != 0 && outNumber > 0){//入围数量超过一品N规，要去掉多余的入围记录并替补相应的记录
				
				//记录下出围多余的一品N规记录
				outVoteResults.addAll(sameVoteResults.subList(sameVoteResults.size()-outNumber, sameVoteResults.size()));

			}
		}
		

		if(voteResults.size()-outVoteResults.size()<matchNumber){//入围的总数小于设置都入围数量，就替补相应的入围记录
			//替补入围出围的一品N规相应数量的记录
			List<VoteResult> outAllVoteResults = getVoteResultRepository().findOutVoteResult(reviewMain.getId(), reviewProcessId, drugCategoryEnum);
			int subIndex = 0;
			for (VoteResult outAllVoteResult : outAllVoteResults) {
				vo = outAllVoteResult.getCommonNameContents();
				if (EmptyUtil.isNotNull(systemParameter)) {
					if(vo.getAdministration().getId()==1){//口服一品两规限数
						maxNumber = systemParameter.getOralDeclarationLimt().intValue();
					}else if(vo.getAdministration().getId()==2){//注射一品两规限数
						maxNumber = systemParameter.getInjectDeclarationLimt().intValue();
					}else if(vo.getAdministration().getId()==3){//外用及其他一品两规限数
						maxNumber = systemParameter.getOtherDeclarationLimt().intValue();
					}
				}
				List<VoteResult> sameVoteResults = matchNumberByVoteResult(vo, reviewProcessId);

				List<HospitalContents> hospitalContentsList = matchNumberByHospital(vo);
				
				if(maxNumber-sameVoteResults.size()-hospitalContentsList.size()>0){// 不违规一品N规就替补成入围记录
					outAllVoteResult.setSelected(Boolean.TRUE);
					update(outAllVoteResult);
					subIndex++;
					if(subIndex == outVoteResults.size()|| subIndex==(matchNumber-voteResults.size()-outVoteResults.size()))break;//替补数量已经达到违规一品N规出围的总数，就终止替补
				}
			}
		}
		
		for (VoteResult outVoteResult : outVoteResults) {//将所有违规一品N规的入围记录改成出围
			outVoteResult.setSelected(Boolean.FALSE);
			update(outVoteResult);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<VoteResult>  matchNumberByVoteResult(CommonNameContents vo, Long reviewProcessId){
		List<VoteResult> sameVoteResults = Lists.newArrayList();
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return sameVoteResults;
		
		List<CommonName> commonNameList = commonNameService.findByMatchNumber(vo.getCommon().getMatchNumber());
		List<Long> commonNameIds = Collections3.extractToList(commonNameList, "id");
		if(EmptyUtil.isCollectionNotEmpty(commonNameIds)){ 
			sameVoteResults =  getVoteResultRepository().findSameVoteResult(reviewMain.getId(), reviewProcessId, commonNameIds, vo.getAdministration().getId());
		}
		return sameVoteResults;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<HospitalContents>  matchNumberByHospital(CommonNameContents vo){
		List<HospitalContents> sameVoteResults = Lists.newArrayList();
		List<CommonName> commonNameList = commonNameService.findByMatchNumber(vo.getCommon().getMatchNumber());
		List<Long> commonNameIds = Collections3.extractToList(commonNameList, "id");
		if(EmptyUtil.isCollectionNotEmpty(commonNameIds)){ 
			sameVoteResults =  hospitalContentsService.findByCommonIdInInAndAdministrationIdAndDeletedFalse(commonNameIds, vo.getAdministration().getId());
		}
		return sameVoteResults;		
	}
	
	private Boolean isMayTransferIn(Long voteResultId, Long reviewProcessId){
		VoteResult voteResult = this.findOne(voteResultId);
		CommonNameContents vo = voteResult.getDrugForm().getCommonNameContents();
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) return Boolean.FALSE;
		SystemParameter systemParameter = reviewMain.getSystemParameter();
		int maxNumber = 0;
		if (EmptyUtil.isNotNull(systemParameter)) {
			if(vo.getAdministration().getId() == 1){//口服一品两规限数
				maxNumber = systemParameter.getOralDeclarationLimt().intValue();
			}else if(vo.getAdministration().getId() == 2){//注射一品两规限数
				maxNumber = systemParameter.getInjectDeclarationLimt().intValue();
			}else if(vo.getAdministration().getId() == 3){//外用及其他一品两规限数
				maxNumber = systemParameter.getOtherDeclarationLimt().intValue();
			}
		}
		
		List<VoteResult> sameVoteResults = matchNumberByVoteResult(vo, reviewProcessId);

		List<HospitalContents> hospitalContentsList = matchNumberByHospital(vo);
		
		if(sameVoteResults.size() + hospitalContentsList.size() < maxNumber || maxNumber == 0)return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	private int findMaxNumber(CommonNameContents commonNameContents) {
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		SystemParameter systemParameter = reviewMain.getSystemParameter();
		int maxNumber = 0;
		if (EmptyUtil.isNotNull(systemParameter)) {
			if(commonNameContents.getAdministration().getId() == 1){//口服一品N规限数
				maxNumber = systemParameter.getOralDeclarationLimt().intValue();
			}else if(commonNameContents.getAdministration().getId() == 2){//注射一品N规限数
				maxNumber = systemParameter.getInjectDeclarationLimt().intValue();
			}else if(commonNameContents.getAdministration().getId() == 3){//外用及其他一品N规限数
				maxNumber = systemParameter.getOtherDeclarationLimt().intValue();
			}
		}
		return maxNumber;
	}
	
	public String statisticalNotes(Long reviewProcessId) {
		Long resultNumber = getVoteResultRepository().countByReviewProcessId(reviewProcessId);//投票的药品数
		Long hResultNumber = getVoteResultRepository().countByReviewProcessIdAndDrugFormCommonNameContentsCommonDrugCategory(reviewProcessId, DrugCategoryEnum.H);//投票的西药数量
		Long zResultNumber = getVoteResultRepository().countByReviewProcessIdAndDrugFormCommonNameContentsCommonDrugCategory(reviewProcessId, DrugCategoryEnum.Z);//投票的中成药数量
		
		Long selectedNumber = getVoteResultRepository().countByReviewProcessIdAndSelectedTrue(reviewProcessId);//拟入围数
		Long hSelectedNumber = getVoteResultRepository().countByReviewProcessIdAndSelectedTrueAndDrugFormCommonNameContentsCommonDrugCategory(reviewProcessId, DrugCategoryEnum.H);//拟入围的西药数量
		Long zSelectedNumber = getVoteResultRepository().countByReviewProcessIdAndSelectedTrueAndDrugFormCommonNameContentsCommonDrugCategory(reviewProcessId, DrugCategoryEnum.Z);//拟入围的中成药数量
		
		Long ensureOrganNumber = getVoteResultRepository().countByReviewProcessIdAndSelectedTrueAndEnsureOrganTrue(reviewProcessId);//确保科室入围数
		Long hEnsureOrganNumber = getVoteResultRepository().countByReviewProcessIdAndSelectedTrueAndEnsureOrganTrueAndDrugFormCommonNameContentsCommonDrugCategory(reviewProcessId, DrugCategoryEnum.H);//确保科室的西药数量
		Long zEnsureOrganNumber = getVoteResultRepository().countByReviewProcessIdAndSelectedTrueAndEnsureOrganTrueAndDrugFormCommonNameContentsCommonDrugCategory(reviewProcessId, DrugCategoryEnum.Z);//确保科室的中成药数量
		
		return String.format("药品总数：%d个（西药：%d，中成药：%d）；拟入围药品总数：%d（西药：%d，中成药：%d）；确保科室入围数：%d（西药：%d，中成药：%d）。", resultNumber, hResultNumber, zResultNumber, selectedNumber, hSelectedNumber, zSelectedNumber, ensureOrganNumber, hEnsureOrganNumber, zEnsureOrganNumber);
	}
}
