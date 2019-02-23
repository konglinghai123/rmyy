package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 手术史
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>generalInformationId:一般信息编号</li>
 * <li>hysterectomy:子宫切除</li>
 * <li>hysterectomyAge:子宫切除年龄</li>
 * <li>oophorectomize:卵巢切除</li>
 * <li>oophorectomizeAge:卵巢切除年龄</li>
 * <li>thyroidectomy:甲状腺切除</li>
 * <li>thyroidectomyAge:甲状腺切除年龄</li>
 * <li>parathyroidectomy:甲状旁腺切除</li>
 * <li>parathyroidectomyAge:甲状旁腺切除年龄</li>
 * <li>usedBoneCement:使用过骨水泥</li>
 * <li>usedBoneCementAge:使用过骨水泥年龄</li>
 * <li>other:其他</li>
 * <li>otherAge:其他年龄</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_operation")
@SequenceGenerator(name = "seq", sequenceName = "hzda_operation_id", allocationSize = 1)
public class Operation extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -2349684240594361646L;

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

	@Column(name = "is_hysterectomy")
	private Boolean hysterectomy;
	@Column(name = "hysterectomy_age")
	private Long hysterectomyAge;
	@Column(name = "is_oophorectomize")
	private Boolean oophorectomize;
	@Column(name = "oophorectomize_age")
	private Long oophorectomizeAge;
	@Column(name = "is_thyroidectomy")
	private Boolean thyroidectomy;
	@Column(name = "thyroidectomy_age")
	private Long thyroidectomyAge;
	@Column(name = "is_parathyroidectomy")
	private Boolean parathyroidectomy;
	@Column(name = "parathyroidectomy_age")
	private Long parathyroidectomyAge;
	@Column(name = "is_used_bone_cement")
	private Boolean usedBoneCement;
	@Column(name = "used_bone_cement_age")
	private Long usedBoneCementAge;
	@Column(name = "is_other")
	private Boolean other;
	@Column(name = "other_age")
	private Long otherAge;

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

	public Boolean getHysterectomy() {
		return hysterectomy;
	}

	public void setHysterectomy(Boolean hysterectomy) {
		this.hysterectomy = hysterectomy;
	}

	public Long getHysterectomyAge() {
		return hysterectomyAge;
	}

	public void setHysterectomyAge(Long hysterectomyAge) {
		this.hysterectomyAge = hysterectomyAge;
	}

	public Boolean getOophorectomize() {
		return oophorectomize;
	}

	public void setOophorectomize(Boolean oophorectomize) {
		this.oophorectomize = oophorectomize;
	}

	public Long getOophorectomizeAge() {
		return oophorectomizeAge;
	}

	public void setOophorectomizeAge(Long oophorectomizeAge) {
		this.oophorectomizeAge = oophorectomizeAge;
	}

	public Boolean getThyroidectomy() {
		return thyroidectomy;
	}

	public void setThyroidectomy(Boolean thyroidectomy) {
		this.thyroidectomy = thyroidectomy;
	}

	public Long getThyroidectomyAge() {
		return thyroidectomyAge;
	}

	public void setThyroidectomyAge(Long thyroidectomyAge) {
		this.thyroidectomyAge = thyroidectomyAge;
	}

	public Boolean getParathyroidectomy() {
		return parathyroidectomy;
	}

	public void setParathyroidectomy(Boolean parathyroidectomy) {
		this.parathyroidectomy = parathyroidectomy;
	}

	public Long getParathyroidectomyAge() {
		return parathyroidectomyAge;
	}

	public void setParathyroidectomyAge(Long parathyroidectomyAge) {
		this.parathyroidectomyAge = parathyroidectomyAge;
	}

	public Boolean getUsedBoneCement() {
		return usedBoneCement;
	}

	public void setUsedBoneCement(Boolean usedBoneCement) {
		this.usedBoneCement = usedBoneCement;
	}

	public Long getUsedBoneCementAge() {
		return usedBoneCementAge;
	}

	public void setUsedBoneCementAge(Long usedBoneCementAge) {
		this.usedBoneCementAge = usedBoneCementAge;
	}

	public Boolean getOther() {
		return other;
	}

	public void setOther(Boolean other) {
		this.other = other;
	}

	public Long getOtherAge() {
		return otherAge;
	}

	public void setOtherAge(Long otherAge) {
		this.otherAge = otherAge;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationName() {
		return organizationName;
	}
}
