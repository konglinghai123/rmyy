package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * <li>rheumatism:风湿系统疾病其他</li>
 * <li>rheumatismDesc:风湿系统疾病其他说明</li>
 * <li>hyperparathyroidism:甲状旁腺功能亢进症</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_anamnesis")
@SequenceGenerator(name = "seq", sequenceName = "hzda_anamnesis_id", allocationSize = 1)
public class Anamnesis extends BaseSequenceEntity<Long>{

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
	@Column(name = "is_rheumatism")
	private Boolean rheumatism;
	@Column(name = "rheumatism_desc")
	private String rheumatismDesc;
	@Column(name = "is_hyperparathyroidism")
	private Boolean hyperparathyroidism;
}
