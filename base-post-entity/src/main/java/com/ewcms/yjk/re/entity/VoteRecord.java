package com.ewcms.yjk.re.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;

/**
 * 评审专家投票结果记录
 * <ul>
 * <li>userId:评审专家Id</li>
 * <li>reviewMainId:评审Id</li>
 *  <li>drugForm:药品申报对象</li>
 *  <li>commonNameContents:大总目录对象</li>
 *  <li>voteTypeEnum:投票种类</li>
 *  <li>submittDate:投票时间</li>
 *  <li>submitted:提交标志</li>
 *  <li>reviewProcessId:投票流程Id</li>
 * </ul>
 * 
 * @author zhoudongchu
 *
 */
@Entity
@Table(name = "re_vote_record")
@SequenceGenerator(name="seq", sequenceName="seq_re_vote_record_id", allocationSize = 1)
public class VoteRecord extends BaseSequenceEntity<Long> {
	private static final long serialVersionUID = -5879863363971625009L;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "review_main_id", nullable = false)
	private Long reviewMainId;
	
	@ManyToOne()
    @Fetch(FetchMode.SELECT)
	private DrugForm drugForm;
	
	@ManyToOne()
    @Fetch(FetchMode.SELECT)	
	private CommonNameContents commonNameContents;
	
	@Enumerated(EnumType.STRING)
	private VoteTypeEnum voteTypeEnum = VoteTypeEnum.abstain;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "submitt_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittDate;
	
	@Column(name = "is_submitted")
	private Boolean submitted = Boolean.FALSE;
	
	@Column(name = "review_process_id", nullable = false)
	private Long reviewProcessId;
	
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

	public DrugForm getDrugForm() {
		return drugForm;
	}

	public void setDrugForm(DrugForm drugForm) {
		this.drugForm = drugForm;
	}

	public VoteTypeEnum getVoteTypeEnum() {
		return voteTypeEnum;
	}

	public void setVoteTypeEnum(VoteTypeEnum voteTypeEnum) {
		this.voteTypeEnum = voteTypeEnum;
	}

	public String getVoteTypeInfo() {
		return voteTypeEnum == null ? "" : voteTypeEnum.getInfo();
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

	public Long getReviewProcessId() {
		return reviewProcessId;
	}

	public void setReviewProcessId(Long reviewProcessId) {
		this.reviewProcessId = reviewProcessId;
	}

	public CommonNameContents getCommonNameContents() {
		return commonNameContents;
	}

	public void setCommonNameContents(CommonNameContents commonNameContents) {
		this.commonNameContents = commonNameContents;
	}
}
