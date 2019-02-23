package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 婚育史
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>generalInformationId:一般信息编号</li>
 * <li>menarcheAge:月经初潮年龄</li>
 * <li>menopauseAge:绝经年龄</li>
 * <li>pregnant:孕</li>
 * <li>delivery:产</li>
 * <li>liveBirth:活产</li>
 * <li>deadBirth:死产</li>
 * <li>naturalAbortion:自然流产</li>
 * <li>abactio:人工流产</li>
 * <li>drugAbortion:药物流产</li>
 * <li>primigravida:首次妊娠年龄</li>
 * <li>lastGestationalAge:末次妊娠年龄</li>
 * <li>totalLactationTime:哺乳总时间</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_marriage_bearing")
@SequenceGenerator(name = "seq", sequenceName = "hzda_marriage_bearing_id", allocationSize = 1)
public class MarriageBearing extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 3658983467080193990L;

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

	@Column(name = "menarche_age")
	private Double menarcheAge;
	@Column(name = "menopause_age")
	private Double menopauseAge;
	@Column(name = "pregnant")
	private Long pregnant;
	@Column(name = "delivery")
	private Long delivery;
	@Column(name = "live_birth")
	private Long liveBirth;
	@Column(name = "dead_birth")
	private Long deadBirth;
	@Column(name = "natural_abortion")
	private Long naturalAbortion;
	@Column(name = "abactio")
	private Long abactio;
	@Column(name = "drug_abortion")
	private Long drugAbortion;
	@Column(name = "primigravida")
	private Double primigravida;
	@Column(name = "last_gestational_age")
	private Double lastGestationalAge;
	@Column(name = "total_lactation_time")
	private Double totalLactationTime;

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

	public Double getMenarcheAge() {
		return menarcheAge;
	}

	public void setMenarcheAge(Double menarcheAge) {
		this.menarcheAge = menarcheAge;
	}

	public Double getMenopauseAge() {
		return menopauseAge;
	}

	public void setMenopauseAge(Double menopauseAge) {
		this.menopauseAge = menopauseAge;
	}

	public Long getPregnant() {
		return pregnant;
	}

	public void setPregnant(Long pregnant) {
		this.pregnant = pregnant;
	}

	public Long getDelivery() {
		return delivery;
	}

	public void setDelivery(Long delivery) {
		this.delivery = delivery;
	}

	public Long getLiveBirth() {
		return liveBirth;
	}

	public void setLiveBirth(Long liveBirth) {
		this.liveBirth = liveBirth;
	}

	public Long getDeadBirth() {
		return deadBirth;
	}

	public void setDeadBirth(Long deadBirth) {
		this.deadBirth = deadBirth;
	}

	public Long getNaturalAbortion() {
		return naturalAbortion;
	}

	public void setNaturalAbortion(Long naturalAbortion) {
		this.naturalAbortion = naturalAbortion;
	}

	public Long getAbactio() {
		return abactio;
	}

	public void setAbactio(Long abactio) {
		this.abactio = abactio;
	}

	public Long getDrugAbortion() {
		return drugAbortion;
	}

	public void setDrugAbortion(Long drugAbortion) {
		this.drugAbortion = drugAbortion;
	}

	public Double getPrimigravida() {
		return primigravida;
	}

	public void setPrimigravida(Double primigravida) {
		this.primigravida = primigravida;
	}

	public Double getLastGestationalAge() {
		return lastGestationalAge;
	}

	public void setLastGestationalAge(Double lastGestationalAge) {
		this.lastGestationalAge = lastGestationalAge;
	}

	public Double getTotalLactationTime() {
		return totalLactationTime;
	}

	public void setTotalLactationTime(Double totalLactationTime) {
		this.totalLactationTime = totalLactationTime;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationName() {
		return organizationName;
	}
}
