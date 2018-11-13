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
insert into zd_common_name_rule(id, is_deleted,rule_cn_name,rule_name,weight,is_enabled) values
(1, 'false', '通用名', 'common.commonName', 1,'true')
,(2, 'false', '给药途径', 'common.administration.id', 2,'true')
,(3, 'false', '给药途径', 'common.drugCategory', 3,'true')
,(4, 'false', '剂型', 'pill', 4, 'false')
,(5, 'false', '规格', 'specifications', 5, 'false')
,(6, 'false', '生产企业', 'manufacturer', 6, 'false')
,(7, 'false', '包装数量', 'amount', 7, 'false')
,(8, 'false', '包装单位', 'packageUnit', 8, 'false')
;
select setval('seq_zd_common_name_rule_id', 8);