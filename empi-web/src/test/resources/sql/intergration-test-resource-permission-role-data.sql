
--资源
SET IDENTITY_INSERT sec_resource ON;
insert into sec_resource(id, icon, id_entity, name, parent_id, parent_ids, is_show, style, url, weight) values
(1,'','test:test','测试列表',0,'0/','true','','/empi/test',1)
,(2,'','test:deleted','删除列表',0,'0/','false','','/empi/deleted',2)
,(3,'','test:view','查看列表',0,'0/','true','','/empi/view',3)
;
SET IDENTITY_INSERT sec_resource OFF;

--权限
SET IDENTITY_INSERT sec_permission ON;
insert into sec_permission(id, name, permission, description, is_show) values
(1, '所有', '*', '所有数据操作的权限', 'true')
,(2, '新增', 'create', '新增数据操作的权限', 'true')
,(3, '修改', 'update', '修改数据操作的权限', 'true')
,(4, '删除', 'delete', '删除数据操作的权限', 'true')
,(5, '查看', 'view', '查看数据操作的权限', 'true')
,(6, '不显示权限', 'none', '不显示的权限', 'false')
;
SET IDENTITY_INSERT sec_permission OFF;

--角色
SET IDENTITY_INSERT sec_role ON;
insert into sec_role(id, name, role, description, is_show) values
(1, '超级管理员', 'role_admin', '拥有所有权限', 'true')
,(2, '测试人员', 'role_test', '测试人员', 'true')
,(3, '不显示角色', 'role_none', '测试人员', 'false')
;
SET IDENTITY_INSERT sec_role OFF;


--角色--资源--权限
SET IDENTITY_INSERT sec_role_resource_permission ON;
insert into sec_role_resource_permission(id, role_id, resource_id, permission_ids) values
(1, 1, 1, '1,2,6')
,(2, 1, 2, '1,3,5')
,(3, 2, 3, '1,3,6')
,(4, 3, 1, '1,4,6')
;
SET IDENTITY_INSERT sec_role_resource_permission OFF;