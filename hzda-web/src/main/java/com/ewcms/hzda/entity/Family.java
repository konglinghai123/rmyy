package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 家族史
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_family")
@SequenceGenerator(name = "seq", sequenceName = "hzda_family_id", allocationSize = 1)
public class Family extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 2283753954193560056L;
}
