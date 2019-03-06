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