package com.ewcms.security.dictionary.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.dictionary.entity.Technical;

public interface TechnicalRepository extends BaseRepository<Technical, Long>{

	Technical findByNameAndLevel(String name, String level);
}
