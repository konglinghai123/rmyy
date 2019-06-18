package com.ewcms.yjk.re.zd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 可显示的字段库
 * <ul>
 * <li>ruleName:规则名(字段名)</li>
 * <li>ruleCnName:规则名(中文名)</li>
 *  <li>width:字段显示宽度</li>
 * </ul>
 * 
 * @author zhoudongchu
 *
 */
@Entity
@Table(name = "re_zd_display_column")
@SequenceGenerator(name="seq", sequenceName="seq_re_zd_display_column_id", allocationSize = 1)
public class DisplayColumn extends BaseSequenceEntity<Long>{
	private static final long serialVersionUID = -1670134352959536427L;

	@Column(name = "rule_name", nullable = false, unique=true)
	private String ruleName;
	
	@Column(name = "rule_cn_name", nullable = false, unique=true)
	private String ruleCnName;
	
	@Column(name = "width", nullable = false)
	private Long width = Long.valueOf(0);
	
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

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}
	
	
}
