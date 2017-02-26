package com.ewcms.trace.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Reflections;
import com.ewcms.trace.entity.Trace;
import com.ewcms.trace.repository.TraceRepository;

/**
 *
 * @author wu_zhijun
 */
@Service
public class TraceService extends BaseService<Trace, String>{

	private TraceRepository getTraceRepository() {
        return (TraceRepository) baseRepository;
    }
	
	public byte[] lookImage(String cpid, String field){
		Trace trace = getTraceRepository().findByCpid(cpid);
		return (byte[]) Reflections.getFieldValue(trace, field);
	}
}
