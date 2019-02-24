package com.ewcms.system.notice.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchRequest;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.system.notice.entity.Notice;
import com.ewcms.system.notice.repository.NoticeRepository;
import com.google.common.collect.Lists;

@Service
public class NoticeService extends BaseSequenceMovableService<Notice, Long>{

	private NoticeRepository getNoticeRepository() {
		return (NoticeRepository) baseRepository;
	}
	
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
        List<Notice> list = Lists.newArrayList();
        
		Searchable searchable = SearchRequest.newSearchable();

		searchable.addSearchFilter("release", SearchOperator.EQ, Boolean.TRUE);
		searchable.addSort(Direction.ASC, "weight");
		
		List<Notice> notices = findAllWithSort(searchable);

		if (EmptyUtil.isCollectionNotEmpty(notices)) list.addAll(notices);
		
		return list;
	}
}
