package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 现病史
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>generalInformationId:一般信息编号</li>
 * <li>afterFatigue:乏力活动后</li>
 * <li>fatiguePersistence:乏力持续性</li>
 * <li>fatiguePersistenceHour:乏力持续小时</li>
 * <li>fatigueNo:无乏力</li>
 * <li>lowBackPain:腰背疼痛</li>
 * <li>lowerLimbPain:下肢疼痛</li>
 * <li>upperLimbPain:上肢疼痛</li>
 * <li>soreRibs:肋骨疼痛</li>
 * <li>toAcheAllOver:全身疼痛</li>
 * <li>otherPain:其他疼痛</li>
 * <li>otherPainDesc:其他疼痛说明</li>
 * <li>heightShort:身高缩短</li>
 * <li>heightShortNumber:身高缩短数</li>
 * <li>sideBend:侧弯</li>
 * <li>humpback:驼背</li>
 * <li>thoracocyllosis:胸廓畸形</li>
 * <li>extensionLimitation:伸展受限</li>
 * <li>otherDeformation:其他变形</li>
 * <li>otherDeformationDesc:其他变形说明</li>
 * <li>otherDesc:其他说明</li>
 * <li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_present_illness")
@SequenceGenerator(name = "seq", sequenceName = "hzda_present_illness_id", allocationSize = 1)
public class PresentIllness extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -4906838832527597678L;

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

	@Column(name = "is_after_fatigue")
	private Boolean afterFatigue;
	@Column(name = "is_fatigue_persistence")
	private Boolean fatiguePersistence;
	@Column(name = "fatigue_persistence_hour")
	private Double fatiguePersistenceHour;
	@Column(name = "is_fatigue_no")
	private Boolean fatigueNo;
	@Column(name = "is_low_back_pain")
	private Boolean lowBackPain;
	@Column(name = "is_lower_limb_pain")
	private Boolean lowerLimbPain;
	@Column(name = "is_upper_limb_pain")
	private Boolean upperLimbPain;
	@Column(name = "is_sore_ribs")
	private Boolean soreRibs;
	@Column(name = "is_to_ache_all_over")
	private Boolean toAcheAllOver;
	@Column(name = "is_other_pain")
	private Boolean otherPain;
	@Column(name = "other_pain_desc", columnDefinition = "text")
	private String otherPainDesc;
	@Column(name = "is_height_short")
	private Boolean heightShort;
	@Column(name = "height_short_number")
	private Double heightShortNumber;
	@Column(name = "is_side_bend")
	private Boolean sideBend;
	@Column(name = "is_humpback")
	private Boolean humpback;
	@Column(name = "is_thoracocyllosis")
	private Boolean thoracocyllosis;
	@Column(name = "is_extension_limitation")
	private Boolean extensionLimitation;
	@Column(name = "is_other_deformation")
	private Boolean otherDeformation;
	@Column(name = "other_deformation_desc", columnDefinition = "text")
	private String otherDeformationDesc;
	@Column(name = "other_desc", columnDefinition = "text")
	private String otherDesc;

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

	public Boolean getAfterFatigue() {
		return afterFatigue;
	}

	public void setAfterFatigue(Boolean afterFatigue) {
		this.afterFatigue = afterFatigue;
	}

	public Boolean getFatiguePersistence() {
		return fatiguePersistence;
	}

	public void setFatiguePersistence(Boolean fatiguePersistence) {
		this.fatiguePersistence = fatiguePersistence;
	}

	public Double getFatiguePersistenceHour() {
		return fatiguePersistenceHour;
	}

	public void setFatiguePersistenceHour(Double fatiguePersistenceHour) {
		this.fatiguePersistenceHour = fatiguePersistenceHour;
	}
	
	public Boolean getFatigueNo() {
		return fatigueNo;
	}

	public void setFatigueNo(Boolean fatigueNo) {
		this.fatigueNo = fatigueNo;
	}

	public Boolean getLowBackPain() {
		return lowBackPain;
	}

	public void setLowBackPain(Boolean lowBackPain) {
		this.lowBackPain = lowBackPain;
	}

	public Boolean getLowerLimbPain() {
		return lowerLimbPain;
	}

	public void setLowerLimbPain(Boolean lowerLimbPain) {
		this.lowerLimbPain = lowerLimbPain;
	}

	public Boolean getUpperLimbPain() {
		return upperLimbPain;
	}

	public void setUpperLimbPain(Boolean upperLimbPain) {
		this.upperLimbPain = upperLimbPain;
	}

	public Boolean getSoreRibs() {
		return soreRibs;
	}

	public void setSoreRibs(Boolean soreRibs) {
		this.soreRibs = soreRibs;
	}

	public Boolean getToAcheAllOver() {
		return toAcheAllOver;
	}

	public void setToAcheAllOver(Boolean toAcheAllOver) {
		this.toAcheAllOver = toAcheAllOver;
	}

	public Boolean getOtherPain() {
		return otherPain;
	}

	public void setOtherPain(Boolean otherPain) {
		this.otherPain = otherPain;
	}

	public String getOtherPainDesc() {
		return otherPainDesc;
	}

	public void setOtherPainDesc(String otherPainDesc) {
		this.otherPainDesc = otherPainDesc;
	}

	public Boolean getHeightShort() {
		return heightShort;
	}

	public void setHeightShort(Boolean heightShort) {
		this.heightShort = heightShort;
	}

	public Double getHeightShortNumber() {
		return heightShortNumber;
	}

	public void setHeightShortNumber(Double heightShortNumber) {
		this.heightShortNumber = heightShortNumber;
	}

	public Boolean getSideBend() {
		return sideBend;
	}

	public void setSideBend(Boolean sideBend) {
		this.sideBend = sideBend;
	}

	public Boolean getHumpback() {
		return humpback;
	}

	public void setHumpback(Boolean humpback) {
		this.humpback = humpback;
	}

	public Boolean getThoracocyllosis() {
		return thoracocyllosis;
	}

	public void setThoracocyllosis(Boolean thoracocyllosis) {
		this.thoracocyllosis = thoracocyllosis;
	}

	public Boolean getExtensionLimitation() {
		return extensionLimitation;
	}

	public void setExtensionLimitation(Boolean extensionLimitation) {
		this.extensionLimitation = extensionLimitation;
	}

	public Boolean getOtherDeformation() {
		return otherDeformation;
	}

	public void setOtherDeformation(Boolean otherDeformation) {
		this.otherDeformation = otherDeformation;
	}

	public String getOtherDeformationDesc() {
		return otherDeformationDesc;
	}

	public void setOtherDeformationDesc(String otherDeformationDesc) {
		this.otherDeformationDesc = otherDeformationDesc;
	}

	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationName() {
		return organizationName;
	}
}
