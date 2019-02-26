package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 查体
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationName:医院名称</li>
 * <li>generalInformationId:患者ID</li>
 * <li>height:身高</li>
 * <li>weight:体重</li>
 * <li>BMI:BMI</li>
 * <li>normal:脊柱生理曲度正常</li>
 * <li>straighten:脊柱生理曲度变直 </li>
 * <li>sideBending:脊柱生理曲度侧弯行动状态</li>
 * <li>ambulation:脊柱生理曲度独立行走</li>
 * <li>abduction:脊柱生理曲度需拄拐或他人搀扶行走</li>
 * <li>wheelChair:脊柱生理曲度依靠轮椅</li>
 * <li>bedCare:脊柱生理曲度长期卧床</li>
 * <li>noNeed:不需要</li>
 * <li>need:需要</li>
 * <li>lowerLimbPain:下肢压痛</li>
 * <li>upperLimbPain:上肢压痛</li>
 * <li>centrumPain:椎体压痛</li>
 * <li>basinPain:骨盆压痛</li>
 * <li>hipPain:髋部压痛</li>
 * <li>otherPain:其他压痛</li>
 * <li>otherPainPart:其他压痛部位</li>
 * <li>lowerLimbConstrain:下肢受限</li>
 * <li>upperLimbConstrain:上肢受限</li>
 * <li>centrumConstrain:椎体受限</li>
 * <li>basinConstrain:骨盆受限</li>
 * <li>hipConstrain:髋部受限</li>
 * <li>otherConstrain:其他受限</li>
 * <li>otherConstrainPart:其他受限部位</li>
 * <li>sign:其他症状或体征</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_examined")
@SequenceGenerator(name = "seq", sequenceName = "hzda_examined_id", allocationSize = 1)
public class Examined extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 8553601268097010437L;

	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	@Column(name = "organization_id")
	private Long organizationId;
	@Formula(value = "(select s_o.name from sec_organization s_o where s_o.id=organization_id)")
	private String organizationName;
	@Column(name = "general_information_id", nullable = false)
	private Long generalInformationId;
    @Column(name = "height")
    private String height;
    @Column(name = "weight")
    private String weight;
    @Column(name = "bmi")
    private String BMI;

	@Column(name = "is_normal")
	private Boolean normal;
	@Column(name = "is_straighten")
	private Boolean straighten;
	@Column(name = "is_side_bending")
	private Boolean sideBending;
	@Column(name = "is_ambulation")
	private Boolean ambulation;
	@Column(name = "is_abduction")
	private Boolean abduction;	
	@Column(name = "is_wheel_chair")
	private Boolean wheelChair;
	@Column(name = "is_bed_care")
	private Boolean bedCare;
	@Column(name = "sign", columnDefinition = "text")
	private String sign;
	
	@Column(name = "is_no_need")
	private Boolean noNeed;
	@Column(name = "is_need")
	private Boolean need;
	@Column(name = "is_upper_limb_pain")
	private Boolean upperLimbPain;
	@Column(name = "is_lower_limb_pain")
	private Boolean lowerLimbPain;
	@Column(name = "is_centrum_pain")
	private Boolean centrumPain;
	@Column(name = "is_basin_pain")
	private Boolean basinPain;
	@Column(name = "is_hip_pain")
	private Boolean hipPain;
	@Column(name = "is_other_pain")
	private Boolean otherPain;
    @Column(name = "other_pain_part")
    private String otherPainPart;
	
	@Column(name = "is_lower_limb_constrain")
	private Boolean lowerLimbConstrain;
	@Column(name = "is_upper_limb_constrain")
	private Boolean upperLimbConstrain;
	@Column(name = "is_centrum_constrain")
	private Boolean centrumConstrain;
	@Column(name = "is_basin_constrain")
	private Boolean basinConstrain;
	@Column(name = "is_hip_constrain")
	private Boolean hipConstrain;
	@Column(name = "is_other_constrain")
	private Boolean otherConstrain;
    @Column(name = "other_constrain_part")
    private String otherConstrainPart;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Long getGeneralInformationId() {
		return generalInformationId;
	}
	public void setGeneralInformationId(Long generalInformationId) {
		this.generalInformationId = generalInformationId;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBMI() {
		return BMI;
	}
	public void setBMI(String bMI) {
		BMI = bMI;
	}
	public Boolean getNormal() {
		return normal;
	}
	public void setNormal(Boolean normal) {
		this.normal = normal;
	}
	public Boolean getStraighten() {
		return straighten;
	}
	public void setStraighten(Boolean straighten) {
		this.straighten = straighten;
	}
	public Boolean getSideBending() {
		return sideBending;
	}
	public void setSideBending(Boolean sideBending) {
		this.sideBending = sideBending;
	}
	public Boolean getAmbulation() {
		return ambulation;
	}
	public void setAmbulation(Boolean ambulation) {
		this.ambulation = ambulation;
	}
	public Boolean getWheelChair() {
		return wheelChair;
	}
	public void setWheelChair(Boolean wheelChair) {
		this.wheelChair = wheelChair;
	}
	public Boolean getBedCare() {
		return bedCare;
	}
	public void setBedCare(Boolean bedCare) {
		this.bedCare = bedCare;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Boolean getNoNeed() {
		return noNeed;
	}
	public void setNoNeed(Boolean noNeed) {
		this.noNeed = noNeed;
	}
	public Boolean getNeed() {
		return need;
	}
	public void setNeed(Boolean need) {
		this.need = need;
	}
	public Boolean getUpperLimbPain() {
		return upperLimbPain;
	}
	public void setUpperLimbPain(Boolean upperLimbPain) {
		this.upperLimbPain = upperLimbPain;
	}
	public Boolean getLowerLimbPain() {
		return lowerLimbPain;
	}
	public void setLowerLimbPain(Boolean lowerLimbPain) {
		this.lowerLimbPain = lowerLimbPain;
	}
	public Boolean getCentrumPain() {
		return centrumPain;
	}
	public void setCentrumPain(Boolean centrumPain) {
		this.centrumPain = centrumPain;
	}
	public Boolean getBasinPain() {
		return basinPain;
	}
	public void setBasinPain(Boolean basinPain) {
		this.basinPain = basinPain;
	}
	public Boolean getHipPain() {
		return hipPain;
	}
	public void setHipPain(Boolean hipPain) {
		this.hipPain = hipPain;
	}
	public Boolean getOtherPain() {
		return otherPain;
	}
	public void setOtherPain(Boolean otherPain) {
		this.otherPain = otherPain;
	}
	public Boolean getLowerLimbConstrain() {
		return lowerLimbConstrain;
	}
	public void setLowerLimbConstrain(Boolean lowerLimbConstrain) {
		this.lowerLimbConstrain = lowerLimbConstrain;
	}
	public Boolean getUpperLimbConstrain() {
		return upperLimbConstrain;
	}
	public void setUpperLimbConstrain(Boolean upperLimbConstrain) {
		this.upperLimbConstrain = upperLimbConstrain;
	}
	public Boolean getCentrumConstrain() {
		return centrumConstrain;
	}
	public void setCentrumConstrain(Boolean centrumConstrain) {
		this.centrumConstrain = centrumConstrain;
	}
	public Boolean getBasinConstrain() {
		return basinConstrain;
	}
	public void setBasinConstrain(Boolean basinConstrain) {
		this.basinConstrain = basinConstrain;
	}
	public Boolean getHipConstrain() {
		return hipConstrain;
	}
	public void setHipConstrain(Boolean hipConstrain) {
		this.hipConstrain = hipConstrain;
	}
	public Boolean getOtherConstrain() {
		return otherConstrain;
	}
	public void setOtherConstrain(Boolean otherConstrain) {
		this.otherConstrain = otherConstrain;
	}
	public String getOtherPainPart() {
		return otherPainPart;
	}
	public void setOtherPainPart(String otherPainPart) {
		this.otherPainPart = otherPainPart;
	}
	public String getOtherConstrainPart() {
		return otherConstrainPart;
	}
	public void setOtherConstrainPart(String otherConstrainPart) {
		this.otherConstrainPart = otherConstrainPart;
	}
	public Boolean getAbduction() {
		return abduction;
	}
	public void setAbduction(Boolean abduction) {
		this.abduction = abduction;
	}	
	
}
