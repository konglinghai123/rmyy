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
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.auth.service.AutomaticAuthService;
import com.ewcms.security.auth.util.AutomaticAuthUtil;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.security.user.service.UserService;
import com.ewcms.yjk.YjkConstants;
import com.ewcms.yjk.re.entity.ReviewExpert;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.repository.ReviewMainRepository;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
public class ReviewMainService extends BaseService<ReviewMain, Long> {

	private static final String ROLE_NAME = "专家评审";
	private static final String ROLE_IDENTIFICATION = "role_reviewform";
	private static final String GROUP_NAME = "专家评审用户组";
	private static final Long RESOURCE_ID = AutomaticAuthUtil.RE_RESOURCE_ID;

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
	@Autowired
	private AutomaticAuthService automaticAuthService;
	@Autowired
	private SystemParameterService systemParameterService;
	@Autowired
	private ReviewProcessService reviewProcessService;
	@Autowired
	private VoteRecordService voteRecordService;
	@Override
	public ReviewMain update(ReviewMain reviewMain) {
		ReviewMain dbReviewMain = findOne(reviewMain.getId());
		reviewMain.setReviewExperts(dbReviewMain.getReviewExperts());
		reviewMain.setReviewProcesses(dbReviewMain.getReviewProcesses());
		reviewMain.setSystemParameter(dbReviewMain.getSystemParameter());
		reviewMain.setUsers(dbReviewMain.getUsers());

		return super.update(reviewMain);
	}

	public ReviewMain findByEnabledTrue() {
		return getReviewMainRepository().findByEnabledTrue();
	}

	public ReviewMain findByName(String name) {
		return getReviewMainRepository().findByName(name);
	}

	@SuppressWarnings("unchecked")
	public void filter(Long id) {
		ReviewMain reviewMain = findOne(id);
		if (EmptyUtil.isNotNull(reviewMain)) {
			
			List<Long> userIdSelected = Lists.newArrayList();//被选中的用户ID号
			
			List<ReviewExpert> reviewExperts = reviewMain.getReviewExperts();
			if (EmptyUtil.isCollectionNotEmpty(reviewExperts)) {
				for (ReviewExpert reviewExpert : reviewExperts) {
					if (!reviewExpert.getEnabled()) {
						reviewExpert.setUsers(null);
						reviewExpertService.update(reviewExpert);
						continue;
					}
					Set<Long> reviewExpertUserIds = Sets.newHashSet();
					List<Long> userIds = null;
					if (EmptyUtil.isCollectionNotEmpty(userIdSelected)) {
						userIds = userService.findUserIdOffscale(userIdSelected);
					} else {
						userIds = userService.findUserIds();
					}

					if (EmptyUtil.isCollectionEmpty(userIds)) break;// 无用户，退出筛选

					// 查询未被选中的用户编号集合
					List<Long> matchUserIds = userOrganizationJobService.findFilterUsers(reviewExpert.getDirector(),
							reviewExpert.getSecondDirector(), reviewExpert.getPharmacy(), reviewExpert.getScience(),
							reviewExpert.getAntibiosis(), reviewExpert.getOrganizationIds(),
							reviewExpert.getDepartmentAttributeIds(), reviewExpert.getProfessionIds(),
							reviewExpert.getTechnicalTitleIds(), reviewExpert.getAppointmentIds(), userIds);

					if (EmptyUtil.isCollectionNotEmpty(matchUserIds)) {
						int useNumber = 0;// 已使用的人数
						int departmentNumber = reviewExpert.getDepartmentNumber().intValue();// 选定科室/病区确保人数
						int matchSize = matchUserIds.size();// 匹配到的人数
						int percentNumber = (int)((reviewExpert.getPercent() * matchSize) / 100);// 百分比人数
						int randomNumber = reviewExpert.getRandomNumber().intValue();// 随机人数
						int overplusNumber = percentNumber;// 剩余人数
						if (randomNumber > 0) {// 随机人数大于零，取百分比人数与随机人数最小的数
							overplusNumber = Math.min(percentNumber, randomNumber);
						}
						
						if (departmentNumber > 0) {// 从每个科室/病区里随机选择确保的人数
							List<Long> organizationIds = reviewExpert.getOrganiztionIdsList();
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
										reviewExpert.getDirector(), reviewExpert.getSecondDirector(),
										reviewExpert.getPharmacy(), reviewExpert.getScience(), reviewExpert.getAntibiosis(),
										Sets.newHashSet(organizationId), reviewExpert.getDepartmentAttributeIds(),
										reviewExpert.getProfessionIds(), reviewExpert.getTechnicalTitleIds(),
										reviewExpert.getAppointmentIds(), userIds);
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
									reviewExpertUserIds.addAll(selectIds);
									
									if (departmentSize <= useNumber) break;
								}
							}
						}
						
						overplusNumber -= useNumber;// 剩余人数

						if (overplusNumber > 0) {
							if (overplusNumber >= matchSize) {// 当匹配到人数小于剩余人数时，让所有匹配人数全部可以访问
								reviewExpertUserIds.addAll(matchUserIds);
							} else {
								Set<Long> selectIds = Sets.newHashSet();
								Random random = new Random();
								while (true) {
									selectIds.add(matchUserIds.get(random.nextInt(matchSize)));
									if (selectIds.size() >= overplusNumber) {
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

				if (reviewMain.getEnabled()) {
					automaticAuthService.automaticRemoveAllUser(GROUP_NAME);
					automaticAuthService.automaticAddAuth(ROLE_NAME, ROLE_IDENTIFICATION, GROUP_NAME, userIdSelected, Boolean.TRUE, RESOURCE_ID);
				}

			}
			reviewMain.setUsers(null);
			reviewMain.setExtractDate(new Date());
			super.update(reviewMain);
		}
	}

	public AjaxResponse openReview(User opUser, Long reviewMainId) {
		if (systemParameterService.findByEnabledTrue() != null) {
			return new AjaxResponse(Boolean.FALSE, "还有申报新药在进行中，必须先停止申报新药才能开启评审！");
		}
			
		ReviewMain reviewMain = findOne(reviewMainId);
		if (reviewMain == null) {
			return new AjaxResponse(Boolean.FALSE, "评审不存在！");
		}
		if (EmptyUtil.isCollectionEmpty(reviewMain.getReviewProcesses())) {
			return new AjaxResponse(Boolean.FALSE, "评审流程未定义，不能开启评审！");
		}
		
		ReviewMain dbReviewMain = findByEnabledTrue();
		if (dbReviewMain != null) {
			dbReviewMain.setEnabled(Boolean.FALSE);
			super.update(dbReviewMain);
		}
		reviewMain.setEnabled(Boolean.TRUE);

		List<Long> userIds = Lists.newArrayList(reviewMain.getUserIds());
		List<ReviewExpert> reviewExperts = reviewMain.getReviewExperts();
		if (EmptyUtil.isCollectionNotEmpty(reviewExperts)) {
			for (ReviewExpert reviewExpert : reviewExperts) {
				Set<Long> selectUserIds = reviewExpert.getUserIds();
				if (EmptyUtil.isCollectionNotEmpty(selectUserIds))
					userIds.addAll(selectUserIds);
			}
		}
		if (EmptyUtil.isCollectionEmpty(userIds)) {
			return new AjaxResponse(Boolean.FALSE, "没有评审专家，请先添加评审专家！");
		}
		
		automaticAuthService.automaticAddAuth(ROLE_NAME, ROLE_IDENTIFICATION, GROUP_NAME, userIds, Boolean.TRUE, RESOURCE_ID);
		super.update(reviewMain);
		
		ReviewProcess currentReviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMain.getId());
		if(currentReviewProcess != null){
			if(currentReviewProcess.getReviewBaseRule().getRuleName().equals(YjkConstants.ACN)){
				voteRecordService.initDrugFormVoteResult(currentReviewProcess.getId(), "新增通用名");
			}
			if(currentReviewProcess.getReviewBaseRule().getRuleName().equals(YjkConstants.ASAP)){
				voteRecordService.initDrugFormVoteResult(currentReviewProcess.getId(), "新增规格/剂型");
			}	
		}
		return new AjaxResponse(Boolean.TRUE, "评审流程开启成功！");
	}

	public AjaxResponse closeReview(User opUser, Long reviewMainId) {
		ReviewMain reviewMain = findOne(reviewMainId);
		if (reviewMain == null) return new AjaxResponse(Boolean.FALSE, "评审不存在！");
//		if (EmptyUtil.isCollectionNotEmpty(reviewMain.getReviewProcesses())) {
//			ReviewProcess currentReviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			
//			List<ReviewProcess> reviewProcesses = reviewProcessService.findByReviewMainIdAndFinishedFalseOrderByWeightDesc(reviewMainId);
//			if (EmptyUtil.isCollectionNotEmpty(reviewProcesses) && reviewProcesses.get(0).equals(currentReviewProcess)) {
//				Long countNoAffirm = voteResultService.countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedFalse(reviewMainId, currentReviewProcess.getId());
//				if (countNoAffirm.longValue() != 0) {
//					return new AjaxResponse(Boolean.FALSE, "还有未确认最终结果的记录，不能关闭本次评审！");
//				}
//			}
//		}
		
		reviewMain.setEnabled(Boolean.FALSE);
		update(reviewMain);
		
		automaticAuthService.automaticRemoveAllUser(GROUP_NAME);
		
		return new AjaxResponse(Boolean.TRUE, "评审流程关闭成功！");
	}

	public boolean isOpenReview() {
		return findByEnabledTrue() == null ? false : true;
	}

	public void isCloseReview() {
		ReviewMain reviewMain = findByEnabledTrue();
		if (EmptyUtil.isNotNull(reviewMain)) {
			List<User> users = userService.findByAdminTrueAndDeletedFalseAndStatus(UserStatus.normal);
			if (EmptyUtil.isCollectionNotEmpty(users)) {
				User opUser = users.get(0);
				closeReview(opUser, reviewMain.getId());
			}
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
		
		if (reviewMain.getEnabled()) {
			automaticAuthService.automaticRemoveUser(GROUP_NAME, Sets.newHashSet(userIds));
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
		
		if (reviewMain.getEnabled()) {
			automaticAuthService.automaticAddAuth(ROLE_NAME, ROLE_IDENTIFICATION, GROUP_NAME, userIds, Boolean.FALSE, RESOURCE_ID);
		}

		super.update(reviewMain);
	}
	
	public void updSystemParameter(Long reviewMainId, Long systemParameterId) {
		ReviewMain reviewMain = findOne(reviewMainId);
		SystemParameter systemParameter = systemParameterService.findOne(systemParameterId);
		if (EmptyUtil.isNotNull(reviewMain) && EmptyUtil.isNotNull(systemParameter) && EmptyUtil.isNull(reviewMain.getSystemParameter())) {
			reviewMain.setSystemParameter(systemParameter);
			super.update(reviewMain);
		}
	}
	
	public List<ReviewMain> findBySystemParameterId(Long systemParameterId){
		return getReviewMainRepository().findBySystemParameterId(systemParameterId);
	}
	
	public Boolean isUseSystemParameter(Long systemParameterId) {
		List<ReviewMain> reviewMains = findBySystemParameterId(systemParameterId);
		if (EmptyUtil.isCollectionNotEmpty(reviewMains)) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findReviewUserIds(ReviewMain reviewMain) {
		List<Long> allReviewUserIds = Lists.newArrayList();

		if (reviewMain == null) return allReviewUserIds;
		
		List<User> reviewMainUsers = reviewMain.getUsers();
		if (EmptyUtil.isCollectionNotEmpty(reviewMainUsers)) {
			allReviewUserIds.addAll(Collections3.extractToList(reviewMainUsers, "id"));
		}

		List<ReviewExpert> reviewExperts = reviewMain.getReviewExperts();
		if (EmptyUtil.isCollectionNotEmpty(reviewExperts)) {
			for (ReviewExpert reviewExpert : reviewExperts) {
				allReviewUserIds.addAll(Collections3.extractToList(reviewExpert.getUsers(), "id"));
			}
		}

		return allReviewUserIds;
	}
	
}