package com.ewcms.yjk.sp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.dictionary.entity.DepartmentAttribute;
import com.ewcms.security.dictionary.entity.Profession;
import com.ewcms.security.dictionary.entity.Technical;

/**
 * 系统参数设置
 * 
 * <ul>
 * <li>applyStartDate:申请开始时间</li>
 * <li>applyEndDate:申请结束时间</li>
 * <li>declarationLimt:申报限数</li>
 * <li>enabled:是否启用（系统中只能有1或0条设置启用）</li>
 * <li>departmentAttributes:科室属性对象集合</li>
 * <li>professions:职业对象集合</li>
 * <li>technicals:职称对象集合</li>
 * <li>deleted:是否删除</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */

@Entity
@Table(name = "sp_system_parameter")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_zd_system_parameter_id", allocationSize = 1)
public class SystemParameter extends BaseSequenceEntity<Long> implements LogicDeleteable{

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
	@Column(name = "is_enabled")
	private Boolean enabled = Boolean.FALSE;
    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="sp_system_parameter_department_attribute", joinColumns= {@JoinColumn(name = "system_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "department_attribute_id", referencedColumnName = "id")})
    private List<DepartmentAttribute> departmentAttributes;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="sp_system_parameter_profession", joinColumns= {@JoinColumn(name = "system_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "profession_id", referencedColumnName = "id")})
    private List<Profession> professions;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="sp_system_parameter_technical", joinColumns= {@JoinColumn(name = "system_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "technical_id", referencedColumnName = "id")})
    private List<Technical> technicals;
    
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<DepartmentAttribute> getDepartmentAttributes() {
		return departmentAttributes;
	}

	public void setDepartmentAttributes(List<DepartmentAttribute> departmentAttributes) {
		this.departmentAttributes = departmentAttributes;
	}

	public String getDepartmentAttributeNames() {
		return (EmptyUtil.isCollectionNotEmpty(departmentAttributes)) ?  Collections3.convertToString(Collections3.extractToList(departmentAttributes, "name"), "/") : "";
	}
	
	public List<Profession> getProfessions() {
		return professions;
	}

	public void setProfessions(List<Profession> professions) {
		this.professions = professions;
	}
	
	public String getProfessionNames() {
		return (EmptyUtil.isCollectionNotEmpty(professions)) ?  Collections3.convertToString(Collections3.extractToList(professions, "name"), "/") : "";
	}

	public List<Technical> getTechnicals() {
		return technicals;
	}

	public void setTechnicals(List<Technical> technicals) {
		this.technicals = technicals;
	}

	public String getTechnicalNames() {
		return (EmptyUtil.isCollectionNotEmpty(technicals)) ?  Collections3.convertToString(Collections3.extractToList(technicals, "name"), "/") : "";
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
