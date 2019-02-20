package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 肾移植患者
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_renal_transplant")
@SequenceGenerator(name = "seq", sequenceName = "hzda_renal_transplant_id", allocationSize = 1)
public class RenalTransplant extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -1908104378790577657L;
}
