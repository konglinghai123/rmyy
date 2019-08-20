package com.ewcms.yjk.re.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;

/**
 * 评审专家投票结果记录
 * 
 * <ul>
 * <li>reviewMainId:评审Id</li>
 * <li>reviewProcessId:投票流程Id</li>
 * <li>drugForm:药品申报对象</li>
 * <li>commonNameContents:大总目录对象</li>
 * <li>passSum:通过的数量</li>
 * <li>opposeSum:反对的数量</li>
 * <li>abstainSum:弃权的数量
 * <li>selected:是否拟入围</li>
 * <li>affirmVoteResulted:确认投票结果</li>
 * <li>adjusted:调整操作</li>
 * <li>chosen:是否入围</li>
 * <li>ensureOrgan:是否确保科室</li>
 * </ul>
 * 
 * @author zhoudongchu
 *
 */
@Entity
@Table(name = "re_vote_result")
@SequenceGenerator(name="seq", sequenceName="seq_re_vote_result_id", allocationSize = 1)
public class VoteResult extends BaseSequenceEntity<Long> {
	private static final long serialVersionUID = 8733576873788743591L;
	@Column(name = "review_main_id", nullable = false)
	private Long reviewMainId;
	
	@Column(name = "review_process_id", nullable = false)
	private Long reviewProcessId;
	
	@ManyToOne()
    @Fetch(FetchMode.SELECT)
	private DrugForm drugForm;
	
	@ManyToOne()
    @Fetch(FetchMode.SELECT)	
	private CommonNameContents commonNameContents;
	
	@Column(name = "is_selected")
	private Boolean selected = Boolean.FALSE;
	
	@Column(name = "is_affirm_resulted")
	private Boolean affirmVoteResulted = Boolean.FALSE;
	
	@Column(name = "adjusted")
	@Enumerated(EnumType.STRING)
	private AdjustedEnum adjusted;	
	
	@Column(name = "pass_sum")
	private Integer passSum;
	
	@Column(name = "oppose_sum")
	private Integer opposeSum;
	
	@Column(name = "abstain_sum")
	private Integer abstainSum;
	
	@Column(name = "is_chosen")
	private Boolean chosen = Boolean.FALSE;
	
	@Column(name = "is_ensure_organ")
	private Boolean ensureOrgan = Boolean.FALSE;

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

	public DrugForm getDrugForm() {
		return drugForm;
	}

	public void setDrugForm(DrugForm drugForm) {
		this.drugForm = drugForm;
	}

	public CommonNameContents getCommonNameContents() {
		return commonNameContents;
	}

	public void setCommonNameContents(CommonNameContents commonNameContents) {
		this.commonNameContents = commonNameContents;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Integer getPassSum() {
		return passSum;
	}

	public void setPassSum(Integer passSum) {
		this.passSum = passSum;
	}

	public Integer getOpposeSum() {
		return opposeSum;
	}

	public void setOpposeSum(Integer opposeSum) {
		this.opposeSum = opposeSum;
	}

	public Integer getAbstainSum() {
		return abstainSum;
	}

	public void setAbstainSum(Integer abstainSum) {
		this.abstainSum = abstainSum;
	}

	public Boolean getAffirmVoteResulted() {
		return affirmVoteResulted;
	}

	public void setAffirmVoteResulted(Boolean affirmVoteResulted) {
		this.affirmVoteResulted = affirmVoteResulted;
	}

	public AdjustedEnum getAdjusted() {
		return adjusted;
	}

	public void setAdjusted(AdjustedEnum adjusted) {
		this.adjusted = adjusted;
	}

	public String getAdjustedInfo() {
		return adjusted == null ? "" : adjusted.getInfo();
	}

	public Boolean getChosen() {
		return chosen;
	}

	public void setChosen(Boolean chosen) {
		this.chosen = chosen;
	}
	
	public Boolean getEnsureOrgan() {
		return ensureOrgan;
	}

	public void setEnsureOrgan(Boolean ensureOrgan) {
		this.ensureOrgan = ensureOrgan;
	}

	public String getChemicalSubCategory() {
		return (drugForm != null && drugForm.getCommonNameContents() != null && drugForm.getCommonNameContents().getCommon() != null) ? drugForm.getCommonNameContents().getCommon().getChemicalSubCategory() : "";
	}
}
