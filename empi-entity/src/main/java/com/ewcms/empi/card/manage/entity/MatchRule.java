package com.ewcms.empi.card.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.ewcms.common.entity.BaseEntity;

/**
 * 匹配规则信息
 * <ul>
 * <li>fieldName:字段名称</li>
 * <li>cnName：中文名称</li>
 * <li>matched:是否为匹配项</li>
 * </ul>
 * @author zhoudongchu
 *
 */
@Entity
@Table(name = "card_match_rule")
public class MatchRule extends BaseEntity<Long> {
	private static final long serialVersionUID = 355468078858679311L;
	@NotNull(message = "{not.null}")
    @Column(name = "field_name", nullable = false,unique = true)
	private String fieldName;
	@NotNull(message = "{not.null}")
    @Column(name = "cn_name", nullable = false)
	private String cnName;
	@Column(name = "is_matched")
    private Boolean matched = Boolean.FALSE;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public Boolean getMatched() {
		return matched;
	}
	public void setMatched(Boolean matched) {
		this.matched = matched;
	}
}
