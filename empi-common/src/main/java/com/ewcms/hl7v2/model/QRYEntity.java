package com.ewcms.hl7v2.model;

/**
 * QRY消息实体
 *
 * <ul>
 * <li>practiceNo:患者卡号</li>
 * </ul>
 *
 * @author wu_zhijun
 */
public class QRYEntity extends AbstractEntity{

	private String practiceNo;

	public String getPracticeNo() {
		return practiceNo;
	}

	public void setPracticeNo(String practiceNo) {
		this.practiceNo = practiceNo;
	}
}
