package com.ewcms.yjk.re.model;

import java.io.Serializable;

import com.ewcms.yjk.sb.entity.DrugForm;

public class UserVote implements Serializable {

	private static final long serialVersionUID = -4911046504874420083L;

	private DrugForm drugForm;
	private Long userVote1;
	private Long userVote2;
	private Long userVote3;
	private Long userVote4;

	public DrugForm getDrugForm() {
		return drugForm;
	}

	public void setDrugForm(DrugForm drugForm) {
		this.drugForm = drugForm;
	}

	public Long getUserVote1() {
		return userVote1;
	}

	public void setUserVote1(Long userVote1) {
		this.userVote1 = userVote1;
	}

	public Long getUserVote2() {
		return userVote2;
	}

	public void setUserVote2(Long userVote2) {
		this.userVote2 = userVote2;
	}

	public Long getUserVote3() {
		return userVote3;
	}

	public void setUserVote3(Long userVote3) {
		this.userVote3 = userVote3;
	}

	public Long getUserVote4() {
		return userVote4;
	}

	public void setUserVote4(Long userVote4) {
		this.userVote4 = userVote4;
	}

}
