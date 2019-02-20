package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 既往史
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_anamnesis")
@SequenceGenerator(name = "seq", sequenceName = "hzda_anamnesis_id", allocationSize = 1)
public class Anamnesis extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 2951884781694274310L;

}
