package com.ewcms.yjk.zd.commonname.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.repository.HospitalContentsRepository;

/**
 *@author zhoudongchu
 */
@Service
public class HospitalContentsService extends BaseService<HospitalContents, Long> {
	@Autowired
	private CommonNameContentsService commonNameContentsService;
	@Autowired
	private CommonNameService commonNameService;
	
    private HospitalContentsRepository getHospitalContentsRepository() {
        return (HospitalContentsRepository) baseRepository;
    }
	
	public List<HospitalContents> findByExtractCommonNameAndDeletedFalse(String extractCommonName){
		return getHospitalContentsRepository().findByExtractCommonNameAndDeletedFalse(extractCommonName);
	}
	
	public List<HospitalContents> matchByCommonNameContentsId(Long commonNameContentsId){
		CommonNameContents commonNameContentsvo = commonNameContentsService.findOne(commonNameContentsId);
		String  matchingNumber = commonNameContentsvo.getCommon().getMatchingNumber();
		List<String> commonNameList = commonNameService.findByMatchingNumber(matchingNumber);
		List<HospitalContents> hospitalContentsList = new ArrayList<HospitalContents>();
		
		for(String commonName:commonNameList){
			hospitalContentsList.addAll(findByExtractCommonNameAndDeletedFalse(commonName));
		}
		
		return hospitalContentsList;
	}
	
	@Override
	public HospitalContents update(HospitalContents m) {
		m.setUpdateDate(new Date(Calendar.getInstance().getTime().getTime()));
		return super.update(m);
	}
}
