package com.ewcms.system.scheduling.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 触发器
 * 
 * <ul>
 * <li>version:版本号</li>
 * <li>timeZone:时区</li>
 * <li>startType:开始类型</li>
 * <li>startDate:开始时间</li>
 * <li>endDate:结束时间</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_job_trigger")
@SequenceGenerator(name = "seq", sequenceName = "seq_sys_job_trigger_id", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public class JobTrigger extends BaseSequenceEntity<Long> {

    private static final long serialVersionUID = -6866914186969004480L;
    public static final Integer START_TYPE_NOW = 1;//立刻执行
    public static final Integer START_TYPE_SCHEDULE = 2;//调度策略
    
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "timezone", length = 40)
    private String timeZone = "Asia/Shanghai";
    @Column(name = "starttype", nullable = false)
    private Integer startType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "startdate")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "enddate")
    private Date endDate;

    public JobTrigger() {
        version = -1;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getStartType() {
        return startType;
    }

    public void setStartType(Integer startType) {
        this.startType = startType;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
