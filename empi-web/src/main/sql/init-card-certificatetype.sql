/**
 * 初始化证件类型
 */
delete from dict_certificate_type;

SET IDENTITY_INSERT dict_certificate_type ON;
insert into dict_certificate_type(id, name) values
(1,'身份证')
,(2,'军官证')
,(3,'护照')
,(4,'驾驶证')
;
SET IDENTITY_INSERT dict_certificate_type OFF;