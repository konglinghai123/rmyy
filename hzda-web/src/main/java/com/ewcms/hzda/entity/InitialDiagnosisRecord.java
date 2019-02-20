package com.ewcms.hzda.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 初诊记录表
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationNames:医院名称</li>
 * <li>recordingTime:记录时间</li>
 * <li>deleted:删除标志</li>
 * <li>generalInformation:一般信息(对象)</li>
 * <li>complained:主诉(对象)</li>
 * <li>presentIllness:现病史(对象)</li>
 * <li>anamnesis:既往史(对象)</li>
 * <li>anamnesis:用药史(对象)</li>
 * <li>cataclasis:骨折史(对象)</li>
 * <li>operation:手术史(对象)</li>
 * <li>personal:个人史(对象)</li>
 * <li>marriageBearing:婚育史(对象)-女性填写</li>
 * <li>family:家族史(对象)</li>
 * <li>renalTransplant:肾移植患者(对象)</li>
 * <li>examined:查体(对象)</li>
 * <li>diagnosis:诊断(对象)</li>
 * <li>diagnosticMeasures:诊断措施(对象)</li>
 * <li>examinationLaboratoryResults:检查及化验结果(对象)</li>
 * <li>boneDensity:骨密度(对象)</li>
 * <li>fracture:影像学检查:骨折(对象)</li>
 * <li>fractureOther:其它相关检查(对象)</li>
 * <li>medicationRecord:用药记录(对象)</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_initial_diagnosis_record")
@SequenceGenerator(name="seq", sequenceName="hzda_initial_diagnosis_record_id", allocationSize = 1)
public class InitialDiagnosisRecord extends BaseSequenceEntity<Long> implements LogicDeleteable{

	private static final long serialVersionUID = -7664091306139564448L;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	@Formula(value = "(select string_agg(t2.name,',') from sec_organization t2  where t2.id in (select t1.organization_id from sec_user_organization_job t1 where t1.user_id=user_id))")
	private String organizationNames;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "recording_time", columnDefinition = "Timestamp default CURRENT_DATE", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date recordingTime;
    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "general_information_id")
    private GeneralInformation generalInformation;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "complained_id")
	private Complained complained;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "present_illness_id")
	private PresentIllness presentIllness;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "anamnesis_id")
	private Anamnesis anamnesis;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "cataclasis_id")
	private Cataclasis cataclasis;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "operation_id")
	private Operation operation;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "personal_id")
	private Personal personal;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "marriage_bearing_id")
	private MarriageBearing marriageBearing;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "family_id")
	private Family family;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "renal_transplant_id")
	private RenalTransplant renalTransplant;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "examined_id")
	private Examined examined;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "diagnosis_id")
	private Diagnosis diagnosis;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "diagnostic_measures_id")
	private DiagnosticMeasures diagnosticMeasures;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "examination_laboratory_results_id")
	private ExaminationLaboratoryResults examinationLaboratoryResults;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "boneDensity_id")
	private BoneDensity boneDensity;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "fracture_id")
	private Fracture fracture;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "fracture_other_id")
	private FractureOther fractureOther;
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "medication_record_id")
	private MedicationRecord medicationRecord;
	
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationNames() {
		return organizationNames;
	}

	public Date getRecordingTime() {
		return recordingTime;
	}

	public void setRecordingTime(Date recordingTime) {
		this.recordingTime = recordingTime;
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

	public GeneralInformation getGeneralInformation() {
		return generalInformation;
	}

	public void setGeneralInformation(GeneralInformation generalInformation) {
		this.generalInformation = generalInformation;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setOrganizationNames(String organizationNames) {
		this.organizationNames = organizationNames;
	}
}
