package com.ewcms.hzda.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 用药记录
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_medication_record")
@SequenceGenerator(name = "seq", sequenceName = "hzda_medication_record_id", allocationSize = 1)
public class MedicationRecord extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 1194398867855807316L;
}
