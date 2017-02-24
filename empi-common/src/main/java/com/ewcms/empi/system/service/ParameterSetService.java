package com.ewcms.empi.system.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.empi.system.entity.ParameterSet;

/**
 *@author zhoudongchu
 */
@Service
public class ParameterSetService extends BaseService<ParameterSet, String> {

	public static final String FORMAT_PATIENT_ID_ID = "001";
	
	private Integer patientIdLength = 20;
	
	public Integer findPatientIdVariableValue(){
		ParameterSet parameterSet = findOne(FORMAT_PATIENT_ID_ID);
		if (parameterSet != null){
			try{
				patientIdLength = Integer.valueOf(parameterSet.getVariableValue());
			} catch (Exception e){}
		}
		return patientIdLength;
	}
}
