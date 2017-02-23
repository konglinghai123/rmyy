package com.ewcms.empi.card.manage.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.card.manage.entity.PracticeCard;

/**
 *@author zhoudongchu
 */
public interface PracticeCardRepository extends BaseRepository<PracticeCard, Long> {
	PracticeCard findByPracticeNoAndDeleted(String practiceNo,Boolean deleted);
}
