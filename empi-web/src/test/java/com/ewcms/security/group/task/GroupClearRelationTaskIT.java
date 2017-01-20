package com.ewcms.security.group.task;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.BaseIT;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.security.group.entity.Group;
import com.ewcms.security.group.entity.GroupRelation;
import com.ewcms.security.group.entity.GroupType;
import com.ewcms.security.group.service.GroupRelationService;
import com.ewcms.security.group.service.GroupService;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.OrganizationService;

/**
 * 
 * @author wu_zhijun
 */
public class GroupClearRelationTaskIT extends BaseIT {

    @Autowired
    private GroupClearRelationTask groupClearRelationTask;

    @Autowired
    private GroupService groupService;
    
    @Autowired
    private GroupRelationService groupRelationService;
    
    @Autowired
    private OrganizationService organizationService;

    @Test
    public void testClearDeletedGroupRelation() {
        Group group = new Group();
        group.setType(GroupType.organization);
        group.setShow(false);
        group.setName("123");
        groupService.save(group);

        Organization organization1 = new Organization();
        organization1.setName("test1");
        Organization organization2 = new Organization();
        organization2.setName("test2");
        organizationService.save(organization1);
        organizationService.save(organization2);

        GroupRelation groupRelation1 = new GroupRelation();
        groupRelation1.setGroupId(group.getId());
        groupRelation1.setOrganizationId(organization1.getId());
        GroupRelation groupRelation2 = new GroupRelation();
        groupRelation2.setGroupId(group.getId());
        groupRelation2.setOrganizationId(organization2.getId());

        groupRelationService.save(groupRelation1);
        groupRelationService.save(groupRelation2);

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilter("groupId", SearchOperator.EQ, group.getId());
        Assert.assertEquals(2, groupRelationService.count());

        organizationService.delete(organization1);

        groupClearRelationTask.clearDeletedGroupRelation();
        Assert.assertEquals(1, groupRelationService.count());

        groupService.delete(group);
        groupClearRelationTask.clearDeletedGroupRelation();
        Assert.assertEquals(0, groupRelationService.count());

    }


}
