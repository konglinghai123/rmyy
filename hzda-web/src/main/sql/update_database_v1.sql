ALTER TABLE public.hzda_present_illness ADD COLUMN is_fatigue_no boolean;
ALTER TABLE public.hzda_anamnesis ADD COLUMN ciclosporin_bc character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN tacrolimus_bc character varying(255);

ALTER TABLE public.hzda_anamnesis ADD COLUMN shard_duration character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN aeds_duration character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN aluminum_preparation_duration character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN lithium_preparations_duration character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN heparin_duration character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN aromatase_inhibitor_duration character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN last_other_duration character varying(255);

CREATE TABLE public.hzda_risk_evaluation
(
  id bigint NOT NULL,
  general_information_id bigint NOT NULL,
  is_omt_1 boolean,
  is_omt_10 boolean,
  is_omt_11 boolean,
  is_omt_12 boolean,
  is_omt_13 boolean,
  is_omt_14 boolean,
  is_omt_15 boolean,
  is_omt_16 boolean,
  is_omt_17 boolean,
  is_omt_18 boolean,
  is_omt_19 boolean,
  is_omt_2 boolean,
  is_omt_3 boolean,
  is_omt_4 boolean,
  is_omt_5 boolean,
  is_omt_6 boolean,
  is_omt_7 boolean,
  is_omt_8 boolean,
  is_omt_9 boolean,
  organization_id bigint,
  user_id bigint NOT NULL,
  CONSTRAINT hzda_risk_evaluation_pkey PRIMARY KEY (id),
  CONSTRAINT uk_ricevgd2qjals9e1tojdqireb UNIQUE (general_information_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.hzda_risk_evaluation
  OWNER TO postgres;
  
  CREATE SEQUENCE public.hzda_risk_evaluation_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.hzda_risk_evaluation_id
  OWNER TO postgres;

ALTER TABLE public.hzda_general_information ADD COLUMN is_special_tab boolean;
ALTER TABLE public.hzda_general_information ADD COLUMN special_tab_number character varying(255);
ALTER TABLE public.hzda_general_information ADD COLUMN egfr character varying(255);

ALTER TABLE public.hzda_general_information DROP COLUMN age;
ALTER TABLE public.hzda_general_information DROP COLUMN osta;
ALTER TABLE public.hzda_general_information DROP COLUMN frax_main;
ALTER TABLE public.hzda_general_information DROP COLUMN frax_hipbone;
ALTER TABLE public.hzda_general_information DROP COLUMN weight;

ALTER TABLE public.hzda_risk_evaluation ADD COLUMN age bigint;
ALTER TABLE public.hzda_risk_evaluation ADD COLUMN osta character varying(255);
ALTER TABLE public.hzda_risk_evaluation ADD COLUMN frax_main character varying(255);
ALTER TABLE public.hzda_risk_evaluation ADD COLUMN frax_hipbone character varying(255);
ALTER TABLE public.hzda_risk_evaluation ADD COLUMN weight bigint;

ALTER TABLE public.hzda_fracture ADD COLUMN format_name character varying(255);
ALTER TABLE public.hzda_fracture ADD COLUMN upload_picture bytea;
ALTER TABLE public.hzda_bone_density ADD COLUMN id_no character varying(255);

CREATE SEQUENCE public.hzda_followup_time_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.hzda_followup_time_id
  OWNER TO postgres;

CREATE TABLE public.hzda_followup_time
(
  id bigint NOT NULL,
  general_information_id bigint NOT NULL,
  next_time date,
  organization_id bigint,
  is_sms boolean,
  sms_date date,
  is_tip boolean,
  user_id bigint NOT NULL,
  CONSTRAINT hzda_followup_time_pkey PRIMARY KEY (id),
  CONSTRAINT uk_g9aaxoupnxrsk8jd0lgka3lhc UNIQUE (general_information_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.hzda_followup_time
  OWNER TO postgres;
  

ALTER TABLE public.hzda_anamnesis ADD COLUMN aeds_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN aluminum_preparation_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN aromatase_inhibitor_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN calcitonin_time_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN calcium_time_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN ciclosporin_bc_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN ciclosporin_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN ciclosporin_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN ciclosporin_initial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN diphosphonate_time_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN dxm_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN dxm_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN dxm_initial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN glucocorticoid_other_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN glucocorticoid_other_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN glucocorticoid_other_initial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN heparin_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN imuran_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN imuran_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN imuran_initial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN lastother_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN lithium_preparations_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN mmf_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN mmf_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN mmfinitial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN mp_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN mp_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN mpinitial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN prednisolone_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN prednisolone_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN prednisolone_initial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN prednisone_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN prednisone_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN prednisone_initial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN serm_time_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN shard_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN tacrolimus_bc_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN tacrolimus_current_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN tacrolimus_duration_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN tacrolimus_initial_dose_unit character varying(255);
ALTER TABLE public.hzda_anamnesis ADD COLUMN vitamin_d_time_unit character varying(255);

