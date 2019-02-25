package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 诊断
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationName:医院名称</li>
 * <li>generalInformationId:患者ID</li>
 * <li>boneNormal:骨量正常</li>
 * <li>osteopenia:骨量减少</li>
 * <li>primaryOsteoporosis:原发性骨质疏松症</li>
 * <li>primaryOsteoporosisFracture:原发性骨质疏松症病理性骨折</li>
 * <li>secondaryOsteoporosisReason:继发性骨质疏松症病因</li>
 * <li>secondaryOsteoporosisFractureReason:继发性骨质疏松症病理性骨折病因</li>
 * <li>other:其它</li>
 * <li>otherReason:其它病因</li>
 * <li>secondaryOsteoporosis:继发性骨质疏松症</li>
 * <li>secondaryOsteoporosisFracture:继发性骨质疏松症病理性骨折</li>
 * 
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_diagnosis")
@SequenceGenerator(name = "seq", sequenceName = "hzda_diagnosis_id", allocationSize = 1)
public class Diagnosis extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 2611408066838264721L;

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


	@Column(name = "is_bone_normal")
	private Boolean boneNormal;
	@Column(name = "is_osteopenia")
	private Boolean osteopenia;
	@Column(name = "is_primary_osteoporosis")
	private Boolean primaryOsteoporosis;
	@Column(name = "is_primary_osteoporosis_fracture")
	private Boolean primaryOsteoporosisFracture;
	@Column(name = "is_secondary_osteoporosis")
	private Boolean secondaryOsteoporosis;
	@Column(name = "is_secondary_osteoporosis_fracture")
	private Boolean secondaryOsteoporosisFracture;
	@Column(name = "secondary_osteoporosis_reason", columnDefinition = "text")
	private String secondaryOsteoporosisReason;
	@Column(name = "secondary_osteoporosis_fracture_reason", columnDefinition = "text")
	private String secondaryOsteoporosisFractureReason;
	@Column(name = "is_other")
	private Boolean other;
	@Column(name = "other_reason", columnDefinition = "text")
	private String otherReason;
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
	public Boolean getBoneNormal() {
		return boneNormal;
	}
	public void setBoneNormal(Boolean boneNormal) {
		this.boneNormal = boneNormal;
	}
	public Boolean getOsteopenia() {
		return osteopenia;
	}
	public void setOsteopenia(Boolean osteopenia) {
		this.osteopenia = osteopenia;
	}
	public Boolean getPrimaryOsteoporosis() {
		return primaryOsteoporosis;
	}
	public void setPrimaryOsteoporosis(Boolean primaryOsteoporosis) {
		this.primaryOsteoporosis = primaryOsteoporosis;
	}
	public Boolean getPrimaryOsteoporosisFracture() {
		return primaryOsteoporosisFracture;
	}
	public void setPrimaryOsteoporosisFracture(Boolean primaryOsteoporosisFracture) {
		this.primaryOsteoporosisFracture = primaryOsteoporosisFracture;
	}
	public Boolean getSecondaryOsteoporosis() {
		return secondaryOsteoporosis;
	}
	public void setSecondaryOsteoporosis(Boolean secondaryOsteoporosis) {
		this.secondaryOsteoporosis = secondaryOsteoporosis;
	}
	public Boolean getSecondaryOsteoporosisFracture() {
		return secondaryOsteoporosisFracture;
	}
	public void setSecondaryOsteoporosisFracture(Boolean secondaryOsteoporosisFracture) {
		this.secondaryOsteoporosisFracture = secondaryOsteoporosisFracture;
	}
	public String getSecondaryOsteoporosisReason() {
		return secondaryOsteoporosisReason;
	}
	public void setSecondaryOsteoporosisReason(String secondaryOsteoporosisReason) {
		this.secondaryOsteoporosisReason = secondaryOsteoporosisReason;
	}
	public String getSecondaryOsteoporosisFractureReason() {
		return secondaryOsteoporosisFractureReason;
	}
	public void setSecondaryOsteoporosisFractureReason(String secondaryOsteoporosisFractureReason) {
		this.secondaryOsteoporosisFractureReason = secondaryOsteoporosisFractureReason;
	}
	public Boolean getOther() {
		return other;
	}
	public void setOther(Boolean other) {
		this.other = other;
	}
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
}
