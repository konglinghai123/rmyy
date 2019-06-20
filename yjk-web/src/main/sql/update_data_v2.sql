insert into sec_resource(id, icon, identity, name, parent_id, parent_ids, is_show, style, url, weight) values
(122,'rmyy-yjk-reviewbaserule-tree','reviewbaserule','评审基本规则',125,'0/1/102/125/','true',null,'/yjk/re/zd/reviewbaserule/index',2)
,(123,'rmyy-yjk-displaycolumn-tre','displaycolumn','可显示的字段库',125,'0/1/102/125/','true',null,'/yjk/re/zd/displaycolumn/index',3)
,(124,'ztree_file','','申报管理',102,'0/1/102/','true',null,'',17)
,(125,'ztree_file','','评审管理',102,'0/1/102/','true',null,'',19)
,(126,'ztree_file','','字典库',102,'0/1/102/','true',null,'',20)
,(127,'rmyy-yjk-voterecord-tree','voterecord','专家投票',125,'0/1/102/125/','true',null,'/yjk/re/voterecord/index',4)
;
select setval('seq_sec_resource_id', 127);

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

update sb_drug_form set is_reviewed=false;

insert into sys_icon(id,css_class, description, icon_height, icon_type, identity, img_src, icon_left, sprite_src, icon_style, icon_top, icon_width) values
(91,'','系统管理-公告栏',48,'css_sprite','rmyy-system-notice','',0,'static/image/menu/system/notice.png','',0,48)
,(92,'','系统管理-公告栏',18,'css_sprite','rmyy-system-notice-tree','',0,'static/image/menu/system/notice.png','background-size:100%;',0,16)
,(93,'','药剂管理-可显示的字段库',48,'css_sprite','rmyy-yjk-displaycolumn','',0,'static/image/menu/yjk/displaycolumn.png','',0;48)
,(94,'','药剂管理-可显示的字段库',18,'css_sprite','rmyy-yjk-displaycolumn-tree','',0,'static/image/menu/yjk/displaycolumn.png','background-size:100%;',0,16)
,(95,'','药刘管理-评审基本规则',48,'css_sprite','rmyy-yjk-reviewbaserule','',0,'static/image/menu/yjk/reviewbaserule.png','',0;48)
,(96,'','药刘管理-评审基本规则',18,'css_sprite','rmyy-yjk-reviewbaserule-tree','',0,'static/image/menu/yjk/reviewbaserule.png','background-size:100%;',0,16)
,(97,'','药刘管理-专家投票',48,'css_sprite','rmyy-yjk-voterecord','',0,'static/image/menu/yjk/voterecord.png','',0;48)
,(98,'','药刘管理-专家投票',18,'css_sprite','rmyy-yjk-voterecord-tree','',0,'static/image/menu/yjk/voterecord.png','background-size:100%;',0,16)
;
select setval('seq_sys_icon_id', 98);