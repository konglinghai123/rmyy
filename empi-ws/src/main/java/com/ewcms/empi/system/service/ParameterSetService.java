package com.ewcms.empi.system.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.system.entity.ParameterSet;

/**
 *
 * @author wu_zhijun
 */
@Service
public class ParameterSetService extends BaseService<ParameterSet, String>{
	
	private final static String PRACTICE_ID_PARAMETER = "001";
	
	private Integer practice_id_length = 20;
	
	public Integer findPracticeIdLengthValue(){
		ParameterSet parameterSet = findOne(PRACTICE_ID_PARAMETER);
		if (parameterSet != null) {
			try{
				practice_id_length = Integer.getInteger(parameterSet.getVariableValue());
			} catch (Exception e){}
		}
		return practice_id_length;
	}
}
