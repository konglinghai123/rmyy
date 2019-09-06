package com.ewcms.yjk.re.entity;

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
import com.ewcms.security.organization.entity.Organization;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 确保科室通过数量
 * 
 * <ul>
 * <li>organizations:科室对象集合</li>
 * <li>passNumber:确保申报科室通过数</li>
 * <li>weight:排序</li>
 * <li>enabled:是否启用</li>
 * </ul>
 * 
 * @author wu_zhijun
 *
 */
@Entity
@Table(name = "re_ensure_pass_through")
@SequenceGenerator(name = "seq", sequenceName = "seq_re_ensure_pass_through_id", allocationSize = 1)
public class EnsurePassThrough extends BaseSequenceEntity<Long> implements Movable{
	
	private static final long serialVersionUID = -5940242470467274656L;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	private ReviewProcess reviewProcess;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "re_ensure_pass_through_organization", joinColumns = {
			@JoinColumn(name = "re_ensure_pass_through_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "organization_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"re_ensure_pass_through_id", "organization_id" })})
	private List<Organization> organizations;
	@Column(name = "pass_number")
	private Integer passNumber = 0;
	@Column(name = "is_enabled")
	private Boolean enabled = Boolean.TRUE;
	@Column(name = "weight")
	private Integer weight;

	public ReviewProcess getReviewProcess() {
		return reviewProcess;
	}

	public void setReviewProcess(ReviewProcess reviewProcess) {
		this.reviewProcess = reviewProcess;
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
	
	@SuppressWarnings("unchecked")
	public List<Long> getOrganiztionIdsList(){
		return (EmptyUtil.isCollectionNotEmpty(organizations)) ? Collections3.extractToList(organizations, "id") : Lists.newArrayList();
	}

	public Integer getPassNumber() {
		return passNumber;
	}

	public void setPassNumber(Integer passNumber) {
		this.passNumber = passNumber;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
