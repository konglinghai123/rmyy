package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 骨折史
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_cataclasis")
@SequenceGenerator(name = "seq", sequenceName = "hzda_cataclasis_id", allocationSize = 1)
public class Cataclasis extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -9185530740971251607L;
}
