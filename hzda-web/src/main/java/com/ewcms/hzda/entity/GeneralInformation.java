package com.ewcms.hzda.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.hzda.zd.entity.CertificateType;
import com.ewcms.hzda.zd.entity.Nation;

/**
 * 一般信息
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>recordingTime:记录时间</li>
 * <li>totalNumber:总编号</li>
 * <li>transplantNumber:移植编号</li>
 * <li>name:姓名</li>
 * <li>hospitalizationNumber:住院号</li>
 * <li>dxaNumber:DXA号</li>
 * <li>sex:性别(枚举)</li>
 * <li>birthday:出生年月</li>
 * <li>nation:民族(对象)</li>
 * <li>degreeEducation:文化程度</li>
 * <li>occupation:职业</li>
 * <li>address:现住址</li>
 * <li>certificateType:证件类型(对象)</li>
 * <li>certificateNumber:证件号</li>
 * <li>medicalInsuranceNumber:医保号</li>
 * <li>mobilePhoneNumber:常用手机号码</li>
 * <li>otherTelephone:其他联系电话</li>
 * <li>specialTab:特殊患者标记(肾移植)</li>
 * <li>specialTabNumber:特殊患者标记(肾移植)编号</li>
 * <li>remakrs:备注</li>
 * <li>age:年龄</li>
 * <li> :体重</li>
 * <li>OSTA:OSTA评分</li>
 * <li>eGFR:eGFR</li>
 * <li>FRAXMain:FRAX主要部位骨折风险</li>
 * <li>FRAXHipbone:FRAX髋部骨折风险</li>
 * <li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_general_information")
@SequenceGenerator(name = "seq", sequenceName = "hzda_general_information_id", allocationSize = 1)
public class GeneralInformation extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -6771018256614165122L;

	public enum Sex {
		MALE("男"), FEMALE("女");

		private String description;

		private Sex(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	@Column(name = "user_id")
	private Long userId;
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	@Column(name = "organization_id")
	private Long organizationId;
	@Formula(value = "(select s_o.name from sec_organization s_o where s_o.id=organization_id)")
	private String organizationName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "recording_time", columnDefinition = "Timestamp default CURRENT_DATE", insertable = false, updatable = false)
	@Temporal(TemporalType.DATE)
	private Date recordingTime;
	@Column(name = "total_number")
	private String totalNumber;
	@Column(name = "transplant_number")
	private String transplantNumber;
	@Column(name = "name")
	private String name;
	@Column(name = "hospitalization_number")
	private String hospitalizationNumber;
	@Column(name = "dxa_number")
	private String dxaNumber;
	@Enumerated(EnumType.STRING)
	@Column(name = "sex")
	private Sex sex = Sex.MALE;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday")
	private Date birthday;
	@OneToOne(cascade = { CascadeType.REFRESH }, targetEntity = Nation.class)
	@JoinColumn(name = "nation_id")
	private Nation nation;
	@Column(name = "degree_education")
	private String degreeEducation;
	@Column(name = "occupation")
	private String occupation;
	@Column(name = "address")
	private String address;
	@OneToOne(cascade = { CascadeType.REFRESH }, targetEntity = CertificateType.class)
	@JoinColumn(name = "certificate_type_id")
	private CertificateType certificateType;
	@Column(name = "certificate_number")
	private String certificateNumber;
	@Column(name = "medicalI_insurance_number")
	private String medicalInsuranceNumber;
	@Column(name = "mobile_phone_number")
	private String mobilePhoneNumber;
	@Column(name = "other_telephone")
	private String otherTelephone;
	@Column(name = "is_special_tab")
	private Boolean specialTab;
	@Column(name = "special_tab_number")
	private String specialTabNumber;
	@Column(name = "age")
	private Long age;
	@Column(name = "weight")
	private Long weight;
	@Column(name = "osta")
	private String oSTA;
	@Column(name = "egfr")
	private String eGFR;
	@Column(name = "frax_main")
	private String fRAXMain;
	@Column(name = "frax_hipbone")
	private String fRAXHipbone;
	@Column(name = "remakrs", columnDefinition = "text")
	private String remakrs;

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

	@JSONField(format = "yyyy-MM-dd")
	public Date getRecordingTime() {
		return recordingTime;
	}

	public void setRecordingTime(Date recordingTime) {
		this.recordingTime = recordingTime;
	}

	public String getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getTransplantNumber() {
		return transplantNumber;
	}

	public void setTransplantNumber(String transplantNumber) {
		this.transplantNumber = transplantNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHospitalizationNumber() {
		return hospitalizationNumber;
	}

	public void setHospitalizationNumber(String hospitalizationNumber) {
		this.hospitalizationNumber = hospitalizationNumber;
	}

	public String getDxaNumber() {
		return dxaNumber;
	}

	public void setMaNumber(String dxaNumber) {
		this.dxaNumber = dxaNumber;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getSexDescription() {
		return sex == null ? "" : sex.getDescription();
	}
	
	@JSONField(format = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Nation getNation() {
		return nation;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}

	public String getDegreeEducation() {
		return degreeEducation;
	}

	public void setDegreeEducation(String degreeEducation) {
		this.degreeEducation = degreeEducation;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CertificateType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getMedicalInsuranceNumber() {
		return medicalInsuranceNumber;
	}

	public void setMedicalInsuranceNumber(String medicalInsuranceNumber) {
		this.medicalInsuranceNumber = medicalInsuranceNumber;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getOtherTelephone() {
		return otherTelephone;
	}

	public void setOtherTelephone(String otherTelephone) {
		this.otherTelephone = otherTelephone;
	}

	public String getRemakrs() {
		return remakrs;
	}

	public void setRemakrs(String remakrs) {
		this.remakrs = remakrs;
	}
	
	public Boolean getSpecialTab() {
		return specialTab;
	}

	public void setSpecialTab(Boolean specialTab) {
		this.specialTab = specialTab;
	}

	public String getSpecialTabNumber() {
		return specialTabNumber;
	}

	public void setSpecialTabNumber(String specialTabNumber) {
		this.specialTabNumber = specialTabNumber;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public String getoSTA() {
		return oSTA;
	}

	public void setoSTA(String oSTA) {
		this.oSTA = oSTA;
	}

	public String geteGFR() {
		return eGFR;
	}

	public void seteGFR(String eGFR) {
		this.eGFR = eGFR;
	}

	public String getfRAXMain() {
		return fRAXMain;
	}

	public void setfRAXMain(String fRAXMain) {
		this.fRAXMain = fRAXMain;
	}

	public String getfRAXHipbone() {
		return fRAXHipbone;
	}

	public void setfRAXHipbone(String fRAXHipbone) {
		this.fRAXHipbone = fRAXHipbone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDxaNumber(String dxaNumber) {
		this.dxaNumber = dxaNumber;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

}
