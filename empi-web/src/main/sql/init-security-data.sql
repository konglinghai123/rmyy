/**
 * 用户初始化，默认密码123456
 */
delete from sec_user;

SET IDENTITY_INSERT sec_user ON;
insert into sec_user(id, user_name, email, mobile_phone_number, password, salt, create_date, status, is_admin, is_deleted, real_name) values
(1, 'admin', 'admin@jict.org', '18970986887', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', getdate(), 'normal', 'true', 'false', '管理员')
;
SET IDENTITY_INSERT sec_user OFF;

/**
 * 组织机构初始化
 */
delete from sec_organization;

SET IDENTITY_INSERT sec_organization ON;
insert into sec_organization(id, parent_id, parent_ids, weight, name, is_show) values(1, 0, '0/', 1, '组织机构', 'true');
SET IDENTITY_INSERT sec_organization OFF;

/**
 * 职务初始化
 */
delete from sec_job;

SET IDENTITY_INSERT sec_job ON;
insert into sec_job(id, parent_id, parent_ids, weight, name, is_show) values(1, 0, '0/', 1, '职务', 'true');
SET IDENTITY_INSERT sec_job OFF;

/**
 * 菜单资源初始化
 */
delete from sec_resource;

SET IDENTITY_INSERT sec_resource ON;
insert into sec_resource(id, icon, id_entity, name, parent_id, parent_ids, is_show, style, url, weight) values
(1,'','','资源',0,'0/','true',null,'',1)
,(5,'ztree_file','security','权限管理',1,'0/1/','true','tree','',8)
,(6,'ztree_file','system','系统管理',1,'0/1/','true','tree','',9)
,(9,'empi-security-resource-tree','resource','控制列表',5,'0/1/5/','true',null,'/security/resource',1)
,(49,'empi-security-group-tree','group','分组列表',5,'0/1/5/','true',null,'/security/group/group',2)
,(50,'ztree_file','','用户管理',5,'0/1/5/','true',null,'',4)
,(51,'empi-security-user-user-tree','user','用户列表',50,'0/1/5/50/','true',null,'/security/user/user',1)
,(52,'empi-security-user-online-tree','userOnline','在线用户列表',50,'0/1/5/50/','true',null,'/security/user/online',2)
,(53,'empi-security-user-statushistory-tree','userStatusHistory','状态变更历史列表',50,'0/1/5/50/','true',null,'/security/user/statusHistory',3)
,(54,'empi-security-user-lastonline-tree','userLastOnline','用户最后在线历史列表',50,'0/1/5/50/','true',null,'/security/user/lastOnline',4)
,(55,'ztree_file','','组织机构管理',5,'0/1/5/','true',null,'',5)
,(56,'empi-security-organization-organiation-tree','organization','组织机构列表',55,'0/1/5/55/','true',null,'/security/organization/organization',1)
,(57,'empi-security-organization-job-tree','job','工作职务列表',55,'0/1/5/55/','true',null,'/security/organization/job',2)
,(58,'ztree_file','','权限管理',5,'0/1/5/','true',null,'',6)
,(59,'empi-security-permission-permission-tree','permission','权限列表',58,'0/1/5/58/','true',null,'/security/permission/permission',1)
,(60,'empi-security-permission-role-tree','role','授权权限给角色',58,'0/1/5/58/','true',null,'/security/permission/role',2)
,(61,'empi-security-permission-auth-tree','auth','授权角色给实体',58,'0/1/5/58/','true',null,'/security/auth',3)
,(62,'ztree_file','','个人中心',1,'0/1/','true','accordion','',5)
,(63,'empi-personal-memoranda-tree','','备忘录',62,'0/1/62/','true',null,'/personal/memoranda',3)
,(64,'empi-personal-message-tree','','个人消息',62,'0/1/62/','true',null,'/personal/message',4)
,(65,'empi-security-switch-tree','','切换身份',5,'0/1/5/','true',null,'/security/user/runAs',3)
,(66,'empi-system-icon-tree','icon','图标管理',6,'0/1/6/','true',null,'/system/icon',1)
,(67,'empi-system-history-tree','','历史记录',6,'0/1/6/','true',null,'/system/history',2)
,(68,'empi-system-externalds-tree','externalds','数据源',6,'0/1/6/','true',null,'/system/externalds',3)
,(81,'ztree_file','','报表管理',6,'0/1/6/','true',null,'',6)
,(82,'empi-system-report-text-tree','textreport','文字报表',81,'0/1/6/81/','true',null,'/system/report/text',1)
,(83,'empi-system-report-chart-tree','chartreport','图型报表',81,'0/1/6/81/','true',null,'/system/report/chart',2)
,(84,'empi-system-report-category-tree','categoryreport','报表分类',81,'0/1/6/81/','true',null,'/system/report/category',3)
,(85,'empi-system-report-repository-tree','categoryreport','报表存储',81,'0/1/6/81/','true',null,'/system/report/repository',4)
,(86,'empi-system-report-show-tree','','报表集',81,'0/1/6/81/','true',null,'/system/report/show',5)
,(95,'ztree_file','monitor','监控管理',1,'0/1/','true',null,'',10)
,(96,'empi-monitor-db-tree','db','数据库监控',95,'0/1/95/','true',null,'/monitor/druid.html',1)
,(97,'empi-monitor-ehcache-tree','ehcache','Ehcache监控',95,'0/1/95/','true',null,'/monitor/ehcache',2)
,(98,'empi-monitor-jvm-tree','jvm','JVM监控',95,'0/1/95/','true',null,'/monitor/jvm',3)
,(99,'empi-monitor-sql-tree','ql','SQL执行',95,'0/1/95/','true',null,'/monitor/db/sqlIndex',4)
,(100,'empi-monitor-jpaql-tree','ql','JPAQL执行',95,'0/1/95/','true',null,'/monitor/db/jpaqlIndex',5)
,(101,'empi-monitor-hibernate-tree','hibernate','Hibernate监控',95,'0/1/95/','true',null,'/monitor/hibernate',6)
,(102,'ztree_file','cardmanage','卡证服务',1,'0/1/','true','accordion','',8)
,(103,'empi-card-patient-tree','patientbaseinfo','患者信息管理',102,'0/1/102/','true',NULL,'/empi/card/manage/patientbaseinfo',1)
,(104,'empi-dictionary-certificatetype-tree','','匹配规则管理',102,'0/1/102/','true',NULL,'/empi/card/manage/matchrule',2)
,(105,'empi-card-practicecard-tree','','诊疗卡管理',102,'0/1/102/','true',NULL,'/empi/card/manage/practicecard',3)
;
SET IDENTITY_INSERT sec_resource OFF;

/**
 * 权限初始化
 */
delete from sec_permission;

SET IDENTITY_INSERT sec_permission ON;
insert into sec_permission(id, name, permission, description, is_show) values
(1, '所有', '*', '所有数据操作的权限', 'true')
,(2, '新增', 'create', '新增数据操作的权限', 'true')
,(3, '修改', 'update', '修改数据操作的权限', 'true')
,(4, '删除', 'delete', '删除数据操作的权限', 'true')
,(5, '查看', 'view', '查看数据操作的权限', 'true')
,(6, '审核', 'audit', '审核数据操作的权限', 'true')
;
SET IDENTITY_INSERT sec_permission OFF;

/**
 * 角色初始化
 */
delete from sec_role;

SET IDENTITY_INSERT sec_role ON;
insert into sec_role(id, name, role, description, is_show) values
(1, '超级管理员', 'role_admin', '拥有所有权限', 'true')
;
SET IDENTITY_INSERT sec_role OFF;

/**
 * 角色/资源/权限初始化
 */
delete from sec_role_resource_permission;

SET IDENTITY_INSERT sec_role_resource_permission ON;
insert into sec_role_resource_permission(id, role_id, resource_id, permission_ids) values
(1, 1, 2, '1')
;
SET IDENTITY_INSERT sec_role_resource_permission OFF;

/**
 * 授权初始化
 */
delete from sec_auth;

SET IDENTITY_INSERT sec_auth ON;
insert into sec_auth(id, organization_id, job_id, user_id, group_id, role_ids, type) values
(1, 0, 0, 1, 0, '1', 'user')
;
SET IDENTITY_INSERT sec_auth OFF;