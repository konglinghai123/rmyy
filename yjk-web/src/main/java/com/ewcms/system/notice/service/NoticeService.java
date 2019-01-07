package com.ewcms.system.notice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchRequest;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.system.notice.entity.Notice;
import com.ewcms.system.notice.repository.NoticeRepository;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.google.common.collect.Lists;

@Service
public class NoticeService extends BaseSequenceMovableService<Notice, Long>{

	private NoticeRepository getNoticeRepository() {
		return (NoticeRepository) baseRepository;
	}
	
	@Autowired
	private SystemParameterService systemParameterService;
	
	public NoticeService() {
		super(1);
	}
	
	@Override
	public Notice save(Notice m) {
		m.setCreateDate(new Date());
		return super.save(m);
	}
	
	@Override
	public Notice update(Notice m) {
		Notice dbNotice = findOne(m.getId());
		if (dbNotice != null) {
			m.setCreateDate(dbNotice.getCreateDate());
			m.setWeight(dbNotice.getWeight());
		}
		m.setUpdateDate(new Date());
		return super.update(m);
	}
	
	public void changeStatus(List<Long> noticeIds, Boolean newStatus) {
		if (EmptyUtil.isCollectionEmpty(noticeIds)) return;
		getNoticeRepository().changeStatus(noticeIds, newStatus);
	}

	public Notice findByTitle(String title) {
		return getNoticeRepository().findByTitle(title);
	}
	
	public List<Notice> findByIndexShow(){
        SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
        String systemParameterNotice = (systemParameter == null) ? "申报新药还未开启，请等待!!!" : "请大家在 " + systemParameter.getApplyStartDate() + " 至 " + systemParameter.getApplyEndDate() + " 之间申报新药！！！";

        Notice notice = new Notice();
        notice.setHead(true);
        notice.setRelease(true);
        notice.setTitle(systemParameterNotice);
        
        List<Notice> list = Lists.newArrayList();
        list.add(notice);
        
		Searchable searchable = SearchRequest.newSearchable();

		searchable.addSearchFilter("release", SearchOperator.EQ, Boolean.TRUE);
		searchable.addSort(Direction.ASC, "weight");
		
		List<Notice> notices = findAllWithSort(searchable);

		if (EmptyUtil.isCollectionNotEmpty(notices)) list.addAll(notices);
		
		return list;
	}
}
