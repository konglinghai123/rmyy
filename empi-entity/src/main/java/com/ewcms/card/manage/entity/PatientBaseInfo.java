package com.ewcms.card.manage.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.ewcms.common.utils.EmptyUtil;
import com.google.common.collect.Lists;

/**
 * 患者基本信息
 * <ul>
 * <li>patientId：患者唯一索引号</li>
 * <li>name:姓名</li>
 * <li>sex：性别</li>
 * <li>birthday：出生日期</li>
 * <li>sourcePlace：来源地</li>
 * <li>certificateType：证件类别</li>
 * <li>certificateNo：证件号码</li>
 * <li>telephone：联系电话</li>
 * <li>contactName：联系人姓名</li>
 * <li>workUnit：工作单位</li>
 * <li>address：地址</li>
 * <li>nation：民族</li>
 * <li>profession：职业</li>
 * <li>maritalStatus：婚姻状况</li>
 * <li>medicalType：医保类别</li>
 * <li>medicalOrganize：医保机构</li>
 * <li>medicalAccount：医保账号</li>
 * <li>patientType：病人类别</li>
 * <li>contactTelephone：联系人电话</li>
 * <li>contactRelation：与联系人关系</li>
 * <li>contactAddress：联系人地址</li>
 * <li>allergyHistory：过敏史</li>
 * <li>familyHistory：家族史</li>
 * <li>practiceCards：关联的诊疗卡集合</li>
 * <li>deleted:是否删除(逻辑删除)</li>
 * </ul>
 * @author zhoudongchu
 *
 */
@Entity
@Table(name = "card_patient_base_info")
public class PatientBaseInfo extends BaseEntity<Long> implements LogicDeleteable{
	private static final long serialVersionUID = -5543736099194132304L;
	
	public final static int patientIdlength = 10;
//	@NotNull(message = "{not.null}")
    @Column(name = "patient_id", unique = true, length=patientIdlength)
	private String patientId;
	@NotNull(message = "{not.null}")
    @Column(name = "name", nullable = false)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "sex")
	private Sex sex = Sex.male;
	@NotNull(message = "{not.null}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday")
	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;
	@Column(name = "source_place")
	private String sourcePlace;
//	@NotNull(message = "{not.null}")
	@OneToOne(cascade = {CascadeType.REFRESH}, targetEntity = CertificateType.class)
	@JoinColumn(name = "certificate_type_id")
	private CertificateType certificateType;
	@Column(name = "certificate_no", nullable = false, unique = true)
	private String certificateNo; 
	@Column(name = "telephone")
	private String telephone;
	@Column(name = "contact_name")
	private String contactName;
	@Column(name = "work_unit")
	private String workUnit;
	@Column(name = "address")
	private String address;
//	@NotNull(message = "{not.null}")
	@OneToOne(cascade = {CascadeType.REFRESH}, targetEntity = Nation.class)
	@JoinColumn(name = "nation_id")
	private Nation nation;
	@Column(name = "profession")
	private String profession;
	@Column(name = "marital_status")
	private String maritalStatus;
	@Column(name = "medical_type")
	private String medicalType;
	@Column(name = "medical_organize")
	private String medicalOrganize;
	@Column(name = "medical_account")
	private String medicalAccount;
	@Column(name = "patient_type")
	private String patientType;
	@Column(name = "contact_telephone")
	private String contactTelephone;
	@Column(name = "contact_relation")
	private String contactRelation;
	@Column(name = "contact_address")
	private String contactAddress;
	@Column(name = "allergy_history")
	private String allergyHistory;
	@Column(name = "family_history")
	private String familyHistory;
	@OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = PracticeCard.class, mappedBy = "patientBaseInfo")
	@Fetch(FetchMode.SELECT)
	@Basic(optional = true, fetch = FetchType.EAGER)
	@OrderBy()
	private List<PracticeCard> practiceCards;
    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
    
    public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public String getSexInfo(){
		return sex == null ? Sex.male.getInfo() : sex.getInfo();
	}
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSourcePlace() {
		return sourcePlace;
	}

	public void setSourcePlace(String sourcePlace) {
		this.sourcePlace = sourcePlace;
	}
	
	@JSONField(serialize = false)
	public CertificateType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}
	
	public String getCertificateTypeName(){
		return EmptyUtil.isNull(certificateType) ? "" : certificateType.getName();
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Nation getNation() {
		return nation;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}

	public String getNationName(){
		return EmptyUtil.isNull(nation) ? "" : nation.getName();
	}
	
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMedicalType() {
		return medicalType;
	}

	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}

	public String getMedicalOrganize() {
		return medicalOrganize;
	}

	public void setMedicalOrganize(String medicalOrganize) {
		this.medicalOrganize = medicalOrganize;
	}

	public String getMedicalAccount() {
		return medicalAccount;
	}

	public void setMedicalAccount(String medicalAccount) {
		this.medicalAccount = medicalAccount;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getContactRelation() {
		return contactRelation;
	}

	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getAllergyHistory() {
		return allergyHistory;
	}

	public void setAllergyHistory(String allergyHistory) {
		this.allergyHistory = allergyHistory;
	}

	public String getFamilyHistory() {
		return familyHistory;
	}

	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
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
    public List<PracticeCard> getPracticeCards() {
    	return (practiceCards == null) ? Lists.<PracticeCard>newArrayList() : practiceCards;
    }

    public void addPracticeCard(PracticeCard practiceCard) {
    	practiceCard.setPatientBaseInfo(this);
        getPracticeCards().add(practiceCard);
    }

    public void setPracticeCards(List<PracticeCard> practiceCards) {
        this.practiceCards = practiceCards;
    }
}
