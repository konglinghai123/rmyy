package com.ewcms.hzda.zd.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.hzda.zd.entity.CertificateType;

/**
 * 
 * @author wuzhijun
 *
 */
public interface CertificateTypeRepository extends BaseRepository<CertificateType, Long> {

	public List<CertificateType> findByName(String name);
}
