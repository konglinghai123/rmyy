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

/**
 * 评审专家投票结果记录
 * <ul>
 *  <li>drugForm:药品申报对象</li>
 *  <li>voteTypeEnum:投票种类</li>
 *  <li>voteDate:投票时间</li>
 *  <li>voteSerial:评审专家投票流水过程对象</li>
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
	
	@ManyToOne()
    @Fetch(FetchMode.SELECT)
	private DrugForm drugForm;
	
	@Enumerated(EnumType.STRING)
	private VoteTypeEnum voteTypeEnum = VoteTypeEnum.abstain;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "vote_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date voteDate = new Date();
	
	@ManyToOne()
    @Fetch(FetchMode.SELECT)
	private VoteSerialProcess voteSerial;


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

	public VoteSerialProcess getVoteSerial() {
		return voteSerial;
	}

	public void setVoteSerial(VoteSerialProcess voteSerial) {
		this.voteSerial = voteSerial;
	}

	public Date getVoteDate() {
		return voteDate;
	}

	public void setVoteDate(Date voteDate) {
		this.voteDate = voteDate;
	}
	
}
