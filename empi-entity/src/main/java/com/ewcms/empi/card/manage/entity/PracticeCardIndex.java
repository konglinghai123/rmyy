package com.ewcms.empi.card.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ewcms.common.entity.BaseEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 诊疗卡主索引
 * 
 * <ul>
 * <li>patientId：同一患者索引号</li>
 * <li>patientBaseInfoId：患者信息ID</li>
 * <li>practiceCardId：就诊卡ID</li>
 * <li>deleted:是否删除(逻辑删除)</li>
 * </ul>
 *@author zhoudongchu
 */
@Entity
@Table(name = "card_practice_card_index")
public class PracticeCardIndex extends BaseEntity<Long> implements LogicDeleteable{
	private static final long serialVersionUID = 8834095514686869169L;
	@Column(name = "patient_id")
	private String patientId;
	@Column(name = "patient_base_info_id")
	private Long patientBaseInfoId;
	@Column(name = "practice_card_id")
	private Long practiceCardId;
	@Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
	
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public Long getPatientBaseInfoId() {
		return patientBaseInfoId;
	}

	public void setPatientBaseInfoId(Long patientBaseInfoId) {
		this.patientBaseInfoId = patientBaseInfoId;
	}

	public Long getPracticeCardId() {
		return practiceCardId;
	}

	public void setPracticeCardId(Long practiceCardId) {
		this.practiceCardId = practiceCardId;
	}

	@Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }
}
