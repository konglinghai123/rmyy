package com.ewcms.security.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.PatternUtils;
import com.ewcms.security.dictionary.entity.DepartmentAttribute;
import com.ewcms.security.dictionary.entity.Profession;
import com.ewcms.security.dictionary.entity.Technical;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.Date;
import java.util.List;

/**
 * 用户
 * 
 * <ul>
 * <li>username:用户名</li>
 * <li>email:email</li>
 * <li>mobilePhoneNumber:手机号</li>
 * <li>password:密码(使用md5(username + original password + salt)加密存储)</li>
 * <li>salt:加密密码时使用的种子</li>
 * <li>createDate:创建时间</li>
 * <li>status:用户状态</li>
 * <li>admin:是否是管理员</li>
 * <li>deleted:是否删除(逻辑删除)</li>
 * <li>organizationJobs:用户/组织机构/工作职务关联</li>
 * <li>realname:真实姓名</li>
 * <li>sex:性别</li>
 * <li>profession:职业</li>
 * <li>technical:技术职称</li>
 * <li>appoint:是否聘任</li>
 * <li>departmentAttribute:科室属性</li>
 * <li>pharmacy:是否药事会成员</li>
 * <li>antibiosis:是否抗菌药物遴选小组成员</li>
 * <li>professionalAttr:专业属性</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_user",
		uniqueConstraints = {
			@UniqueConstraint(name = "unique_sec_user_username", columnNames = "username"),
			@UniqueConstraint(name = "unique_sec_user_email", columnNames = "email"),
			@UniqueConstraint(name = "unique_sec_user_mobile_phone_number", columnNames = "mobile_phone_number")
		},
		indexes = {
			@Index(name = "idx_sec_user_status", columnList = "status")
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_user_id", allocationSize = 1)
public class User extends BaseSequenceEntity<Long> implements LogicDeleteable {
	
	private static final long serialVersionUID = -6104610983204668263L;
	
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
	
    @NotNull(message = "{not.null}")
//    @Pattern(regexp = PatternUtils.USERNAME_PATTERN, message = "{user.username.not.valid}")
    @Column(name = "username")
    private String username;
    @NotEmpty(message = "{not.null}")
    @Pattern(regexp = PatternUtils.EMAIL_PATTERN, message = "{user.email.not.valid}")
    @Column(name = "email")
    private String email;
    @NotEmpty(message = "{not.null}")
    @Pattern(regexp = PatternUtils.MOBILE_PHONE_NUMBER_PATTERN, message = "{user.mobile.phone.number.not.valid}")
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;
    @Length(min = PatternUtils.PASSWORD_MIN_LENGTH, max = PatternUtils.PASSWORD_MAX_LENGTH, message = "{user.password.not.valid}")
    @Column(name = "password")
    private String password;
    @Column(name = "salt")
    private String salt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date", columnDefinition = "Timestamp default CURRENT_DATE", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status = UserStatus.normal;
    @Column(name = "is_admin")
    private Boolean admin = false;
    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = UserOrganizationJob.class, mappedBy = "user", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Basic(optional = true, fetch = FetchType.EAGER)
    //集合缓存引起的
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//集合缓存
    @OrderBy()
    private List<UserOrganizationJob> organizationJobs;
    @Column(name = "realname")
    private String realname;
    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
	private Sex sex;
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	private Profession profession;
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	@JoinColumn(name = "technical_id")
	private Technical technical;
	@Column(name = "is_appoint")
	private Boolean appoint = Boolean.FALSE;
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
	@JoinColumn(name = "department_attribute_id")
	private DepartmentAttribute departmentAttribute;
	@Column(name = "is_pharmacy")
	private Boolean pharmacy = Boolean.FALSE;
	@Column(name = "is_antibiosis")
	private Boolean antibiosis = Boolean.FALSE;
	@Column(name = "professional_attribute")
	private String professionalAttribute;
  
    public User() {
    }

    public User(Long id) {
        setId(id);
    }

    @JSONField(serialize = false)
    public List<UserOrganizationJob> getOrganizationJobs() {
    	return (organizationJobs == null) ? Lists.<UserOrganizationJob>newArrayList() : organizationJobs;
    }

    public void addOrganizationJob(UserOrganizationJob userOrganizationJob) {
        userOrganizationJob.setUser(this);
        getOrganizationJobs().add(userOrganizationJob);
    }

    public void setOrganizationJobs(List<UserOrganizationJob> organizationJobs) {
        this.organizationJobs = organizationJobs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JSONField(serialize = false)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 生成新的种子
     */
    public void randomSalt() {
        setSalt(RandomStringUtils.randomAlphanumeric(10));
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    public String getStatusInfo(){
    	return status == null ? "" : status.getInfo();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public String getUsernameAndRealname(){
		return this.username + (EmptyUtil.isStringNotEmpty(this.realname) ? "(" + this.realname + ")" : ""); 
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public Technical getTechnical() {
		return technical;
	}

	public void setTechnical(Technical technical) {
		this.technical = technical;
	}

	public Boolean getAppoint() {
		return appoint;
	}

	public void setAppoint(Boolean appoint) {
		this.appoint = appoint;
	}

	public DepartmentAttribute getDepartmentAttribute() {
		return departmentAttribute;
	}

	public void setDepartmentAttribute(DepartmentAttribute departmentAttribute) {
		this.departmentAttribute = departmentAttribute;
	}

	public Boolean getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Boolean pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Boolean getAntibiosis() {
		return antibiosis;
	}

	public void setAntibiosis(Boolean antibiosis) {
		this.antibiosis = antibiosis;
	}

	public String getProfessionalAttribute() {
		return professionalAttribute;
	}

	public void setProfessionalAttribute(String professionalAttribute) {
		this.professionalAttribute = professionalAttribute;
	}
}