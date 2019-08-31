package com.ewcms.hzda.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 用药记录
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationName:医院名称</li>
 * <li>generalInformationId:患者ID</li>
 * <li>medicationDate:用药日期</li>
 * <li>vitd:VitD</li>
 * <li>calcium:钙剂</li>
 * <li>diphosphonate:双膦酸盐</li>
 * <li>calcitonin:降钙素</li>
 * <li>teriparatide:特立帕肽</li>
 * <li>denosumab:地诺单抗</li>
 * <li>chineseMedicine:中药</li>
 * <li>other:其他</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_medication_record")
@SequenceGenerator(name = "seq", sequenceName = "hzda_medication_record_id", allocationSize = 1)
public class MedicationRecord extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 1194398867855807316L;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "medication_date")
	@Temporal(TemporalType.DATE)
	private Date medicationDate;
	@Column(name = "vitd")
	private String vitd;
	@Column(name = "calcium")
	private String calcium;
	@Column(name = "diphosphonate")
	private String diphosphonate;
	@Column(name = "calcitonin")
	private String calcitonin;
	@Column(name = "teriparatide")
	private String teriparatide;
	@Column(name = "denosumab")
	private String denosumab;
	@Column(name = "chinese_medicine")
	private String chineseMedicine;
	@Column(name = "other")
	private String other;

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

	@JSONField(format = "yyyy-MM-dd")
	public Date getMedicationDate() {
		return medicationDate;
	}

	public void setMedicationDate(Date medicationDate) {
		this.medicationDate = medicationDate;
	}

	public String getCalcium() {
		return calcium;
	}

	public void setCalcium(String calcium) {
		this.calcium = calcium;
	}

	public String getDiphosphonate() {
		return diphosphonate;
	}

	public void setDiphosphonate(String diphosphonate) {
		this.diphosphonate = diphosphonate;
	}

	public String getCalcitonin() {
		return calcitonin;
	}

	public void setCalcitonin(String calcitonin) {
		this.calcitonin = calcitonin;
	}

	public String getTeriparatide() {
		return teriparatide;
	}

	public void setTeriparatide(String teriparatide) {
		this.teriparatide = teriparatide;
	}

	public String getDenosumab() {
		return denosumab;
	}

	public void setDenosumab(String denosumab) {
		this.denosumab = denosumab;
	}

	public String getChineseMedicine() {
		return chineseMedicine;
	}

	public void setChineseMedicine(String chineseMedicine) {
		this.chineseMedicine = chineseMedicine;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getVitd() {
		return vitd;
	}

	public void setVitd(String vitd) {
		this.vitd = vitd;
	}

}
