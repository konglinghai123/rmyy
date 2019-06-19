package com.ewcms.yjk.re.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 评审专家投票流水过程
 * <ul>
 * <li>userId:评审专家Id</li>
 * <li>reviewMainId:评审Id</li>
 *  <li>submittDate:投票提交时间</li>
 *  <li>submitted:提交标志</li>
 *  <li>reviewProcessId:投票流程Id</li>
 * </ul>
 * 
 * @author zhoudongchu
 *
 */
@Entity
@Table(name = "re_vote_serial")
@SequenceGenerator(name="seq", sequenceName="seq_re_vote_serial_id", allocationSize = 1)
public class VoteSerialProcess extends BaseSequenceEntity<Long> {
	private static final long serialVersionUID = -3776279747278677042L;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "review_main_id", nullable = false)
	private Long reviewMainId;
	
	@Column(name = "review_process_id", nullable = false)
	private Long reviewProcessId;	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "vote_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittDate;
	
	@Column(name = "is_submitted")
	private Boolean submitted = Boolean.FALSE;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getReviewMainId() {
		return reviewMainId;
	}

	public void setReviewMainId(Long reviewMainId) {
		this.reviewMainId = reviewMainId;
	}

	public Long getReviewProcessId() {
		return reviewProcessId;
	}

	public void setReviewProcessId(Long reviewProcessId) {
		this.reviewProcessId = reviewProcessId;
	}

	public Date getSubmittDate() {
		return submittDate;
	}

	public void setSubmittDate(Date submittDate) {
		this.submittDate = submittDate;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}
}
