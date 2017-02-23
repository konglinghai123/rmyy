package com.ewcms.empi.card.manage.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.card.manage.entity.PracticeCardIndex;

/**
 *
 * @author wu_zhijun
 */
public interface PracticeCardIndexRepository extends BaseRepository<PracticeCardIndex, String>{

	PracticeCardIndex findByIdAndDeleted(String id, Boolean deleted);
}
