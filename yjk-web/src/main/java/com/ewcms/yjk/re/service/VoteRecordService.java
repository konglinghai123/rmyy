package com.ewcms.yjk.re.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.entity.VoteSerialProcess;
import com.ewcms.yjk.re.repository.VoteRecordRepository;
import com.ewcms.yjk.sb.service.DrugFormService;

@Service
public class VoteRecordService extends BaseService<VoteRecord, Long> {
	@Autowired
	private DrugFormService drugFormService;
	@Autowired
	private VoteSerialProcessService voteSerialProcessService;
	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private ReviewProcessService reviewProcessService;
	
	private VoteRecordRepository getVoteRecordRepository() {
		return (VoteRecordRepository) baseRepository;
	}

	public String ExpertStartVoteAddCommonName(Long userId){

		VoteSerialProcess vo = new VoteSerialProcess();
		String voteStartResult="";
		vo.setSubmitted(Boolean.FALSE);
		vo.setUserId(userId);
		ReviewMain reviewMainEnable = reviewMainService.findByEnabledTrue();
		if(reviewMainEnable == null)return "评审还未启动！";
		vo.setReviewMainId(reviewMainEnable.getId()); 
		ReviewProcess reviewProcess = reviewProcessService.findByReviewBaseRuleRuleNameAndReviewMainId("addCommonName", reviewMainEnable.getId());
		if(reviewProcess == null)return "新增通用名评审流程未找到！";
		vo.setReviewProcessId(reviewProcess.getId()); 
		
		
		return voteStartResult;
	}
	
	
}
