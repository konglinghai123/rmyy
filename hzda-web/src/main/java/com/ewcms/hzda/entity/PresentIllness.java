package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 现病史
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_present_illness")
@SequenceGenerator(name = "seq", sequenceName = "hzda_present_illness_id", allocationSize = 1)
public class PresentIllness extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -4906838832527597678L;
}
