package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 检查及化验结果
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_examination_laboratory_results")
@SequenceGenerator(name = "seq", sequenceName = "hzda_examination_laboratory_results_id", allocationSize = 1)
public class ExaminationLaboratoryResults extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 5865754068366891028L;
}
