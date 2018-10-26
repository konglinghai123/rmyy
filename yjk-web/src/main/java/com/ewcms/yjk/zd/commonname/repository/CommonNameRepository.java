package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.CommonName;

/**
 *@author zhoudongchu
 */
public interface CommonNameRepository extends BaseRepository<CommonName, Long> {
	@Query("from CommonName where (spell like %?1% or spellSimplify like %?1%) and deleted is false")
	List<CommonName> findCommonNameBySpell(String spell);
	
	List<CommonName> findByCommonName(String commonName);
}
