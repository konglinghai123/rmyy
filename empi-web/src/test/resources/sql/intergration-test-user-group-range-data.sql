--用户
SET IDENTITY_INSERT sec_user ON;
insert into sec_user(id, user_name, email, mobile_phone_number, password, salt, create_date, status, is_admin, is_deleted, real_name) values
(1, 'admin', 'admin@jict.org', '18970986887', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', getdate(), 'normal', 'true', 'false', '管理员')
;
SET IDENTITY_INSERT sec_user OFF;

--分组
SET IDENTITY_INSERT sec_group ON;
insert into sec_group (id, name, type, is_show, default_group) values
(1, '默认', 'user', true, true)
,(2, '用户组', 'user', true, false)
;
SET IDENTITY_INSERT sec_group OFF;

SET IDENTITY_INSERT sec_group_relation ON;
insert into sec_group_relation (id, group_id, start_user_id, end_user_id)
    values(2, 2, 1, 10);
SET IDENTITY_INSERT sec_group_relation OFF;

--用户组授权
SET IDENTITY_INSERT sys_auth ON;
insert into sys_auth (id, organization_id, job_id, user_id, group_id, role_ids, type) values
(1, 0, 0, 0, 1, '1,3', 'user_group')
,(2, 0, 0, 0, 2, '2,3', 'user_group')
;
SET IDENTITY_INSERT sys_auth OFF;