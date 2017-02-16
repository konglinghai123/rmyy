package com.ewcms.system.scheduling.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseEntity;

/**
 * 调度器任务
 * 
 * <ul>
 * <li>version:版本号</li>
 * <li>userName:用户名</li>
 * <li>label:名称</li>
 * <li>outputLoacal:输出格式本地化</li>
 * <li>description:说明</li>
 * <li>trigger:AlqcJobTrigger对象</li>
 * <li>state:状态(不持久化)</li>
 * <li>previousFireTime:上一次执行时间(不持久化)</li>
 * <li>nextFireTime:下一次执行时间(不持久化)</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_job_info")
@Inheritance(strategy = InheritanceType.JOINED)
public class JobInfo extends BaseEntity<Long> {

    private static final long serialVersionUID = 5651638241918468407L;
    
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "username", length = 100, nullable = false)
    private String userName;
    @NotNull(message = "{not.null}")
    @Column(name = "label", length = 100, nullable = false)
    private String label;
    @Column(name = "outputlocale", length = 20)
    private String outputLocale = "zh_CN";
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "trigger_id")
    private JobTrigger trigger = new JobTrigger();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "jobclass_id")
    private JobClass jobClass = new JobClass();
    
    @Transient
    private String jobClassName;
    @Transient
    private String state;
    @Transient
    private Date startTime;
    @Transient
    private Date endTime;
    @Transient
    private Date previousFireTime;
    @Transient
    private Date nextFireTime;
    @Transient
    private String remark;

    public JobInfo() {
        version = -1;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOutputLocale() {
        return outputLocale;
    }

    public void setOutputLocale(String outputLocale) {
        this.outputLocale = outputLocale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JSONField(serialize = false)
    public JobTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(JobTrigger trigger) {
        this.trigger = trigger;
    }

    @JSONField(serialize = false)
    public JobClass getJobClass(){
        return jobClass;
    }

    public void setJobClass(JobClass jobClass){
        this.jobClass = jobClass;
    }
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
}
