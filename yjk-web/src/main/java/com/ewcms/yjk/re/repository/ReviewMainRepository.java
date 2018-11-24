package com.ewcms.yjk.re.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.ReviewMain;

public interface ReviewMainRepository extends BaseRepository<ReviewMain, Long>{

	ReviewMain findByName(String name);
	
	ReviewMain findByEnabledTrue();
}
