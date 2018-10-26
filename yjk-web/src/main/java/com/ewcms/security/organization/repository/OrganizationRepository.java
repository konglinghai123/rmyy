package com.ewcms.security.organization.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.organization.entity.Organization;

/**
 * @author wu_zhijun
 */
public interface OrganizationRepository extends BaseRepository<Organization, Long> {
	
	List<Organization> findByName(String name);
}
