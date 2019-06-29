package com.ewcms.yjk.re.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.ReviewProcessRecord;
import com.ewcms.yjk.re.repository.ReviewProcessRepository;
import com.ewcms.yjk.re.zd.entity.DisplayColumn;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;
import com.ewcms.yjk.re.zd.service.ReviewBaseRuleService;
import com.google.common.collect.Lists;

@Service
public class ReviewProcessService extends BaseSequenceMovableService<ReviewProcess, Long>{

	public ReviewProcessService() {
		super(1);
	}
	
	@Autowired
	private ReviewProcessRecordService reviewProcessRecordService;
	@Autowired
	private ReviewBaseRuleService reviewBaseRuleService;
	
	private ReviewProcessRepository getReviewProcessRepository() {
		return (ReviewProcessRepository) baseRepository;
	}
	
	public ReviewProcess findByReviewMainAndReviewBaseRule(ReviewMain reviewMain, ReviewBaseRule reviewBaseRule) {
		return getReviewProcessRepository().findByReviewMainAndReviewBaseRule(reviewMain, reviewBaseRule);
	}
	
	public ReviewProcess findByReviewMainIdAndReviewBaseRuleId(Long reviewMainId, Long reviewBaseRuleId) {
		return getReviewProcessRepository().findByReviewMainIdAndReviewBaseRuleId(reviewMainId, reviewBaseRuleId);
	}
	
	public ReviewProcess findByReviewBaseRuleRuleNameAndReviewMainId(String ruleName, Long reviewMainId) {
		return getReviewProcessRepository().findByReviewBaseRuleRuleNameAndReviewMainId(ruleName, reviewMainId);
	}
	
	public ReviewProcess findCurrentReviewProcess(Long reviewMainId){
		List<ReviewProcess> reviewProcesses = getReviewProcessRepository().findByReviewMainIdAndFinishedFalseOrderByWeightAsc(reviewMainId);
		if (EmptyUtil.isCollectionNotEmpty(reviewProcesses)) {
			return reviewProcesses.get(0);
		} else {
			return null;
		}
	}
	
	public ReviewProcess findCurrentReviewProcessNext(Long reviewMainId){
		List<ReviewProcess> reviewProcesses = getReviewProcessRepository().findByReviewMainIdAndFinishedFalseOrderByWeightAsc(reviewMainId);
		if (EmptyUtil.isCollectionNotEmpty(reviewProcesses) && reviewProcesses.size() >= 2) {
			return reviewProcesses.get(1);
		} else {
			return null;
		}
	}
	
	@Override
	@Deprecated
	public ReviewProcess save(ReviewProcess m) {
		throw new RuntimeException("discarded method");
	}
	
	public ReviewProcess save(ReviewProcess m, Long opUserId) {
		ReviewProcess dbReviewProcess = findByReviewMainAndReviewBaseRule(m.getReviewMain(), m.getReviewBaseRule());
		
		ReviewProcess newReviewProcess = null;
		if (dbReviewProcess == null) {
			ReviewBaseRule dbReviewBaseRule = reviewBaseRuleService.findOne(m.getReviewBaseRule().getId());
			
			if (EmptyUtil.isCollectionNotEmpty(dbReviewBaseRule.getDisplayColumns())) {
				List<DisplayColumn> displayColumns = Lists.newArrayList();
				for (DisplayColumn displayColumn : dbReviewBaseRule.getDisplayColumns()) {
					displayColumns.add(displayColumn);
				}
				m.setDisplayColumns(displayColumns);
			}
			
			newReviewProcess = super.save(m);
			
			ReviewProcessRecord reviewProcessRecord = new ReviewProcessRecord(newReviewProcess.getId(), opUserId, "新增 评审流程");
			reviewProcessRecordService.save(reviewProcessRecord);
		}
		return newReviewProcess;
	}
	
	@Override
	@Deprecated
	public ReviewProcess update(ReviewProcess m) {
		throw new RuntimeException("discarded method");
	}
	
	public ReviewProcess update(ReviewProcess m, Long opUserId, String reason) {
		ReviewProcess dbReviewProcess = findByReviewMainAndReviewBaseRule(m.getReviewMain(), m.getReviewBaseRule());
		
		if (dbReviewProcess != null && m.getId().equals(dbReviewProcess.getId())) {
			ReviewProcessRecord reviewProcessRecord = new ReviewProcessRecord(m.getId(), opUserId, reason + " 评审流程");
			reviewProcessRecordService.save(reviewProcessRecord);
			
			return super.update(m);
		} else {
			return null;
		}
	}
	
	@Override
	@Deprecated
	public void down(Long fromId, Long toId) {
		throw new RuntimeException("discarded method");
	}
	
	public void down(Long fromId, Long toId, Long opUserId) {
		super.down(fromId, toId);
		
		ReviewProcessRecord fromReviewProcessRecord = new ReviewProcessRecord(fromId, opUserId, "互换 与[" + toId + "]编号的流程进行互换");
		ReviewProcessRecord toReviewProcessRecord = new ReviewProcessRecord(toId, opUserId, "互换 与[" + fromId + "]编号的流程进行互换");
		reviewProcessRecordService.save(fromReviewProcessRecord);
		reviewProcessRecordService.save(toReviewProcessRecord);
	}
	
	public void changeStatus(Long opUserId, List<Long> ids, Boolean newStatus, String reason) {
		for (Long id : ids) {
			ReviewProcess reviewProcess = findOne(id);
			if (reviewProcess.getFinished() != newStatus) {
				reviewProcess.setFinished(newStatus);
				super.update(reviewProcess);
				
				ReviewProcessRecord reviewProcessRecord = new ReviewProcessRecord(reviewProcess.getId(), opUserId, "强制 " + (newStatus ? "关闭" : "开启") + ", 原因：" + reason);
				reviewProcessRecordService.save(reviewProcessRecord);
			}
		}
	}
}
