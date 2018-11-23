package com.ewcms.yjk.re.service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.security.user.service.UserService;
import com.ewcms.yjk.re.entity.ReviewExpert;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.repository.ReviewMainRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
public class ReviewMainService extends BaseService<ReviewMain, Long> {

	private ReviewMainRepository getReviewMainRepository() {
		return (ReviewMainRepository) baseRepository;
	}

	@Autowired
	private UserService userService;
	@Autowired
	private UserOrganizationJobService userOrganizationJobService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ReviewExpertService reviewExpertService;
	
	@Override
	public ReviewMain update(ReviewMain reviewMain) {
		ReviewMain dbReviewMain = findOne(reviewMain.getId());
		reviewMain.setReviewExperts(dbReviewMain.getReviewExperts());
		reviewMain.setUsers(dbReviewMain.getUsers());

		return super.update(reviewMain);
	}

	public ReviewMain findByName(String name) {
		return getReviewMainRepository().findByName(name);
	}

	@SuppressWarnings("unchecked")
	public void filter(Long id) {
		ReviewMain reviewMain = findOne(id);
		if (EmptyUtil.isNotNull(reviewMain)) {
			List<ReviewExpert> reviewExperts = reviewMain.getReviewExperts();
			if (EmptyUtil.isCollectionNotEmpty(reviewExperts)) {
				List<Long> userIdSelected = Lists.newArrayList();
				for (ReviewExpert reviewExpert : reviewExperts) {
					if (!reviewExpert.getEnabled()) continue;
					Set<Long> reviewExpertUserIds = Sets.newHashSet();
					List<Long> userIds = null;
					if (EmptyUtil.isCollectionNotEmpty(userIdSelected)) {
						userIds = userService.findUserIdOffscale(userIdSelected);
					} else {
						userIds = userService.findUserIds();
					}

					if (EmptyUtil.isCollectionEmpty(userIds))
						break;// 无用户，退出筛选

					Long useNumber = 0L;// 已使用的人数

					Long departmentNumber = reviewExpert.getDepartmentNumber();// 选定科室/病区确保人数
					if (departmentNumber > 0L) {// 从每个科室/病区里随机选择确保的人数
						Set<Long> organizationIds = reviewExpert.getOrganizationIds();
						if (EmptyUtil.isCollectionEmpty(organizationIds)) {// 未选择科室/病区，就是所有科室/病区
							organizationIds = Collections3.extractToSet(organizationService.findAll(), "id");
						}
						for (Long organizationId : organizationIds) {
							List<Long> matchUserIds = userOrganizationJobService.findExpertUsers(
									reviewExpert.getDirector(), reviewExpert.getSecondDirector(),
									reviewExpert.getPharmacy(), reviewExpert.getScience(), reviewExpert.getAntibiosis(),
									Sets.newHashSet(organizationId), reviewExpert.getDepartmentAttributeIds(),
									reviewExpert.getProfessionIds(), reviewExpert.getTechnicalTitleIds(),
									reviewExpert.getAppointmentIds(), userIds);
							if (EmptyUtil.isCollectionNotEmpty(matchUserIds)) {
								useNumber += ((departmentNumber >= matchUserIds.size()) ? matchUserIds.size() : departmentNumber);

								if (departmentNumber >= matchUserIds.size()) {// 当选定科室人数大于选定科室人数时，选定科室所有人可以访问
									reviewExpertUserIds.addAll(matchUserIds);
								} else {
									Set<Long> selectIds = Sets.newHashSet();
									Random random = new Random();
									int i = 0;
									while (true) {
										i = random.nextInt(matchUserIds.size());
										selectIds.add(matchUserIds.get(i));
										if (selectIds.size() >= departmentNumber) {
											break;
										}
									}
									reviewExpertUserIds.addAll(selectIds);
								}
							}
						}
					}

					List<Long> matchUserIds = userOrganizationJobService.findExpertUsers(reviewExpert.getDirector(),
							reviewExpert.getSecondDirector(), reviewExpert.getPharmacy(), reviewExpert.getScience(),
							reviewExpert.getAntibiosis(), reviewExpert.getOrganizationIds(),
							reviewExpert.getDepartmentAttributeIds(), reviewExpert.getProfessionIds(),
							reviewExpert.getTechnicalTitleIds(), reviewExpert.getAppointmentIds(), userIds);

					if (EmptyUtil.isCollectionNotEmpty(matchUserIds)) {
						int matchSize = matchUserIds.size();// 匹配到的人数

						Long percentNumber = Long.valueOf((reviewExpert.getPercent() * matchSize) / 100);// 百分比人数
						Long randomNumber = reviewExpert.getRandomNumber();// 随机人数

						if (randomNumber > 0L) {
							percentNumber = Math.min(percentNumber, randomNumber);
						}

						Long overplusNumber = percentNumber - useNumber;// 剩余人数

						if (overplusNumber > 0) {
							if (overplusNumber >= matchSize) {//当匹配到人数小于剩余人数时，让所有匹配人数全部可以访问
								reviewExpertUserIds.addAll(matchUserIds);
							} else {
								Set<Long> selectIds = Sets.newHashSet();
								Random random = new Random();
								int i = 0;
								while (true) {
									i = random.nextInt(matchSize);
									selectIds.add(matchUserIds.get(i));
									if (selectIds.size() >= percentNumber) {
										break;
									}
								}
								reviewExpertUserIds.addAll(selectIds);
							}
						}
					}
					
					userIdSelected.addAll(reviewExpertUserIds);
					
					List<User> users = Lists.newArrayList(userService.findUserDisplay(reviewExpertUserIds));
					reviewExpert.setUsers(users);
					
					reviewExpertService.update(reviewExpert);
				}
			}
			
			reviewMain.setExtractDate(new Date());
			update(reviewMain);
		}
	}
	
	public void removeUser(ReviewMain reviewMain, List<Long> userIds) {
		Set<User> removeUsers = userService.findUserDisplay(Sets.newHashSet(userIds));
		
		List<User> reviewMainUsers = reviewMain.getUsers();
		if (EmptyUtil.isCollectionNotEmpty(reviewMainUsers)) {
			reviewMainUsers.removeAll(removeUsers);
			reviewMain.setUsers(reviewMainUsers);
		}
		
		List<ReviewExpert> reviewExperts = reviewMain.getReviewExperts();
		if (EmptyUtil.isCollectionNotEmpty(reviewExperts)) {
			for (ReviewExpert reviewExpert : reviewExperts) {
				List<User> users = reviewExpert.getUsers();
				users.removeAll(removeUsers);
				reviewExpert.setUsers(users);
			}
			reviewMain.setReviewExperts(reviewExperts);
		}
		super.update(reviewMain);
	}
	
	public void addUser(ReviewMain reviewMain, List<Long> userIds) {
		Set<User> addUsers = userService.findUserDisplay(Sets.newHashSet(userIds));
		addUsers.addAll(reviewMain.getUsers());
		
		List<User> existUsers = Lists.newArrayList();
		List<ReviewExpert> reviewExperts = reviewMain.getReviewExperts();
		for (ReviewExpert reviewExpert : reviewExperts) {
			existUsers.addAll(reviewExpert.getUsers());
		}
		
		addUsers.removeAll(existUsers);
		
		reviewMain.setUsers(Lists.newArrayList(addUsers));
		super.update(reviewMain);
	}
}
