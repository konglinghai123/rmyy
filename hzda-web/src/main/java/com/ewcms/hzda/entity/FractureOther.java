package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 其它相关检查
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_fracture_other")
@SequenceGenerator(name = "seq", sequenceName = "hzda_fracture_other_id", allocationSize = 1)
public class FractureOther extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 3493939727890676745L;
}
