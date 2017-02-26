package com.ewcms.trace.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.trace.entity.Trace;

/**
 *
 * @author wu_zhijun
 */
public interface TraceRepository extends BaseRepository<Trace, String>{

	Trace findByCpid(String cpid);
}
