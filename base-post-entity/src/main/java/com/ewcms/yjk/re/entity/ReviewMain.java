package com.ewcms.yjk.re.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.user.entity.User;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 评审主表
 * <ul>
 * <li>name:名称</li>
 * <li>createDate:创建时间</li>
 * <li>remark:说明</li>
 * <li>reviewExperts:评审专家过滤条件集合</li>
 * <li>:筛选时间</li>
 * <li>users:用户对象集合(直接指定的用户)</li>
 * <li>enabled:是否启用</li>
 * <li>systemParameter:系统参数对象(评审申报)</li>
 * <li>generalNameChinese:新增通用名中成药数量</li>
 * <li>generalNameWestern:新增通用名西药数量</li>
 * <li>formulaChinese:新增剂型/规格中成药数量</li>
 * <li>formulaWestern:新增剂型/规格西药数量</li>
 * <li>reviewProcesses:评审流程集合</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "re_review_main")
@SequenceGenerator(name = "seq", sequenceName = "seq_re_review_main_id", allocationSize = 1)
public class ReviewMain extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -439118229004524195L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_date", columnDefinition = "Timestamp default CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@Column(name = "remark", columnDefinition = "text")
	private String remark;
	@OneToMany(cascade = { CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, targetEntity = ReviewExpert.class, mappedBy = "reviewMain", orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	@Basic(optional = true, fetch = FetchType.EAGER)
	@OrderBy("weight")
	private List<ReviewExpert> reviewExperts;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "extract_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date extractDate;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "re_review_main_user", joinColumns = {
			@JoinColumn(name = "review_main_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"user_id", "review_main_id" })})
	private List<User> users;
	@Column(name = "is_enabled")
	private Boolean enabled = Boolean.FALSE;
	@ManyToOne()
    @Fetch(FetchMode.SELECT)
	private SystemParameter systemParameter;
	@Column(name = "general_name_chinese")
	private Long generalNameChinese = 0L;
	@Column(name = "general_name_western")
	private Long generalNameWestern = 0L;
	@Column(name = "formula_chinese")
	private Long formulaChinese = 0L;
	@Column(name = "formula_western")
	private Long formulaWestern = 0L;
	@OneToMany(cascade = { CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, targetEntity = ReviewProcess.class, mappedBy = "reviewMain", orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	@Basic(optional = true, fetch = FetchType.EAGER)
	@OrderBy("weight")
	private List<ReviewProcess> reviewProcesses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JSONField(serialize = false)
	public List<ReviewExpert> getReviewExperts() {
		return (reviewExperts == null) ? Lists.<ReviewExpert>newArrayList() : reviewExperts;
	}

	public void addReviewExpert(ReviewExpert reviewExpert) {
		reviewExpert.setReviewMain(this);
		getReviewExperts().add(reviewExpert);
	}
	
	public void setReviewExperts(List<ReviewExpert> reviewExperts) {
		this.reviewExperts = reviewExperts;
	}

	@JSONField(serialize = false)
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUserNames() {
		return (EmptyUtil.isCollectionNotEmpty(users))
				? Collections3.convertToString(Collections3.extractToList(users, "username"), "/")
				: "";
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getUserIds() {
		return (EmptyUtil.isCollectionNotEmpty(users)) ? Collections3.extractToSet(users, "id")
				: Sets.newHashSet();
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getExtractDate() {
		return extractDate;
	}

	public void setExtractDate(Date extractDate) {
		this.extractDate = extractDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JSONField(serialize = false)
	public SystemParameter getSystemParameter() {
		return systemParameter;
	}

	public void setSystemParameter(SystemParameter systemParameter) {
		this.systemParameter = systemParameter;
	}
	
	public String getSystemParameterProjectRemark() {
		return (systemParameter != null) ? systemParameter.getProjectRemark() : "";
	}
	
	public String getSystemParameterRange() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return (systemParameter != null) ? sdf.format(systemParameter.getApplyStartDate()) + " 至 " + sdf.format(systemParameter.getApplyEndDate()) : "";
		} catch (Exception e) {
			return "";
		}
	}

	public Long getGeneralNameChinese() {
		return generalNameChinese;
	}

	public void setGeneralNameChinese(Long generalNameChinese) {
		this.generalNameChinese = generalNameChinese;
	}

	public Long getGeneralNameWestern() {
		return generalNameWestern;
	}

	public void setGeneralNameWestern(Long generalNameWestern) {
		this.generalNameWestern = generalNameWestern;
	}

	public Long getFormulaChinese() {
		return formulaChinese;
	}

	public void setFormulaChinese(Long formulaChinese) {
		this.formulaChinese = formulaChinese;
	}

	public Long getFormulaWestern() {
		return formulaWestern;
	}

	public void setFormulaWestern(Long formulaWestern) {
		this.formulaWestern = formulaWestern;
	}

	@JSONField(serialize = false)
	public List<ReviewProcess> getReviewProcesses() {
		return reviewProcesses;
	}
	
	public void addReviewProcess(ReviewProcess reviewProcess) {
		reviewProcess.setReviewMain(this);
		getReviewProcesses().add(reviewProcess);
	}

	public void setReviewProcesses(List<ReviewProcess> reviewProcesses) {
		this.reviewProcesses = reviewProcesses;
	}
}
