package com.ewcms.yjk.sp.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.dictionary.entity.Appointment;
import com.ewcms.security.dictionary.entity.DepartmentAttribute;
import com.ewcms.security.dictionary.entity.Profession;
import com.ewcms.security.dictionary.entity.TechnicalTitle;
import com.ewcms.security.organization.entity.Organization;
import com.google.common.collect.Sets;

/**
 * 系统参数设置
 * 
 * <ul>
 * <li>applyStartDate:申请开始时间</li>
 * <li>applyEndDate:申请结束时间</li>
 * <li>declarationLimt:申报限数（院目录在用数量）</li>
 * <li>declareTotalLimt:申报总数限制（单个医生）</li>
 * <li>enabled:是否启用（系统中只能有1或0条设置启用）</li>
 * <li>organizations:科室对象集合
 * <li>departmentAttributes:科室属性对象集合</li>
 * <li>professions:执业类别对象集合</li>
 * <li>technicalTitle:技术职称(资格)对象集合</li>
 * <li>appointment:聘任对象集合</li>
 * <li>percent:百分比</li>
 * <li>totalNumber:人数</li>
 * <li>departmentNumber:部门人数</li>
 * <li>enabled:是否启用</li>
 * <li>deleted:是否删除</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */

@Entity
@Table(name = "sp_system_parameter")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "seq", sequenceName = "seq_zd_system_parameter_id", allocationSize = 1)
public class SystemParameter extends BaseSequenceEntity<Long> implements LogicDeleteable {

	private static final long serialVersionUID = 4409780231040092738L;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "apply_start_date", nullable = false, unique = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyStartDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "apply_end_date", nullable = false, unique = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyEndDate;
	@Column(name = "declaration_limt", nullable = false)
	private Long declarationLimt = Long.valueOf(2);
	@Column(name = "declare_total_limt", nullable = false)
	private Long declareTotalLimt = Long.valueOf(999);
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_parameter_organization", joinColumns = {
			@JoinColumn(name = "system_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "organization_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_id", "organization_id" })})
	private List<Organization> organizations;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_parameter_department_attribute", joinColumns = {
			@JoinColumn(name = "system_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "department_attribute_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_id", "department_attribute_id" })})
	private List<DepartmentAttribute> departmentAttributes;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_parameter_profession", joinColumns = {
			@JoinColumn(name = "system_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "profession_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_id", "profession_id" })})
	private List<Profession> professions;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_parameter_technical_title", joinColumns = {
			@JoinColumn(name = "system_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "technical_title_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_id", "technical_title_id" })})
	private List<TechnicalTitle> technicalTitles;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_parameter_appointment", joinColumns = {
			@JoinColumn(name = "system_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "appointment_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"system_id", "appointment_id" })})
	private List<Appointment> appointments;
	@Column(name = "percent")
	private Long percent = 100L;
	@Column(name = "random_number")
	private Long randomNumber = 0L;
	@Column(name = "department_number")
	private Long departmentNumber = 0L;
	@Column(name = "is_enabled")
	private Boolean enabled = Boolean.FALSE;
	@Column(name = "is_deleted")
	private Boolean deleted = Boolean.FALSE;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyStartDate() {
		return applyStartDate;
	}

	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyEndDate() {
		return applyEndDate;
	}

	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
	}

	public Long getDeclarationLimt() {
		return declarationLimt;
	}

	public void setDeclarationLimt(Long declarationLimt) {
		this.declarationLimt = declarationLimt;
	}

	public Long getDeclareTotalLimt() {
		return declareTotalLimt;
	}

	public void setDeclareTotalLimt(Long declareTotalLimt) {
		this.declareTotalLimt = declareTotalLimt;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public String getOrganizationNames() {
		return (EmptyUtil.isCollectionNotEmpty(organizations))
				? Collections3.convertToString(Collections3.extractToList(organizations, "name"), "/")
				: "";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getOrganizationsIds() {
		return (EmptyUtil.isCollectionNotEmpty(organizations)) ? Collections3.extractToSet(organizations, "id")
				: Sets.newHashSet();
	}

	public List<DepartmentAttribute> getDepartmentAttributes() {
		return departmentAttributes;
	}

	public void setDepartmentAttributes(List<DepartmentAttribute> departmentAttributes) {
		this.departmentAttributes = departmentAttributes;
	}

	public String getDepartmentAttributeNames() {
		return (EmptyUtil.isCollectionNotEmpty(departmentAttributes))
				? Collections3.convertToString(Collections3.extractToList(departmentAttributes, "name"), "/")
				: "";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getDepartmentAttributeIds() {
		return (EmptyUtil.isCollectionNotEmpty(departmentAttributes))
				? Collections3.extractToSet(departmentAttributes, "id")
				: Sets.newHashSet();
	}

	public List<Profession> getProfessions() {
		return professions;
	}

	public void setProfessions(List<Profession> professions) {
		this.professions = professions;
	}

	public String getProfessionNames() {
		return (EmptyUtil.isCollectionNotEmpty(professions))
				? Collections3.convertToString(Collections3.extractToList(professions, "name"), "/")
				: "";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getProfessionIds() {
		return (EmptyUtil.isCollectionNotEmpty(professions)) ? Collections3.extractToSet(professions, "id")
				: Sets.newHashSet();
	}

	public List<TechnicalTitle> getTechnicalTitles() {
		return technicalTitles;
	}

	public void setTechnicalTitles(List<TechnicalTitle> technicalTitles) {
		this.technicalTitles = technicalTitles;
	}

	public String getTechnicalTitleNames() {
		return (EmptyUtil.isCollectionNotEmpty(technicalTitles))
				? Collections3.convertToString(Collections3.extractToList(technicalTitles, "name"), "/")
				: "";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getTechnicalTitleIds() {
		return (EmptyUtil.isCollectionNotEmpty(technicalTitles)) ? Collections3.extractToSet(technicalTitles, "id")
				: Sets.newHashSet();
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String getAppointmentNames() {
		return (EmptyUtil.isCollectionNotEmpty(appointments))
				? Collections3.convertToString(Collections3.extractToList(appointments, "name"), "/")
				: "";
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

	public void setRandomNumber(Long totalNumber) {
		this.randomNumber = totalNumber;
	}

	public Long getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(Long departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	@Override
	public Boolean getDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public void markDeleted() {
		this.deleted = Boolean.TRUE;
	}
}
