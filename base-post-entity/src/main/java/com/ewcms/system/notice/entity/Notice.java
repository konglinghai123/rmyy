package com.ewcms.system.notice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.Movable;

/**
 * 公告栏
 * 
 * <ul>
 * <li>title:标题</li>
 * <li>titleStyle:标题样式</li>
 * <li>content:内容</li>
 * <li>externalLinks:外部链接地址</li>
 * <li>createDate:创建时间</li>
 * <li>updateDate:修改时间</li>
 * <li>head:是否标题</li>
 * <li>release:是否发布</li>
 * <li>weight:顺序号</li>
 * <li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "sys_notice")
@SequenceGenerator(name="seq", sequenceName="seq_sys_notice_id", allocationSize = 1)
public class Notice extends BaseSequenceEntity<Long> implements Movable{

	private static final long serialVersionUID = -845222640210042956L;

	@Column(name = "title", nullable = false, unique = true)
	private String title;
	@Column(name = "title_style")
	private String titleStyle;
	@Column(name = "content", columnDefinition = "text")
	private String content;
	@Column(name = "external_links", columnDefinition = "text")
	private String externalLinks;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	@Column(name = "is_head")
	private Boolean head = Boolean.TRUE;
	@Column(name = "is_release")
	private Boolean release = Boolean.TRUE;
	@Column(name = "weight")
	private Integer weight;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(String titleStyle) {
		this.titleStyle = titleStyle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getExternalLinks() {
		return externalLinks;
	}

	public void setExternalLinks(String externalLinks) {
		this.externalLinks = externalLinks;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getHead() {
		return head;
	}

	public void setHead(Boolean head) {
		this.head = head;
	}

	public Boolean getRelease() {
		return release;
	}

	public void setRelease(Boolean release) {
		this.release = release;
	}

	@Override
	public Integer getWeight() {
		return weight;
	}

	@Override
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}