package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 诊断
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_diagnosis")
@SequenceGenerator(name = "seq", sequenceName = "hzda_diagnosis_id", allocationSize = 1)
public class Diagnosis extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 2611408066838264721L;


}
