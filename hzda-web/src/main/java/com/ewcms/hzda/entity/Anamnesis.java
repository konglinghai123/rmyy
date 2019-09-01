package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 既往史
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>generalInformationId:一般信息编号</li>
 * <li>pastHealth:既往体健</li>
 * <li>rheumatoidArthritis:类风湿关节炎</li>
 * <li>osteoarthritis:骨关节炎</li>
 * <li>sle:系统性红斑狼疮</li>
 * <li>ankylosingSpondylitis:强直性脊柱炎</li>
 * <li>autoimmuneLiverDisease:自身免疫性肝病</li>
 * <li>vasculitis:血管炎</li>
 * <li>primaryDesiccationSyndrome:原发性干燥综合征</li>
 * <li>systemicSclerosis:系统性硬化症</li>
 * <li>polymyositisDermatomyositis:多发性肌炎和皮肌炎</li>
 * <li>rheumatismOther:风湿系统疾病其他</li>
 * <li>rheumatismOtherDesc:风湿系统疾病其他说明</li>
 * <li>hyperparathyroidism:甲状旁腺功能亢进症</li>
 * <li>hyperthyroidism:甲状腺功能亢进症</li>
 * <li>hypogonadism:性腺功能减退</li>
 * <li>diabetes1:1型糖尿病</li>
 * <li>diabetes2:2型糖尿病</li>
 * <li>cushingSyndrome:库欣综合征</li>
 * <li>endocrineOther:内分泌及代谢系统疾病其他</li>
 * <li>endocrineDesc:内分泌及代谢系统疾病其他说明</li>
 * <li>hypertension:高血压</li>
 * <li>cahd:冠状动脉粥样硬化性心脏病</li>
 * <li>hyperlipidemia:高脂血症</li>
 * <li>loopOther:循环系统疾病说明</li>
 * <li>loopOtherDesc:循环系统疾病其他说明</li>
 * <li>copd:慢性阻塞性肺疾病</li>
 * <li>bronchialAsthma:支气管哮喘</li>
 * <li>breathOther:呼吸系统疾病其他</li>
 * <li>breathOtherDesc:呼吸系统疾病其他说明</li>
 * <li>anorexiaNervosa:神经性厌食</li>
 * <li>cerebralVascularDisease:脑血管病</li>
 * <li>myastheniaGravis:重症肌无力</li>
 * <li>nerveOther:神经及精神系统疾病其他</li>
 * <li>nerveOtherDesc:神经及精神系统疾病其他说明</li>
 * <li>cirrhosis:肝硬化</li>
 * <li>ibd:炎症性肠病(溃疡性结肠炎、克罗恩病)</li>
 * <li>celiacDisease:乳糜泻</li>
 * <li>breadbasketResection:胃部大切除术后</li>
 * <li>digestionOther:消化系统疾病其他</li>
 * <li>digestionOtherDesc:消化系统疾病其他说明</li>
 * <li>crif:慢性肾功能不全或衰竭</li>
 * <li>renalAmyloidosis:肾淀粉样变性</li>
 * <li>rtd:肾小管性疾病</li>
 * <li>pgn:原发性肾小球肾病</li>
 * <li>urinationOther:泌尿系统疾病其他</li>
 * <li>urinationOtherDesc:泌尿系统疾病其他说明</li>
 * <li>mds:骨髓增生异常综合征</li>
 * <li>leukemia:白血病</li>
 * <li>bloodOther:血液系统疾病其他</li>
 * <li>bloodOtherDesc:血液系统疾病其他说明</li>
 * <li>msf:马凡综合征</li>
 * <li>bronzeDiabetes:血色病</li>
 * <li>porphyria:卟啉病</li>
 * <li>brittleBones:成骨不全</li>
 * <li>congenitalOther:先天性及遗传性疾病其他</li>
 * <li>congenitalOtherDesc:先天性及遗传性疾病其他说明</li>
 * <li>pbt:原发性骨肿瘤</li>
 * <li>sbt:继发性骨肿瘤</li>
 * <li>mammaryCancer:乳腺癌</li>
 * <li>aids:艾滋病</li>
 * <li>otherOther:其他其他</li>
 * <li>otherOtherDesc:其他其他说明</li>
 * <li>refuseAnswer:拒绝回答</li>
 * <li>osteoporosisUnused:骨质疏松未使用过</li>
 * <li>calcium:钙剂</li>
 * <li>calciumLaw:钙剂是否规律用药</li>
 * <li>calciumDuration:钙剂用药总时间</li>
 * <li>calciumTimeUnit:钙剂用药总时间单位</li>
 * <li>vitaminD:维生素D及活性维生素D</li>
 * <li>vitaminDLaw:维生素D及活性维生素D是否规律用药</li>
 * <li>vitaminDDuration:维生素D及活性维生素D用药总时间</li>
 * <li>vitaminDTimeUnit:维生素D及活性维生素D总时间单位</li>
 * <li>diphosphonate:双磷酸盐</li>
 * <li>diphosphonateLaw:双磷酸盐是否规律用药</li>
 * <li>diphosphonateDuration:双磷酸盐用药总时间</li>
 * <li>diphosphonateTimeUnit:双磷酸盐用药总时间单位</li>
 * <li>calcitonin:降钙素类</li>
 * <li>calcitoninLaw:降钙素类否规律用药</li>
 * <li>calcitoninDuration:降钙素类用药总时间</li>
 * <li>calcitoninTimeUnit:降钙素类用药总时间单位</li>
 * <li>serm:选择性雌激素受体调节剂</li>
 * <li>sermLaw:选择性雌激素受体调节剂是否规律用药</li>
 * <li>sermDuration:选择性雌激素受体调节剂用药总时间</li>
 * <li>sermTimeUnit:选择性雌激素受体调节剂用药总时间单位</li>
 * <li>glucocorticoidUnused:肾上腺糖皮质激素未使用过</li>
 * <li>prednisone:泼尼松</li>
 * <li>prednisoneInitialDose:泼尼松起始剂量</li>
 * <li>prednisoneInitialDoseUnit:泼尼松起始剂量单位</li>
 * <li>prednisoneCurrentDose:泼尼松目前剂量</li>
 * <li>prednisoneCurrentDoseUnit:泼尼松目前剂量单位</li>
 * <li>prednisoneDuration:泼尼松用药总时间</li>
 * <li>prednisoneDurationUnit:泼尼松用药总时间</li>
 * <li>prednisolone:泼尼松龙</li>
 * <li>prednisoloneInitialDose:泼尼松龙起始剂量</li>
 * <li>prednisoloneInitialDoseUnit:泼尼松龙起始剂量单位</li>
 * <li>prednisoloneCurrentDose:泼尼松龙目前剂量</li>
 * <li>prednisoloneCurrentDoseUnit:泼尼松龙目前剂量单位</li>
 * <li>prednisoloneDuration:泼尼松龙用药总时间</li>
 * <li>prednisoloneDurationUnit:泼尼松龙用药总时间单位</li>
 * <li>mp:甲强龙</li>
 * <li>mpInitialDose:甲强龙起始剂量</li>
 * <li>mpInitialDoseUnit:甲强龙起始剂量单位</li>
 * <li>mpCurrentDose:甲强龙目前剂量</li>
 * <li>mpCurrentDoseUnit:甲强龙目前剂量单位</li>
 * <li>mpDuration:甲强龙用药总时间</li>
 * <li>mpDurationUnit:甲强龙用药总时间单位</li>
 * <li>dxm:地塞米松</li>
 * <li>dxmInitialDose:地塞米松起始剂量</li>
 * <li>dxmInitialDoseUnit:地塞米松起始剂量单位</li>
 * <li>dxmCurrentDose:地塞米松目前剂量</li>
 * <li>dxmCurrentDoseUnit:地塞米松目前剂量单位</li>
 * <li>dxmDuration:地塞米松用药总时间</li>
 * <li>dxmDurationUnit:地塞米松用药总时间单位</li>
 * <li>imuran:硫唑嘌呤</li>
 * <li>imuranInitialDose:硫唑嘌呤起始剂量</li>
 * <li>imuranInitialDoseUnit:硫唑嘌呤起始剂量单位</li>
 * <li>imuranCurrentDose:硫唑嘌呤目前剂量</li>
 * <li>imuranCurrentDoseUnit:硫唑嘌呤目前剂量单位</li>
 * <li>imuranDuration:硫唑嘌呤用药总时间</li>
 * <li>imuranDurationUnit:硫唑嘌呤用药总时间单位</li>
 * <li>ciclosporin:环孢素</li>
 * <li>ciclosporinInitialDose:环孢素起始剂量</li>
 * <li>ciclosporinInitialDoseUnit:环孢素起始剂量单位</li>
 * <li>ciclosporinCurrentDose:环孢素目前剂量</li>
 * <li>ciclosporinCurrentDoseUnit:环孢素目前剂量单位</li>
 * <li>ciclosporinDuration:环孢素用药总时间</li>
 * <li>ciclosporinDurationUnit:环孢素用药总时间单位</li>
 * <li>ciclosporinBC:环孢素血药浓度</li>
 * <li>ciclosporinBCUnit:环孢素血药浓度单位</li>
 * <li>tacrolimus:他克莫司</li>
 * <li>tacrolimusInitialDose:他克莫司起始剂量</li>
 * <li>tacrolimusInitialDoseUnit:他克莫司起始剂量单位</li>
 * <li>tacrolimusCurrentDose:他克莫司目前剂量</li>
 * <li>tacrolimusCurrentDoseUnit:他克莫司目前剂量单位</li>
 * <li>tacrolimusDuration:他克莫司用药总时间</li>
 * <li>tacrolimusDurationUnit:他克莫司用药总时间单位</li>
 * <li>tacrolimusBC:他克莫司血药浓度</li>
 * <li>tacrolimusBCUnit:他克莫司血药浓度单位</li>
 * <li>mmf:吗替麦考酚酯</li>
 * <li>mmfInitialDose:吗替麦考酚酯起始剂量</li>
 * <li>mmfInitialDoseUnit:吗替麦考酚酯起始剂量单位</li>
 * <li>mmfCurrentDose:吗替麦考酚酯目前剂量</li>
 * <li>mmfCurrentDoseUnit:吗替麦考酚酯目前剂量单位</li>
 * <li>mmfDuration:吗替麦考酚酯用药总时间</li>
 * <li>mmfDurationUnit:吗替麦考酚酯用药总时间单位</li>
 * <li>glucocorticoidOther:肾上腺糖皮质激素其他</li>
 * <li>glucocorticoidOtherInitialDose:肾上腺糖皮质激素其他起始剂量</li>
 * <li>glucocorticoidOtherInitialDoseUnit:肾上腺糖皮质激素其他起始剂量单位</li>
 * <li>glucocorticoidOtherCurrentDose:肾上腺糖皮质激素其他目前剂量</li>
 * <li>glucocorticoidOtherCurrentDoseUnit:肾上腺糖皮质激素其他目前剂量单位</li>
 * <li>glucocorticoidOtherDuration:肾上腺糖皮质激素其他用药总时间</li>
 * <li>glucocorticoidOtherDurationUnit:肾上腺糖皮质激素其他用药总时间单位</li>
 * <li>shard:性激素及其相关药物</li>
 * <li>shardDuration:性激素及其相关药物时长</li>
 * <li>shardDurationUnit:性激素及其相关药物时长单位</li>
 * <li>aeds:抗癫痫药物</li>
 * <li>aedsDuration:抗癫痫药物时长</li>
 * <li>aedsDurationUnit:抗癫痫药物时长单位</li>
 * <li>aluminumPreparation:铝制剂</li>
 * <li>aluminumPreparationDuration:铝制剂时长</li>
 * <li>aluminumPreparationDurationUnit:铝制剂时长单位</li>
 * <li>lithiumPreparations:锂制剂</li>
 * <li>lithiumPreparationsDuration:锂制剂时长</li>
 * <li>lithiumPreparationsDurationUnit:锂制剂时长单位</li>
 * <li>heparin:肝素</li>
 * <li>heparinDuration:肝素时长</li>
 * <li>heparinDurationUnit:肝素时长单位</li>
 * <li>aromataseInhibitor:芳重化酶抵制剂</li>
 * <li>aromataseInhibitorDuration:芳重化酶抵制剂时长</li>
 * <li>aromataseInhibitorDurationUnit:芳重化酶抵制剂时长单位</li>
 * <li>lastOther:其他</li>
 * <li>lastOtherDesc:其他说明</li>
 * <li>lastOtherDuration:其他时长</li>
 * <li>lastOtherDurationUnit:其他时长单位</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_anamnesis")
@SequenceGenerator(name = "seq", sequenceName = "hzda_anamnesis_id", allocationSize = 1)
public class Anamnesis extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 2951884781694274310L;

	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	@Column(name = "organization_id")
	private Long organizationId;
	@Formula(value = "(select s_o.name from sec_organization s_o where s_o.id=organization_id)")
	private String organizationName;
	@Column(name = "general_information_id", unique = true, nullable = false)
	private Long generalInformationId;

	@Column(name = "is_past_health")
	private Boolean pastHealth;
	@Column(name = "is_rheumatoid_arthritis")
	private Boolean rheumatoidArthritis;
	@Column(name = "is_osteoarthritis")
	private Boolean osteoarthritis;
	@Column(name = "is_sle")
	private Boolean sle;
	@Column(name = "is_ankylosing_spondylitis")
	private Boolean ankylosingSpondylitis;
	@Column(name = "is_autoimmune_liver_disease")
	private Boolean autoimmuneLiverDisease;
	@Column(name = "is_vasculitis")
	private Boolean vasculitis;
	@Column(name = "is_primary_desiccation_syndrome")
	private Boolean primaryDesiccationSyndrome;
	@Column(name = "is_systemic_sclerosis")
	private Boolean systemicSclerosis;
	@Column(name = "is_polymyositis_dermatomyositis")
	private Boolean polymyositisDermatomyositis;
	@Column(name = "is_rheumatism_other")
	private Boolean rheumatismOther;
	@Column(name = "rheumatism_other_desc")
	private String rheumatismOtherDesc;
	@Column(name = "is_hyperparathyroidism")
	private Boolean hyperparathyroidism;
	@Column(name = "is_hyperthyroidism")
	private Boolean hyperthyroidism;
	@Column(name = "is_hypogonadism")
	private Boolean hypogonadism;
	@Column(name = "is_diabetes1")
	private Boolean diabetes1;
	@Column(name = "is_diabetes2")
	private Boolean diabetes2;
	@Column(name = "is_cushing_syndrome")
	private Boolean cushingSyndrome;
	@Column(name = "is_endocrine_other")
	private Boolean endocrineOther;
	@Column(name = "endocrine_desc")
	private String endocrineDesc;
	@Column(name = "is_hypertension")
	private Boolean hypertension;
	@Column(name = "is_cahd")
	private Boolean cahd;
	@Column(name = "is_hyperlipidemia")
	private Boolean hyperlipidemia;
	@Column(name = "is_loop_other")
	private Boolean loopOther;
	@Column(name = "loop_other_desc")
	private String loopOtherDesc;
	@Column(name = "is_copd")
	private Boolean copd;
	@Column(name = "is_bronchial_asthma")
	private Boolean bronchialAsthma;
	@Column(name = "is_breath_other")
	private Boolean breathOther;
	@Column(name = "breath_other_desc")
	private String breathOtherDesc;
	@Column(name = "is_anorexia_nervosa")
	private Boolean anorexiaNervosa;
	@Column(name = "is_cerebral_vascular_disease")
	private Boolean cerebralVascularDisease;
	@Column(name = "is_myasthenia_gravis")
	private Boolean myastheniaGravis;
	@Column(name = "is_nerve_other")
	private Boolean nerveOther;
	@Column(name = "nerve_other_desc")
	private String nerveOtherDesc;
	@Column(name = "is_cirrhosis")
	private Boolean cirrhosis;
	@Column(name = "is_ibd")
	private Boolean ibd;
	@Column(name = "is_celiac_disease")
	private Boolean celiacDisease;
	@Column(name = "is_breadbasket_resection")
	private Boolean breadbasketResection;
	@Column(name = "is_digestion_other")
	private Boolean digestionOther;
	@Column(name = "digestion_other_desc")
	private String digestionOtherDesc;
	@Column(name = "is_crif")
	private Boolean crif;
	@Column(name = "is_renal_amyloidosis")
	private Boolean renalAmyloidosis;
	@Column(name = "is_rtd")
	private Boolean rtd;
	@Column(name = "is_pgn")
	private Boolean pgn;
	@Column(name = "is_urination_other")
	private Boolean urinationOther;
	@Column(name = "urination_other_desc")
	private String urinationOtherDesc;
	@Column(name = "is_mds")
	private Boolean mds;
	@Column(name = "is_leukemia")
	private Boolean leukemia;
	@Column(name = "is_blood_other")
	private Boolean bloodOther;
	@Column(name = "blood_other_desc")
	private String bloodOtherDesc;
	@Column(name = "is_msf")
	private Boolean msf;
	@Column(name = "is_bronze_diabetes")
	private Boolean bronzeDiabetes;
	@Column(name = "is_porphyria")
	private Boolean porphyria;
	@Column(name = "is_brittle_bones")
	private Boolean brittleBones;
	@Column(name = "is_congenital_other")
	private Boolean congenitalOther;
	@Column(name = "congenital_other_desc")
	private String congenitalOtherDesc;
	@Column(name = "is_pbt")
	private Boolean pbt;
	@Column(name = "is_sbt")
	private Boolean sbt;
	@Column(name = "is_mammary_cancer")
	private Boolean mammaryCancer;
	@Column(name = "is_aids")
	private Boolean aids;
	@Column(name = "is_other_other")
	private Boolean otherOther;
	@Column(name = "other_other_desc")
	private String otherOtherDesc;
	@Column(name = "is_refuse_answer")
	private Boolean refuseAnswer;
	@Column(name = "is_osteoporosis_unused")
	private Boolean osteoporosisUnused;
	@Column(name = "is_calcium")
	private Boolean calcium;
	@Column(name = "is_calcium_law")
	private Boolean calciumLaw;
	@Column(name = "calcium_duration")
	private String calciumDuration;
	@Column(name = "calcium_time_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum calciumTimeUnit;
	@Column(name = "is_vitamin_d")
	private Boolean vitaminD;
	@Column(name = "is_vitamin_d_law")
	private Boolean vitaminDLaw;
	@Column(name = "vitamin_d_duration")
	private String vitaminDDuration;
	@Column(name = "vitamin_d_time_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum vitaminDTimeUnit;
	@Column(name = "is_diphosphonate")
	private Boolean diphosphonate;
	@Column(name = "is_diphosphonate_law")
	private Boolean diphosphonateLaw;
	@Column(name = "diphosphonate_duration")
	private String diphosphonateDuration;
	@Column(name = "diphosphonate_time_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum diphosphonateTimeUnit;
	@Column(name = "is_calcitonin")
	private Boolean calcitonin;
	@Column(name = "is_calcitonin_law")
	private Boolean calcitoninLaw;
	@Column(name = "calcitonin_duration")
	private String calcitoninDuration;
	@Column(name = "calcitonin_time_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum calcitoninTimeUnit;
	@Column(name = "is_serm")
	private Boolean serm;
	@Column(name = "is_serm_law")
	private Boolean sermLaw;
	@Column(name = "serm_duration")
	private String sermDuration;
	@Column(name = "serm_time_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum sermTimeUnit;
	@Column(name = "is_glucocorticoid_unused")
	private Boolean glucocorticoidUnused;
	@Column(name = "is_prednisone")
	private Boolean prednisone;
	@Column(name = "prednisone_initial_dose")
	private Double prednisoneInitialDose;
	@Column(name = "prednisone_current_dose")
	private Double prednisoneCurrentDose;
	@Column(name = "prednisone_duration")
	private String prednisoneDuration;
	@Column(name = "is_prednisolone")
	private Boolean prednisolone;
	@Column(name = "prednisolone_initial_dose")
	private Double prednisoloneInitialDose;
	@Column(name = "prednisolone_current_dose")
	private Double prednisoloneCurrentDose;
	@Column(name = "prednisolone_duration")
	private String prednisoloneDuration;
	@Column(name = "is_mp")
	private Boolean mp;
	@Column(name = "mp_initial_dose")
	private Double mpInitialDose;
	@Column(name = "mp_current_dose")
	private Double mpCurrentDose;
	@Column(name = "mp_duration")
	private String mpDuration;
	@Column(name = "is_dxm")
	private Boolean dxm;
	@Column(name = "dxm_initial_dose")
	private Double dxmInitialDose;
	@Column(name = "dxm_current_dose")
	private Double dxmCurrentDose;
	@Column(name = "dxm_duration")
	private String dxmDuration;
	@Column(name = "is_imuran")
	private Boolean imuran;
	@Column(name = "imuran_initial_dose")
	private Double imuranInitialDose;
	@Column(name = "imuran_current_dose")
	private Double imuranCurrentDose;
	@Column(name = "imuran_duration")
	private String imuranDuration;
	@Column(name = "is_ciclosporin")
	private Boolean ciclosporin;
	@Column(name = "ciclosporin_initial_dose")
	private Double ciclosporinInitialDose;
	@Column(name = "ciclosporin_current_dose")
	private Double ciclosporinCurrentDose;
	@Column(name = "ciclosporin_duration")
	private String ciclosporinDuration;
	@Column(name = "ciclosporin_bc")
	private String ciclosporinBC;
	@Column(name = "is_tacrolimus")
	private Boolean tacrolimus;
	@Column(name = "tacrolimus_initial_dose")
	private Double tacrolimusInitialDose;
	@Column(name = "tacrolimus_current_dose")
	private Double tacrolimusCurrentDose;
	@Column(name = "tacrolimus_duration")
	private String tacrolimusDuration;
	@Column(name = "tacrolimus_bc")
	private String tacrolimusBC;
	@Column(name = "is_mmf")
	private Boolean mmf;
	@Column(name = "mmf_initial_dose")
	private Double mmfInitialDose;
	@Column(name = "mmf_current_dose")
	private Double mmfCurrentDose;
	@Column(name = "mmf_duration")
	private String mmfDuration;
	@Column(name = "is_glucocorticoid_other")
	private Boolean glucocorticoidOther;
	@Column(name = "glucocorticoid_other_initial_dose")
	private Double glucocorticoidOtherInitialDose;
	@Column(name = "glucocorticoid_other_current_dose")
	private Double glucocorticoidOtherCurrentDose;
	@Column(name = "glucocorticoid_other_duration")
	private String glucocorticoidOtherDuration;
	@Column(name = "is_shard")
	private Boolean shard;
	@Column(name = "shard_duration")
	private String shardDuration;
	@Column(name = "is_aeds")
	private Boolean aeds;
	@Column(name = "aeds_duration")
	private String aedsDuration;
	@Column(name = "is_aluminum_preparation")
	private Boolean aluminumPreparation;
	@Column(name = "aluminum_preparation_duration")
	private String aluminumPreparationDuration;
	@Column(name = "is_lithium_preparations")
	private Boolean lithiumPreparations;
	@Column(name = "lithium_preparations_duration")
	private String lithiumPreparationsDuration;
	@Column(name = "is_heparin")
	private Boolean heparin;
	@Column(name = "heparin_duration")
	private String heparinDuration;
	@Column(name = "is_aromatase_inhibitor")
	private Boolean aromataseInhibitor;
	@Column(name = "aromatase_inhibitor_duration")
	private String aromataseInhibitorDuration;
	@Column(name = "is_last_other")
	private Boolean lastOther;
	@Column(name = "last_other_desc")
	private String lastOtherDesc;
	@Column(name = "last_other_duration")
	private String lastOtherDuration;
	@Column(name = "prednisone_initial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum prednisoneInitialDoseUnit;
	@Column(name = "prednisone_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum prednisoneCurrentDoseUnit;
	@Column(name = "prednisone_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum prednisoneDurationUnit;
	@Column(name = "prednisolone_initial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum prednisoloneInitialDoseUnit;
	@Column(name = "prednisolone_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum prednisoloneCurrentDoseUnit;
	@Column(name = "prednisolone_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum prednisoloneDurationUnit;
	@Column(name = "mpInitial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum mpInitialDoseUnit;
	@Column(name = "mp_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum mpCurrentDoseUnit;
	@Column(name = "mp_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum mpDurationUnit;
	@Column(name = "dxm_initial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum dxmInitialDoseUnit;
	@Column(name = "dxm_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum dxmCurrentDoseUnit;
	@Column(name = "dxm_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum dxmDurationUnit;
	@Column(name = "imuran_initial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum imuranInitialDoseUnit;
	@Column(name = "imuran_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum imuranCurrentDoseUnit;
	@Column(name = "imuran_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum imuranDurationUnit;
	@Column(name = "ciclosporin_initial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum ciclosporinInitialDoseUnit;
	@Column(name = "ciclosporin_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum ciclosporinCurrentDoseUnit;
	@Column(name = "ciclosporin_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum ciclosporinDurationUnit;
	@Column(name = "ciclosporin_bc_unit")
	@Enumerated(EnumType.STRING)
	private BCEnum ciclosporinBCUnit;
	@Column(name = "tacrolimus_initial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum tacrolimusInitialDoseUnit;
	@Column(name = "tacrolimus_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum tacrolimusCurrentDoseUnit;
	@Column(name = "tacrolimus_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum tacrolimusDurationUnit;
	@Column(name = "tacrolimus_bc_unit")
	@Enumerated(EnumType.STRING)
	private BCEnum tacrolimusBCUnit;
	@Column(name = "mmfInitial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum mmfInitialDoseUnit;
	@Column(name = "mmf_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum mmfCurrentDoseUnit;
	@Column(name = "mmf_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum mmfDurationUnit;
	@Column(name = "glucocorticoid_other_initial_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum glucocorticoidOtherInitialDoseUnit;
	@Column(name = "glucocorticoid_other_current_dose_unit")
	@Enumerated(EnumType.STRING)
	private DoseUnitEnum glucocorticoidOtherCurrentDoseUnit;
	@Column(name = "glucocorticoid_other_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum glucocorticoidOtherDurationUnit;
	@Column(name = "shard_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum shardDurationUnit;
	@Column(name = "aeds_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum aedsDurationUnit;
	@Column(name = "aluminum_preparation_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum aluminumPreparationDurationUnit;
	@Column(name = "lithium_preparations_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum lithiumPreparationsDurationUnit;
	@Column(name = "heparin_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum heparinDurationUnit;
	@Column(name = "aromatase_inhibitor_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum aromataseInhibitorDurationUnit;
	@Column(name = "lastOther_duration_unit")
	@Enumerated(EnumType.STRING)
	private TimeUnitEnum lastOtherDurationUnit;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getGeneralInformationId() {
		return generalInformationId;
	}

	public void setGeneralInformationId(Long generalInformationId) {
		this.generalInformationId = generalInformationId;
	}

	public Boolean getPastHealth() {
		return pastHealth;
	}

	public void setPastHealth(Boolean pastHealth) {
		this.pastHealth = pastHealth;
	}

	public Boolean getRheumatoidArthritis() {
		return rheumatoidArthritis;
	}

	public void setRheumatoidArthritis(Boolean rheumatoidArthritis) {
		this.rheumatoidArthritis = rheumatoidArthritis;
	}

	public Boolean getOsteoarthritis() {
		return osteoarthritis;
	}

	public void setOsteoarthritis(Boolean osteoarthritis) {
		this.osteoarthritis = osteoarthritis;
	}

	public Boolean getSle() {
		return sle;
	}

	public void setSle(Boolean sle) {
		this.sle = sle;
	}

	public Boolean getAnkylosingSpondylitis() {
		return ankylosingSpondylitis;
	}

	public void setAnkylosingSpondylitis(Boolean ankylosingSpondylitis) {
		this.ankylosingSpondylitis = ankylosingSpondylitis;
	}

	public Boolean getAutoimmuneLiverDisease() {
		return autoimmuneLiverDisease;
	}

	public void setAutoimmuneLiverDisease(Boolean autoimmuneLiverDisease) {
		this.autoimmuneLiverDisease = autoimmuneLiverDisease;
	}

	public Boolean getVasculitis() {
		return vasculitis;
	}

	public void setVasculitis(Boolean vasculitis) {
		this.vasculitis = vasculitis;
	}

	public Boolean getPrimaryDesiccationSyndrome() {
		return primaryDesiccationSyndrome;
	}

	public void setPrimaryDesiccationSyndrome(Boolean primaryDesiccationSyndrome) {
		this.primaryDesiccationSyndrome = primaryDesiccationSyndrome;
	}

	public Boolean getSystemicSclerosis() {
		return systemicSclerosis;
	}

	public void setSystemicSclerosis(Boolean systemicSclerosis) {
		this.systemicSclerosis = systemicSclerosis;
	}

	public Boolean getPolymyositisDermatomyositis() {
		return polymyositisDermatomyositis;
	}

	public void setPolymyositisDermatomyositis(Boolean polymyositisDermatomyositis) {
		this.polymyositisDermatomyositis = polymyositisDermatomyositis;
	}

	public Boolean getRheumatismOther() {
		return rheumatismOther;
	}

	public void setRheumatismOther(Boolean rheumatismOther) {
		this.rheumatismOther = rheumatismOther;
	}

	public String getRheumatismOtherDesc() {
		return rheumatismOtherDesc;
	}

	public void setRheumatismOtherDesc(String rheumatismOtherDesc) {
		this.rheumatismOtherDesc = rheumatismOtherDesc;
	}

	public Boolean getHyperparathyroidism() {
		return hyperparathyroidism;
	}

	public void setHyperparathyroidism(Boolean hyperparathyroidism) {
		this.hyperparathyroidism = hyperparathyroidism;
	}

	public Boolean getHyperthyroidism() {
		return hyperthyroidism;
	}

	public void setHyperthyroidism(Boolean hyperthyroidism) {
		this.hyperthyroidism = hyperthyroidism;
	}

	public Boolean getHypogonadism() {
		return hypogonadism;
	}

	public void setHypogonadism(Boolean hypogonadism) {
		this.hypogonadism = hypogonadism;
	}

	public Boolean getDiabetes1() {
		return diabetes1;
	}

	public void setDiabetes1(Boolean diabetes1) {
		this.diabetes1 = diabetes1;
	}

	public Boolean getDiabetes2() {
		return diabetes2;
	}

	public void setDiabetes2(Boolean diabetes2) {
		this.diabetes2 = diabetes2;
	}

	public Boolean getCushingSyndrome() {
		return cushingSyndrome;
	}

	public void setCushingSyndrome(Boolean cushingSyndrome) {
		this.cushingSyndrome = cushingSyndrome;
	}

	public Boolean getEndocrineOther() {
		return endocrineOther;
	}

	public void setEndocrineOther(Boolean endocrineOther) {
		this.endocrineOther = endocrineOther;
	}

	public String getEndocrineDesc() {
		return endocrineDesc;
	}

	public void setEndocrineDesc(String endocrineDesc) {
		this.endocrineDesc = endocrineDesc;
	}

	public Boolean getHypertension() {
		return hypertension;
	}

	public void setHypertension(Boolean hypertension) {
		this.hypertension = hypertension;
	}

	public Boolean getCahd() {
		return cahd;
	}

	public void setCahd(Boolean cahd) {
		this.cahd = cahd;
	}

	public Boolean getHyperlipidemia() {
		return hyperlipidemia;
	}

	public void setHyperlipidemia(Boolean hyperlipidemia) {
		this.hyperlipidemia = hyperlipidemia;
	}

	public Boolean getLoopOther() {
		return loopOther;
	}

	public void setLoopOther(Boolean loopOther) {
		this.loopOther = loopOther;
	}

	public String getLoopOtherDesc() {
		return loopOtherDesc;
	}

	public void setLoopOtherDesc(String loopOtherDesc) {
		this.loopOtherDesc = loopOtherDesc;
	}

	public Boolean getCopd() {
		return copd;
	}

	public void setCopd(Boolean copd) {
		this.copd = copd;
	}

	public Boolean getBronchialAsthma() {
		return bronchialAsthma;
	}

	public void setBronchialAsthma(Boolean bronchialAsthma) {
		this.bronchialAsthma = bronchialAsthma;
	}

	public Boolean getBreathOther() {
		return breathOther;
	}

	public void setBreathOther(Boolean breathOther) {
		this.breathOther = breathOther;
	}

	public String getBreathOtherDesc() {
		return breathOtherDesc;
	}

	public void setBreathOtherDesc(String breathOtherDesc) {
		this.breathOtherDesc = breathOtherDesc;
	}

	public Boolean getAnorexiaNervosa() {
		return anorexiaNervosa;
	}

	public void setAnorexiaNervosa(Boolean anorexiaNervosa) {
		this.anorexiaNervosa = anorexiaNervosa;
	}

	public Boolean getCerebralVascularDisease() {
		return cerebralVascularDisease;
	}

	public void setCerebralVascularDisease(Boolean cerebralVascularDisease) {
		this.cerebralVascularDisease = cerebralVascularDisease;
	}

	public Boolean getMyastheniaGravis() {
		return myastheniaGravis;
	}

	public void setMyastheniaGravis(Boolean myastheniaGravis) {
		this.myastheniaGravis = myastheniaGravis;
	}

	public Boolean getNerveOther() {
		return nerveOther;
	}

	public void setNerveOther(Boolean nerveOther) {
		this.nerveOther = nerveOther;
	}

	public String getNerveOtherDesc() {
		return nerveOtherDesc;
	}

	public void setNerveOtherDesc(String nerveOtherDesc) {
		this.nerveOtherDesc = nerveOtherDesc;
	}

	public Boolean getCirrhosis() {
		return cirrhosis;
	}

	public void setCirrhosis(Boolean cirrhosis) {
		this.cirrhosis = cirrhosis;
	}

	public Boolean getIbd() {
		return ibd;
	}

	public void setIbd(Boolean ibd) {
		this.ibd = ibd;
	}

	public Boolean getCeliacDisease() {
		return celiacDisease;
	}

	public void setCeliacDisease(Boolean celiacDisease) {
		this.celiacDisease = celiacDisease;
	}

	public Boolean getBreadbasketResection() {
		return breadbasketResection;
	}

	public void setBreadbasketResection(Boolean breadbasketResection) {
		this.breadbasketResection = breadbasketResection;
	}

	public Boolean getDigestionOther() {
		return digestionOther;
	}

	public void setDigestionOther(Boolean digestionOther) {
		this.digestionOther = digestionOther;
	}

	public String getDigestionOtherDesc() {
		return digestionOtherDesc;
	}

	public void setDigestionOtherDesc(String digestionOtherDesc) {
		this.digestionOtherDesc = digestionOtherDesc;
	}

	public Boolean getCrif() {
		return crif;
	}

	public void setCrif(Boolean crif) {
		this.crif = crif;
	}

	public Boolean getRenalAmyloidosis() {
		return renalAmyloidosis;
	}

	public void setRenalAmyloidosis(Boolean renalAmyloidosis) {
		this.renalAmyloidosis = renalAmyloidosis;
	}

	public Boolean getRtd() {
		return rtd;
	}

	public void setRtd(Boolean rtd) {
		this.rtd = rtd;
	}

	public Boolean getPgn() {
		return pgn;
	}

	public void setPgn(Boolean pgn) {
		this.pgn = pgn;
	}

	public Boolean getUrinationOther() {
		return urinationOther;
	}

	public void setUrinationOther(Boolean urinationOther) {
		this.urinationOther = urinationOther;
	}

	public String getUrinationOtherDesc() {
		return urinationOtherDesc;
	}

	public void setUrinationOtherDesc(String urinationOtherDesc) {
		this.urinationOtherDesc = urinationOtherDesc;
	}

	public Boolean getMds() {
		return mds;
	}

	public void setMds(Boolean mds) {
		this.mds = mds;
	}

	public Boolean getLeukemia() {
		return leukemia;
	}

	public void setLeukemia(Boolean leukemia) {
		this.leukemia = leukemia;
	}

	public Boolean getBloodOther() {
		return bloodOther;
	}

	public void setBloodOther(Boolean bloodOther) {
		this.bloodOther = bloodOther;
	}

	public String getBloodOtherDesc() {
		return bloodOtherDesc;
	}

	public void setBloodOtherDesc(String bloodOtherDesc) {
		this.bloodOtherDesc = bloodOtherDesc;
	}

	public Boolean getMsf() {
		return msf;
	}

	public void setMsf(Boolean msf) {
		this.msf = msf;
	}

	public Boolean getBronzeDiabetes() {
		return bronzeDiabetes;
	}

	public void setBronzeDiabetes(Boolean bronzeDiabetes) {
		this.bronzeDiabetes = bronzeDiabetes;
	}

	public Boolean getPorphyria() {
		return porphyria;
	}

	public void setPorphyria(Boolean porphyria) {
		this.porphyria = porphyria;
	}

	public Boolean getBrittleBones() {
		return brittleBones;
	}

	public void setBrittleBones(Boolean brittleBones) {
		this.brittleBones = brittleBones;
	}

	public Boolean getCongenitalOther() {
		return congenitalOther;
	}

	public void setCongenitalOther(Boolean congenitalOther) {
		this.congenitalOther = congenitalOther;
	}

	public String getCongenitalOtherDesc() {
		return congenitalOtherDesc;
	}

	public void setCongenitalOtherDesc(String congenitalOtherDesc) {
		this.congenitalOtherDesc = congenitalOtherDesc;
	}

	public Boolean getPbt() {
		return pbt;
	}

	public void setPbt(Boolean pbt) {
		this.pbt = pbt;
	}

	public Boolean getSbt() {
		return sbt;
	}

	public void setSbt(Boolean sbt) {
		this.sbt = sbt;
	}

	public Boolean getMammaryCancer() {
		return mammaryCancer;
	}

	public void setMammaryCancer(Boolean mammaryCancer) {
		this.mammaryCancer = mammaryCancer;
	}

	public Boolean getAids() {
		return aids;
	}

	public void setAids(Boolean aids) {
		this.aids = aids;
	}

	public Boolean getOtherOther() {
		return otherOther;
	}

	public void setOtherOther(Boolean otherOther) {
		this.otherOther = otherOther;
	}

	public String getOtherOtherDesc() {
		return otherOtherDesc;
	}

	public void setOtherOtherDesc(String otherOtherDesc) {
		this.otherOtherDesc = otherOtherDesc;
	}

	public Boolean getRefuseAnswer() {
		return refuseAnswer;
	}

	public void setRefuseAnswer(Boolean refuseAnswer) {
		this.refuseAnswer = refuseAnswer;
	}

	public Boolean getOsteoporosisUnused() {
		return osteoporosisUnused;
	}

	public void setOsteoporosisUnused(Boolean osteoporosisUnused) {
		this.osteoporosisUnused = osteoporosisUnused;
	}

	public Boolean getCalcium() {
		return calcium;
	}

	public void setCalcium(Boolean calcium) {
		this.calcium = calcium;
	}

	public Boolean getCalciumLaw() {
		return calciumLaw;
	}

	public void setCalciumLaw(Boolean calciumLaw) {
		this.calciumLaw = calciumLaw;
	}

	public String getCalciumDuration() {
		return calciumDuration;
	}

	public void setCalciumDuration(String calciumDuration) {
		this.calciumDuration = calciumDuration;
	}

	public Boolean getVitaminD() {
		return vitaminD;
	}

	public void setVitaminD(Boolean vitaminD) {
		this.vitaminD = vitaminD;
	}

	public Boolean getVitaminDLaw() {
		return vitaminDLaw;
	}

	public void setVitaminDLaw(Boolean vitaminDLaw) {
		this.vitaminDLaw = vitaminDLaw;
	}

	public String getVitaminDDuration() {
		return vitaminDDuration;
	}

	public void setVitaminDDuration(String vitaminDDuration) {
		this.vitaminDDuration = vitaminDDuration;
	}

	public Boolean getDiphosphonate() {
		return diphosphonate;
	}

	public void setDiphosphonate(Boolean diphosphonate) {
		this.diphosphonate = diphosphonate;
	}

	public Boolean getDiphosphonateLaw() {
		return diphosphonateLaw;
	}

	public void setDiphosphonateLaw(Boolean diphosphonateLaw) {
		this.diphosphonateLaw = diphosphonateLaw;
	}

	public String getDiphosphonateDuration() {
		return diphosphonateDuration;
	}

	public void setDiphosphonateDuration(String diphosphonateDuration) {
		this.diphosphonateDuration = diphosphonateDuration;
	}

	public Boolean getCalcitonin() {
		return calcitonin;
	}

	public void setCalcitonin(Boolean calcitonin) {
		this.calcitonin = calcitonin;
	}

	public Boolean getCalcitoninLaw() {
		return calcitoninLaw;
	}

	public void setCalcitoninLaw(Boolean calcitoninLaw) {
		this.calcitoninLaw = calcitoninLaw;
	}

	public String getCalcitoninDuration() {
		return calcitoninDuration;
	}

	public void setCalcitoninDuration(String calcitoninDuration) {
		this.calcitoninDuration = calcitoninDuration;
	}

	public Boolean getSerm() {
		return serm;
	}

	public void setSerm(Boolean serm) {
		this.serm = serm;
	}

	public Boolean getSermLaw() {
		return sermLaw;
	}

	public void setSermLaw(Boolean sermLaw) {
		this.sermLaw = sermLaw;
	}

	public String getSermDuration() {
		return sermDuration;
	}

	public void setSermDuration(String sermDuration) {
		this.sermDuration = sermDuration;
	}

	public Boolean getGlucocorticoidUnused() {
		return glucocorticoidUnused;
	}

	public void setGlucocorticoidUnused(Boolean glucocorticoidUnused) {
		this.glucocorticoidUnused = glucocorticoidUnused;
	}

	public Boolean getPrednisone() {
		return prednisone;
	}

	public void setPrednisone(Boolean prednisone) {
		this.prednisone = prednisone;
	}

	public Double getPrednisoneInitialDose() {
		return prednisoneInitialDose;
	}

	public void setPrednisoneInitialDose(Double prednisoneInitialDose) {
		this.prednisoneInitialDose = prednisoneInitialDose;
	}

	public Double getPrednisoneCurrentDose() {
		return prednisoneCurrentDose;
	}

	public void setPrednisoneCurrentDose(Double prednisoneCurrentDose) {
		this.prednisoneCurrentDose = prednisoneCurrentDose;
	}

	public String getPrednisoneDuration() {
		return prednisoneDuration;
	}

	public void setPrednisoneDuration(String prednisoneDuration) {
		this.prednisoneDuration = prednisoneDuration;
	}

	public Boolean getPrednisolone() {
		return prednisolone;
	}

	public void setPrednisolone(Boolean prednisolone) {
		this.prednisolone = prednisolone;
	}

	public Double getPrednisoloneInitialDose() {
		return prednisoloneInitialDose;
	}

	public void setPrednisoloneInitialDose(Double prednisoloneInitialDose) {
		this.prednisoloneInitialDose = prednisoloneInitialDose;
	}

	public Double getPrednisoloneCurrentDose() {
		return prednisoloneCurrentDose;
	}

	public void setPrednisoloneCurrentDose(Double prednisoloneCurrentDose) {
		this.prednisoloneCurrentDose = prednisoloneCurrentDose;
	}

	public String getPrednisoloneDuration() {
		return prednisoloneDuration;
	}

	public void setPrednisoloneDuration(String prednisoloneDuration) {
		this.prednisoloneDuration = prednisoloneDuration;
	}

	public Boolean getMp() {
		return mp;
	}

	public void setMp(Boolean mp) {
		this.mp = mp;
	}

	public Double getMpInitialDose() {
		return mpInitialDose;
	}

	public void setMpInitialDose(Double mpInitialDose) {
		this.mpInitialDose = mpInitialDose;
	}

	public Double getMpCurrentDose() {
		return mpCurrentDose;
	}

	public void setMpCurrentDose(Double mpCurrentDose) {
		this.mpCurrentDose = mpCurrentDose;
	}

	public String getMpDuration() {
		return mpDuration;
	}

	public void setMpDuration(String mpDuration) {
		this.mpDuration = mpDuration;
	}

	public Boolean getDxm() {
		return dxm;
	}

	public void setDxm(Boolean dxm) {
		this.dxm = dxm;
	}

	public Double getDxmInitialDose() {
		return dxmInitialDose;
	}

	public void setDxmInitialDose(Double dxmInitialDose) {
		this.dxmInitialDose = dxmInitialDose;
	}

	public Double getDxmCurrentDose() {
		return dxmCurrentDose;
	}

	public void setDxmCurrentDose(Double dxmCurrentDose) {
		this.dxmCurrentDose = dxmCurrentDose;
	}

	public String getDxmDuration() {
		return dxmDuration;
	}

	public void setDxmDuration(String dxmDuration) {
		this.dxmDuration = dxmDuration;
	}

	public Boolean getImuran() {
		return imuran;
	}

	public void setImuran(Boolean imuran) {
		this.imuran = imuran;
	}

	public Double getImuranInitialDose() {
		return imuranInitialDose;
	}

	public void setImuranInitialDose(Double imuranInitialDose) {
		this.imuranInitialDose = imuranInitialDose;
	}

	public Double getImuranCurrentDose() {
		return imuranCurrentDose;
	}

	public void setImuranCurrentDose(Double imuranCurrentDose) {
		this.imuranCurrentDose = imuranCurrentDose;
	}

	public String getImuranDuration() {
		return imuranDuration;
	}

	public void setImuranDuration(String imuranDuration) {
		this.imuranDuration = imuranDuration;
	}

	public Boolean getCiclosporin() {
		return ciclosporin;
	}

	public void setCiclosporin(Boolean ciclosporin) {
		this.ciclosporin = ciclosporin;
	}

	public Double getCiclosporinInitialDose() {
		return ciclosporinInitialDose;
	}

	public void setCiclosporinInitialDose(Double ciclosporinInitialDose) {
		this.ciclosporinInitialDose = ciclosporinInitialDose;
	}

	public Double getCiclosporinCurrentDose() {
		return ciclosporinCurrentDose;
	}

	public void setCiclosporinCurrentDose(Double ciclosporinCurrentDose) {
		this.ciclosporinCurrentDose = ciclosporinCurrentDose;
	}

	public String getCiclosporinDuration() {
		return ciclosporinDuration;
	}

	public void setCiclosporinDuration(String ciclosporinDuration) {
		this.ciclosporinDuration = ciclosporinDuration;
	}

	public Boolean getTacrolimus() {
		return tacrolimus;
	}

	public void setTacrolimus(Boolean tacrolimus) {
		this.tacrolimus = tacrolimus;
	}

	public Double getTacrolimusInitialDose() {
		return tacrolimusInitialDose;
	}

	public void setTacrolimusInitialDose(Double tacrolimusInitialDose) {
		this.tacrolimusInitialDose = tacrolimusInitialDose;
	}

	public Double getTacrolimusCurrentDose() {
		return tacrolimusCurrentDose;
	}

	public void setTacrolimusCurrentDose(Double tacrolimusCurrentDose) {
		this.tacrolimusCurrentDose = tacrolimusCurrentDose;
	}

	public String getTacrolimusDuration() {
		return tacrolimusDuration;
	}

	public void setTacrolimusDuration(String tacrolimusDuration) {
		this.tacrolimusDuration = tacrolimusDuration;
	}

	public Boolean getMmf() {
		return mmf;
	}

	public void setMmf(Boolean mmf) {
		this.mmf = mmf;
	}

	public Double getMmfInitialDose() {
		return mmfInitialDose;
	}

	public void setMmfInitialDose(Double mmfInitialDose) {
		this.mmfInitialDose = mmfInitialDose;
	}

	public Double getMmfCurrentDose() {
		return mmfCurrentDose;
	}

	public void setMmfCurrentDose(Double mmfCurrentDose) {
		this.mmfCurrentDose = mmfCurrentDose;
	}

	public String getMmfDuration() {
		return mmfDuration;
	}

	public void setMmfDuration(String mmfDuration) {
		this.mmfDuration = mmfDuration;
	}

	public Boolean getGlucocorticoidOther() {
		return glucocorticoidOther;
	}

	public void setGlucocorticoidOther(Boolean glucocorticoidOther) {
		this.glucocorticoidOther = glucocorticoidOther;
	}

	public Double getGlucocorticoidOtherInitialDose() {
		return glucocorticoidOtherInitialDose;
	}

	public void setGlucocorticoidOtherInitialDose(Double glucocorticoidOtherInitialDose) {
		this.glucocorticoidOtherInitialDose = glucocorticoidOtherInitialDose;
	}

	public Double getGlucocorticoidOtherCurrentDose() {
		return glucocorticoidOtherCurrentDose;
	}

	public void setGlucocorticoidOtherCurrentDose(Double glucocorticoidOtherCurrentDose) {
		this.glucocorticoidOtherCurrentDose = glucocorticoidOtherCurrentDose;
	}

	public String getGlucocorticoidOtherDuration() {
		return glucocorticoidOtherDuration;
	}

	public void setGlucocorticoidOtherDuration(String glucocorticoidOtherDuration) {
		this.glucocorticoidOtherDuration = glucocorticoidOtherDuration;
	}

	public Boolean getShard() {
		return shard;
	}

	public void setShard(Boolean shard) {
		this.shard = shard;
	}

	public Boolean getAeds() {
		return aeds;
	}

	public void setAeds(Boolean aeds) {
		this.aeds = aeds;
	}

	public Boolean getAluminumPreparation() {
		return aluminumPreparation;
	}

	public void setAluminumPreparation(Boolean aluminumPreparation) {
		this.aluminumPreparation = aluminumPreparation;
	}

	public Boolean getLithiumPreparations() {
		return lithiumPreparations;
	}

	public void setLithiumPreparations(Boolean lithiumPreparations) {
		this.lithiumPreparations = lithiumPreparations;
	}

	public Boolean getHeparin() {
		return heparin;
	}

	public void setHeparin(Boolean heparin) {
		this.heparin = heparin;
	}

	public Boolean getAromataseInhibitor() {
		return aromataseInhibitor;
	}

	public void setAromataseInhibitor(Boolean aromataseInhibitor) {
		this.aromataseInhibitor = aromataseInhibitor;
	}

	public Boolean getLastOther() {
		return lastOther;
	}

	public void setLastOther(Boolean lastOther) {
		this.lastOther = lastOther;
	}

	public String getLastOtherDesc() {
		return lastOtherDesc;
	}

	public void setLastOtherDesc(String lastOtherDesc) {
		this.lastOtherDesc = lastOtherDesc;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getCiclosporinBC() {
		return ciclosporinBC;
	}

	public void setCiclosporinBC(String ciclosporinBC) {
		this.ciclosporinBC = ciclosporinBC;
	}

	public String getTacrolimusBC() {
		return tacrolimusBC;
	}

	public void setTacrolimusBC(String tacrolimusBC) {
		this.tacrolimusBC = tacrolimusBC;
	}

	public String getShardDuration() {
		return shardDuration;
	}

	public void setShardDuration(String shardDuration) {
		this.shardDuration = shardDuration;
	}

	public String getAedsDuration() {
		return aedsDuration;
	}

	public void setAedsDuration(String aedsDuration) {
		this.aedsDuration = aedsDuration;
	}

	public String getAluminumPreparationDuration() {
		return aluminumPreparationDuration;
	}

	public void setAluminumPreparationDuration(String aluminumPreparationDuration) {
		this.aluminumPreparationDuration = aluminumPreparationDuration;
	}

	public String getLithiumPreparationsDuration() {
		return lithiumPreparationsDuration;
	}

	public void setLithiumPreparationsDuration(String lithiumPreparationsDuration) {
		this.lithiumPreparationsDuration = lithiumPreparationsDuration;
	}

	public String getHeparinDuration() {
		return heparinDuration;
	}

	public void setHeparinDuration(String heparinDuration) {
		this.heparinDuration = heparinDuration;
	}

	public String getAromataseInhibitorDuration() {
		return aromataseInhibitorDuration;
	}

	public void setAromataseInhibitorDuration(String aromataseInhibitorDuration) {
		this.aromataseInhibitorDuration = aromataseInhibitorDuration;
	}

	public String getLastOtherDuration() {
		return lastOtherDuration;
	}

	public void setLastOtherDuration(String lastOtherDuration) {
		this.lastOtherDuration = lastOtherDuration;
	}

	public TimeUnitEnum getCalciumTimeUnit() {
		return calciumTimeUnit;
	}

	public void setCalciumTimeUnit(TimeUnitEnum calciumTimeUnit) {
		this.calciumTimeUnit = calciumTimeUnit;
	}

	public TimeUnitEnum getVitaminDTimeUnit() {
		return vitaminDTimeUnit;
	}

	public void setVitaminDTimeUnit(TimeUnitEnum vitaminDTimeUnit) {
		this.vitaminDTimeUnit = vitaminDTimeUnit;
	}

	public TimeUnitEnum getDiphosphonateTimeUnit() {
		return diphosphonateTimeUnit;
	}

	public void setDiphosphonateTimeUnit(TimeUnitEnum diphosphonateTimeUnit) {
		this.diphosphonateTimeUnit = diphosphonateTimeUnit;
	}

	public TimeUnitEnum getCalcitoninTimeUnit() {
		return calcitoninTimeUnit;
	}

	public void setCalcitoninTimeUnit(TimeUnitEnum calcitoninTimeUnit) {
		this.calcitoninTimeUnit = calcitoninTimeUnit;
	}

	public TimeUnitEnum getSermTimeUnit() {
		return sermTimeUnit;
	}

	public void setSermTimeUnit(TimeUnitEnum sermTimeUnit) {
		this.sermTimeUnit = sermTimeUnit;
	}

	public DoseUnitEnum getPrednisoneInitialDoseUnit() {
		return prednisoneInitialDoseUnit;
	}

	public void setPrednisoneInitialDoseUnit(DoseUnitEnum prednisoneInitialDoseUnit) {
		this.prednisoneInitialDoseUnit = prednisoneInitialDoseUnit;
	}

	public DoseUnitEnum getPrednisoneCurrentDoseUnit() {
		return prednisoneCurrentDoseUnit;
	}

	public void setPrednisoneCurrentDoseUnit(DoseUnitEnum prednisoneCurrentDoseUnit) {
		this.prednisoneCurrentDoseUnit = prednisoneCurrentDoseUnit;
	}

	public TimeUnitEnum getPrednisoneDurationUnit() {
		return prednisoneDurationUnit;
	}

	public void setPrednisoneDurationUnit(TimeUnitEnum prednisoneDurationUnit) {
		this.prednisoneDurationUnit = prednisoneDurationUnit;
	}

	public DoseUnitEnum getPrednisoloneInitialDoseUnit() {
		return prednisoloneInitialDoseUnit;
	}

	public void setPrednisoloneInitialDoseUnit(DoseUnitEnum prednisoloneInitialDoseUnit) {
		this.prednisoloneInitialDoseUnit = prednisoloneInitialDoseUnit;
	}

	public DoseUnitEnum getPrednisoloneCurrentDoseUnit() {
		return prednisoloneCurrentDoseUnit;
	}

	public void setPrednisoloneCurrentDoseUnit(DoseUnitEnum prednisoloneCurrentDoseUnit) {
		this.prednisoloneCurrentDoseUnit = prednisoloneCurrentDoseUnit;
	}

	public TimeUnitEnum getPrednisoloneDurationUnit() {
		return prednisoloneDurationUnit;
	}

	public void setPrednisoloneDurationUnit(TimeUnitEnum prednisoloneDurationUnit) {
		this.prednisoloneDurationUnit = prednisoloneDurationUnit;
	}

	public DoseUnitEnum getMpInitialDoseUnit() {
		return mpInitialDoseUnit;
	}

	public void setMpInitialDoseUnit(DoseUnitEnum mpInitialDoseUnit) {
		this.mpInitialDoseUnit = mpInitialDoseUnit;
	}

	public DoseUnitEnum getMpCurrentDoseUnit() {
		return mpCurrentDoseUnit;
	}

	public void setMpCurrentDoseUnit(DoseUnitEnum mpCurrentDoseUnit) {
		this.mpCurrentDoseUnit = mpCurrentDoseUnit;
	}

	public TimeUnitEnum getMpDurationUnit() {
		return mpDurationUnit;
	}

	public void setMpDurationUnit(TimeUnitEnum mpDurationUnit) {
		this.mpDurationUnit = mpDurationUnit;
	}

	public DoseUnitEnum getDxmInitialDoseUnit() {
		return dxmInitialDoseUnit;
	}

	public void setDxmInitialDoseUnit(DoseUnitEnum dxmInitialDoseUnit) {
		this.dxmInitialDoseUnit = dxmInitialDoseUnit;
	}

	public DoseUnitEnum getDxmCurrentDoseUnit() {
		return dxmCurrentDoseUnit;
	}

	public void setDxmCurrentDoseUnit(DoseUnitEnum dxmCurrentDoseUnit) {
		this.dxmCurrentDoseUnit = dxmCurrentDoseUnit;
	}

	public TimeUnitEnum getDxmDurationUnit() {
		return dxmDurationUnit;
	}

	public void setDxmDurationUnit(TimeUnitEnum dxmDurationUnit) {
		this.dxmDurationUnit = dxmDurationUnit;
	}

	public DoseUnitEnum getImuranInitialDoseUnit() {
		return imuranInitialDoseUnit;
	}

	public void setImuranInitialDoseUnit(DoseUnitEnum imuranInitialDoseUnit) {
		this.imuranInitialDoseUnit = imuranInitialDoseUnit;
	}

	public DoseUnitEnum getImuranCurrentDoseUnit() {
		return imuranCurrentDoseUnit;
	}

	public void setImuranCurrentDoseUnit(DoseUnitEnum imuranCurrentDoseUnit) {
		this.imuranCurrentDoseUnit = imuranCurrentDoseUnit;
	}

	public TimeUnitEnum getImuranDurationUnit() {
		return imuranDurationUnit;
	}

	public void setImuranDurationUnit(TimeUnitEnum imuranDurationUnit) {
		this.imuranDurationUnit = imuranDurationUnit;
	}

	public DoseUnitEnum getCiclosporinInitialDoseUnit() {
		return ciclosporinInitialDoseUnit;
	}

	public void setCiclosporinInitialDoseUnit(DoseUnitEnum ciclosporinInitialDoseUnit) {
		this.ciclosporinInitialDoseUnit = ciclosporinInitialDoseUnit;
	}

	public DoseUnitEnum getCiclosporinCurrentDoseUnit() {
		return ciclosporinCurrentDoseUnit;
	}

	public void setCiclosporinCurrentDoseUnit(DoseUnitEnum ciclosporinCurrentDoseUnit) {
		this.ciclosporinCurrentDoseUnit = ciclosporinCurrentDoseUnit;
	}

	public TimeUnitEnum getCiclosporinDurationUnit() {
		return ciclosporinDurationUnit;
	}

	public void setCiclosporinDurationUnit(TimeUnitEnum ciclosporinDurationUnit) {
		this.ciclosporinDurationUnit = ciclosporinDurationUnit;
	}

	public BCEnum getCiclosporinBCUnit() {
		return ciclosporinBCUnit;
	}

	public void setCiclosporinBCUnit(BCEnum ciclosporinBCUnit) {
		this.ciclosporinBCUnit = ciclosporinBCUnit;
	}

	public DoseUnitEnum getTacrolimusInitialDoseUnit() {
		return tacrolimusInitialDoseUnit;
	}

	public void setTacrolimusInitialDoseUnit(DoseUnitEnum tacrolimusInitialDoseUnit) {
		this.tacrolimusInitialDoseUnit = tacrolimusInitialDoseUnit;
	}

	public DoseUnitEnum getTacrolimusCurrentDoseUnit() {
		return tacrolimusCurrentDoseUnit;
	}

	public void setTacrolimusCurrentDoseUnit(DoseUnitEnum tacrolimusCurrentDoseUnit) {
		this.tacrolimusCurrentDoseUnit = tacrolimusCurrentDoseUnit;
	}

	public TimeUnitEnum getTacrolimusDurationUnit() {
		return tacrolimusDurationUnit;
	}

	public void setTacrolimusDurationUnit(TimeUnitEnum tacrolimusDurationUnit) {
		this.tacrolimusDurationUnit = tacrolimusDurationUnit;
	}

	public BCEnum getTacrolimusBCUnit() {
		return tacrolimusBCUnit;
	}

	public void setTacrolimusBCUnit(BCEnum tacrolimusBCUnit) {
		this.tacrolimusBCUnit = tacrolimusBCUnit;
	}

	public DoseUnitEnum getMmfInitialDoseUnit() {
		return mmfInitialDoseUnit;
	}

	public void setMmfInitialDoseUnit(DoseUnitEnum mmfInitialDoseUnit) {
		this.mmfInitialDoseUnit = mmfInitialDoseUnit;
	}

	public DoseUnitEnum getMmfCurrentDoseUnit() {
		return mmfCurrentDoseUnit;
	}

	public void setMmfCurrentDoseUnit(DoseUnitEnum mmfCurrentDoseUnit) {
		this.mmfCurrentDoseUnit = mmfCurrentDoseUnit;
	}

	public TimeUnitEnum getMmfDurationUnit() {
		return mmfDurationUnit;
	}

	public void setMmfDurationUnit(TimeUnitEnum mmfDurationUnit) {
		this.mmfDurationUnit = mmfDurationUnit;
	}

	public DoseUnitEnum getGlucocorticoidOtherInitialDoseUnit() {
		return glucocorticoidOtherInitialDoseUnit;
	}

	public void setGlucocorticoidOtherInitialDoseUnit(DoseUnitEnum glucocorticoidOtherInitialDoseUnit) {
		this.glucocorticoidOtherInitialDoseUnit = glucocorticoidOtherInitialDoseUnit;
	}

	public DoseUnitEnum getGlucocorticoidOtherCurrentDoseUnit() {
		return glucocorticoidOtherCurrentDoseUnit;
	}

	public void setGlucocorticoidOtherCurrentDoseUnit(DoseUnitEnum glucocorticoidOtherCurrentDoseUnit) {
		this.glucocorticoidOtherCurrentDoseUnit = glucocorticoidOtherCurrentDoseUnit;
	}

	public TimeUnitEnum getGlucocorticoidOtherDurationUnit() {
		return glucocorticoidOtherDurationUnit;
	}

	public void setGlucocorticoidOtherDurationUnit(TimeUnitEnum glucocorticoidOtherDurationUnit) {
		this.glucocorticoidOtherDurationUnit = glucocorticoidOtherDurationUnit;
	}

	public TimeUnitEnum getShardDurationUnit() {
		return shardDurationUnit;
	}

	public void setShardDurationUnit(TimeUnitEnum shardDurationUnit) {
		this.shardDurationUnit = shardDurationUnit;
	}

	public TimeUnitEnum getAedsDurationUnit() {
		return aedsDurationUnit;
	}

	public void setAedsDurationUnit(TimeUnitEnum aedsDurationUnit) {
		this.aedsDurationUnit = aedsDurationUnit;
	}

	public TimeUnitEnum getAluminumPreparationDurationUnit() {
		return aluminumPreparationDurationUnit;
	}

	public void setAluminumPreparationDurationUnit(TimeUnitEnum aluminumPreparationDurationUnit) {
		this.aluminumPreparationDurationUnit = aluminumPreparationDurationUnit;
	}

	public TimeUnitEnum getLithiumPreparationsDurationUnit() {
		return lithiumPreparationsDurationUnit;
	}

	public void setLithiumPreparationsDurationUnit(TimeUnitEnum lithiumPreparationsDurationUnit) {
		this.lithiumPreparationsDurationUnit = lithiumPreparationsDurationUnit;
	}

	public TimeUnitEnum getHeparinDurationUnit() {
		return heparinDurationUnit;
	}

	public void setHeparinDurationUnit(TimeUnitEnum heparinDurationUnit) {
		this.heparinDurationUnit = heparinDurationUnit;
	}

	public TimeUnitEnum getAromataseInhibitorDurationUnit() {
		return aromataseInhibitorDurationUnit;
	}

	public void setAromataseInhibitorDurationUnit(TimeUnitEnum aromataseInhibitorDurationUnit) {
		this.aromataseInhibitorDurationUnit = aromataseInhibitorDurationUnit;
	}

	public TimeUnitEnum getLastOtherDurationUnit() {
		return lastOtherDurationUnit;
	}

	public void setLastOtherDurationUnit(TimeUnitEnum lastOtherDurationUnit) {
		this.lastOtherDurationUnit = lastOtherDurationUnit;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
