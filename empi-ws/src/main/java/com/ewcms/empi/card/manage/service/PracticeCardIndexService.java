package com.ewcms.empi.card.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.card.manage.entity.PracticeCardIndex;
import com.ewcms.empi.card.manage.repository.PracticeCardIndexRepository;
import com.ewcms.empi.system.service.ParameterSetService;

/**
 *
 * @author wu_zhijun
 */
@Service
public class PracticeCardIndexService extends BaseService<PracticeCardIndex, String>{

	@Autowired
	private ParameterSetService parameterSetService;
	
	private PracticeCardIndexRepository getPracticeCardIndexRepository(){
		return (PracticeCardIndexRepository) baseRepository;
	}
	
	public PracticeCardIndex findByIdAndDeleted(String id, Boolean deleted){
		return getPracticeCardIndexRepository().findByIdAndDeleted(id, deleted);
	}
}
