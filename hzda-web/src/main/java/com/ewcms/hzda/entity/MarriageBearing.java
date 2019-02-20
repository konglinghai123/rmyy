package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 婚育史
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_marriage_bearing")
@SequenceGenerator(name = "seq", sequenceName = "hzda_marriage_bearing_id", allocationSize = 1)
public class MarriageBearing extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 3658983467080193990L;
}
