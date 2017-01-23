package com.ewcms.security.auth.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.BaseIT;
import com.ewcms.common.Constants;
import com.ewcms.common.repository.hibernate.HibernateUtils;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;

import java.util.Set;

/**
 * 
 * @author wu_zhijun
 */
public class UserAuthServiceIT extends BaseIT {
	
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        setSqlScriptEncoding(Constants.ENCODING);
        executeSqlScript("sql/intergration-test-clear-all-data.sql", false);
        executeSqlScript("sql/intergration-test-resource-permission-role-data.sql", false);
        //clear cache
        userService.delete(1L);
        HibernateUtils.evictLevel1Cache(entityManager);
        HibernateUtils.evictLevel2Cache(entityManager);

    }

    ///////////////////////////用户相关
    @Test
    public void testUserAuth() {
        executeSqlScript("sql/intergration-test-user-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));
        Assert.assertTrue(roles.contains("test"));

        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertTrue(permissions.contains("test:view:all"));
        Assert.assertTrue(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }


    @Test
    public void testDefaultUserGroupAuth() {
        executeSqlScript("sql/intergration-test-defualt-user-group-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertFalse(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertFalse(permissions.contains("test:view:all"));
        Assert.assertFalse(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }


    @Test
    public void testUserGroupWithUniqueUserAuth() {
        executeSqlScript("sql/intergration-test-user-group-unique-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));
        Assert.assertTrue(roles.contains("test"));

        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertTrue(permissions.contains("test:view:all"));
        Assert.assertTrue(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }

    @Test
    public void testUserGroupWithRangeUserAuth() {
        executeSqlScript("sql/intergration-test-user-group-range-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));
        Assert.assertTrue(roles.contains("test"));

        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertTrue(permissions.contains("test:view:all"));
        Assert.assertTrue(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }


    /////////////////////////////组织机构相关
    @Test
    public void testOrganizationAuth() {

        executeSqlScript("sql/intergration-test-organization-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertFalse(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertFalse(permissions.contains("test:view:all"));
        Assert.assertFalse(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }

    @Test
    public void testOrganizationWithInheritAuth() {

        executeSqlScript("sql/intergration-test-organization-with-inherit-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertTrue(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertTrue(permissions.contains("test:view:all"));
        Assert.assertTrue(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }


    @Test
    public void testOrganizationGroupAuth() {

        executeSqlScript("sql/intergration-test-organization-group-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertTrue(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertTrue(permissions.contains("test:view:all"));
        Assert.assertTrue(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }


    /////////////////////////////工作职务相关
    @Test
    public void testJobAuth() {

        executeSqlScript("sql/intergration-test-job-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertFalse(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertFalse(permissions.contains("test:view:all"));
        Assert.assertFalse(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }

    @Test
    public void testJobWithInheritAuth() {
        executeSqlScript("sql/intergration-test-job-with-inherit-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertTrue(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertTrue(permissions.contains("test:view:all"));
        Assert.assertTrue(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }


    /////////////////////////////组织机构和工作职务混合
    @Test
    public void testOrganizationAndJobAuth() {
        executeSqlScript("sql/intergration-test-organization-and-job-data.sql", false);

        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertTrue(roles.contains("admin"));

        Assert.assertFalse(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertFalse(permissions.contains("test:view:all"));
        Assert.assertFalse(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }


    ///////////////////////////all

    @Test
    public void testAllAuth() {
        executeSqlScript("sql/intergration-test-all-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertTrue(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("test:test:all"));
        Assert.assertTrue(permissions.contains("test:test:save"));

        Assert.assertTrue(permissions.contains("test:view:all"));
        Assert.assertTrue(permissions.contains("test:view:update"));

        Assert.assertFalse(permissions.contains("test:deleted:all"));
        Assert.assertFalse(permissions.contains("test:test:none"));
    }

}
