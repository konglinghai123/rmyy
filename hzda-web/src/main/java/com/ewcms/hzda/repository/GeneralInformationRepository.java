package com.ewcms.hzda.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.entity.GeneralInformation;

public interface GeneralInformationRepository extends BaseRepository<GeneralInformation, Long> {

	Page<GeneralInformation> findByOrganizationIdIn(Set<Long> organizationIds, Pageable pageable);
}
