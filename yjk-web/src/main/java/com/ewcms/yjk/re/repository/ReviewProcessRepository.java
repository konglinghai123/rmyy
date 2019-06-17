package com.ewcms.yjk.re.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;

public interface ReviewProcessRepository extends BaseRepository<ReviewProcess, Long>{
	ReviewProcess findByReviewMainAndId(ReviewMain reviewMain, Long reviewExpertId);
}
