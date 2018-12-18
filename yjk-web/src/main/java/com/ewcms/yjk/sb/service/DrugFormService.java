package com.ewcms.yjk.sb.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.Reflections;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.entity.DrugFormCount;
import com.ewcms.yjk.sb.repository.DrugFormRepository;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.entity.SpecialRule;
import com.ewcms.yjk.zd.commonname.service.CommonNameContentsService;
import com.ewcms.yjk.zd.commonname.service.CommonNameRuleService;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.ewcms.yjk.zd.commonname.service.HospitalContentsService;
import com.ewcms.yjk.zd.commonname.service.SpecialRuleService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserOrganizationJobService userOrganizationJobService;
	@Autowired
	private CommonNameRuleService commonNameRuleService;
	
	private DrugFormRepository getDrugFormRepository() {
		return (DrugFormRepository) baseRepository;
	}
	/**
	 * 新药填写，满足一品两规和特殊药品规则的才能填写入库
	 * 
	 */
	public String drugDeclare(User user, DrugForm drugForm) {
		CommonNameContents vo = drugForm.getCommonNameContents();
		if(isRepeatDeclare(vo.getId())){//判断新药是否重复申报，如果新药是重复的申报，则终止申报
			return "该药已经被申报，不能重复申报";
		}
		
		String isDeclareLimt = isDeclareUpperLimt(vo.getId()).get("isDeclareLimt");
		if (isDeclareLimt.equals("false")) {//判断新药是否满足一品两规和特殊药，满足才可以申报
			SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
			drugForm.setUserId(user.getId());
			drugForm.setSystemParameterId(systemParameter.getId());
			drugForm = baseRepository.save(drugForm);
		}
		return isDeclareLimt;
	}
	
	/**
	 * 判断新药是否符合申报条件
	 * 
	 */
	public String isSatisfyDrugDeclare(Long commonNameContentsId){
		if(isRepeatDeclare(commonNameContentsId)){//判断是否重复申报，如果新药是重复的申报，则终止申报
			return "该药已经被申报，不能重复申报";
		}
		return isDeclareUpperLimt(commonNameContentsId).get("isDeclareLimt");
	}
	
	
	/**
	 * 批量对填写新药进行申报，同时要满足一品两规和申报上限、特殊药规则以及不重复的才可申报入库
	 * 
	 */
	public String saveDeclareSubmit(List<Long> selections) {
		StringBuffer noDeclareCommonName = new StringBuffer();
		if (selections != null && !selections.isEmpty()) {
			for (Long id : selections) {
				DrugForm drugForm = findOne(id);
				if(!drugForm.getDeclared()){
					Map<String,String> resulstMap = isDeclareUpperLimt(drugForm.getCommonNameContents().getId());
					String isDeclareLimt = resulstMap.get("isDeclareLimt");
					if (isDeclareLimt.equals("false")){
						if(!isDeclareTotalUpperLimt(drugForm.getUserId())){
							if(!isRepeatDeclare(drugForm.getCommonNameContents().getId())){
								drugForm.setDeclared(Boolean.TRUE);
								drugForm.setAuditStatus(AuditStatusEnum.init);
								drugForm.setDeclareDate(new Date(Calendar.getInstance().getTime().getTime()));
								drugForm.setDeclareCategory(resulstMap.get("declareCategory"));
								super.save(drugForm);
							}else{
								noDeclareCommonName.append(drugForm.getCommonNameContents().getCommon().getCommonName() + "申报重复|");
							}
						}
						else{
							noDeclareCommonName.append(drugForm.getCommonNameContents().getCommon().getCommonName() + "超出申报数|");	
						}
					} else {
						noDeclareCommonName.append(drugForm.getCommonNameContents().getCommon().getCommonName() + isDeclareLimt+ "|");
					}
				}
			}
		}
		return noDeclareCommonName.toString();
	}
	
//	/**
//	 * 批量撤销还未初审的申报药品
//	 * 
//	 */
//	public void saveDeclareCancel(List<Long> selections) {
//		if (selections != null && !selections.isEmpty()) {
//			for (Long id : selections) {
//				DrugForm drugForm = findOne(id);
//				if (drugForm.getAuditStatus() == AuditStatusEnum.init) {
//					drugForm.setDeclared(Boolean.FALSE);
//					drugForm.setAuditStatus(AuditStatusEnum.nodeclare);
//					drugForm.setDeclareDate(null);
//					super.save(drugForm);
//				}
//			}
//		}
//	}
	
	/**
	 * 查询未申报的新药
	 * 
	 */
	public List<DrugForm> findByUserIdAndDeclaredFalse(Long userId) {
		SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
		if (EmptyUtil.isNotNull(systemParameter)) {
			return getDrugFormRepository().findByUserIdAndDeclaredFalseAndSystemParameterId(userId, systemParameter.getId());
		}
		return Lists.newArrayList();
	}
	/**
	 * 查询未审核的申报的新药
	 * 
	 */
//	public List<DrugForm> findByUserIdAndAuditStatus(Long userId, AuditStatusEnum auditStatus) {
//		SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
//		if (EmptyUtil.isNotNull(systemParameter)) {
//			return getDrugFormRepository().findByUserIdAndAuditStatusAndSystemParameterId(userId, auditStatus, systemParameter.getId());
//		}
//		return Lists.newArrayList();
//	}

	/**
	 * 新药初审
	 * 
	 */
	public void initAudit(List<Long> selections,Boolean isAuditPassed,String remark){
		for(Long drugFormId:selections){
			DrugForm drugForm = findOne(drugFormId);
			if(isAuditPassed){
				drugForm.setAuditStatus(AuditStatusEnum.passed);
				drugForm.setAuditDate(new Date(Calendar.getInstance().getTime().getTime()));
			}else{
				drugForm.setAuditStatus(AuditStatusEnum.un_passed);
				drugForm.setAuditDate(new Date(Calendar.getInstance().getTime().getTime()));

			}
			drugForm.setRemark(remark);
			super.save(drugForm);
		}
	}
	private Boolean isRepeatDeclare(Long commonNameContentsId){
		Boolean isRepeatDeclare = Boolean.FALSE;
		SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
		if(EmptyUtil.isNotNull(systemParameter)){
			if(!systemParameter.getRepeatDeclared()){
				Searchable searchable = Searchable.newSearchable();
				searchable.addSearchFilter("systemParameterId", SearchOperator.EQ, systemParameter.getId());
				List<AuditStatusEnum> auditstatus = new ArrayList<AuditStatusEnum>();
				auditstatus.add(AuditStatusEnum.passed);
				auditstatus.add(AuditStatusEnum.init);
				searchable.addSearchFilter("auditStatus", SearchOperator.IN, auditstatus);
				List<CommonNameRule> cnRuleList = commonNameRuleService.findByDeletedFalseAndEnabledTrueOrderByWeightAsc();
				CommonNameContents commonNameContents = commonNameContentsService.findOne(commonNameContentsId);
				String columnName;
				for(CommonNameRule commonNameRule:cnRuleList){
					columnName = commonNameRule.getRuleName();
					if(columnName.equals("common.commonName")){
						searchable.addSearchFilter("commonNameContents."+columnName, SearchOperator.EQ, commonNameContents.getCommon().getCommonName());
					}else if(columnName.equals("administration.id")){
						searchable.addSearchFilter("commonNameContents."+columnName, SearchOperator.EQ, commonNameContents.getAdministration().getId());
					}else if(columnName.equals("common.drugCategory")){
						searchable.addSearchFilter("commonNameContents."+columnName, SearchOperator.EQ, commonNameContents.getCommon().getDrugCategory());
					}else{
						searchable.addSearchFilter("commonNameContents."+columnName, SearchOperator.EQ, Reflections.getFieldValue(commonNameContents, columnName));
					}	
				}
				
				List<DrugForm> drugFormList = findAllWithNoPageNoSort(searchable);
				if(EmptyUtil.isCollectionNotEmpty(drugFormList)){
					isRepeatDeclare = Boolean.TRUE;
				}
			}
		}
		return isRepeatDeclare;
	}
	
	/**
	 * 判断当前申报新药是否满足一品两规的上限
	 * 
	 * @param commonNameContentsId
	 * @return
	 */
	private Map<String,String> isDeclareUpperLimt(Long commonNameContentsId) {
		Map<String,String> resulstMap = new HashMap<String,String>();
		String isDeclareLimt = "false";
		if (commonNameContentsId != null) {// 申报目录编号存在
			// 获取新药申报的大目录对象
			CommonNameContents vo = commonNameContentsService.findOne(commonNameContentsId);
			// 根据匹配编号查询归为一品的通用名集
			List<CommonName> commonNameList = commonNameService.findByMatchNumber(vo.getCommon().getMatchNumber());
			List<Long> commonNameIds = Collections3.extractToList(commonNameList, "id");
			
			//查询系统参数的限数
			Long maxNumber = 0L;
			SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
			if (EmptyUtil.isNotNull(systemParameter)) {
				maxNumber = systemParameter.getDeclarationLimt();
			}
			
			//根据通用名编号找院目录在该通用名中的记录集，看是否满足一品两规的限制
			int existNumber = 0;
			List<HospitalContents> hospitalContentsList = hospitalContentsService.findByCommonIdInInAndAdministrationIdAndDeletedFalse(commonNameIds, vo.getAdministration().getId());
			if(EmptyUtil.isCollectionNotEmpty(hospitalContentsList)){
				existNumber = hospitalContentsList.size();
			}

			if (existNumber < maxNumber) {//满足一品两规，再判断是否满足特殊规则
				if(existNumber==0){
					resulstMap.put("declareCategory", "新增通用名");
				}else{
					resulstMap.put("declareCategory", "新增规格");
				}
				//查询特殊规则中的随数与特殊规则对象
				Map<Long, SpecialRule> map = specialRuleService.findMaxLimitNumber(vo.getCommon().getId(),vo.getAdministration().getId());
				
				SpecialRule specialRule = null;
				
				for(Long key : map.keySet()) {
					maxNumber = key;
					specialRule = map.get(key);
				}
				
				if (EmptyUtil.isNotNull(specialRule)) {
					commonNameList.clear();
					commonNameList = specialRule.getCommonNames();
					commonNameIds = Collections3.extractToList(commonNameList, "id");
					hospitalContentsList = hospitalContentsService.findByCommonIdInInAndAdministrationIdAndDeletedFalse(commonNameIds,specialRule.getAdministration().getId());
					existNumber = 0;
					if (hospitalContentsList != null && hospitalContentsList.size() > 0)
						existNumber += hospitalContentsList.size();
					
					
					if (existNumber >= maxNumber) {
						isDeclareLimt = "申报药品达到特殊药品："+ specialRule.getName() +"的最高" + maxNumber +"的限制，不能申报";
					}
				}
			}else{
				isDeclareLimt = "申报药品达到一品两规最高" + maxNumber +"的限制，不能申报";
			}
		}else{
			isDeclareLimt = "申报药品不在大目录范围，不能申报";
		}
		resulstMap.put("isDeclareLimt", isDeclareLimt);
		return resulstMap;
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
		Long declareTotal = getDrugFormRepository().findDeclareTotalByUserId(userId,systemParameter.getId());

		if (declareTotal < systemParameter.getDeclareTotalLimt()) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
//	public Long countByAuditStatusAndFillInDateBetween(AuditStatusEnum auditStatus) {
//		SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
//		if (systemParameter == null) return 0L;
//		else return getDrugFormRepository().countByAuditStatusAndSystemParameterId(auditStatus, systemParameter.getId());
//	}
	
	public Map<String, Object> findDrugFormCount(User user, SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();
		SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
		if (systemParameter == null) {
			map.put("total", 0);
			map.put("rows", Lists.newArrayList());
			return map;
		}
		
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, Organization.class);
		searchable.addSort(Direction.ASC, "weight");
		searchable.addSearchFilter("parentId", SearchOperator.NE, 0L);
		
		if (!user.getAdmin() && EmptyUtil.isCollectionNotEmpty(user.getOrganizationJobs())) {
			List<UserOrganizationJob>  userOrganizationJobs = user.getOrganizationJobs();
			searchable.addSearchFilter("id", SearchOperator.IN, Collections3.extractToSet(userOrganizationJobs, "organizationId"));
		}
		Page<Organization> organizationPages = organizationService.findAll(searchable);
		List<Organization> organizations = organizationPages.getContent();
		
		List<DrugFormCount> drugFormCounts = Lists.newArrayList();
		DrugFormCount drugFormCount = null;
		for (Organization organization : organizations) {
			Set<Long> userIds = userOrganizationJobService.findUsersByOrganization(organization.getId());
			if (EmptyUtil.isCollectionNotEmpty(userIds)) {
				Long noDeclareNumber = getDrugFormRepository().countByUserIdInAndAuditStatusAndSystemParameterId(userIds, AuditStatusEnum.nodeclare, systemParameter.getId());
				Long initNumber = getDrugFormRepository().countByUserIdInAndAuditStatusAndSystemParameterId(userIds, AuditStatusEnum.init, systemParameter.getId());
				Long passedNumber = getDrugFormRepository().countByUserIdInAndAuditStatusAndSystemParameterId(userIds, AuditStatusEnum.passed, systemParameter.getId());
				Long unPassedNumber = getDrugFormRepository().countByUserIdInAndAuditStatusAndSystemParameterId(userIds, AuditStatusEnum.un_passed, systemParameter.getId());
				drugFormCount = new DrugFormCount(organization.getId(), organization.getName(), noDeclareNumber, initNumber, passedNumber, unPassedNumber);
			} else {
				drugFormCount = new DrugFormCount(organization.getId(), organization.getName());
			}
			drugFormCounts.add(drugFormCount);
		}
		
		map.put("total", organizationPages.getTotalElements());
		map.put("rows", drugFormCounts);
		return map;
	}
	
	public Map<String, Long> drupFromCountChart(){
		Map<String, Long> map = Maps.newHashMap();
		SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
		if (systemParameter == null) return map;
		
		map.put("未提交初审", count(Searchable.newSearchable().addSearchFilter("auditStatus", SearchOperator.EQ, AuditStatusEnum.nodeclare).addSearchFilter("fillInDate", SearchOperator.GTE, systemParameter.getApplyStartDate()).addSearchFilter("fillInDate", SearchOperator.LTE, systemParameter.getApplyEndDate())));
		map.put("已提交初审", count(Searchable.newSearchable().addSearchFilter("auditStatus", SearchOperator.EQ, AuditStatusEnum.init).addSearchFilter("fillInDate", SearchOperator.GTE, systemParameter.getApplyStartDate()).addSearchFilter("fillInDate", SearchOperator.LTE, systemParameter.getApplyEndDate())));
		map.put("初审核已通过", count(Searchable.newSearchable().addSearchFilter("auditStatus", SearchOperator.EQ, AuditStatusEnum.passed).addSearchFilter("fillInDate", SearchOperator.GTE, systemParameter.getApplyStartDate()).addSearchFilter("fillInDate", SearchOperator.LTE, systemParameter.getApplyEndDate())));
		map.put("初审核未通过", count(Searchable.newSearchable().addSearchFilter("auditStatus", SearchOperator.EQ, AuditStatusEnum.un_passed).addSearchFilter("fillInDate", SearchOperator.GTE, systemParameter.getApplyStartDate()).addSearchFilter("fillInDate", SearchOperator.LTE, systemParameter.getApplyEndDate())));
		
		return map;
	}
}
