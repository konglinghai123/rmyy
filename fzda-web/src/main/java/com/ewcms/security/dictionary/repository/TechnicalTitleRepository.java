package com.ewcms.security.dictionary.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.dictionary.entity.TechnicalTitle;

public interface TechnicalTitleRepository extends BaseRepository<TechnicalTitle, Long>{

	TechnicalTitle findByName(String name);
}
