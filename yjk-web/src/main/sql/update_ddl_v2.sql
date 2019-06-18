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
 * sb_drug_form
 */

ALTER TABLE sb_drug_form ADD COLUMN is_reviewed boolean;
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
 * re_zd_review_base_rule
 */
CREATE SEQUENCE public.seq_re_zd_review_base_rule_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_re_zd_review_base_rule_id
  OWNER TO postgres;

  
CREATE TABLE public.re_zd_review_base_rule
(
  id bigint NOT NULL,
  rule_cn_name character varying(255) NOT NULL,
  rule_name character varying(255) NOT NULL,
  CONSTRAINT re_zd_review_base_rule_pkey PRIMARY KEY (id),
  CONSTRAINT uk_1nwx5ld4vku34dl9t6vw5uvog UNIQUE (rule_name),
  CONSTRAINT uk_gx4iodwu17l5kkpslibtjyr75 UNIQUE (rule_cn_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.re_zd_review_base_rule
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
  is_finished boolean,
  formula_chinese bigint,
  formula_western bigint,
  general_name_chinese bigint,
  general_name_western bigint,
  weight integer,
  reviewbaserule_id bigint,
  reviewmain_id bigint,
  CONSTRAINT re_review_process_pkey PRIMARY KEY (id),
  CONSTRAINT fk_49avd1oe4o0dwimaxue8nmkvl FOREIGN KEY (reviewmain_id)
      REFERENCES public.re_review_main (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ik1ux4qimp8m9f9fl7ym33ce4 FOREIGN KEY (reviewbaserule_id)
      REFERENCES public.re_zd_review_base_rule (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.re_review_process
  OWNER TO postgres;


/*
 * re_review_process_column
 */
CREATE TABLE public.re_review_process_column
(
  process_id bigint NOT NULL,
  column_id bigint NOT NULL,
  CONSTRAINT fk_fbkjcdhcyoywj5fimtm7koff3 FOREIGN KEY (process_id)
      REFERENCES public.re_review_process (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_j7ltrwq5f5y6vaudh7w0ncx0f FOREIGN KEY (column_id)
      REFERENCES public.re_zd_display_column (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_el5hi3fg4jjkyxqlqxxe5481j UNIQUE (process_id, column_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.re_review_process_column
  OWNER TO postgres;

/*
 * re_zd_review_display_column
 */
CREATE TABLE public.re_zd_review_display_column
(
  review_base_rule_id bigint NOT NULL,
  display_column_id bigint NOT NULL,
  CONSTRAINT fk_6bnrk64u869yry7nh18fykjo0 FOREIGN KEY (display_column_id)
      REFERENCES public.re_zd_display_column (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_qmyteui5ijp6eoro8e0u3n1xs FOREIGN KEY (review_base_rule_id)
      REFERENCES public.re_zd_review_base_rule (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_gpe2f8fg61m33psp4jttexj96 UNIQUE (review_base_rule_id, display_column_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.re_zd_review_display_column
  OWNER TO postgres;

  