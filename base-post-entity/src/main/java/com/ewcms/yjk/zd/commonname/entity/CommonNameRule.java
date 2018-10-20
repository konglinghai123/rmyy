package com.ewcms.yjk.zd.commonname.entity;

import javax.persistence.Column;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.ewcms.common.plugin.entity.Movable;

/**
 * 药品匹配规则
 * <ul>
 * <li>ruleName:规则名(字段名)</li>
 * <li>ruleCnName:规则名(中文名)</li>
 * <li>weight:排序</li>
 * <li>deleted:是否删除</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
public class CommonNameRule extends BaseSequenceEntity<Long> implements Movable, LogicDeleteable {

	private static final long serialVersionUID = -1670134352959536427L;

	@Column(name = "rule_name", nullable = false)
	private String ruleName;
	@Column(name = "rule_cn_name", nullable = false)
	private String ruleCnName;
	@Column(name = "weith")
	private Integer weight;
	@Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
	
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

	@Override
	public Integer getWeight() {
		return null;
	}

	@Override
	public void setWeight(Integer weight) {
	}
}
