package com.ewcms.hzda.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.repository.GeneralInformationRepository;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;
import com.google.common.collect.Sets;

@Service
public class GeneralInformationService extends BaseService<GeneralInformation, Long> {

	private GeneralInformationRepository getGeneralInformationRepository() {
		return (GeneralInformationRepository) baseRepository;
	}
	
	public Page<GeneralInformation> findByGeneralInformation(User user, Pageable pageable){
		if (user.getAdmin()) {
			return findAll(pageable);
		} else {
			Set<Long> organizationIds = Sets.newHashSet();
			
			for (UserOrganizationJob o : user.getOrganizationJobs()) {
	            Long organizationId = o.getOrganizationId();
	            organizationIds.add(organizationId);
	        }
			return getGeneralInformationRepository().findByOrganizationIdIn(organizationIds, pageable);
		}
	}
	
}
