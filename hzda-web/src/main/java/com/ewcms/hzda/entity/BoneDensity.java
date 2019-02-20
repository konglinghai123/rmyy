package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 骨密度
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_bone_density")
@SequenceGenerator(name = "seq", sequenceName = "hzda_bone_density_id", allocationSize = 1)
public class BoneDensity extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 7934071651906269300L;
}
