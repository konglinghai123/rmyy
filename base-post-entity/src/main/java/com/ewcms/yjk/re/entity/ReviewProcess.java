package com.ewcms.yjk.re.entity;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.Movable;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.yjk.re.zd.entity.DisplayColumn;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;
import com.google.common.collect.Sets;

/**
 * 评审流程
 * 
 * <li>reviewMain:评审主表对象</li>
 * <li>reviewBaseRule:评审基本规则对象</li>
 * <li>weight:排序号</li>
 * <li>displayColumns:可显示的字段库对象集合</li>
 * <li>finished:是否投票完成</li>
 * <li>generalNameChinese:通用名中成药通过数量</li>
 * <li>generalNameWestern:通用名西药通过数量</li>
 * <li>formulaChinese:剂型/规格中成药通过数量</li>
 * <li>formulaWestern:剂型/规格西药通过数量</li>
 * <li>ensureOrganPassChineseNumber:确保申报科室通过中成药数</li>
 * <li>ensureOrganPassWesternNumber:确保申报科室通过西药数</li>
 * <li>hospitalData:是否显示院用数据</li>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "re_review_process")
@SequenceGenerator(name = "seq", sequenceName = "seq_re_review_process_id", allocationSize = 1)
public class ReviewProcess extends BaseSequenceEntity<Long> implements Movable{

	private static final long serialVersionUID = 4159108502524796016L;

	@ManyToOne()
    @Fetch(FetchMode.SELECT)
	private ReviewMain reviewMain;
	@ManyToOne()
    @Fetch(FetchMode.SELECT)
	private ReviewBaseRule reviewBaseRule;
	@Column(name = "weight")
	private Integer weight;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "re_review_process_column", joinColumns = {
			@JoinColumn(name = "process_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "column_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"process_id", "column_id" })})
	private List<DisplayColumn> displayColumns;
	@Column(name = "is_finished")
	private Boolean finished = Boolean.FALSE;
	@Column(name = "general_name_chinese")
	private Long generalNameChinese = 0L;
	@Column(name = "general_name_western")
	private Long generalNameWestern = 0L;
	@Column(name = "formula_chinese")
	private Long formulaChinese = 0L;
	@Column(name = "formula_western")
	private Long formulaWestern = 0L;
	@Column(name = "ensure_organ_pass_chinese_number")
	private Long ensureOrganPassChineseNumber = 0L;
	@Column(name = "ensure_organ_pass_western_number")
	private Long ensureOrganPassWesternNumber = 0L;
	@OneToMany(cascade = { CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, targetEntity = EnsurePassThrough.class, mappedBy = "reviewProcess", orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	@Basic(optional = true, fetch = FetchType.EAGER)
	@OrderBy("weight")
	private List<EnsurePassThrough> ensurePassThrough;
	@Column(name = "is_hospital_data")
	private Boolean hospitalData = Boolean.FALSE;
	
	@JSONField(serialize = false)
	public ReviewMain getReviewMain() {
		return reviewMain;
	}

	public void setReviewMain(ReviewMain reviewMain) {
		this.reviewMain = reviewMain;
	}

	public ReviewBaseRule getReviewBaseRule() {
		return reviewBaseRule;
	}

	public void setReviewBaseRule(ReviewBaseRule reviewBaseRule) {
		this.reviewBaseRule = reviewBaseRule;
	}

	public List<DisplayColumn> getDisplayColumns() {
		return displayColumns;
	}
	
	public String getDisplayColumnRuleCnNames() {
		return (EmptyUtil.isCollectionNotEmpty(displayColumns))
				? Collections3.convertToString(Collections3.extractToList(displayColumns, "ruleCnName"), "，")
				: "";
	}
	
	@SuppressWarnings("unchecked")
	public Set<Long> getDisplayColumnsIds() {
		return (EmptyUtil.isCollectionNotEmpty(displayColumns)) ? Collections3.extractToSet(displayColumns, "id")
				: Sets.newHashSet();
	}
	
	public void setDisplayColumns(List<DisplayColumn> displayColumns) {
		this.displayColumns = displayColumns;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	@Override
	public Integer getWeight() {
		return weight;
	}

	@Override
	public void setWeight(Integer weight) {
		this.weight = weight;
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

	public Long getEnsureOrganPassChineseNumber() {
		return ensureOrganPassChineseNumber;
	}

	public void setEnsureOrganPassChineseNumber(Long ensureOrganPassChineseNumber) {
		this.ensureOrganPassChineseNumber = ensureOrganPassChineseNumber;
	}

	public Long getEnsureOrganPassWesternNumber() {
		return ensureOrganPassWesternNumber;
	}

	public void setEnsureOrganPassWesternNumber(Long ensureOrganPassWesternNumber) {
		this.ensureOrganPassWesternNumber = ensureOrganPassWesternNumber;
	}

	@JSONField(serialize = false)
	public List<EnsurePassThrough> getEnsurePassThrough() {
		return ensurePassThrough;
	}
	
	public void addEnsurePassThrough(EnsurePassThrough ensurePassThrough) {
		ensurePassThrough.setReviewProcess(this);
		getEnsurePassThrough().add(ensurePassThrough);
	}

	public void setEnsurePassThrough(List<EnsurePassThrough> ensurePassThrough) {
		this.ensurePassThrough = ensurePassThrough;
	}
	
	public Boolean getIsEnsurePassThrough() {
		return (EmptyUtil.isCollectionEmpty(ensurePassThrough)) ? false : true;
	}

	public Boolean getHospitalData() {
		return hospitalData;
	}

	public void setHospitalData(Boolean hospitalData) {
		this.hospitalData = hospitalData;
	}
}
