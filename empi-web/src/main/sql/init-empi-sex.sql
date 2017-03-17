/**
 * 初始化性别字典库
 */
delete from dict_sex;

insert into dict_sex(id, en_name,cn_name) values
('F','Femal','女'),
('M','Male','男'),
('O','Other','其他'),
('U','Unknown','未知'),
('A','Ambiguous','不明确'),
('N','Not applicable','不适用')
;