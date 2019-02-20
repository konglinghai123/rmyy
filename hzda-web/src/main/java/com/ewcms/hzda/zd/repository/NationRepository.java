package com.ewcms.hzda.zd.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.zd.entity.Nation;

/**
 * 
 * @author wuzhijun
 *
 */
public interface NationRepository extends BaseRepository<Nation, Long>{

	public List<Nation> findByName(String name);
}
