package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 诊断措施
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_diagnostic_measures")
@SequenceGenerator(name = "seq", sequenceName = "hzda_diagnostic_measures_id", allocationSize = 1)
public class DiagnosticMeasures extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -283170483416935351L;
}
