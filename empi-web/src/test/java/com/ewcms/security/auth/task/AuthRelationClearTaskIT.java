package com.ewcms.security.auth.task;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.BaseIT;
import com.ewcms.common.Constants;
import com.ewcms.common.repository.hibernate.HibernateUtils;
import com.ewcms.security.auth.service.UserAuthService;
import com.ewcms.security.group.service.GroupService;
import com.ewcms.security.organization.service.JobService;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;

import java.util.Arrays;
import java.util.Set;

/**
 * 
 * @author wu_zhijun
 */
public class AuthRelationClearTaskIT extends BaseIT {

    @Autowired
    private AuthRelationClearTask authRelationClearTask;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private JobService jobService;

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


    @Test
    public void testClearDeletedAuthRelationForDefaultGroup() {
        executeSqlScript("sql/intergration-test-defualt-user-group-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(1, roles.size());

        groupService.delete(1L);
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(0, roles.size());
    }

    @Test
    public void testClearDeletedAuthRelationForUserGroup() {
        executeSqlScript("sql/intergration-test-user-group-unique-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(2, roles.size());

        groupService.delete(2L);
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(1, roles.size());
    }


    @Test
    public void testClearDeletedAuthRelationForOrganization() {

        executeSqlScript("sql/intergration-test-organization-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(1, roles.size());

        organizationService.delete(2L);
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(0, roles.size());
    }

    @Test
    public void testClearDeletedAuthRelationForOrganizationWithInherit() {
        executeSqlScript("sql/intergration-test-organization-with-inherit-data.sql", false);
        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(2, roles.size());

        organizationService.delete(1L);
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(1, roles.size());
    }


    @Test
    public void testClearDeletedAuthRelationForOrganizationGroup() {

        executeSqlScript("sql/intergration-test-organization-group-data.sql", false);
        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(2, roles.size());

        groupService.delete(3L);
        groupService.delete(4L);
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(1, roles.size());
    }


    @Test
    public void testClearDeletedAuthRelationForJob() {

        executeSqlScript("sql/intergration-test-job-data.sql", false);
        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(1, roles.size());

        jobService.delete(2L);
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(0, roles.size());
    }

    @Test
    public void testClearDeletedAuthRelationForJobWithInherit() {

        executeSqlScript("sql/intergration-test-job-with-inherit-data.sql", false);
        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(2, roles.size());

        jobService.delete(1L);
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(0, roles.size());
    }


    @Test
    public void testClearDeletedAuthRelationForOrganizationAndJob() {

        executeSqlScript("sql/intergration-test-organization-and-job-data.sql", false);
        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(1, roles.size());

        organizationService.delete(2L);
        jobService.delete(1L);
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(0, roles.size());
    }


    @Test
    public void testClearDeletedAuthRelationForAll() {
        executeSqlScript("sql/intergration-test-all-data.sql", false);
        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(2, roles.size());

        organizationService.delete(Arrays.asList(new Long[]{1L, 2L, 3L}));
        jobService.delete(Arrays.asList(new Long[]{1L, 2L, 3L}));
        groupService.delete(Arrays.asList(new Long[]{1L, 2L, 3L, 4L}));
        clear();
        authRelationClearTask.clearDeletedAuthRelation();
        clear();

        roles = userAuthService.findStringRoles(user);
        Assert.assertEquals(1, roles.size());
    }

}
