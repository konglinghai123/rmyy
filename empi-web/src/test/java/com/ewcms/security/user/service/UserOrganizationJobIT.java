package com.ewcms.security.user.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.security.organization.entity.Job;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.JobService;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;

/**
 * 
 * @author wu_zhijun
 */
public class UserOrganizationJobIT extends BaseUserIT {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private JobService jobService;

    @Test
    public void testCascadeSaveOrgnizationAndJob() {
        User user = createDefaultUser();

        Organization organization1 = new Organization();
        organization1.setName("test1");
        Organization organization2 = new Organization();
        organization2.setName("test2");
        organizationService.save(organization1);
        organizationService.save(organization2);

        Job job1 = new Job();
        job1.setName("test1");
        Job job2 = new Job();
        job2.setName("test2");
        jobService.save(job1);
        jobService.save(job2);

        user.addOrganizationJob(new UserOrganizationJob(organization1.getId(), null));
        user.addOrganizationJob(new UserOrganizationJob(organization2.getId(), job1.getId()));
        user.addOrganizationJob(new UserOrganizationJob(organization2.getId(), job2.getId()));
        userService.update(user);

        clear();

        user = userService.findOne(user.getId());

        Assert.assertEquals(3, user.getOrganizationJobs().size());
        Assert.assertEquals(organization1.getId(), user.getOrganizationJobs().get(0).getOrganizationId());

        Assert.assertEquals(organization2.getId(), user.getOrganizationJobs().get(1).getOrganizationId());
        Assert.assertEquals(organization2.getId(), user.getOrganizationJobs().get(2).getOrganizationId());
    }

    @Test
    public void testCascadeUpdateOrgnizationAndJob() {
        User user = createDefaultUser();

        Organization organization1 = new Organization();
        organization1.setName("test1");
        Organization organization2 = new Organization();
        organization2.setName("test2");
        organizationService.save(organization1);
        organizationService.save(organization2);

        user.addOrganizationJob(new UserOrganizationJob(organization1.getId(), null));
        user.addOrganizationJob(new UserOrganizationJob(organization2.getId(), null));
        userService.update(user);

        //清除缓存
        clear();

        user = userService.findOne(user.getId());
        Assert.assertEquals(2, user.getOrganizationJobs().size());
        Assert.assertEquals(organization1.getId(), user.getOrganizationJobs().get(0).getOrganizationId());
        Assert.assertEquals(organization2.getId(), user.getOrganizationJobs().get(1).getOrganizationId());
    }


    @Test
    public void testCascadeDeleteOrgnizationAndJob() {
        User user = createDefaultUser();

        Organization organization1 = new Organization();
        organization1.setName("test1");
        Organization organization2 = new Organization();
        organization2.setName("test2");
        organizationService.save(organization1);
        organizationService.save(organization2);


        Job job1 = new Job();
        job1.setName("test1");
        Job job2 = new Job();
        job2.setName("test2");
        jobService.save(job1);
        jobService.save(job2);

        user.addOrganizationJob(new UserOrganizationJob(organization1.getId(), null));
        user.addOrganizationJob(new UserOrganizationJob(organization2.getId(), job1.getId()));
        user.addOrganizationJob(new UserOrganizationJob(organization2.getId(), job2.getId()));
        userService.update(user);

        clear();

        user = userService.findOne(user.getId());
        user.getOrganizationJobs().remove(0);
        userService.update(user);

        clear();

        user = userService.findOne(user.getId());

        Assert.assertEquals(2, user.getOrganizationJobs().size());

        Assert.assertEquals(organization2.getId(), user.getOrganizationJobs().get(0).getOrganizationId());
        Assert.assertEquals(organization2.getId(), user.getOrganizationJobs().get(1).getOrganizationId());
    }
}
