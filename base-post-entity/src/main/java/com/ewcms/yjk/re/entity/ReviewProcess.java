package com.ewcms.yjk.re.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.Movable;
import com.ewcms.yjk.re.zd.entity.DisplayColumn;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;

/**
 * 评审流程
 * <li>finished:是否投票完成</li>
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
}
