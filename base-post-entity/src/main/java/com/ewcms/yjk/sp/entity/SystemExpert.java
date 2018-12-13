package com.ewcms.yjk.sp.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.Movable;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.dictionary.entity.Appointment;
import com.ewcms.security.dictionary.entity.DepartmentAttribute;
import com.ewcms.security.dictionary.entity.Profession;
import com.ewcms.security.dictionary.entity.TechnicalTitle;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.user.entity.User;
import com.google.common.collect.Sets;

/**
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "sp_system_expert")
@SequenceGenerator(name = "seq", sequenceName = "seq_sp_system_expert_id", allocationSize = 1)
public class SystemExpert extends BaseSequenceEntity<Long> implements Movable{

	private static final long serialVersionUID = 8663691075247631494L;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	private SystemParameter systemParameter;
	@Column(name = "weight")
	private Integer weight;
	@Column(name = "is_director")
	private Boolean director;
	@Column(name = "is_second_director")
	private Boolean secondDirector;
	@Column(name = "is_pharmacy")
	private Boolean pharmacy;
	@Column(name = "is_science")
	private Boolean science;
	@Column(name = "is_antibiosis")
	private Boolean antibiosis;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_expert_organization", joinColumns = {
			@JoinColumn(name = "system_expert_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "organization_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_expert_id", "organization_id" })})
	private List<Organization> organizations;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_expert_department_attribute", joinColumns = {
			@JoinColumn(name = "system_expert_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "department_attribute_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_expert_id", "department_attribute_id" })})
	private List<DepartmentAttribute> departmentAttributes;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_expert_profession", joinColumns = {
			@JoinColumn(name = "system_expert_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "profession_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_expert_id", "profession_id" })})
	private List<Profession> professions;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_expert_technical_title", joinColumns = {
			@JoinColumn(name = "system_expert_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "technical_title_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_expert_id", "technical_title_id" })})
	private List<TechnicalTitle> technicalTitles;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_expert_appointment", joinColumns = {
			@JoinColumn(name = "system_expert_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "appointment_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_expert_id", "appointment_id" })})
	private List<Appointment> appointments;
	@Column(name = "percent")
	private Long percent = 100L;
	@Column(name = "random_number")
	private Long randomNumber = 0L;
	@Column(name = "department_number")
	private Long departmentNumber = 0L;
	@Column(name = "is_enabled")
	private Boolean enabled = Boolean.TRUE;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_expert_user", joinColumns = {
			@JoinColumn(name = "system_expert_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"user_id", "system_expert_id" })})
	private List<User> users;

	@JSONField(serialize = false)
	public SystemParameter getSystemParameter() {
		return systemParameter;
	}

	public void setSystemParameter(SystemParameter systemParameter) {
		this.systemParameter = systemParameter;
	}

	public Boolean getDirector() {
		return director;
	}

	public void setDirector(Boolean director) {
		this.director = director;
	}

	public Boolean getSecondDirector() {
		return secondDirector;
	}

	public void setSecondDirector(Boolean secondDirector) {
		this.secondDirector = secondDirector;
	}

	public Boolean getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Boolean pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Boolean getScience() {
		return science;
	}

	public void setScience(Boolean science) {
		this.science = science;
	}

	public Boolean getAntibiosis() {
		return antibiosis;
	}

	public void setAntibiosis(Boolean antibiosis) {
		this.antibiosis = antibiosis;
	}

	@JSONField(serialize = false)
	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public String getOrganizationNames() {
		return (EmptyUtil.isCollectionNotEmpty(organizations))
				? Collections3.convertToString(Collections3.extractToList(organizations, "name"), "/")
				: "/";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getOrganizationIds() {
		return (EmptyUtil.isCollectionNotEmpty(organizations)) ? Collections3.extractToSet(organizations, "id")
				: Sets.newHashSet();
	}
	
	@JSONField(serialize = false)
	public List<DepartmentAttribute> getDepartmentAttributes() {
		return departmentAttributes;
	}

	public void setDepartmentAttributes(List<DepartmentAttribute> departmentAttributes) {
		this.departmentAttributes = departmentAttributes;
	}

	public String getDepartmentAttributeNames() {
		return (EmptyUtil.isCollectionNotEmpty(departmentAttributes))
				? Collections3.convertToString(Collections3.extractToList(departmentAttributes, "name"), "/")
				: "/";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getDepartmentAttributeIds() {
		return (EmptyUtil.isCollectionNotEmpty(departmentAttributes))
				? Collections3.extractToSet(departmentAttributes, "id")
				: Sets.newHashSet();
	}

	@JSONField(serialize = false)
	public List<Profession> getProfessions() {
		return professions;
	}

	public String getProfessionNames() {
		return (EmptyUtil.isCollectionNotEmpty(professions))
				? Collections3.convertToString(Collections3.extractToList(professions, "name"), "/")
				: "/";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getProfessionIds() {
		return (EmptyUtil.isCollectionNotEmpty(professions)) ? Collections3.extractToSet(professions, "id")
				: Sets.newHashSet();
	}

	public void setProfessions(List<Profession> professions) {
		this.professions = professions;
	}

	@JSONField(serialize = false)
	public List<TechnicalTitle> getTechnicalTitles() {
		return technicalTitles;
	}

	public void setTechnicalTitles(List<TechnicalTitle> technicalTitles) {
		this.technicalTitles = technicalTitles;
	}

	public String getTechnicalTitleNames() {
		return (EmptyUtil.isCollectionNotEmpty(technicalTitles))
				? Collections3.convertToString(Collections3.extractToList(technicalTitles, "name"), "/")
				: "/";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getTechnicalTitleIds() {
		return (EmptyUtil.isCollectionNotEmpty(technicalTitles)) ? Collections3.extractToSet(technicalTitles, "id")
				: Sets.newHashSet();
	}

	@JSONField(serialize = false)
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String getAppointmentNames() {
		return (EmptyUtil.isCollectionNotEmpty(appointments))
				? Collections3.convertToString(Collections3.extractToList(appointments, "name"), "/")
				: "/";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getAppointmentIds() {
		return (EmptyUtil.isCollectionNotEmpty(appointments)) ? Collections3.extractToSet(appointments, "id")
				: Sets.newHashSet();
	}

	public Long getPercent() {
		return percent;
	}

	public void setPercent(Long percent) {
		this.percent = percent;
	}

	public Long getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(Long randomNumber) {
		this.randomNumber = randomNumber;
	}

	public Long getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(Long departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JSONField(serialize = false)
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUserNames() {
		return (EmptyUtil.isCollectionNotEmpty(users))
				? Collections3.convertToString(Collections3.extractToList(users, "username"), "/")
				: "";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getUserIds() {
		return (EmptyUtil.isCollectionNotEmpty(users)) ? Collections3.extractToSet(users, "id")
				: Sets.newHashSet();
	}

	@Override
	public Integer getWeight() {
		return weight;
	}

	@Override
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
