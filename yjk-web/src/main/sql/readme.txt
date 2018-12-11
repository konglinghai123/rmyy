数据库备份
pg_dump.exe --host localhost --username postgres --encoding UTF8 --file C:\ewcms_rc.dump ewcms_rc

数据库恢复
psql.exe --host localhost --username postgres --dbname ewcms_rc < E:\work\ewcms_rc.dump


//对已使用的数据库，在不删除数据库的情况下更新表字段结构

ALTER TABLE public.zd_special_rule ALTER COLUMN administration_id SET NOT NULL;