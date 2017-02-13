package com.ewcms.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.repository.PracticeCardRepository;

/**
 *
 * @author wu_zhijun
 */
@Service
public class PracticeCardService extends BaseService<PracticeCard, Long>{
	
	private PracticeCardRepository getPracticeCardRepository() {
		return (PracticeCardRepository) baseRepository;
	}
	
	public Long findPatientIdByPracticeNo(String practiceNo, Boolean deleted){
		return getPracticeCardRepository().findPatientIdByPracticeNo(practiceNo, deleted);
	}
}
