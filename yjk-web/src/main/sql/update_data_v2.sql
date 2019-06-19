insert into sec_resource(id, icon, identity, name, parent_id, parent_ids, is_show, style, url, weight) values
(122,'ztree_file','reviewbaserule','评审基本规则',125,'0/1/102/125/','true','','/yjk/re/zd/reviewbaserule/index',2)
,(123,'ztree_file','displaycolumn','可显示的字段库',125,'0/1/102/125/','true','','/yjk/re/zd/displaycolumn/index',3)
,(124,'ztree_file','','申报管理',102,'0/1/102/','true','','',17)
,(125,'ztree_file','','评审管理',102,'0/1/102/','true','','',19)
,(126,'ztree_file','','字典库',102,'0/1/102/','true','','',20)
;
select setval('seq_sec_resource_id', 126);

update sec_resource set parent_id=124, parent_ids='0/1/102/124', weight=1 where id=119;
update sec_resource set parent_id=124, parent_ids='0/1/102/124', weight=2, name='申报设置' where id=108;
update sec_resource set parent_id=124, parent_ids='0/1/102/124', weight=3 where id=110;
update sec_resource set parent_id=124, parent_ids='0/1/102/124', weight=4 where id=111;

update sec_resource set parent_id=125, parent_ids='0/1/102/125', weight=1, name='评审设置' where id=120;

update sec_resource set parent_id=126, parent_ids='0/1/102/126', weight=1 where id=104;
update sec_resource set parent_id=126, parent_ids='0/1/102/126', weight=2 where id=105;
update sec_resource set parent_id=126, parent_ids='0/1/102/126', weight=3 where id=106;
update sec_resource set parent_id=126, parent_ids='0/1/102/126', weight=4 where id=115;
update sec_resource set parent_id=126, parent_ids='0/1/102/126', weight=5 where id=116;
update sec_resource set parent_id=126, parent_ids='0/1/102/126', weight=6 where id=117;
update sec_resource set parent_id=126, parent_ids='0/1/102/126', weight=7 where id=118;