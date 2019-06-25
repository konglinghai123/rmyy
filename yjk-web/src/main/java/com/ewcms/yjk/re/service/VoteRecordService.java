package com.ewcms.yjk.re.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.entity.VoteTypeEnum;
import com.ewcms.yjk.re.repository.VoteRecordRepository;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.service.DrugFormService;

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
				if(vo != null && vo.getUserId().equals(userId)&&!vo.getSubmitted()){
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
