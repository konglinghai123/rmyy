package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 骨折史
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>generalInformationId:一般信息编号</li>
 * <li>boneDismantling:患者是否骨折过</li>
 * <li>fall:患者过去一年跌倒过几次</li>
 * <li>number:骨折次数</li>
 * <li>age1:骨折年龄</li>
 * <li>brittleness1:脆性</li>
 * <li>violence1:暴力</li>
 * <li>position1:骨折部位</li>
 * <li>age2:骨折年龄</li>
 * <li>brittleness2:脆性</li>
 * <li>violence2:暴力</li>
 * <li>position2:骨折部位</li>
 * <li>age3:骨折年龄</li>
 * <li>brittleness3:脆性</li>
 * <li>violence3:暴力</li>
 * <li>position3:骨折部位</li>
 * <li>age4:骨折年龄</li>
 * <li>brittleness4:脆性</li>
 * <li>violence4:暴力</li>
 * <li>position4:骨折部位</li>
 * <li>age5:骨折年龄</li>
 * <li>brittleness5:脆性</li>
 * <li>violence5:暴力</li>
 * <li>position5:骨折部位</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_cataclasis")
@SequenceGenerator(name = "seq", sequenceName = "hzda_cataclasis_id", allocationSize = 1)
public class Cataclasis extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -9185530740971251607L;

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

	@Column(name = "is_bone_dismantling")
	private Boolean boneDismantling;
	@Column(name = "fall")
	private Long fall;
	@Column(name = "number")
	private Long number;
	@Column(name = "age1")
	private Long age1;
	@Column(name = "is_brittleness1")
	private Boolean brittleness1;
	@Column(name = "is_violence1")
	private Boolean violence1;
	@Column(name = "position1", columnDefinition = "text")
	private String position1;
	@Column(name = "age2")
	private Long age2;
	@Column(name = "is_brittleness2")
	private Boolean brittleness2;
	@Column(name = "is_violence2")
	private Boolean violence2;
	@Column(name = "position2", columnDefinition = "text")
	private String position2;
	@Column(name = "age3")
	private Long age3;
	@Column(name = "is_brittleness3")
	private Boolean brittleness3;
	@Column(name = "is_violence3")
	private Boolean violence3;
	@Column(name = "position3", columnDefinition = "text")
	private String position3;
	@Column(name = "age4")
	private Long age4;
	@Column(name = "is_brittleness4")
	private Boolean brittleness4;
	@Column(name = "is_violence4")
	private Boolean violence4;
	@Column(name = "position4", columnDefinition = "text")
	private String position4;
	@Column(name = "age5")
	private Long age5;
	@Column(name = "is_brittleness5")
	private Boolean brittleness5;
	@Column(name = "is_violence5")
	private Boolean violence5;
	@Column(name = "position5", columnDefinition = "text")
	private String position5;

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

	public Boolean getBoneDismantling() {
		return boneDismantling;
	}

	public void setBoneDismantling(Boolean boneDismantling) {
		this.boneDismantling = boneDismantling;
	}

	public Long getFall() {
		return fall;
	}

	public void setFall(Long fall) {
		this.fall = fall;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getAge1() {
		return age1;
	}

	public void setAge1(Long age1) {
		this.age1 = age1;
	}

	public Boolean getBrittleness1() {
		return brittleness1;
	}

	public void setBrittleness1(Boolean brittleness1) {
		this.brittleness1 = brittleness1;
	}

	public Boolean getViolence1() {
		return violence1;
	}

	public void setViolence1(Boolean violence1) {
		this.violence1 = violence1;
	}

	public String getPosition1() {
		return position1;
	}

	public void setPosition1(String position1) {
		this.position1 = position1;
	}

	public Long getAge2() {
		return age2;
	}

	public void setAge2(Long age2) {
		this.age2 = age2;
	}

	public Boolean getBrittleness2() {
		return brittleness2;
	}

	public void setBrittleness2(Boolean brittleness2) {
		this.brittleness2 = brittleness2;
	}

	public Boolean getViolence2() {
		return violence2;
	}

	public void setViolence2(Boolean violence2) {
		this.violence2 = violence2;
	}

	public String getPosition2() {
		return position2;
	}

	public void setPosition2(String position2) {
		this.position2 = position2;
	}

	public Long getAge3() {
		return age3;
	}

	public void setAge3(Long age3) {
		this.age3 = age3;
	}

	public Boolean getBrittleness3() {
		return brittleness3;
	}

	public void setBrittleness3(Boolean brittleness3) {
		this.brittleness3 = brittleness3;
	}

	public Boolean getViolence3() {
		return violence3;
	}

	public void setViolence3(Boolean violence3) {
		this.violence3 = violence3;
	}

	public String getPosition3() {
		return position3;
	}

	public void setPosition3(String position3) {
		this.position3 = position3;
	}

	public Long getAge4() {
		return age4;
	}

	public void setAge4(Long age4) {
		this.age4 = age4;
	}

	public Boolean getBrittleness4() {
		return brittleness4;
	}

	public void setBrittleness4(Boolean brittleness4) {
		this.brittleness4 = brittleness4;
	}

	public Boolean getViolence4() {
		return violence4;
	}

	public void setViolence4(Boolean violence4) {
		this.violence4 = violence4;
	}

	public String getPosition4() {
		return position4;
	}

	public void setPosition4(String position4) {
		this.position4 = position4;
	}

	public Long getAge5() {
		return age5;
	}

	public void setAge5(Long age5) {
		this.age5 = age5;
	}

	public Boolean getBrittleness5() {
		return brittleness5;
	}

	public void setBrittleness5(Boolean brittleness5) {
		this.brittleness5 = brittleness5;
	}

	public Boolean getViolence5() {
		return violence5;
	}

	public void setViolence5(Boolean violence5) {
		this.violence5 = violence5;
	}

	public String getPosition5() {
		return position5;
	}

	public void setPosition5(String position5) {
		this.position5 = position5;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationName() {
		return organizationName;
	}
}
