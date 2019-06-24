package com.ewcms.yjk.re.zd.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.google.common.collect.Sets;

/**
 * 评审基本规则制定
 * <ul>
 * <li>ruleName:规则名(字段名)</li>
 * <li>ruleCnName:规则名(中文名)</li>
 *  <li>displayColumns:所要显示的字段</li>
 * </ul>
 * 
 * @author zhoudongchu
 *
 */
@Entity
@Table(name = "re_zd_review_base_rule")
@SequenceGenerator(name="seq", sequenceName="seq_re_zd_review_base_rule_id", allocationSize = 1)
public class ReviewBaseRule extends BaseSequenceEntity<Long> {
	private static final long serialVersionUID = -1670134352959536427L;

	@Column(name = "rule_name", nullable = false, unique=true)
	private String ruleName;
	
	@Column(name = "rule_cn_name", nullable = false, unique=true)
	private String ruleCnName;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "re_zd_review_display_column", joinColumns = {
			@JoinColumn(name = "review_base_rule_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "display_column_id", referencedColumnName = "id") }, uniqueConstraints = {@UniqueConstraint(columnNames = {
							"review_base_rule_id", "display_column_id" })})
	private List<DisplayColumn> displayColumns;
	
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

	public List<DisplayColumn> getDisplayColumns() {
		return displayColumns;
	}

	public void setDisplayColumns(List<DisplayColumn> displayColumns) {
		this.displayColumns = displayColumns;
	}
	public String getDisplayColumnsNames() {
		return (EmptyUtil.isCollectionNotEmpty(displayColumns))
				? Collections3.convertToString(Collections3.extractToList(displayColumns, "ruleCnName"), "，")
				: "";
	}
	@SuppressWarnings("unchecked")
	public Set<Long> getDisplayColumnsIds() {
		return (EmptyUtil.isCollectionNotEmpty(displayColumns)) ? Collections3.extractToSet(displayColumns, "id")
				: Sets.newHashSet();
	}
}
