package com.ewcms.yjk.sp.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.user.entity.User;
import com.google.common.collect.Sets;

/**
 * 系统参数设置
 * 
 * <ul>
 * <li>applyStartDate:申请开始时间</li>
 * <li>applyEndDate:申请结束时间</li>
 * <li>injectDeclarationLimt:注射两规限数（院目录在用数量）</li>
 * <li>oralDeclarationLimt:口服两规限数（院目录在用数量）</li>
 * <li>otherDeclarationLimt:外用及其他两规限数（院目录在用数量）</li>
 * <li>declareTotalLimt:申报总数限制（单个医生）</li>
 * <li>enabled:是否启用（系统中只能有1或0条设置启用）</li>
 * <li>repeatDeclared:是否重复申报</li>
 * <li>projectRemark:项目说明</li>
 * <li>deleted:是否删除</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */

@Entity
@Table(name = "sp_system_parameter")
@SequenceGenerator(name = "seq", sequenceName = "seq_sp_system_parameter_id", allocationSize = 1)
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
	
	@Column(name = "inject_declaration_limt", nullable = false)
	private Long injectDeclarationLimt = Long.valueOf(2);
	
	@Column(name = "oral_declaration_limt", nullable = false)
	private Long oralDeclarationLimt = Long.valueOf(2);
	
	@Column(name = "other_declaration_limt", nullable = false)
	private Long otherDeclarationLimt = Long.valueOf(0);
	
	@Column(name = "declare_total_limt", nullable = false)
	private Long declareTotalLimt = Long.valueOf(0);
	
	@Column(name = "is_enabled")
	private Boolean enabled = Boolean.FALSE;
	
	@Column(name = "is_deleted")
	private Boolean deleted = Boolean.FALSE;
	
	@Column(name = "is_repeat_declared")
	private Boolean repeatDeclared = Boolean.FALSE;

	@Column(name = "project_remark")
	private String projectRemark;
	
	@OneToMany(cascade = { CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, targetEntity = SystemExpert.class, mappedBy = "systemParameter", orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	@Basic(optional = true, fetch = FetchType.EAGER)
	@OrderBy("weight")
	private List<SystemExpert> systemExperts;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "sp_system_parameter_user", joinColumns = {
			@JoinColumn(name = "system_parameter_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"user_id", "system_parameter_id" })})
	private List<User> users;
	
	@Formula(value = "(select count(sb.id) from sb_drug_form sb where sb.system_parameter_id=id and sb.auditstatus = 'nodeclare')")
	private Long nodeclareNumber = 0L;
	
	@Formula(value = "(select count(sb.id) from sb_drug_form sb where sb.system_parameter_id=id and sb.auditstatus = 'init')")
	private Long initNumber = 0L;
	
	@Formula(value = "(select count(sb.id) from sb_drug_form sb where sb.system_parameter_id=id and sb.auditstatus = 'passed')")
	private Long passedNumber = 0L;
	
	@Formula(value = "(select count(sb.id) from sb_drug_form sb where sb.system_parameter_id=id and sb.auditstatus = 'un_passed')")
	private Long unPassedNumber = 0L;
	
	public Long getInjectDeclarationLimt() {
		return injectDeclarationLimt;
	}

	public void setInjectDeclarationLimt(Long injectDeclarationLimt) {
		this.injectDeclarationLimt = injectDeclarationLimt;
	}

	public Long getOralDeclarationLimt() {
		return oralDeclarationLimt;
	}

	public void setOralDeclarationLimt(Long oralDeclarationLimt) {
		this.oralDeclarationLimt = oralDeclarationLimt;
	}

	public Long getOtherDeclarationLimt() {
		return otherDeclarationLimt;
	}

	public void setOtherDeclarationLimt(Long otherDeclarationLimt) {
		this.otherDeclarationLimt = otherDeclarationLimt;
	}

	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyStartDate() {
		return applyStartDate;
	}

	public String getProjectRemark() {
		return projectRemark;
	}

	public void setProjectRemark(String projectRemark) {
		this.projectRemark = projectRemark;
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

	public Boolean getRepeatDeclared() {
		return repeatDeclared;
	}

	public void setRepeatDeclared(Boolean repeatDeclared) {
		this.repeatDeclared = repeatDeclared;
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

	@JSONField(serialize = false)
	public List<SystemExpert> getSystemExperts() {
		return systemExperts;
	}
	
	public void addSystemExpert(SystemExpert systemExpert) {
		systemExpert.setSystemParameter(this);
		getSystemExperts().add(systemExpert);
	}

	public void setSystemExperts(List<SystemExpert> systemExperts) {
		this.systemExperts = systemExperts;
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

	public Long getNodeclareNumber() {
		return nodeclareNumber;
	}

	public Long getInitNumber() {
		return initNumber;
	}

	public Long getPassedNumber() {
		return passedNumber;
	}

	public Long getUnPassedNumber() {
		return unPassedNumber;
	}
}
