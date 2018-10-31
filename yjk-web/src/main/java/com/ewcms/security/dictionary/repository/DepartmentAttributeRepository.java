package com.ewcms.security.dictionary.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.dictionary.entity.DepartmentAttribute;

public interface DepartmentAttributeRepository extends BaseRepository<DepartmentAttribute, Long> {

	DepartmentAttribute findByName(String name);
}
