package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 其它相关检查
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationName:医院名称</li>
 * <li>generalInformationId:患者ID</li>
 * <li>otherExaminations:其它相关检查</li> 
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_fracture_other")
@SequenceGenerator(name = "seq", sequenceName = "hzda_fracture_other_id", allocationSize = 1)
public class FractureOther extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 3493939727890676745L;
	
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
	@Column(name = "other_examinations", columnDefinition = "text")
	private String otherExaminations;
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
	public String getOtherExaminations() {
		return otherExaminations;
	}
	public void setOtherExaminations(String otherExaminations) {
		this.otherExaminations = otherExaminations;
	}
}
