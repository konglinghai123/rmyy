package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.Administration;

public interface AdministrationRepository extends BaseRepository<Administration, Long>{

	public List<Administration> findByName(String name);
}
