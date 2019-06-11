package com.ewcms.yjk.re.zd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.Movable;

/**
 * 评审规则
 * <ul>
 * <li>ruleName:规则名(字段名)</li>
 * <li>ruleCnName:规则名(中文名)</li>
 * <li>weight:排序</li>
 * <li>enabled:是否启用</li>
 * </ul>
 * 
 * @author zhoudongchu
 *
 */
@Entity
@Table(name = "re_zd_review_rule")
@SequenceGenerator(name="seq", sequenceName="seq_re_zd_review_rule_id", allocationSize = 1)
public class ReviewRule extends BaseSequenceEntity<Long> implements Movable{
	private static final long serialVersionUID = -1670134352959536427L;

	@Column(name = "rule_name", nullable = false, unique=true)
	private String ruleName;
	
	@Column(name = "rule_cn_name", nullable = false, unique=true)
	private String ruleCnName;
	
	@Column(name = "weight")
	private Integer weight;
	
	@Column(name = "is_enabled")
	private Boolean enabled = Boolean.FALSE;
	
	@Column(name = "review_main_id", nullable = false)
	private Long reviewMainId;
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleCnName() {
		return ruleCnName;
	}

	public void setRuleCnName(String ruleCnName) {
		this.ruleCnName = ruleCnName;
	}


    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public Long getReviewMainId() {
		return reviewMainId;
	}

	public void setReviewMainId(Long reviewMainId) {
		this.reviewMainId = reviewMainId;
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
