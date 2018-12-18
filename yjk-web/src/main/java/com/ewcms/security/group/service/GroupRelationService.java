package com.ewcms.security.group.service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.group.entity.Group;
import com.ewcms.security.group.entity.GroupRelation;
import com.ewcms.security.group.entity.GroupType;
import com.ewcms.security.group.repository.GroupRelationRepository;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class GroupRelationService extends BaseService<GroupRelation, Long> {

	private GroupRelationRepository getGroupRelationRepository() {
		return (GroupRelationRepository) baseRepository;
	}

	@Autowired
	private GroupService groupService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;

	public Set<Long> findGroupIds(Long userId) {
		return Sets.newHashSet(getGroupRelationRepository().findGroupIds(userId));
	}

	public Set<Long> findGroupIds(Long userId, Set<Long> organizationIds) {
		if (organizationIds.isEmpty()) {
			return Sets.newHashSet(getGroupRelationRepository().findGroupIds(userId));
		}
		return Sets.newHashSet(getGroupRelationRepository().findGroupIds(userId, organizationIds));
	}

	public GroupRelation findByGroupIdAndUserId(Long groupId, Long userId) {
		return getGroupRelationRepository().findByGroupIdAndUserId(groupId, userId);
	}

	public GroupRelation findByGroupIdAndOrganizationId(Long groupId, Long organizationId) {
		return getGroupRelationRepository().findByGroupIdAndOrganizationId(groupId, organizationId);
	}

	public void addOrganizationGroupRelation(Long groupId, Long[] organizationIds, Boolean checkAll) {
    	Group group = groupService.findOne(groupId);
    	
		if (group.getType() == GroupType.organization) {
			List<Organization> organizations = Lists.newArrayList();
			
			if (checkAll) {
				organizations = organizationService.findAll();
			} else {
				if (EmptyUtil.isArrayEmpty(organizationIds)) return;
				organizations = organizationService.findByIdIn(organizationIds);
			}
			
			GroupRelation newGroupRelation;
			for (Organization organization : organizations) {
				if (organization.getParentId() != 0L && organization.getId() != 1) {
					if (findByGroupIdAndOrganizationId(groupId, organization.getId()) == null) {
						newGroupRelation = new GroupRelation();
						newGroupRelation.setGroupId(groupId);
						newGroupRelation.setOrganizationId(organization.getId());
						super.save(newGroupRelation);
					}
				}
			}
		} 
    }

	public void addUserGroupRelation(Long groupId, Long[] userIds, Boolean checkAll) {
		Group group = groupService.findOne(groupId);
		
		if (group.getType() == GroupType.user) {
			List<User> users = Lists.newArrayList();
			
			if (checkAll) {
				users = userService.findAll();
			} else {
				if (EmptyUtil.isArrayEmpty(userIds)) return;
				users = Lists.newArrayList(userService.findUserDisplay(Sets.newHashSet(userIds)));
			}
			
			GroupRelation newGroupRelation;
			for (User user : users) {
				if (!user.getAdmin() && !user.getDeleted()) {
					if (findByGroupIdAndOrganizationId(groupId, user.getId()) == null) {
						newGroupRelation = new GroupRelation();
						newGroupRelation.setGroupId(groupId);
						newGroupRelation.setUserId(user.getId());
						super.save(newGroupRelation);
					}
				}
			}
		}
	}
	
	public List<Long> findGroupRelationIds(Long groupId) {
		return getGroupRelationRepository().findGroupRelationIds(groupId);
	}
	
	public List<Long> findGroupRelationIds(Long groupId, Set<Long> userIds){
		return getGroupRelationRepository().findGroupRelationIds(groupId, userIds);
	}
}
