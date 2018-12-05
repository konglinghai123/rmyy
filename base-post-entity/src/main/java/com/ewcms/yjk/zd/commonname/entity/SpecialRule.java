package com.ewcms.yjk.zd.commonname.entity;

import java.util.List;

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

/**
 * 
 * 特殊的规则
 * 
 * <ul>
 * <li>name:规则名</li>
 * <li>limitNumber:数量</li>
 * <li>commonNames:通用名字典库对象集合</li>
 * <li>enabled:是否启用</li>
 * <li>remark:说明</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "zd_special_rule")
@SequenceGenerator(name = "seq", sequenceName = "seq_zd_special_rule_id", allocationSize = 1)
public class SpecialRule extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -3985889576072072696L;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "limit_number", nullable = false)
	private Long limitNumber = 0L;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "zd_special_rule_common_name", joinColumns = {
			@JoinColumn(name = "special_rule_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "common_name_id", referencedColumnName = "id") }, uniqueConstraints = {
							@UniqueConstraint(columnNames = { "special_rule_id", "common_name_id" }) })
	private List<CommonName> commonNames;
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	@JoinColumn(name = "administration_id")
	private Administration administration;
	@Column(name = "is_enabled", nullable = false)
	private Boolean enabled = Boolean.TRUE;
	
	@Column(name = "remark")
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLimitNumber() {
		return limitNumber;
	}

	public void setLimitNumber(Long limitNumber) {
		this.limitNumber = limitNumber;
	}

	@JSONField(serialize = false)
	public List<CommonName> getCommonNames() {
		return commonNames;
	}

	public void setCommonNames(List<CommonName> commonNames) {
		this.commonNames = commonNames;
	}
	
	public Administration getAdministration() {
		return administration;
	}

	public void setAdministration(Administration administration) {
		this.administration = administration;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
