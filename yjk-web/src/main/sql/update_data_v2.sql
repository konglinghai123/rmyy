insert into sec_resource(id, icon, identity, name, parent_id, parent_ids, is_show, style, url, weight) values
(122,'rmyy-yjk-reviewbaserule-tree','reviewbaserule','基本规则',125,'0/1/102/125/','true',null,'/yjk/re/zd/reviewbaserule/index',7)
,(123,'rmyy-yjk-displaycolumn-tree','displaycolumn','显示字段',125,'0/1/102/125/','true',null,'/yjk/re/zd/displaycolumn/index',8)
,(124,'ztree_file','','申报管理',102,'0/1/102/','true',null,'',17)
,(125,'ztree_file','','评审管理',102,'0/1/102/','true',null,'',19)
,(126,'ztree_file','','字典库',102,'0/1/102/','true',null,'',20)
,(127,'rmyy-yjk-voterecord-tree','re:voterecord','新药投票',62,'0/1/62/','true',null,'/yjk/re/voterecord/index',4)
,(128,'rmyy-yjk-uservote-tree','uservote','用户投票情况',125,'0/1/102/125/','true',null,'/yjk/re/uservote/index',5)
,(129,'rmyy-yjk-voteresult-tree','voteresult','投票监控',125,'0/1/102/125/','true',null,'/yjk/re/voteresult/index',4)
,(130,'rmyy-yjk-drugvote-tree','drugvote','药品投票情况',125,'0/1/102/125/','true',null,'/yjk/re/drugvote/index',6)
,(131,'rmyy-system-notice-tree','notice','公告栏',6,'0/1/6/','true',null,'/system/notice/index',1)
;
select setval('seq_sec_resource_id', 131);

update sec_resource set weight=2 where id=66;
update sec_resource set weight=3 where id=86;
update sec_resource set weight=4 where id=68;
update sec_resource set weight=5 where id=81;
update sec_resource set weight=6 where id=112;
update sec_resource set weight=7 where id=113;

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

update sec_resource set weight=5 where id=63;
update sec_resource set style='tree' where id=102;

update sb_drug_form set is_reviewed=false;

insert into sys_icon(id,css_class, description, icon_height, icon_type, identity, img_src, icon_left, sprite_src, icon_style, icon_top, icon_width) values
(91,'','系统管理-公告栏',48,'css_sprite','rmyy-system-notice','',0,'static/image/menu/system/notice.png','',0,48)
,(92,'','系统管理-公告栏',18,'css_sprite','rmyy-system-notice-tree','',0,'static/image/menu/system/notice.png','background-size:100%;',0,16)
,(93,'','药剂管理-可显示的字段库',48,'css_sprite','rmyy-yjk-displaycolumn','',0,'static/image/menu/yjk/displaycolumn.png','',0,48)
,(94,'','药剂管理-可显示的字段库',18,'css_sprite','rmyy-yjk-displaycolumn-tree','',0,'static/image/menu/yjk/displaycolumn.png','background-size:100%;',0,16)
,(95,'','药刘管理-评审基本规则',48,'css_sprite','rmyy-yjk-reviewbaserule','',0,'static/image/menu/yjk/reviewbaserule.png','',0,48)
,(96,'','药刘管理-评审基本规则',18,'css_sprite','rmyy-yjk-reviewbaserule-tree','',0,'static/image/menu/yjk/reviewbaserule.png','background-size:100%;',0,16)
,(97,'','药刘管理-专家投票',48,'css_sprite','rmyy-yjk-voterecord','',0,'static/image/menu/yjk/voterecord.png','',0,48)
,(98,'','药刘管理-专家投票',18,'css_sprite','rmyy-yjk-voterecord-tree','',0,'static/image/menu/yjk/voterecord.png','background-size:100%;',0,16)
,(99,'','药剂管理-投票监控',48,'css_sprite','rmyy-yjk-voteresult','',0,'static/image/menu/yjk/voteresult.png','',0,48)
,(100,'','药剂管理-投票监控',18,'css_sprite','rmyy-yjk-voteresult-tree','',0,'static/image/menu/yjk/voteresult.png','background-size:100%,',0,16)
,(101,'','药剂管理-用户投票情况',48,'css_sprite','rmyy-yjk-uservote','',0,'static/image/menu/yjk/uservote.png','',0,48)
,(102,'','药剂管理-用户投票情况',18,'css_sprite','rmyy-yjk-uservote-tree','',0,'static/image/menu/yjk/uservote.png','background-size:100%;',0,16)
,(103,'','药剂管理-药品投票情况',48,'css_sprite','rmyy-yjk-drugvote','',0,'static/image/menu/yjk/drugvote.png','',0,48)
,(104,'','药剂管理-药品投票情况',18,'css_sprite','rmyy-yjk-drugvote-tree','',0,'static/image/menu/yjk/drugvote.png','background-size:100%;',0,16)
;
select setval('seq_sys_icon_id', 104);

insert into re_zd_display_column(id, rule_cn_name,rule_name,width) values
(1,'药品通用名','drugForm.commonNameContents.extractCommonName',150)
,(2,'给药途径','drugForm.commonNameContents.administrationName',80)
,(3,'剂型','drugForm.commonNameContents.pill',80)
,(4,'适应症及药理作用','drugForm.indicationsEffect',200)
,(5,'中西药类别','drugForm.commonNameContents.drugCategoryInfo',80)
,(6,'申报科室','drugForm.departName',120)
,(7,'化药大类','drugForm.commonNameContents.common.chemicalBigCategory',120)
,(8,'化药小类','drugForm.commonNameContents.common.chemicalSubCategory',120)
,(9,'商品名','drugForm.commonNameContents.productName',80)
,(10,'数量','drugForm.commonNameContents.amount',50)
,(11,'生产厂家','drugForm.commonNameContents.manufacturer',230)
,(12,'中标价','drugForm.commonNameContents.purchasePrice',50)
,(13,'基药','drugForm.commonNameContents.heds',80)
,(14,'廉价短缺','drugForm.commonNameContents.cheapShortage',80)
,(15,'妇科','drugForm.commonNameContents.gynaecology',50)
,(16,'儿科','drugForm.commonNameContents.pediatric',50)
,(17,'急救','drugForm.commonNameContents.firstAid',50)
,(18,'医保目录编号','drugForm.commonNameContents.medicalDirNo',100)
,(19,'医保类别','drugForm.commonNameContents.medicalCategory',80)
,(20,'一致性评价','drugForm.commonNameContents.consistencyEvaluation',80)
,(21,'谈判品种','drugForm.commonNameContents.negotiationVariety',80)
,(22,'规格','drugForm.commonNameContents.specifications',150)
,(23,'用法用量','drugForm.dosage',150)
,(24,'申请理由','drugForm.declareReason',200)
,(25,'成分','drugForm.constituent',100)
,(26,'是否复方制剂','drugForm.preparationed',50)
;
select setval('seq_re_zd_display_column_id', 26);

insert into re_zd_review_base_rule(id, rule_cn_name,rule_name) values
(1,'新增通用名','addCommonName')
,(2,'新增规格/剂型','addSpecificationsAndPill')
,(3,'入选通用名的厂家遴选','addCommonNameManufacturer')
,(4,'入选规格/剂型的厂家遴选','addSpecificationsAndPillManufacturer')
;
select setval('seq_re_zd_review_base_rule_id', 4);

insert into re_zd_review_display_column(review_base_rule_id, display_column_id) values
(1,1)
,(1,2)
,(1,3)
,(1,4)
,(1,5)
,(1,6)
,(1,7)
,(1,8)
,(2,1)
,(2,2)
,(2,3)
,(2,4)
,(2,5)
,(2,6)
,(2,7)
,(2,8)
,(2,22)
,(3,1)
,(3,9)
,(3,3)
,(3,22)
,(3,10)
,(3,11)
,(3,12)
,(3,13)
,(3,14)
,(3,15)
,(3,16)
,(3,17)
,(3,18)
,(3,19)
,(3,20)
,(3,21)
,(3,5)
,(4,1)
,(4,9)
,(4,3)
,(4,22)
,(4,10)
,(4,11)
,(4,12)
,(4,13)
,(4,14)
,(4,15)
,(4,16)
,(4,17)
,(4,18)
,(4,19)
,(4,20)
,(4,21)
,(4,5)
;
