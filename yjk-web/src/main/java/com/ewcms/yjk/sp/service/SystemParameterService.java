package com.ewcms.yjk.sp.service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.security.user.service.UserService;
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

	private SystemParameterRepository getSystemParameterRepository() {
		return (SystemParameterRepository) baseRepository;
	}

	@Autowired
	private UserService userService;
	@Autowired
	private UserOrganizationJobService userOrganizationJobService;

	@Override
	public SystemParameter update(SystemParameter m) {
		SystemParameter dbSystemParameter = findOne(m.getId());
		m.setOrganizations(dbSystemParameter.getOrganizations());
		m.setDepartmentAttributes(dbSystemParameter.getDepartmentAttributes());
		m.setProfessions(dbSystemParameter.getProfessions());
		m.setTechnicalTitles(dbSystemParameter.getTechnicalTitles());
		m.setAppointments(dbSystemParameter.getAppointments());

		return super.update(m);
	}
	
	public SystemParameter findByEnabledTrue() {
		return getSystemParameterRepository().findByEnabledTrue();
	}

	public SystemParameter openDeclare(User opUser, Long systemParameterId) {
		try {
			SystemParameter vo = findOne(systemParameterId);
			if (vo.getApplyEndDate().after(new Date()) && vo.getApplyStartDate().before(new Date())) {
				SystemParameter enabledvo = findByEnabledTrue();
				if (enabledvo != null) {
					enabledvo.setEnabled(Boolean.FALSE);
					update(enabledvo);
				}
	
				vo.setEnabled(Boolean.TRUE);
	
				//String interval = String.format("在 %tF %tT 到 %tF %tT 之间,", vo.getApplyStartDate(), vo.getApplyStartDate(), vo.getApplyEndDate(), vo.getApplyEndDate());
	
				List<Long> allUserIds = userService.findUserIds();
				userService.changeStatus(opUser, allUserIds, UserStatus.blocked, "您本次未被系统抽取到申报新药!");
	
				Long useNumber = 0L;// 已使用的人数
	
				Long departmentNumber = vo.getDepartmentNumber();// 选定科室/病区确保人数
				if (departmentNumber > 0L) {//从每个科室/病区里随机选择确保的人数
					Set<Long> organizationIds = vo.getOrganizationIds();
					for (Long organizationId : organizationIds) {
						List<Long> userIds = userOrganizationJobService.findUsers(Sets.newHashSet(organizationId), vo.getDepartmentAttributeIds(), vo.getProfessionIds(), vo.getTechnicalTitleIds(), vo.getAppointmentIds());
						if (EmptyUtil.isCollectionNotEmpty(userIds)) {
							useNumber += ((departmentNumber >= userIds.size()) ? userIds.size() : departmentNumber);
	
							if (departmentNumber >= userIds.size()) {//当选定科室人数大于选定科室人数时，选定科室所有人可以访问
								userService.changeStatus(opUser, Lists.newArrayList(userIds), UserStatus.normal, "新药申报 启动后 根据规则允许申报人员");
							} else {
								Set<Long> selectIds = Sets.newHashSet();
								Random random = new Random();
								int i = 0;
								while (true) {
									i = random.nextInt(userIds.size());
									selectIds.add(userIds.get(i));
									if (selectIds.size() >= departmentNumber) {
										break;
									}
								}
								userService.changeStatus(opUser, Lists.newArrayList(selectIds), UserStatus.normal, "新药申报 启动后 根据规则允许申报人员");
							}
						}
					}
				}
	
				List<Long> matchUsers = userOrganizationJobService.findUsers(vo.getOrganizationIds(), vo.getDepartmentAttributeIds(), vo.getProfessionIds(), vo.getTechnicalTitleIds(), vo.getAppointmentIds());
				if (EmptyUtil.isCollectionNotEmpty(matchUsers)) {
					int matchSize = matchUsers.size();//匹配到的人数
					
					Long percentNumber = Long.valueOf((vo.getPercent() * matchSize) / 100);//百分比人数
					Long randomNumber = vo.getRandomNumber();//随机人数
	
					if (randomNumber > 0L && percentNumber <= randomNumber) {// 当随机人数大于比率人数时，使用随机人数
						percentNumber = randomNumber;
					}
	
					Long overplusNumber = percentNumber - useNumber;//剩余人数
					
					if (overplusNumber > 0) {
						if (overplusNumber >= matchSize) {//当匹配到人数小于剩余人数时，让所有匹配人数全部可以访问
							userService.changeStatus(opUser, matchUsers, UserStatus.normal, "新药申报 启动后 根据规则允许申报人员");
						} else {
							Set<Long> selectIds = Sets.newHashSet();
							Random random = new Random();
							int i = 0;
							while (true) {
								i = random.nextInt(matchSize);
								selectIds.add(matchUsers.get(i));
								if (selectIds.size() >= percentNumber) {
									break;
								}
							}
							userService.changeStatus(opUser, Lists.newArrayList(selectIds), UserStatus.normal, "新药申报 启动后 根据规则允许申报人员");
						}
						
					}
				}
				return update(vo);
			}else{
				return vo;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
		
	}
	
	public SystemParameter closeDeclare(User opUser, Long systemParameterId) {
		SystemParameter vo = findOne(systemParameterId);
		vo.setEnabled(Boolean.FALSE);
		
		String interval = String.format("在 %tF %tT 到 %tF %tT 之间,", vo.getApplyStartDate(), vo.getApplyStartDate(), vo.getApplyEndDate(), vo.getApplyEndDate());
		
		List<Long> allUserIds = userService.findUserIds();
		userService.changeStatus(opUser, allUserIds, UserStatus.blocked, interval + "新药申报 关闭后 封禁所有非管理员用户");
		
		return update(vo);
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
}
