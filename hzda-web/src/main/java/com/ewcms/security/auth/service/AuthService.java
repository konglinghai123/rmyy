package com.ewcms.security.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.auth.entity.Auth;
import com.ewcms.security.auth.repository.AuthRepository;
import com.ewcms.security.group.entity.Group;
import com.ewcms.security.group.service.GroupService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;

import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class AuthService extends BaseService<Auth, Long> {

	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;

	private AuthRepository getAuthRepository() {
		return (AuthRepository) baseRepository;
	}

	public void addUserAuth(Long[] userIds, Auth m) {
		if (EmptyUtil.isArrayEmpty(userIds)) {
			return;
		}

		for (Long userId : userIds) {

			User user = userService.findOne(userId);
			if (user == null) {
				continue;
			}

			Auth auth = getAuthRepository().findByUserId(userId);
			if (auth != null) {
				auth.addRoleIds(m.getRoleIds());
				continue;
			}
			auth = new Auth();
			auth.setUserId(userId);
			auth.setType(m.getType());
			auth.setRoleIds(m.getRoleIds());
			save(auth);
		}
	}

	public void addGroupAuth(Long[] groupIds, Auth m) {
		if (EmptyUtil.isArrayEmpty(groupIds)) {
			return;
		}

		for (Long groupId : groupIds) {
			Group group = groupService.findOne(groupId);
			if (group == null) {
				continue;
			}

			Auth auth = getAuthRepository().findByGroupId(groupId);
			if (auth != null) {
				auth.addRoleIds(m.getRoleIds());
				continue;
			}
			auth = new Auth();
			auth.setGroupId(groupId);
			auth.setType(m.getType());
			auth.setRoleIds(m.getRoleIds());
			save(auth);
		}
	}
	
	public Auth findByGroupId(Long groupId) {
		return getAuthRepository().findByGroupId(groupId);
	}

	public void addOrganizationJobAuth(Long[] organizationIds, Long[] jobIds, Auth m) {
		if (EmptyUtil.isArrayEmpty(organizationIds)) {
			return;
		}
		for (int i = 0; i < organizationIds.length; i++) {
			Long organizationId = organizationIds[i];
			if (EmptyUtil.isArrayEmpty(jobIds)) {
				addOrganizationJobAuth(organizationId, null, m);
				continue;
			} else {
				for (int j = 0; j < jobIds.length; j++) {
					Long jobId = jobIds[j];
					addOrganizationJobAuth(organizationId, jobId, m);
				}
			}
		}
	}

	private void addOrganizationJobAuth(Long organizationId, Long jobId, Auth m) {
		if (organizationId == null) {
			organizationId = 0L;
		}
		if (jobId == null) {
			jobId = 0L;
		}

		Auth auth = getAuthRepository().findByOrganizationIdAndJobId(organizationId, jobId);
		if (auth != null) {
			auth.addRoleIds(m.getRoleIds());
			return;
		}

		auth = new Auth();
		auth.setOrganizationId(organizationId);
		auth.setJobId(jobId);
		auth.setType(m.getType());
		auth.setRoleIds(m.getRoleIds());
		save(auth);

	}

	/**
	 * 根据用户信息获取 角色 
	 * 1.1、用户 根据用户绝对匹配 
	 * 1.2、组织机构 根据组织机构绝对匹配 此处需要注意 祖先需要自己获取 
	 * 1.3、工作职务 根据工作职务绝对匹配 此处需要注意 祖先需要自己获取 
	 * 1.4、组织机构和工作职务 根据组织机构和工作职务绝对匹配 此处不匹配祖先 
	 * 1.5、组 根据组绝对匹配
	 *
	 * @param userId
	 *            必须有
	 * @param groupIds
	 *            可选
	 * @param organizationIds
	 *            可选
	 * @param jobIds
	 *            可选
	 * @param organizationJobIds
	 *            可选
	 * @return
	 */
	public Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds,
			Set<Long[]> organizationJobIds) {
		return getAuthRepository().findRoleIds(userId, groupIds, organizationIds, jobIds, organizationJobIds);
	}
}
