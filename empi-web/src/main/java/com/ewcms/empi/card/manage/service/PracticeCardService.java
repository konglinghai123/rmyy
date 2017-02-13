package com.ewcms.empi.card.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ewcms.common.service.BaseService;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.repository.PracticeCardRepository;

/**
 *@author zhoudongchu
 */
@Service
public class PracticeCardService extends BaseService<PracticeCard, Long> {
	private PracticeCardRepository getPracticeCardRepository() {
		return (PracticeCardRepository) baseRepository;
	}
	
	@Autowired
	private PatientBaseInfoService patientBaseInfoService;

	public PracticeCard findByPracticeNo(String practiceNo){
		return getPracticeCardRepository().findByPracticeNo(practiceNo);
	}   
}
