package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 个人史
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_personal")
@SequenceGenerator(name = "seq", sequenceName = "hzda_personal_id", allocationSize = 1)
public class Personal extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 4497015972208293083L;
}
