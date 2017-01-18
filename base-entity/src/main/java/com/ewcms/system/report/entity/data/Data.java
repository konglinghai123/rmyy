package com.ewcms.system.report.entity.data;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseEntity;

/**
 * 数据父类
 * 
 * <ul>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_data")
@Inheritance(strategy = InheritanceType.JOINED)
public class Data extends BaseEntity<Long> {

    private static final long serialVersionUID = -4261767148281294408L;
}
