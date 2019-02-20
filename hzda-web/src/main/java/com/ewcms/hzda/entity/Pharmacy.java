package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 用药史
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_pharmacy")
@SequenceGenerator(name = "seq", sequenceName = "hzda_pharmacy_id", allocationSize = 1)
public class Pharmacy extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 3333283547457263547L;
}
