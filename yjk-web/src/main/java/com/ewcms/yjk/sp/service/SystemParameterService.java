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
	
				String interval = String.format("在 %tF %tT 到 %tF %tT 之间,", vo.getApplyStartDate(), vo.getApplyStartDate(), vo.getApplyEndDate(), vo.getApplyEndDate());
	
				List<Long> allUserIds = userService.findUserIds();
				userService.changeStatus(opUser, allUserIds, UserStatus.blocked, interval + "新药申报 启动前 封禁所有非管理员用户");
	
				Long useNumber = 0L;// 已使用的人数
	
				Long departmentNumber = vo.getDepartmentNumber();// 选定科室确保人数
				if (departmentNumber > 0L) {
					Set<Long> organizationIds = vo.getOrganizationsIds();
					for (Long organizationId : organizationIds) {
						List<Long> userIds = userOrganizationJobService.findUsers(Sets.newHashSet(organizationId), vo.getDepartmentAttributeIds(), vo.getProfessionIds(), vo.getTechnicalTitleIds(), vo.getAppointmentIds());
						if (EmptyUtil.isCollectionNotEmpty(userIds)) {
							useNumber += ((departmentNumber >= userIds.size()) ? userIds.size() : departmentNumber);
	
							if (departmentNumber >= userIds.size()) {
								userService.changeStatus(opUser, Lists.newArrayList(userIds), UserStatus.normal, interval + "新药申报 启动后 根据规则允许申报人员");
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
								userService.changeStatus(opUser, Lists.newArrayList(selectIds), UserStatus.normal, interval + "新药申报 启动后 根据规则允许申报人员");
							}
						}
					}
				}
	
				List<Long> matchUsers = userOrganizationJobService.findUsers(vo.getOrganizationsIds(), vo.getDepartmentAttributeIds(), vo.getProfessionIds(), vo.getTechnicalTitleIds(), vo.getAppointmentIds());
				if (EmptyUtil.isCollectionNotEmpty(matchUsers)) {
					int matchSize = matchUsers.size();
	
					Long totalNumber = vo.getTotalNumber();
	
					Long number = 0L;
					if (vo.getPercent() > 0L && vo.getPercent() < 100L) {// 选取比例在0%到100%之间时
						number = Long.valueOf((vo.getPercent() * matchSize) / 100);
					}
	
					if (totalNumber > 0L) {
						if (number <= totalNumber) {// 当设定人数大于比率人数时，使用总人数
							number = totalNumber;
						}
					}
	
					number -= useNumber;//剩余人数
	
					if (number > 0) {
						Set<Long> selectIds = Sets.newHashSet();
						Random random = new Random();
						int i = 0;
						while (true) {
							i = random.nextInt(matchSize);
							selectIds.add(matchUsers.get(i));
							if (selectIds.size() >= number) {
								break;
							}
						}
						userService.changeStatus(opUser, Lists.newArrayList(selectIds), UserStatus.normal, interval + "新药申报 启动后 根据规则允许申报人员");
					}
				}
				return update(vo);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
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
}
