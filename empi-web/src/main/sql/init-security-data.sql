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
,(9,'ewcms-security-resource-tree','resource','控制列表',5,'0/1/5/','true',null,'/security/resource/index',1)
,(49,'ewcms-security-group-tree','group','分组列表',5,'0/1/5/','true',null,'/security/group/group/index',2)
,(50,'ztree_file','','用户管理',5,'0/1/5/','true',null,'',4)
,(51,'ewcms-security-user-user-tree','user','用户列表',50,'0/1/5/50/','true',null,'/security/user/user/index',1)
,(52,'ewcms-security-user-online-tree','userOnline','在线用户列表',50,'0/1/5/50/','true',null,'/security/user/online/index',2)
,(53,'ewcms-security-user-statushistory-tree','userStatusHistory','状态变更历史列表',50,'0/1/5/50/','true',null,'/security/user/statusHistory/index',3)
,(54,'ewcms-security-user-lastonline-tree','userLastOnline','用户最后在线历史列表',50,'0/1/5/50/','true',null,'/security/user/lastOnline/index',4)
,(55,'ztree_file','','组织机构管理',5,'0/1/5/','true',null,'',5)
,(56,'ewcms-security-organization-organiation-tree','organization','组织机构列表',55,'0/1/5/55/','true',null,'/security/organization/organization/index',1)
,(57,'ewcms-security-organization-job-tree','job','工作职务列表',55,'0/1/5/55/','true',null,'/security/organization/job/index',2)
,(58,'ztree_file','','权限管理',5,'0/1/5/','true',null,'',6)
,(59,'ewcms-security-permission-permission-tree','permission','权限列表',58,'0/1/5/58/','true',null,'/security/permission/permission/index',1)
,(60,'ewcms-security-permission-role-tree','role','授权权限给角色',58,'0/1/5/58/','true',null,'/security/permission/role/index',2)
,(61,'ewcms-security-permission-auth-tree','auth','授权角色给实体',58,'0/1/5/58/','true',null,'/security/auth/index',3)
,(62,'ztree_file','','个人中心',1,'0/1/','true','accordion','',5)
,(63,'ewcms-personal-memoranda-tree','','备忘录',62,'0/1/62/','true',null,'/personal/memoranda/index',3)
,(64,'ewcms-personal-message-tree','','个人消息',62,'0/1/62/','true',null,'/personal/message/index',4)
,(65,'ewcms-security-switch-tree','','切换身份',5,'0/1/5/','true',null,'/security/user/runAs/index',3)
,(66,'ewcms-system-icon-tree','icon','图标管理',6,'0/1/6/','true',null,'/system/icon/index',1)
,(67,'ewcms-system-history-tree','','历史记录',6,'0/1/6/','true',null,'/system/history/index',2)
,(68,'ewcms-system-externalds-tree','externalds','数据源',6,'0/1/6/','true',null,'/system/externalds/index',3)
,(81,'ztree_file','','报表管理',6,'0/1/6/','true',null,'',6)
,(82,'ewcms-system-report-text-tree','textreport','文字报表',81,'0/1/6/81/','true',null,'/system/report/text/index',1)
,(83,'ewcms-system-report-chart-tree','chartreport','图型报表',81,'0/1/6/81/','true',null,'/system/report/chart/index',2)
,(84,'ewcms-system-report-category-tree','categoryreport','报表分类',81,'0/1/6/81/','true',null,'/system/report/category/index',3)
,(85,'ewcms-system-report-repository-tree','categoryreport','报表存储',81,'0/1/6/81/','true',null,'/system/report/repository/index',4)
,(86,'ewcms-system-report-show-tree','','报表集',81,'0/1/6/81/','true',null,'/system/report/show/index',5)
,(95,'ztree_file','monitor','监控管理',1,'0/1/','true',null,'',10)
,(96,'ewcms-monitor-db-tree','db','数据库监控',95,'0/1/95/','true',null,'/monitor/druid/index.html',1)
,(97,'ewcms-monitor-ehcache-tree','ehcache','Ehcache监控',95,'0/1/95/','true',null,'/monitor/ehcache/index',2)
,(98,'ewcms-monitor-jvm-tree','jvm','JVM监控',95,'0/1/95/','true',null,'/monitor/jvm/index',3)
,(99,'ewcms-monitor-sql-tree','ql','SQL执行',95,'0/1/95/','true',null,'/monitor/db/sqlIndex',4)
,(100,'ewcms-monitor-jpaql-tree','ql','JPAQL执行',95,'0/1/95/','true',null,'/monitor/db/jpaqlIndex',5)
,(101,'ewcms-monitor-hibernate-tree','hibernate','Hibernate监控',95,'0/1/95/','true',null,'/monitor/hibernate/index',6)
,(102,'ztree_file','cardmanage','卡证服务',1,'0/1/','true','accordion','',8)
,(103,'ewcms-system-icon-tree','patientbaseinfo','卡信息管理',102,'0/1/102/','true',NULL,'/empi/card/manage/patientbaseinfo/index',1)
,(104,'ztree_file','nation','民族',105,'0/1/105/','true',NULL,'/empi/dictionary/nation/index',2)
,(105,'ztree_file','dictionary','字典管理',1,'0/1/',	'true','accordion','',10)
,(106,'ztree_file','certificatetype','证件类型',105,'0/1/105/','true',NULL,'/empi/dictionary/certificatetype/index',1)
,(107,'ewcms-system-externalds-tree','practicecard','发诊疗卡',102,'0/1/102/','true',NULL,'/empi/card/manage/practicecard/index',2)
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