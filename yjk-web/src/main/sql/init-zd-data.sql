/**
 * 给药途径
 */
delete from zd_administration;
insert into zd_administration(id, name) values
(1, '口服')
,(2, '注射')
,(3, '外用及其他')
;
select setval('seq_zd_administration_id', 3);

/**
 * 给药途径
 */
delete from zd_common_name_rule;
insert into zd_common_name_rule(id, is_deleted,rule_cn_name,rule_name,weight) values
(1, 'false', '通用名', 'common.commonName', 1)
,(2, 'false', '给药途径', 'common.administration.id', 2)
;
select setval('seq_zd_common_name_rule_id', 2);