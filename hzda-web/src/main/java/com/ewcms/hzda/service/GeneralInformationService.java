package com.ewcms.hzda.service;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.repository.GeneralInformationRepository;

@Service
public class GeneralInformationService extends BaseService<GeneralInformation, Long> {
	
	private GeneralInformationRepository getGeneralInformationRepository() {
		return (GeneralInformationRepository) baseRepository;
	}
	
	@Autowired
	private FollowupTimeService followupTimeService;
	
	public Page<GeneralInformation> findFollowupTime(Long userId, Pageable pageable){
		Set<Long> generalInformationIds = followupTimeService.findByLastMonth();
		
		if (EmptyUtil.isCollectionNotEmpty(generalInformationIds)) {
			if (EmptyUtil.isNotNull(userId)) {
				return getGeneralInformationRepository().findByUserIdAndIdIn(userId, generalInformationIds, pageable);
			} else {
				return getGeneralInformationRepository().findByIdIn(generalInformationIds, pageable);
			}
			
		}
		return new PageImpl<GeneralInformation>(new ArrayList<GeneralInformation>());
	}
	
}
