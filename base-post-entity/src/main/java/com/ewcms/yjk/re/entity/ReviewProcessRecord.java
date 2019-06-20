package com.ewcms.yjk.re.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 评审流程记录
 * 
 * <ul>
 * <li>reviewProcessId:评审流程编号</li>
 * <li>userId:用户编号</li>
 * <li>userName:用户名</li>
 * <li>operateDate:操作时间</li>
 * <li>remark:说明</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "re_review_process_record")
@SequenceGenerator(name = "seq", sequenceName = "seq_re_review_process_record_id", allocationSize = 1)
public class ReviewProcessRecord extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 2080334658306230811L;

	@Column(name = "review_process_id", nullable = false)
	private Long reviewProcessId;
	@Column(name = "user_id")
	private Long userId;
	@Formula(value = "(select s_o.username || case when s_o.realname is not null then ('(' || s_o.realname || ')') else '' end from sec_user s_o where s_o.id = user_id)")
	private String userName;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "operate_date", columnDefinition = "Timestamp default CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date operateDate;
	@Column(name = "remark", columnDefinition = "text")
	private String remark;

	public ReviewProcessRecord() {
	}
	
	public ReviewProcessRecord(Long reviewProcessId, String remark) {
		this.reviewProcessId = reviewProcessId;
		this.remark = remark;
	}
	
	public ReviewProcessRecord(Long reviewProcessId, Long userId, String remark) {
		this(reviewProcessId, remark);
		this.userId = userId;
	}
	
	public Long getReviewProcessId() {
		return reviewProcessId;
	}

	public void setReviewProcessId(Long reviewProcessId) {
		this.reviewProcessId = reviewProcessId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getOperateDate() {
		return operateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
