package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 影像学检查:骨折
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_fracture")
@SequenceGenerator(name = "seq", sequenceName = "hzda_fracture_id", allocationSize = 1)
public class Fracture extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -4508431888237273925L;
}
