package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 手术史
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_operation")
@SequenceGenerator(name = "seq", sequenceName = "hzda_operation_id", allocationSize = 1)
public class Operation extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -2349684240594361646L;

}
