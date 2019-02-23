package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 家族史
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>generalInformationId:一般信息编号</li>
 * <li>inealKinOsteoporosis:是否有直系亲属患有骨质疏松症</li>
 * <li>relationsWithPatients:与患者关系</li>
 * <li>fracture:是否骨折过</li>
 * <li>patientMother:患者母亲是否骨折过</li>
 * <li>montherFracture:患者母亲骨折部位</li>
 * <li>montherBrittleness:是否脆性骨折</li>
 * <li>patientFather:患者母亲是否骨折过</li>
 * <li>fatherFracture:患者母亲骨折部位</li>
 * <li>fatherBrittleness:是否脆性骨折</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_family")
@SequenceGenerator(name = "seq", sequenceName = "hzda_family_id", allocationSize = 1)
public class Family extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 2283753954193560056L;

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

	@Column(name = "is_ineal_kin_osteoporosis")
	private Boolean inealKinOsteoporosis;
	@Column(name = "relations_with_patients")
	private String relationsWithPatients;
	@Column(name = "is_fracture")
	private Boolean fracture;
	@Column(name = "is_patient_mother")
	private Boolean patientMother;
	@Column(name = "monther_fracture")
	private String montherFracture;
	@Column(name = "is_monther_brittleness")
	private Boolean montherBrittleness;
	@Column(name = "is_patient_father")
	private Boolean patientFather;
	@Column(name = "father_fracture")
	private String fatherFracture;
	@Column(name = "is_father_brittleness")
	private Boolean fatherBrittleness;

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

	public Boolean getInealKinOsteoporosis() {
		return inealKinOsteoporosis;
	}

	public void setInealKinOsteoporosis(Boolean inealKinOsteoporosis) {
		this.inealKinOsteoporosis = inealKinOsteoporosis;
	}

	public String getRelationsWithPatients() {
		return relationsWithPatients;
	}

	public void setRelationsWithPatients(String relationsWithPatients) {
		this.relationsWithPatients = relationsWithPatients;
	}

	public Boolean getFracture() {
		return fracture;
	}

	public void setFracture(Boolean fracture) {
		this.fracture = fracture;
	}

	public Boolean getPatientMother() {
		return patientMother;
	}

	public void setPatientMother(Boolean patientMother) {
		this.patientMother = patientMother;
	}

	public String getMontherFracture() {
		return montherFracture;
	}

	public void setMontherFracture(String montherFracture) {
		this.montherFracture = montherFracture;
	}

	public Boolean getMontherBrittleness() {
		return montherBrittleness;
	}

	public void setMontherBrittleness(Boolean montherBrittleness) {
		this.montherBrittleness = montherBrittleness;
	}

	public Boolean getPatientFather() {
		return patientFather;
	}

	public void setPatientFather(Boolean patientFather) {
		this.patientFather = patientFather;
	}

	public String getFatherFracture() {
		return fatherFracture;
	}

	public void setFatherFracture(String fatherFracture) {
		this.fatherFracture = fatherFracture;
	}

	public Boolean getFatherBrittleness() {
		return fatherBrittleness;
	}

	public void setFatherBrittleness(Boolean fatherBrittleness) {
		this.fatherBrittleness = fatherBrittleness;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationName() {
		return organizationName;
	}
}
