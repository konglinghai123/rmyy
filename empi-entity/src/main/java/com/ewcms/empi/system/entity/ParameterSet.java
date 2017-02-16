package com.ewcms.empi.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ewcms.common.entity.AbstractEntity;

/**
 * 系统参数变量设置
 * <ul>
 * <li>id:参数编号</li>
 * <li>variableValue:参数值</li>
 * <li>remark:参数说明</li>
 * </ul>
 *@author zhoudongchu
 */
@Entity
@Table(name = "sys_parameter_set")
public class ParameterSet extends AbstractEntity<String> {
	private static final long serialVersionUID = 6978596599190929320L;
	
	@Id
    private String id;
	@Column(name = "variable_value", nullable = false)
    private String variableValue;
	@Column(name = "remark")
    private String remark;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id  = id;

	}

	public String getVariableValue() {
		return variableValue;
	}

	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
