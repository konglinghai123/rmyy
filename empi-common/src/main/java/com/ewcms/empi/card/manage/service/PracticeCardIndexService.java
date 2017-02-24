package com.ewcms.empi.card.manage.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.card.manage.entity.PracticeCardIndex;
import com.ewcms.empi.card.manage.repository.PracticeCardIndexRepository;

/**
 *
 * @author wu_zhijun
 */
@Service
public class PracticeCardIndexService extends BaseService<PracticeCardIndex, String>{

	private PracticeCardIndexRepository getPracticeCardIndexRepository(){
		return (PracticeCardIndexRepository) baseRepository;
	}
	
	public PracticeCardIndex findByIdAndDeleted(String id, Boolean deleted){
		return getPracticeCardIndexRepository().findByIdAndDeleted(id, deleted);
	}
}
