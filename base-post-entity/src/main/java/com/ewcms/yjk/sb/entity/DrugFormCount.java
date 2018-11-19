package com.ewcms.yjk.sb.entity;

import java.io.Serializable;

public class DrugFormCount implements Serializable {

	private static final long serialVersionUID = 3600172320779562423L;

	private Long organizationId;
	private String organizationName;
	private Long noDeclareNumber = 0L;
	private Long initNumber = 0L;
	private Long passedNumber = 0L;
	private Long unPassedNumber = 0L;

	public DrugFormCount() {
	}
	
	public DrugFormCount(Long organizationId, String organizationName) {
		super();
		this.organizationId = organizationId;
		this.organizationName = organizationName;
	}
	
	public DrugFormCount(Long organizationId, String organizationName, Long noDeclareNumber, Long initNumber, Long passedNumber, Long unPassedNumber) {
		super();
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.noDeclareNumber = noDeclareNumber;
		this.initNumber = initNumber;
		this.passedNumber = passedNumber;
		this.unPassedNumber = unPassedNumber;
	}



	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getNoDeclareNumber() {
		return noDeclareNumber;
	}

	public void setNoDeclareNumber(Long noDeclareNumber) {
		this.noDeclareNumber = noDeclareNumber;
	}

	public Long getInitNumber() {
		return initNumber;
	}

	public void setInitNumber(Long initNumber) {
		this.initNumber = initNumber;
	}

	public Long getPassedNumber() {
		return passedNumber;
	}

	public void setPassedNumber(Long passedNumber) {
		this.passedNumber = passedNumber;
	}

	public Long getUnPassedNumber() {
		return unPassedNumber;
	}

	public void setUnPassedNumber(Long unPassedNumber) {
		this.unPassedNumber = unPassedNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((organizationId == null) ? 0 : organizationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DrugFormCount other = (DrugFormCount) obj;
		if (organizationId == null) {
			if (other.organizationId != null)
				return false;
		} else if (!organizationId.equals(other.organizationId))
			return false;
		return true;
	}

}
