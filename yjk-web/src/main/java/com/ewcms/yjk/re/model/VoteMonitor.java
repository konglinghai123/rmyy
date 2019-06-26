package com.ewcms.yjk.re.model;

import java.io.Serializable;

import com.ewcms.security.user.entity.User;

public class VoteMonitor implements Serializable {

	private static final long serialVersionUID = -3461725134953255962L;

	public User user;
	private Boolean sined;

	public VoteMonitor() {
	}
	
	public VoteMonitor(User user, Boolean sined) {
		super();
		this.user = user;
		this.sined = sined;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getSined() {
		return sined;
	}

	public void setSined(Boolean sined) {
		this.sined = sined;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		VoteMonitor other = (VoteMonitor) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
