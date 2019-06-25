package com.ewcms.yjk.re.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.entity.VoteTypeEnum;
import com.ewcms.yjk.re.repository.VoteRecordRepository;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.service.DrugFormService;
import com.google.common.collect.Maps;

@Service
public class VoteRecordService extends BaseService<VoteRecord, Long> {
	@Autowired
	private DrugFormService drugFormService;
	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private ReviewProcessService reviewProcessService;
	
	private VoteRecordRepository getVoteRecordRepository() {
		return (VoteRecordRepository) baseRepository;
	}
	
	public Boolean expertSubmitCurrentReview(Long userId, Long reviewProcessId){
		if(getVoteRecordRepository().countByUserIdAndReviewProcessIdAndSubmittedTrue(userId, reviewProcessId) == 0){
			return Boolean.FALSE;
		}else{
			return Boolean.TRUE;
		}
	}
	
	public VoteRecord findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewMainId, Long drugFormId, Long reviewProcessId) {
		return getVoteRecordRepository().findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(userId, reviewMainId, drugFormId, reviewProcessId);
	}
	
	//专家投票评审监控
	public Map<DrugForm, Map<String, String>> findUserVote(Long voteUserId){
		//查询评审是否开启，并检验是否关联申报系统
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null || reviewMain.getSystemParameter() == null) return Maps.newHashMap();
		
		//评审流程是否存在
		List<ReviewProcess> reviewProcesses = reviewMain.getReviewProcesses();
		if (EmptyUtil.isCollectionEmpty(reviewProcesses)) return Maps.newHashMap();
		
		//获取申报系统编号，并查询本次初审所有通过的药品记录集
		List<DrugForm> drugForms = drugFormService.findByAuditStatusAndSystemParameterIdOrderByIdAsc(AuditStatusEnum.passed, reviewMain.getSystemParameter().getId());
		
		Map<DrugForm, Map<String, String>> maps = Maps.newHashMap();
		for (DrugForm drugForm : drugForms) {
			Map<String, String> processMap = Maps.newHashMap();
			for (ReviewProcess reviewProcess : reviewProcesses) {
				VoteRecord voteRecord = findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(voteUserId, reviewMain.getId(), drugForm.getId(), reviewProcess.getId());
				String voteType = "";
				if (voteRecord != null) voteType = voteRecord.getVoteTypeEnum().getInfo();
				processMap.put(reviewProcess.getReviewBaseRule().getRuleCnName(), voteType);
			}
			maps.put(drugForm, processMap);
		}
		return maps;
	}

	/**
	 * 启动专家新增通用名投票
	 * 
	 */
	public String expertStartVoteAddCommonName(Long userId,Long currentReviewProcessId){
		ReviewMain reviewMainEnable = reviewMainService.findByEnabledTrue();
		if(reviewMainEnable == null)return "评审还未启动！";

		if(getVoteRecordRepository().countByUserIdAndReviewProcessId(userId, currentReviewProcessId).intValue()  == 0){//初次进入投票，初始化需要投票的申报新曾通用名的药品
			List<DrugForm> validDrugFormList = drugFormService.findByAuditStatusAndSystemParameterIdAndDeclareCategoryAndReviewedFalseOrderByIdAsc(AuditStatusEnum.passed, reviewMainEnable.getSystemParameter().getId(), "新增通用名");
			for(DrugForm drugForm:validDrugFormList){
				VoteRecord vo = new VoteRecord();
				vo.setSubmitted(Boolean.FALSE);
				vo.setUserId(userId);
				vo.setReviewMainId(reviewMainEnable.getId()); 
				vo.setReviewProcessId(currentReviewProcessId);
				vo.setDrugForm(drugForm);
				getVoteRecordRepository().save(vo);
			}
		}
		
		return currentReviewProcessId.toString();
	}
	
	/**
	 * 保存专家新增通用名投票
	 * 
	 */
	public void saveVote(Long userId,List<String> selections){
		for(String voteReult:selections){
			String[] voteResultArr = voteReult.split("@");
			if(voteResultArr.length == 2){
				VoteRecord vo = getVoteRecordRepository().findOne(Long.parseLong(voteResultArr[0]));
				if(vo != null && vo.getUserId().equals(userId)){
					vo.setVoteTypeEnum(VoteTypeEnum.valueOf(voteResultArr[1]));
					super.save(vo);
				}
			}
		}
	}
	
	/**
	 * 提交专家新增通用名投票
	 * 
	 */
	public void submitVote(Long userId,List<String> selections){
		for(String voteReult:selections){
			String[] voteResultArr = voteReult.split("@");
			if(voteResultArr.length == 2){
				VoteRecord vo = getVoteRecordRepository().findOne(Long.parseLong(voteResultArr[0]));
				if(vo != null && vo.getUserId().equals(userId)&&!vo.getSubmitted()){
					vo.setVoteTypeEnum(VoteTypeEnum.valueOf(voteResultArr[1]));
					vo.setSubmitted(Boolean.TRUE);
					vo.setSubmittDate(new Date(Calendar.getInstance().getTime().getTime()));
					super.save(vo);
				}
			}
		}
	}
}
