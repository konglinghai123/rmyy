/**
 * 初始化婚姻状况字典库
 */
delete from dict_marital;

insert into dict_marital(id, en_name,cn_name) values
('M','Married','已婚'),
('B','Unmarried','未婚'),
('A','Separated','分居'),
('D','Divorced','离婚'),
('S','Single','独身'),
('W','Widowed','丧偶'),
('C','Common law','普通法律'),
('G','Living together','同居'),
('P','Domestic partner','同性恋'),
('R','Registered domestic partner','已登记同性恋'),
('E','Legally Separated','合法分居'),
('N','Annulled','废止'),
('I','Interlocutory','中间'),
('U','Unknown','未知'),
('O','Other','其他'),
('T','Unreported','未报告')
;