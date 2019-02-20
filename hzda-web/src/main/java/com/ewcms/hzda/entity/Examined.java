package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 查体
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_examined")
@SequenceGenerator(name = "seq", sequenceName = "hzda_examined_id", allocationSize = 1)
public class Examined extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 8553601268097010437L;
}
