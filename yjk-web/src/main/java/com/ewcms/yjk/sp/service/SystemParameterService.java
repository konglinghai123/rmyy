package com.ewcms.yjk.sp.service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.auth.service.AutomaticAuthService;
import com.ewcms.security.auth.util.AutomaticAuthUtil;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.security.user.service.UserService;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.sp.entity.SystemExpert;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.repository.SystemParameterRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @author wuzhijun
 *
 */
@Service
public class SystemParameterService extends BaseService<SystemParameter, Long> {

	private static final String ROLE_NAME = "新药申报";
	private static final String ROLE_IDENTIFICATION = "role_drugform";
	private static final String GROUP_NAME = "新药申报用户组";
	private static final Long RESOURCE_ID = AutomaticAuthUtil.SB_RESOURCE_ID;
	
	private SystemParameterRepository getSystemParameterRepository() {
		return (SystemParameterRepository) baseRepository;
	}

	@Autowired
	private UserService userService;
	@Autowired
	private UserOrganizationJobService userOrganizationJobService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private SystemExpertService systemExpertService;
	@Autowired
	private AutomaticAuthService automaticAuthService;
	@Autowired
	private ReviewMainService reviewMainService;

	@Override
	public SystemParameter update(SystemParameter systemParameter) {
		SystemParameter dbSystemParameter = findOne(systemParameter.getId());
		systemParameter.setSystemExperts(dbSystemParameter.getSystemExperts());
		systemParameter.setUsers(dbSystemParameter.getUsers());

		return super.update(systemParameter);
	}
	
	public SystemParameter findByEnabledTrue() {
		return getSystemParameterRepository().findByEnabledTrue();
	}
	
	@SuppressWarnings("unchecked")
	public void filter(Long id) {
		SystemParameter systemParameter = findOne(id);
		if (EmptyUtil.isNotNull(systemParameter)) {
			
			List<Long> userIdSelected = Lists.newArrayList();//被选中的用户ID号
			
			List<SystemExpert> systemExperts = systemParameter.getSystemExperts();
			if (EmptyUtil.isCollectionNotEmpty(systemExperts)) {
				for (SystemExpert systemExpert : systemExperts) {
					if (!systemExpert.getEnabled()) {
						systemExpert.setUsers(null);
						systemExpertService.update(systemExpert);
						continue;
					}
					Set<Long> systemExpertUserIds = Sets.newHashSet();
					List<Long> userIds = null;
					if (EmptyUtil.isCollectionNotEmpty(userIdSelected)) {
						userIds = userService.findUserIdOffscale(userIdSelected);
					} else {
						userIds = userService.findUserIds();
					}

					if (EmptyUtil.isCollectionEmpty(userIds)) break;// 无用户，退出筛选

					// 查询未被选中的用户编号集合
					List<Long> matchUserIds = userOrganizationJobService.findFilterUsers(systemExpert.getDirector(),
							systemExpert.getSecondDirector(), systemExpert.getPharmacy(), systemExpert.getScience(),
							systemExpert.getAntibiosis(), systemExpert.getOrganizationIds(),
							systemExpert.getDepartmentAttributeIds(), systemExpert.getProfessionIds(),
							systemExpert.getTechnicalTitleIds(), systemExpert.getAppointmentIds(), userIds);

					if (EmptyUtil.isCollectionNotEmpty(matchUserIds)) {
						int useNumber = 0;// 已使用的人数
						int departmentNumber = systemExpert.getDepartmentNumber().intValue();// 选定科室/病区确保人数
						int matchSize = matchUserIds.size();// 匹配到的人数
						int percentNumber = (int)((systemExpert.getPercent() * matchSize) / 100);// 百分比人数
						int randomNumber = systemExpert.getRandomNumber().intValue();// 随机人数
						int overplusNumber = percentNumber;// 剩余人数
						if (randomNumber > 0) {// 随机人数大于零，取百分比人数与随机人数最小的数
							overplusNumber = Math.min(percentNumber, randomNumber);
						}
						
						if (departmentNumber > 0) {// 从每个科室/病区里随机选择确保的人数
							List<Long> organizationIds = systemExpert.getOrganiztionIdsList();
							if (EmptyUtil.isCollectionEmpty(organizationIds)) {// 未选择科室/病区，就是所有科室/病区
								organizationIds = Collections3.extractToList(organizationService.findAll(), "id");
							}
							
							int departmentSize = Math.min(organizationIds.size() * departmentNumber, overplusNumber);
							
							Random random = new Random();
							
							Set<Long> resetOrganizationIds = Sets.newLinkedHashSet();
							// 把按id顺序取出的部门编号通过随机数打乱存储
							while (true) {
								resetOrganizationIds.add(organizationIds.get(random.nextInt(organizationIds.size())));
								if (resetOrganizationIds.size() >= organizationIds.size()) break;
							}
							
							for (Long organizationId : resetOrganizationIds) {
								List<Long> matchUserIdsByOrg = userOrganizationJobService.findFilterUsers(
										systemExpert.getDirector(), systemExpert.getSecondDirector(),
										systemExpert.getPharmacy(), systemExpert.getScience(), systemExpert.getAntibiosis(),
										Sets.newHashSet(organizationId), systemExpert.getDepartmentAttributeIds(),
										systemExpert.getProfessionIds(), systemExpert.getTechnicalTitleIds(),
										systemExpert.getAppointmentIds(), userIds);
								if (EmptyUtil.isCollectionNotEmpty(matchUserIdsByOrg)) {
									int matchOrganizationIdSize = Math.min(matchUserIdsByOrg.size(), departmentNumber);
									if (overplusNumber - useNumber <= matchOrganizationIdSize) {
										matchOrganizationIdSize = overplusNumber - useNumber;
									}
									
									Set<Long> selectIds = Sets.newHashSet();
									while (true) {
										selectIds.add(matchUserIdsByOrg.get(random.nextInt(matchOrganizationIdSize)));
										if (selectIds.size() >= matchOrganizationIdSize) {
											useNumber += matchOrganizationIdSize;
											break;
										}
									}
									systemExpertUserIds.addAll(selectIds);
									
									if (departmentSize <= useNumber) break;
								}
							}
						}
						
						overplusNumber -= useNumber;// 剩余人数

						if (overplusNumber > 0) {
							if (overplusNumber >= matchSize) {// 当匹配到人数小于剩余人数时，让所有匹配人数全部可以访问
								systemExpertUserIds.addAll(matchUserIds);
							} else {
								Set<Long> selectIds = Sets.newHashSet();
								Random random = new Random();
								while (true) {
									selectIds.add(matchUserIds.get(random.nextInt(matchSize)));
									if (selectIds.size() >= overplusNumber) {
										break;
									}
								}
								systemExpertUserIds.addAll(selectIds);
							}
						}
					}

					userIdSelected.addAll(systemExpertUserIds);

					List<User> users = Lists.newArrayList(userService.findUserDisplay(systemExpertUserIds));
					systemExpert.setUsers(users);

					systemExpertService.update(systemExpert);
				}

				if (systemParameter.getEnabled()) {
					automaticAuthService.automaticRemoveAllUser(GROUP_NAME);
					automaticAuthService.automaticAddAuth(ROLE_NAME, ROLE_IDENTIFICATION, GROUP_NAME, userIdSelected, Boolean.TRUE, RESOURCE_ID);
				}

			}
			systemParameter.setUsers(null);
			super.update(systemParameter);
		}
	}

	public SystemParameter openDeclare(User opUser, Long systemParameterId) throws BaseException{
		if (reviewMainService.isUseSystemParameter(systemParameterId)) throw new BaseException("", "由于申报编号[" + systemParameterId + "]已被评审使用了，不能启动申报");
		if (reviewMainService.findByEnabledTrue() != null) throw new BaseException("", "评审设置中有未关闭的评审情况，请先关闭评审.");
		
		SystemParameter systemParameter = findOne(systemParameterId);
		if (systemParameter.getApplyEndDate().after(new Date()) && systemParameter.getApplyStartDate().before(new Date())) {
			SystemParameter dbSystemParameter = findByEnabledTrue();
			if (dbSystemParameter != null) {
				dbSystemParameter.setEnabled(Boolean.FALSE);
				super.update(dbSystemParameter);
			}
			systemParameter.setEnabled(Boolean.TRUE);
		
			List<Long> userIds = Lists.newArrayList(systemParameter.getUserIds());
			List<SystemExpert> systemExperts = systemParameter.getSystemExperts();
			if (EmptyUtil.isCollectionNotEmpty(systemExperts)) {
				for (SystemExpert systemExpert : systemExperts) {
					Set<Long> selectUserIds = systemExpert.getUserIds();
					if (EmptyUtil.isCollectionNotEmpty(selectUserIds)) userIds.addAll(selectUserIds);
				}
			}
			automaticAuthService.automaticAddAuth(ROLE_NAME, ROLE_IDENTIFICATION, GROUP_NAME, userIds, Boolean.TRUE, RESOURCE_ID);
				
			return super.update(systemParameter);
		}else{
			return systemParameter;
		}
	}
	
	public SystemParameter closeDeclare(User opUser, Long systemParameterId) {
		SystemParameter systemParameter = findOne(systemParameterId);
		systemParameter.setEnabled(Boolean.FALSE);
		
		automaticAuthService.automaticRemoveAllUser(GROUP_NAME);
		
		return super.update(systemParameter);
	}

	public boolean isOpenDrugDeclare() {
		return findByEnabledTrue() == null ? false : true;
	}
	
	public void isCloseDeclare() {
		SystemParameter systemParameter = findByEnabledTrue();
		if (EmptyUtil.isNotNull(systemParameter)) {
			if (systemParameter.getApplyEndDate().before(new Date())) {
				List<User> users = userService.findByAdminTrueAndDeletedFalseAndStatus(UserStatus.normal);
				if (EmptyUtil.isCollectionNotEmpty(users)) {
					User opUser = users.get(0);
					closeDeclare(opUser, systemParameter.getId());
				}
			}
		}
	}
	
	public void removeUser(SystemParameter systemParameter, List<Long> userIds) {
		Set<User> removeUsers = userService.findUserDisplay(Sets.newHashSet(userIds));
		
		List<User> systemParameterUsers = systemParameter.getUsers();
		if (EmptyUtil.isCollectionNotEmpty(systemParameterUsers)) {
			systemParameterUsers.removeAll(removeUsers);
			systemParameter.setUsers(systemParameterUsers);
		}
		
		List<SystemExpert> systemExperts = systemParameter.getSystemExperts();
		if (EmptyUtil.isCollectionNotEmpty(systemExperts)) {
			for (SystemExpert systemExpert : systemExperts) {
				List<User> users = systemExpert.getUsers();
				users.removeAll(removeUsers);
				systemExpert.setUsers(users);
			}
			systemParameter.setSystemExperts(systemExperts);
		}
		
		if (systemParameter.getEnabled()) {
			automaticAuthService.automaticRemoveUser(GROUP_NAME, Sets.newHashSet(userIds));
		}
		
		super.update(systemParameter);
	}
	
	public void addUser(SystemParameter systemParameter, List<Long> userIds) {
		Set<User> addUsers = userService.findUserDisplay(Sets.newHashSet(userIds));
		addUsers.addAll(systemParameter.getUsers());
		
		List<User> existUsers = Lists.newArrayList();
		List<SystemExpert> systemExperts = systemParameter.getSystemExperts();
		for (SystemExpert systemExpert : systemExperts) {
			existUsers.addAll(systemExpert.getUsers());
		}
		
		addUsers.removeAll(existUsers);
		
		systemParameter.setUsers(Lists.newArrayList(addUsers));
		
		if (systemParameter.getEnabled()) {
			automaticAuthService.automaticAddAuth(ROLE_NAME, ROLE_IDENTIFICATION, GROUP_NAME, userIds, Boolean.FALSE, RESOURCE_ID);
		}
		
		super.update(systemParameter);
	}

}
