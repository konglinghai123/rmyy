package com.ewcms.yjk.sb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.user.entity.User;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.service.CommonNameContentsService;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.ewcms.yjk.zd.commonname.service.HospitalContentsService;

/**
 *@author zhoudongchu
 */
@Service
public class DrugFormService extends BaseService<DrugForm, Long> {
	@Autowired
	private CommonNameContentsService commonNameContentsService;
	
	@Autowired
	private CommonNameService commonNameService;
	
	@Autowired
	private HospitalContentsService hospitalContentsService;
	
//    private DrugFormRepository getDrugFormRepository() {
//        return (DrugFormRepository) baseRepository;
//    }
//	
	public DrugForm drugDeclare(User user, CommonNameContents vo){
		DrugForm drugForm = null;
		Long commonNameContentsId = vo.getId();
		if(commonNameContentsId!=null){//申报目录编号存在
			//获取新药申报的通用名匹配编号
			vo = commonNameContentsService.findOne(commonNameContentsId);
			String  matchingNumber = vo.getCommon().getMatchingNumber();
			//根据匹配编号反查所有该匹配编号的通用名集
			List<String> commonNameList = commonNameService.findByMatchingNumber(matchingNumber);
			//根据通用名集找院目录提取通用名在该通用名集中的记录集
			int existNumber=0;
			List<HospitalContents> hospitalContentsList;
			
			
			for(String commonName:commonNameList){
				hospitalContentsList = hospitalContentsService.findByExtractCommonNameAndDeletedFalse(commonName);
				if(hospitalContentsList != null&&hospitalContentsList.size()>0)existNumber += hospitalContentsList.size();
			}
			
			if(existNumber <= 2){//申报新药的在院药品目录限数为2，超过限数2不能申报
				drugForm = new DrugForm();
				drugForm.setUserId(user.getId());
				drugForm.setCommonNameContents(vo);
				drugForm = baseRepository.save(drugForm);
			}
		}
		return drugForm;
	}
}
