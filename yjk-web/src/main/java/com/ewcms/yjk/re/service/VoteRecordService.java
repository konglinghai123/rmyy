package com.ewcms.yjk.re.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteRecord;
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

	public String ExpertStartVoteAddCommonName(Long userId){
		ReviewMain reviewMainEnable = reviewMainService.findByEnabledTrue();
		if(reviewMainEnable == null)return "评审还未启动！";
		ReviewProcess reviewProcess = reviewProcessService.findByReviewBaseRuleRuleNameAndReviewMainId("addCommonName", reviewMainEnable.getId());
		if(reviewProcess == null)return "新增通用名评审流程未找到！";
		
		List<DrugForm> validDrugFormList = drugFormService.findByAuditStatusAndSystemParameterIdAndDeclareCategoryAndReviewedFalse(AuditStatusEnum.passed, reviewMainEnable.getSystemParameter().getId(), "新增通用名");
		for(DrugForm drugForm:validDrugFormList){
			VoteRecord vo = new VoteRecord();
			vo.setSubmitted(Boolean.FALSE);
			vo.setUserId(userId);
			vo.setReviewMainId(reviewMainEnable.getId()); 
			vo.setReviewProcessId(reviewProcess.getId());
			vo.setDrugForm(drugForm);
			getVoteRecordRepository().save(vo);
		}
		return reviewProcess.getId().toString();
	}
	
}
