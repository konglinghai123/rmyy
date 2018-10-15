package com.ewcms.personal.calendar.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 日历
 * 
 * <ul>
 * <li>userId:所属人</li>
 * <li>title:标题</li>
 * <li>details:详细信息</li>
 * <li>startDate:开始日期</li>
 * <li>length:持续时间</li>
 * <li>startTime:开始时间</li>
 * <li>endTime:结束时间</li>
 * <li>backgroundColor:背景颜色</li>
 * <li>textColor:文字颜色</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "per_calendar")
@SequenceGenerator(name="seq", sequenceName="seq_per_calendar_id", allocationSize = 1)
public class Calendar extends BaseSequenceEntity<Long> {
	private static final long serialVersionUID = 3157628270965109201L;
	
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "title")
	@Length(min = 1, max = 200, message = "{length.not.valid}")
	private String title;
	@Column(name = "details")
	@Length(min = 0, max = 500, message = "{length.not.valid}")
	private String details;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "length")
	private Integer length;
	@DateTimeFormat(pattern = "HH:mm:ss")
	@Temporal(TemporalType.TIME)
	@Column(name = "start_time")
	private Date startTime;
	@DateTimeFormat(pattern = "HH:mm:ss")
	@Temporal(TemporalType.TIME)
	@Column(name = "end_time")
	private Date endTime;
	@Column(name = "background_color")
	private String backgroundColor;
	@Column(name = "text_color")
	private String textColor;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate(){
		return DateUtils.addDays(startDate, length - 1);
	}
	
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
}
