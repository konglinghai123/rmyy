package com.ewcms.security.dictionary.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.dictionary.entity.Profession;

public interface ProfessionRepository extends BaseRepository<Profession, Long> {

	Profession findByName(String name);
}
