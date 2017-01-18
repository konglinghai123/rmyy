package com.ewcms.card.manage.repository;

import com.ewcms.card.manage.entity.Nation;
import com.ewcms.common.repository.BaseRepository;

/**
 *@author zhoudongchu
 */
public interface NationRepository extends BaseRepository<Nation, Long> {
	Nation findByName(String name);
}
