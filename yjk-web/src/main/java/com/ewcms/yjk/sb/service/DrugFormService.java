package com.ewcms.yjk.sb.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.user.entity.User;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.repository.DrugFormRepository;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.entity.SpecialRule;
import com.ewcms.yjk.zd.commonname.service.CommonNameContentsService;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.ewcms.yjk.zd.commonname.service.HospitalContentsService;
import com.ewcms.yjk.zd.commonname.service.SpecialRuleService;

/**
 * @author zhoudongchu
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
	
	@Autowired
	private SpecialRuleService specialRuleService;

	private DrugFormRepository getDrugFormRepository() {
		return (DrugFormRepository) baseRepository;
	}
	/**
	 * 新药填写，满足一品两规的才能填写入库
	 * 
	 */
	public String drugDeclare(User user, DrugForm drugForm) {
		CommonNameContents vo = drugForm.getCommonNameContents();
		String isDeclareLimt = isDeclareUpperLimt(vo.getId());
		if (isDeclareLimt.equals("false")) {
			drugForm.setUserId(user.getId());
			drugForm = baseRepository.save(drugForm);
		}
		return isDeclareLimt;
	}
	/**
	 * 批量对填写新药进行申报，同时要满足一品量规和申报上限的才可入库
	 * 
	 */
	public String saveDeclareSubmit(List<Long> selections) {
		StringBuffer noDeclareCommonName = new StringBuffer();
		if (selections != null && !selections.isEmpty()) {
			for (Long id : selections) {
				DrugForm drugForm = findOne(id);
				if(!drugForm.getDeclared()){
					if (isDeclareUpperLimt(drugForm.getCommonNameContents().getId()).equals("false")
							&& !isDeclareTotalUpperLimt(drugForm.getUserId())) {
						drugForm.setDeclared(Boolean.TRUE);
						drugForm.setAuditStatus(AuditStatusEnum.init);
						drugForm.setDeclareDate(new Date(Calendar.getInstance().getTime().getTime()));
						super.save(drugForm);
					} else {
						noDeclareCommonName.append(drugForm.getCommonNameContents().getCommon().getCommonName() + "|");
					}
				}
			}
		}
		return noDeclareCommonName.toString();
	}
	/**
	 * 批量撤销还未初审的申报药品
	 * 
	 */
	public void saveDeclareCancel(List<Long> selections) {
		if (selections != null && !selections.isEmpty()) {
			for (Long id : selections) {
				DrugForm drugForm = findOne(id);
				if (drugForm.getAuditStatus() == AuditStatusEnum.init) {
					drugForm.setDeclared(Boolean.FALSE);
					drugForm.setAuditStatus(AuditStatusEnum.nodeclare);
					drugForm.setDeclareDate(null);
					super.save(drugForm);
				}
			}
		}
	}
	
	/**
	 * 查询未申报的新药
	 * 
	 */
	public List<DrugForm> findByUserIdAndDeclaredFalse(Long userId) {
		return getDrugFormRepository().findByUserIdAndDeclaredFalse(userId);
	}
	/**
	 * 查询未审核的申报的新药
	 * 
	 */
	public List<DrugForm> findByUserIdAndAuditStatus(Long userId, AuditStatusEnum auditStatus) {
		return getDrugFormRepository().findByUserIdAndAuditStatus(userId, auditStatus);
	}

	/**
	 * 新药初审
	 * 
	 */
	public void initAudit(List<Long> selections,Boolean isAuditPassed,String remark){
		for(Long drugFormId:selections){
			DrugForm drugForm = findOne(drugFormId);
			if(isAuditPassed){
				drugForm.setAuditStatus(AuditStatusEnum.passed);
			}else{
				drugForm.setAuditStatus(AuditStatusEnum.un_passed);
			}
			drugForm.setRemark(remark);
			super.save(drugForm);
		}
	}
	/**
	 * 判断当前申报新药是否超过上限
	 * 
	 * @param commonNameContentsId
	 * @return
	 */
	private String isDeclareUpperLimt(Long commonNameContentsId) {
		String isDeclareLimt = "false";
		if (commonNameContentsId != null) {// 申报目录编号存在
			// 获取新药申报的通用名对象
			CommonNameContents vo = commonNameContentsService.findOne(commonNameContentsId);
			// 根据编号、用药途径、药品种类反查所有该匹配的通用名集,一品两规
			List<CommonName> commonNameList = commonNameService.findByNumberAndAdministrationIdAndDrugCategory(vo.getCommon().getNumber(), vo.getCommon().getAdministration().getId(), vo.getCommon().getDrugCategory());
			
			//查询系统参数的限数
			Long maxNumber = 0L;
			SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
			if (EmptyUtil.isNotNull(systemParameter)) {
				maxNumber = systemParameter.getDeclarationLimt();
			}
			
			// 根据通用名集找院目录在该通用名集中的记录集
			int existNumber = 0;
			List<HospitalContents> hospitalContentsList;
			for (CommonName commonName : commonNameList) {
				hospitalContentsList = hospitalContentsService.findByCommonIdAndDeletedFalse(commonName.getId());
				if (hospitalContentsList != null && hospitalContentsList.size() > 0)
					existNumber += hospitalContentsList.size();
			}
			
			if (existNumber < maxNumber) {
				//查询特殊规则中的随数与特殊规则对象
				Map<Long, SpecialRule> map = specialRuleService.findMaxLimitNumber(vo.getCommon().getId());
				
				SpecialRule specialRule = null;
				
				for(Long key : map.keySet()) {
					maxNumber = key;
					specialRule = map.get(key);
				}
				
				if (EmptyUtil.isNotNull(specialRule)) {
					commonNameList.clear();
					commonNameList = specialRule.getCommonNames();
					
					existNumber = 0;
					for (CommonName commonName : commonNameList) {
						hospitalContentsList = hospitalContentsService.findByCommonIdAndDeletedFalse(commonName.getId());
						if (hospitalContentsList != null && hospitalContentsList.size() > 0)
							existNumber += hospitalContentsList.size();
					}
					
					if (existNumber >= maxNumber) {
						isDeclareLimt = "申报药品达到特殊药品最高" + maxNumber +"的限制，不能申报";
					}
				}
			}else{
				isDeclareLimt = "申报药品达到一品两规最高" + maxNumber +"的限制，不能申报";
			}
		}else{
			isDeclareLimt = "申报药品不在大目录范围，不能申报";
		}
		
		return isDeclareLimt;
	}

	/**
	 * 判断医生申报新药数量是否超过上限
	 * 
	 * @param commonNameContentsId
	 * @return
	 */
	private Boolean isDeclareTotalUpperLimt(Long userId) {
		SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
		if (systemParameter == null) {
			return Boolean.TRUE;
		}
		Long declareTotal = getDrugFormRepository().findDeclareTotalByUserId(userId,
				systemParameter.getApplyStartDate(), systemParameter.getApplyEndDate());

		if (declareTotal < systemParameter.getDeclareTotalLimt()) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
}
