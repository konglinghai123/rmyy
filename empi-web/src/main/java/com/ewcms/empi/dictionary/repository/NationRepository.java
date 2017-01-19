package com.ewcms.empi.dictionary.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.dictionary.entity.Nation;

/**
 *@author zhoudongchu
 */
public interface NationRepository extends BaseRepository<Nation, Long> {
	Nation findByName(String name);
}
