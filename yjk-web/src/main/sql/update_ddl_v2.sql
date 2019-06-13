/*
 * sp_system_parameter
 */
ALTER TABLE sp_system_parameter ADD COLUMN inject_declaration_limt bigint;
ALTER TABLE sp_system_parameter ALTER COLUMN inject_declaration_limt SET NOT NULL;
ALTER TABLE sp_system_parameter ADD COLUMN oral_declaration_limt bigint;
ALTER TABLE sp_system_parameter ALTER COLUMN oral_declaration_limt SET NOT NULL;
ALTER TABLE sp_system_parameter ADD COLUMN other_declaration_limt bigint;
ALTER TABLE sp_system_parameter ALTER COLUMN other_declaration_limt SET NOT NULL;
ALTER TABLE sp_system_parameter DROP COLUMN declaration_limt;

/*
 * zd_special_rule
 */
ALTER TABLE public.zd_special_rule ALTER COLUMN administration_id SET NOT NULL;

/*
 * sys_notice
 */
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
  
/*
 * re_review_main
 */
ALTER TABLE public.re_review_main ADD COLUMN formula_chinese bigint;
ALTER TABLE public.re_review_main ADD COLUMN formula_western bigint;
ALTER TABLE public.re_review_main ADD COLUMN general_name_chinese bigint;
ALTER TABLE public.re_review_main ADD COLUMN general_name_western bigint;

/*
 * re_zd_display_column
 */
CREATE SEQUENCE public.seq_re_zd_display_column_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_re_zd_display_column_id
  OWNER TO postgres;

CREATE TABLE public.re_zd_display_column
(
  id bigint NOT NULL,
  rule_cn_name character varying(255) NOT NULL,
  rule_name character varying(255) NOT NULL,
  CONSTRAINT re_zd_display_column_pkey PRIMARY KEY (id),
  CONSTRAINT uk_8shdwx0blgrarpicmtgbstcv1 UNIQUE (rule_name),
  CONSTRAINT uk_hjffgir51tqwaq7qbm9xd9yfn UNIQUE (rule_cn_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.re_zd_display_column
  OWNER TO postgres;

/*
 * re_zd_review_rule
 */
CREATE SEQUENCE public.seq_re_zd_review_rule_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_re_zd_review_rule_id
  OWNER TO postgres;
  
CREATE TABLE public.re_zd_review_rule
(
  id bigint NOT NULL,
  is_enabled boolean,
  review_main_id bigint NOT NULL,
  rule_cn_name character varying(255) NOT NULL,
  rule_name character varying(255) NOT NULL,
  weight integer,
  CONSTRAINT re_zd_review_rule_pkey PRIMARY KEY (id),
  CONSTRAINT uk_i9ffphp2wmhafuyh677e6ugab UNIQUE (rule_cn_name),
  CONSTRAINT uk_sq9jow78rg2sr79b6tumm6xev UNIQUE (rule_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.re_zd_review_rule
  OWNER TO postgres;
  
/*
 * re_review_process
 */
CREATE SEQUENCE public.seq_re_review_process_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_re_review_process_id
  OWNER TO postgres;
  
CREATE TABLE public.re_review_process
(
  id bigint NOT NULL,
  weight integer,
  reviewmain_id bigint,
  reviewrule_id bigint,
  CONSTRAINT re_review_process_pkey PRIMARY KEY (id),
  CONSTRAINT fk_49avd1oe4o0dwimaxue8nmkvl FOREIGN KEY (reviewmain_id)
      REFERENCES public.re_review_main (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_qbxt542y1bbhjjl30tmdnaw89 FOREIGN KEY (reviewrule_id)
      REFERENCES public.re_zd_review_rule (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.re_review_process
  OWNER TO postgres;

  
  