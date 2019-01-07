LTER TABLE sp_system_parameter ADD COLUMN inject_declaration_limt bigint;
ALTER TABLE sp_system_parameter ALTER COLUMN inject_declaration_limt SET NOT NULL;
ALTER TABLE sp_system_parameter ADD COLUMN oral_declaration_limt bigint;
ALTER TABLE sp_system_parameter ALTER COLUMN oral_declaration_limt SET NOT NULL;
ALTER TABLE sp_system_parameter ADD COLUMN other_declaration_limt bigint;
ALTER TABLE sp_system_parameter ALTER COLUMN other_declaration_limt SET NOT NULL;
ALTER TABLE sp_system_parameter DROP COLUMN declaration_limt;

ALTER TABLE public.zd_special_rule ALTER COLUMN administration_id SET NOT NULL;

CREATE SEQUENCE public.seq_sys_notice_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_sys_notice_id
  OWNER TO postgres;

CREATE TABLE public.sys_notice
(
  id bigint NOT NULL,
  content text,
  create_date timestamp without time zone,
  is_head boolean,
  is_release boolean,
  title character varying(255) NOT NULL,
  title_style character varying(255),
  update_date timestamp without time zone,
  weight integer,
  external_links text,
  CONSTRAINT sys_notice_pkey PRIMARY KEY (id),
  CONSTRAINT uk_nync618gafruemlsysyouv2bn UNIQUE (title)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.sys_notice
  OWNER TO postgres;