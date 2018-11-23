package com.ewcms.security.auth.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.auth.entity.Auth;
import com.ewcms.security.auth.entity.AuthType;
import com.ewcms.security.group.entity.Group;
import com.ewcms.security.group.entity.GroupType;
import com.ewcms.security.group.service.GroupRelationService;
import com.ewcms.security.group.service.GroupService;
import com.ewcms.security.permission.entity.Role;
import com.ewcms.security.permission.entity.RoleResourcePermission;
import com.ewcms.security.permission.service.RoleResourcePermissionService;
import com.ewcms.security.permission.service.RoleService;
import com.google.common.collect.Sets;

@Service
public class AutomaticAuthService {
	
	private static final Long PERMISSION_BEGIN = 2L;
	private static final Long PERMISSION_END = 5L;
	private static final Long RESOURCE_ID = 109L;
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleResourcePermissionService roleResourcePermissionService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupRelationService groupRelationService;
	@Autowired
	private AuthService authService;

	/**
	 * 把 userIds 用户加入到权限中（新药申报和专家评审）
	 * 
	 * @param roleName 角色名称
	 * @param roleIdentification 角色标识
	 * @param groupName 用户组名
	 * @param userIds 用户编号集合
	 * @param isclear 是否清除原数据
	 */
	public void automaticAddAuth(String roleName, String roleIdentification, String groupName, List<Long> userIds, Boolean isClear) {
		Role role = roleService.findByNameAndRole(roleName, roleIdentification);
		if (EmptyUtil.isNull(role)) role = new Role();
		role.setName(roleName);
		role.setRole(roleIdentification);
		role.setDescription("根据" + roleName + "规则自动产生");
		role.setShow(Boolean.TRUE);
		roleService.save(role);
		
		RoleResourcePermission roleResourcePermission = new RoleResourcePermission();
		
		roleResourcePermission.setResourceId(RESOURCE_ID);
		
		Set<Long> permissionIds = Sets.newHashSet();
		for (long i = PERMISSION_BEGIN; i <= PERMISSION_END; i++) {
			permissionIds.add(i);
		}
		
		roleResourcePermission.setPermissionIds(permissionIds);
		roleResourcePermission.setRole(role);
		roleResourcePermissionService.save(roleResourcePermission);
		
		Group group = groupService.findByName(groupName);
		if (EmptyUtil.isNull(group)) group = new Group();
		group.setName(groupName);
		group.setType(GroupType.user);
		group.setShow(Boolean.TRUE);
		groupService.save(group);
		
		if (isClear) automaticRemoveAllUser(groupName);
		groupRelationService.addUserGroupRelation(group.getId(), userIds.toArray(new Long[0]), false);
		
		Auth auth = authService.findByGroupId(group.getId());
		if (EmptyUtil.isNull(auth)) auth = new Auth();
		auth.setGroupId(group.getId());
		auth.setType(AuthType.user_group);
		
		Set<Long> roleIds = Sets.newLinkedHashSet();
		roleIds.add(role.getId());
		
		auth.setRoleIds(roleIds);
		authService.save(auth);
	}
	
	public void automaticRemoveAllUser(String groupName) {
		Group group = groupService.findByName(groupName);
		if (EmptyUtil.isNull(group)) return;
		
		List<Long> groupRelationIds = groupRelationService.findGroupRelationIds(group.getId());
		groupRelationService.delete(groupRelationIds);
	}
	
	public void automaticRemoveUser(String groupName, Set<Long> userIds) {
		Group group = groupService.findByName(groupName);
		if (EmptyUtil.isNull(group) || EmptyUtil.isCollectionEmpty(userIds)) return;
		
		List<Long> groupRelationIds = groupRelationService.findGroupRelationIds(group.getId(), userIds);
		groupRelationService.delete(groupRelationIds);
	}
}
