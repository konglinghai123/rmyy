package com.ewcms.system.scheduling.generate.job.report.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseEntity;
import com.ewcms.system.report.entity.Parameter;

/**
 * 报表调度器所使用的参数
 * 
 * <ul>
 * <li>id:参数编号</li>
 * <li>value:参数值</li>
 * <li>parameter:Parameter对象</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_job_report_parameter")
public class JobReportParameter extends BaseEntity<Long> {

    private static final long serialVersionUID = 5822868212042814116L;
    
    @Column(name = "parameter_value")
    private String parameterValue;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "report_parameter_id")
    private Parameter parameter;

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
