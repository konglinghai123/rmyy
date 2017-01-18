package com.ewcms.card.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.card.manage.entity.PracticeCardJournal;
import com.ewcms.card.manage.entity.PracticeCardJournalOperate;
import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;

/**
 *@author zhoudongchu
 */
@Service
public class PracticeCardJournalService extends BaseService<PracticeCardJournal, Long> {


	@Override
	public PracticeCardJournal update(PracticeCardJournal m) {
		throw new BaseException("discards method");
	}

	@Override
	public void delete(Long id) {
		throw new BaseException("discards method");
	}

	@Override
	public void delete(PracticeCardJournal m) {
		throw new BaseException("discards method");
	}

	@Override
	public void delete(List<Long> ids) {
		throw new BaseException("discards method");
	}


}
