/**
 * 用户初始化，默认密码123456
 */
delete from sec_user;
insert into sec_user(id, username, email, mobile_phone_number, password, salt, create_date, status, is_admin, is_deleted, realname) values
(1, 'admin', 'admin@jict.org', '18970986887', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', now(), 'normal', 'true', 'false', '管理员')
;
select setval('seq_sec_user_id', 1);

/**
 * 组织机构初始化,由用户导入Excel生成
 */
delete from sec_organization;
insert into sec_organization(id, parent_id, parent_ids, weight, name, is_show, type) values
(1, 0, '0/', 1, '组织机构', 'true', 'department')
--,(2, 1, '0/1/', 1, '耳鼻喉头颈外科', 'true', 'department')
--,(3, 1, '0/1/', 2, '二部风湿免疫内分泌科', 'true', 'department')
--,(4, 1, '0/1/', 3, '二部呼吸消化科', 'true', 'department')
--,(5, 1, '0/1/', 4, '二部普外科', 'true', 'department')
--,(6, 1, '0/1/', 5, '二部神经内科', 'true', 'department')
--,(7, 1, '0/1/', 6, '二部外科', 'true', 'department')
--,(8, 1, '0/1/', 7, '二部心血管内科', 'true', 'department')
--,(9, 1, '0/1/', 8, '二部中西医结合科', 'true', 'department')
--,(10, 1, '0/1/', 9, '放疗室', 'true', 'department')
--,(11, 1, '0/1/', 10, '放射科', 'true', 'department')
--,(12, 1, '0/1/', 11, '妇科', 'true', 'department')
--,(13, 1, '0/1/', 12, '肝胆外科', 'true', 'department')
--,(14, 1, '0/1/', 13, '干部保健门诊', 'true', 'department')
--,(15, 1, '0/1/', 14, '骨科', 'true', 'department')
--,(16, 1, '0/1/', 15, '核医学科', 'true', 'department')
--,(17, 1, '0/1/', 16, '红谷分院筹小组', 'true', 'department')
--,(18, 1, '0/1/', 17, '呼吸内科', 'true', 'department')
--,(19, 1, '0/1/', 18, '急诊科', 'true', 'department')
--,(20, 1, '0/1/', 19, '检验科', 'true', 'department')
--,(21, 1, '0/1/', 20, '康复医学科', 'true', 'department')
--,(22, 1, '0/1/', 21, '口腔二科', 'true', 'department')
--,(23, 1, '0/1/', 22, '口腔一科', 'true', 'department')
--,(24, 1, '0/1/', 23, '老专家门诊', 'true', 'department')
--,(25, 1, '0/1/', 24, '临床医学研究所', 'true', 'department')
--,(26, 1, '0/1/', 25, '临床营养科', 'true', 'department')
--,(27, 1, '0/1/', 26, '麻醉科', 'true', 'department')
--,(28, 1, '0/1/', 27, '门诊部', 'true', 'department')
--,(29, 1, '0/1/', 28, '泌尿外科', 'true', 'department')
--,(30, 1, '0/1/', 29, '内分泌内科', 'true', 'department')
--,(31, 1, '0/1/', 30, '皮肤科', 'true', 'department')
--,(32, 1, '0/1/', 31, '普外一科', 'true', 'department')
--,(33, 1, '0/1/', 32, '其他', 'true', 'department')
--,(34, 1, '0/1/', 33, '器官移植科', 'true', 'department')
--,(35, 1, '0/1/', 34, '神经内科', 'true', 'department')
--,(36, 1, '0/1/', 35, '神经外科', 'true', 'department')
--,(37, 1, '0/1/', 36, '肾脏内科', 'true', 'department')
--,(38, 1, '0/1/', 37, '省行政中心医务室', 'true', 'department')
--,(39, 1, '0/1/', 38, '疼痛科', 'true', 'department')
--,(40, 1, '0/1/', 39, '体检科', 'true', 'department')
--,(41, 1, '0/1/', 40, '胃肠外科', 'true', 'department')
--,(42, 1, '0/1/', 41, '消化内科', 'true', 'department')
--,(43, 1, '0/1/', 42, '心理门诊', 'true', 'department')
--,(44, 1, '0/1/', 43, '心胸外科', 'true', 'department')
--,(45, 1, '0/1/', 44, '心血管病研究所', 'true', 'department')
--,(46, 1, '0/1/', 45, '心血管内科', 'true', 'department')
--,(47, 1, '0/1/', 46, '血管乳腺外科', 'true', 'department')
--,(48, 1, '0/1/', 47, '血液内科', 'true', 'department')
--,(49, 1, '0/1/', 48, '眼科', 'true', 'department')
--,(50, 1, '0/1/', 49, '一部二区', 'true', 'department')
--,(51, 1, '0/1/', 50, '一部一区', 'true', 'department')
--,(52, 1, '0/1/', 51, '医疗质量控制科', 'true', 'department')
--,(53, 1, '0/1/', 52, '医务处', 'true', 'department')
--,(54, 1, '0/1/', 53, '医院感染科', 'true', 'department')
--,(55, 1, '0/1/', 54, '应急管理办公室', 'true', 'department')
--,(56, 1, '0/1/', 55, '预防保健处', 'true', 'department')
--,(57, 1, '0/1/', 56, '整形颌面外科', 'true', 'department')
--,(58, 1, '0/1/', 57, '职工保健室', 'true', 'department')
--,(59, 1, '0/1/', 58, '肿瘤放疗科', 'true', 'department')
--,(60, 1, '0/1/', 59, '肿瘤科', 'true', 'department')
--,(61, 1, '0/1/', 60, '重症医学科', 'true', 'department')
--,(62, 1, '0/1/', 61, '药学部', 'true', 'department')
--,(63, 1, '0/1/', 62, 'CT室', 'true', 'department')
--,(64, 1, '0/1/', 63, 'PET/CT室', 'true', 'department')
--,(65, 1, '0/1/', 64, '病案科', 'true', 'department')
--,(66, 1, '0/1/', 65, '病理科', 'true', 'department')
--,(67, 1, '0/1/', 66, '产科', 'true', 'department')
--,(68, 1, '0/1/', 67, '超声诊断科', 'true', 'department')
--,(69, 1, '0/1/', 68, '创伤急救科', 'true', 'department')
--,(70, 1, '0/1/', 69, '磁共振室', 'true', 'department')
--,(71, 1, '0/1/', 70, '儿科', 'true', 'department')
;
select setval('seq_sec_organization_id', 1);

/**
 * 职务初始化
 */
delete from sec_job;
insert into sec_job(id, parent_id, parent_ids, weight, name, is_show) values
(1, 0, '0/', 1, '工作职务', 'true')
,(2, 1,'0/1/',1,'无','true')
;
select setval('seq_sec_job_id', 2);

/**
 * 菜单资源初始化
 */
delete from sec_resource;
insert into sec_resource(id, icon, identity, name, parent_id, parent_ids, is_show, style, url, weight) values
(1,'','','资源',0,'0/','true',null,'',1)
,(62,'ztree_file','','个人中心',1,'0/1/','true','accordion','',1)
,(102,'ztree_file','yjk','药剂管理',1,'0/1/','true','accordion','',2)
,(5,'ztree_file','security','权限管理',1,'0/1/','true','tree','',3)
,(6,'ztree_file','system','系统管理',1,'0/1/','true','tree','',4)
,(95,'ztree_file','monitor','监控管理',1,'0/1/','true',null,'',5)
,(9,'rmyy-security-resource-tree','resource','控制列表',5,'0/1/5/','true',null,'/security/resource/index',1)
,(49,'rmyy-security-group-tree','group','分组列表',5,'0/1/5/','true',null,'/security/group/group/index',2)
,(50,'ztree_file','','用户管理',5,'0/1/5/','true',null,'',4)
,(51,'rmyy-security-user-user-tree','user','用户列表',50,'0/1/5/50/','true',null,'/security/user/user/index',1)
,(52,'rmyy-security-user-online-tree','userOnline','在线用户列表',50,'0/1/5/50/','true',null,'/security/user/online/index',2)
,(53,'rmyy-security-user-statushistory-tree','userStatusHistory','状态变更历史列表',50,'0/1/5/50/','true',null,'/security/user/statusHistory/index',3)
,(54,'rmyy-security-user-lastonline-tree','userLastOnline','用户最后在线历史列表',50,'0/1/5/50/','true',null,'/security/user/lastOnline/index',4)
,(55,'ztree_file','','组织机构管理',5,'0/1/5/','true',null,'',5)
,(56,'rmyy-security-organization-organiation-tree','organization','组织机构列表',55,'0/1/5/55/','true',null,'/security/organization/organization/index',1)
,(57,'rmyy-security-organization-job-tree','job','工作职务列表',55,'0/1/5/55/','true',null,'/security/organization/job/index',2)
,(58,'ztree_file','','权限管理',5,'0/1/5/','true',null,'',6)
,(59,'rmyy-security-permission-permission-tree','permission','权限列表',58,'0/1/5/58/','true',null,'/security/permission/permission/index',1)
,(60,'rmyy-security-permission-role-tree','role','授权权限给角色',58,'0/1/5/58/','true',null,'/security/permission/role/index',2)
,(61,'rmyy-security-permission-auth-tree','auth','授权角色给实体',58,'0/1/5/58/','true',null,'/security/auth/index',3)
,(63,'rmyy-personal-memoranda-tree','','备忘录',62,'0/1/62/','true',null,'/personal/calendar/index',4)
/*,(64,'rmyy-personal-message-tree','','个人消息',62,'0/1/62/','true',null,'/personal/message/index',5)*/
,(65,'rmyy-security-switch-tree','','切换身份',5,'0/1/5/','true',null,'/security/user/runAs/index',3)
,(66,'rmyy-system-icon-tree','icon','图标管理',6,'0/1/6/','true',null,'/system/icon/index',1)
,(68,'rmyy-system-externalds-tree','externalds','数据源',6,'0/1/6/','true',null,'/system/externalds/index',3)
,(81,'ztree_file','','报表管理',6,'0/1/6/','true',null,'',6)
,(82,'rmyy-system-report-text-tree','textreport','文字报表',81,'0/1/6/81/','true',null,'/system/report/text/index',1)
,(83,'rmyy-system-report-chart-tree','chartreport','图型报表',81,'0/1/6/81/','true',null,'/system/report/chart/index',2)
,(84,'rmyy-system-report-category-tree','categoryreport','报表分类',81,'0/1/6/81/','true',null,'/system/report/category/index',3)
,(85,'rmyy-system-report-repository-tree','categoryreport','报表存储',81,'0/1/6/81/','true',null,'/system/report/repository/index',4)
,(86,'rmyy-system-report-show-tree','','报表集',81,'0/1/6/81/','true',null,'/system/report/show/index',5)
,(96,'rmyy-monitor-db-tree','db','数据库监控',95,'0/1/95/','true',null,'/monitor/druid/index.html',1)
,(97,'rmyy-monitor-ehcache-tree','ehcache','Ehcache监控',95,'0/1/95/','true',null,'/monitor/ehcache/index',2)
,(98,'rmyy-monitor-jvm-tree','jvm','JVM监控',95,'0/1/95/','true',null,'/monitor/jvm/index',3)
,(99,'rmyy-monitor-sql-tree','ql','SQL执行',95,'0/1/95/','true',null,'/monitor/db/sqlIndex',4)
,(100,'rmyy-monitor-jpaql-tree','ql','JPAQL执行',95,'0/1/95/','true',null,'/monitor/db/jpaqlIndex',5)
,(101,'rmyy-monitor-hibernate-tree','hibernate','Hibernate监控',95,'0/1/95/','true',null,'/monitor/hibernate/index',6)
,(104,'rmyy-yjk-commonname-tree','commonname','通用名',102,'0/1/102/','true',null,'/yjk/zd/commonname/index',1)
,(105,'rmyy-yjk-commonnamecontents-tree','commonnamecontents','大总目录',102,'0/1/102/','true',null,'/yjk/zd/commonnamecontents/index',2)
,(106,'rmyy-yjk-hospitalcontents-tree','hospitalcontents','院用目录',102,'0/1/102/','true',null,'/yjk/zd/hospitalcontents/index',3)
,(108,'rmyy-yjk-systemparameter-tree','systemparameter','系统参数',102,'0/1/102/','true',null,'/yjk/sp/systemparameter/index',5)
,(109,'rmyy-personal-medicine-tree','sb:drugform','新药申报',62,'0/1/62/','true',null,'/yjk/sb/drugform/index',3)
,(110,'rmyy-yjk-commonnamerule-tree','commonnamerule','匹配条件',102,'0/1/102/','true',null,'/yjk/zd/commonnamerule/index',6)
,(111,'rmyy-yjk-specialrule-tree','specialrule','特殊规则',102,'0/1/102/','true',null,'/yjk/zd/specialrule/index',7)
,(112,'rmyy-system-onlineeditor-tree','onlineEditor','在线编辑',6,'0/1/6/','true',null,'/system/editor/index',7)
,(113,'rmyy-system-staticresource-tree','staticResource','静态资源版本控制',6,'0/1/6/','true',null,'/system/staticresource/index',8)
,(115,'rmyy-yjk-departmentattribute-tree','departmentAttribute','科室属性',102,'0/1/102/','true',null,'/security/dictionary/departmentAttribute/index',10)
,(116,'rmyy-yjk-profession-tree','profession','执业类别',102,'0/1/102/','true',null,'/security/dictionary/profession/index',11)
,(117,'rmyy-yjk-technicaltitle-tree','technicalTitle','技术职称(资格)',102,'0/1/102/','true',null,'/security/dictionary/technicalTitle/index',12)
,(118,'rmyy-yjk-appointment-tree','appointment','聘任',102,'0/1/102/','true',null,'/security/dictionary/appointment/index',13)
,(119,'rmyy-security-permission-auth-tree','initaudit','新药初审',102,'0/1/102/','true',null,'/yjk/sb/initaudit/index',8)
,(120,'rmyy-yjk-reviewmain-tree','reviewmain','专家遴选',102,'0/1/102/','true',null,'/yjk/re/reviewmain/index',9)
,(121,'rmyy-system-notice-tree','notice','公告栏',6,'0/1/6/','true',null,'/system/notice/index',3)
;
select setval('seq_sec_resource_id', 121);

/**
 * 权限初始化
 */
delete from sec_permission;
insert into sec_permission(id, name, permission, description, is_show) values
(1, '所有', '*', '所有数据操作的权限', 'true')
,(2, '新增', 'create', '新增数据操作的权限', 'true')
,(3, '修改', 'update', '修改数据操作的权限', 'true')
,(4, '删除', 'delete', '删除数据操作的权限', 'true')
,(5, '查看', 'view', '查看数据操作的权限', 'true')
,(6, '审核', 'audit', '审核数据操作的权限', 'true')
;
select setval('seq_sec_permission_id', 6);

/**
 * 角色初始化
 */
delete from sec_role;
insert into sec_role(id, name, role, description, is_show) values
(1, '超级管理员', 'role_admin', '拥有所有权限', 'true')
;
select setval('seq_sec_role_id', 1);

/**
 * 角色/资源/权限初始化
 */
delete from sec_role_resource_permission;
insert into sec_role_resource_permission(id, role_id, resource_id, permission_ids) values
(1, 1, 1, '1')
;
select setval('seq_sec_role_resource_permission_id', 1);

/**
 * 授权初始化
 */
delete from sec_auth;
insert into sec_auth(id, organization_id, job_id, user_id, group_id, role_ids, type) values
(1, 0, 0, 1, 0, '1', 'user')
;
select setval('seq_sec_auth_id', 1);