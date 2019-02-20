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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.utils.PatternUtils;
import com.ewcms.hzda.zd.entity.CertificateType;
import com.ewcms.hzda.zd.entity.Nation;

/**
 * 一般信息
 * 
 * <ul>
 * <li>totalNumber:总编号</li>
 * <li>transplantNumber:移植编号</li>
 * <li>name:姓名</li>
 * <li>hospitalizationNumber:住院号</li>
 * <li>maNumber:MA号</li>
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
 * <li>remakrs:备注</li>
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

	@Column(name = "total_number")
	private String totalNumber;
	@Column(name = "transplant_number")
	private String transplantNumber;
	@Column(name = "name")
	private String name;
	@Column(name = "hospitalization_number")
	private String hospitalizationNumber;
	@Column(name = "ma_number")
	private String maNumber;
	@Enumerated(EnumType.STRING)
	@Column(name = "sex")
	private Sex sex = Sex.MALE;
	@Column(name = "birthday")
	private Date birthday;
	@NotNull(message = "{not.null}")
	@OneToOne(cascade = { CascadeType.REFRESH }, targetEntity = Nation.class)
	@JoinColumn(name = "nation_id")
	private Nation nation;
	@Column(name = "degree_education")
	private String degreeEducation;
	@Column(name = "occupation")
	private String occupation;
	@Column(name = "address")
	private String address;
	@NotNull(message = "{not.null}")
	@OneToOne(cascade = { CascadeType.REFRESH }, targetEntity = CertificateType.class)
	@JoinColumn(name = "certificate_type_id")
	private CertificateType certificateType;
	@Column(name = "certificate_number")
	private String certificateNumber;
	@Column(name = "medicalI_insurance_number")
	private String medicalInsuranceNumber;
    @NotEmpty(message = "{not.null}")
    @Pattern(regexp = PatternUtils.MOBILE_PHONE_NUMBER_PATTERN, message = "{user.mobile.phone.number.not.valid}")
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;
    @Column(name = "other_telephone")
    private String otherTelephone;
    @Column(name = "remakrs", columnDefinition = "text")
    private String remakrs;

}
