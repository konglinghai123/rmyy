package com.ewcms.security.role.service;

import com.ewcms.BaseIT;
import com.ewcms.security.permission.entity.Permission;
import com.ewcms.security.permission.entity.Role;
import com.ewcms.security.permission.entity.RoleResourcePermission;
import com.ewcms.security.permission.service.PermissionService;
import com.ewcms.security.permission.service.RoleService;
import com.ewcms.security.resource.entity.Resource;
import com.ewcms.security.resource.service.ResourceService;
import com.google.common.collect.Sets;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author wu_zhijun
 */
public class RoleResourcePermissionIT extends BaseIT {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private PermissionService permissionService;


    @Test
    public void testCascadeSave() {

        Resource resource1 = new Resource();
        resource1.setName("123");
        resource1.setIdentity("123");
        resourceService.save(resource1);

        Permission permission1 = new Permission();
        permission1.setName("123");
        permission1.setPermission("abc");
        permissionService.save(permission1);

        Permission permission2 = new Permission();
        permission2.setName("123");
        permission2.setPermission("abc");
        permissionService.save(permission2);

        Role role = new Role();
        role.setName("abc");
        role.setRole("abc");
        role.addResourcePermission(
                new RoleResourcePermission(
                        resource1.getId(),
                        Sets.newHashSet(permission1.getId(), permission2.getId())));


        roleService.save(role);
        clear();
        Role dbRole = roleService.findOne(role.getId());

        Assert.assertEquals(1, dbRole.getResourcePermissions().size());
        Assert.assertEquals(2, dbRole.getResourcePermissions().get(0).getPermissionIds().size());

    }


    @Test
    public void testCascadeDelete() {

        Resource resource1 = new Resource();
        resource1.setName("123");
        resource1.setIdentity("123");
        resourceService.save(resource1);

        Permission permission1 = new Permission();
        permission1.setName("123");
        permission1.setPermission("abc");
        permissionService.save(permission1);

        Permission permission2 = new Permission();
        permission2.setName("123");
        permission2.setPermission("abc");
        permissionService.save(permission2);

        Role role = new Role();
        role.setName("abc");
        role.setRole("abc");

        roleService.save(role);

        role.addResourcePermission(
                new RoleResourcePermission(
                        resource1.getId(),
                        Sets.newHashSet(permission1.getId(), permission2.getId())));

        clear();

        Role dbRole = roleService.findOne(role.getId());

        Assert.assertEquals(1, dbRole.getResourcePermissions().size());

        RoleResourcePermission roleResourcePermission = dbRole.getResourcePermissions().get(0);
        roleResourcePermission.getPermissionIds().remove(roleResourcePermission.getPermissionIds().iterator().next());
        //此处必须复制一份
        clear();

        dbRole = roleService.findOne(role.getId());

        Assert.assertEquals(1, dbRole.getResourcePermissions().get(0).getPermissionIds().size());

    }

}
