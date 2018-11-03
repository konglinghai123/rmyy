package com.ewcms.yjk.sb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.user.entity.User;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.repository.DrugFormRepository;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
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
	
	@Autowired
	private SystemParameterService systemParameterService;
	
    private DrugFormRepository getDrugFormRepository() {
        return (DrugFormRepository) baseRepository;
    }
	
	public DrugForm drugDeclare(User user, CommonNameContents vo){
		DrugForm drugForm = null;
		if(isDeclareUpperList(vo.getId())){	
			drugForm = new DrugForm();
			drugForm.setUserId(user.getId());
			drugForm.setCommonNameContents(vo);
			drugForm = baseRepository.save(drugForm);
		}
		return drugForm;
	}
	
	public String saveDeclareSubmit(List<Long> selections){
		StringBuffer noDeclareCommonName = new StringBuffer();
		if (selections != null && !selections.isEmpty()){
			for(Long id:selections){
				DrugForm drugForm = findOne(id);
				if(isDeclareUpperList(drugForm.getCommonNameContents().getId())){
					drugForm.setDeclared(Boolean.TRUE);
					drugForm.setAuditStatus(AuditStatusEnum.init);
					super.save(drugForm);
				}else{
					noDeclareCommonName.append(drugForm.getCommonNameContents().getCommon().getCommonName()+"|");
				}
			}
		}
		return noDeclareCommonName.toString();
	}
	
	public void saveDeclareCancel(List<Long> selections){
		if (selections != null && !selections.isEmpty()){
			for(Long id:selections){
				DrugForm drugForm = findOne(id);
				if(drugForm.getAuditStatus()==AuditStatusEnum.init){
					drugForm.setDeclared(Boolean.FALSE);
					drugForm.setAuditStatus(AuditStatusEnum.nodeclare);
				}
				super.save(drugForm);
			}
		}
	}
	
	public List<DrugForm> findByUserIdAndDeclaredFalse(Long userId){
		return getDrugFormRepository().findByUserIdAndDeclaredFalse(userId);
	}
	
	public List<DrugForm> findByUserIdAndAuditStatus(Long userId,AuditStatusEnum auditStatus){
		return getDrugFormRepository().findByUserIdAndAuditStatus(userId, auditStatus);
	}
	
	/**
	 * 判断当前申报新药是否超过上限
	 * @param commonNameContentsId
	 * @return
	 */
	private Boolean isDeclareUpperList(Long commonNameContentsId){
		if(commonNameContentsId!=null){//申报目录编号存在
			//获取新药申报的通用名对象
			CommonNameContents vo = commonNameContentsService.findOne(commonNameContentsId);
			//根据编号、用药途径、药品种类反查所有该匹配的通用名集
			List<CommonName> commonNameList = commonNameService.findByNumberAndAdministrationIdAndDrugCategory(vo.getCommon().getNumber(),vo.getCommon().getAdministration().getId(),vo.getCommon().getDrugCategory());
			//根据通用名集找院目录在该通用名集中的记录集
			
			int existNumber=0;
			List<HospitalContents> hospitalContentsList;
			for(CommonName commonName:commonNameList){
				hospitalContentsList = hospitalContentsService.findByCommonIdAndDeletedFalse(commonName.getId());
				if(hospitalContentsList != null&&hospitalContentsList.size()>0)existNumber += hospitalContentsList.size();
			}
			SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
			if(systemParameter != null){
				if(existNumber < systemParameter.getDeclarationLimt()){//申报新药的在院药品目录没有超过限数 
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
}
