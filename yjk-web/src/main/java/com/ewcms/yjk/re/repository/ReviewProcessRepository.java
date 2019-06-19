package com.ewcms.yjk.re.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;

public interface ReviewProcessRepository extends BaseRepository<ReviewProcess, Long>{
	
	ReviewProcess findByReviewMainAndReviewBaseRule(ReviewMain reviewMain, ReviewBaseRule reviewBaseRule);
	
	ReviewProcess findByReviewMainIdAndReviewBaseRuleId(Long reviewMainId, Long reviewBaseRuleId);
	
	ReviewProcess findByReviewBaseRuleRuleNameAndReviewMainId(String ruleName, Long reviewMainId);
}
