package com.ewcms.empi.card.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.empi.card.manage.entity.PracticeCardHistory;

/**
 *@author zhoudongchu
 */
@Service
public class PracticeCardHistoryService extends BaseService<PracticeCardHistory, Long> {

	@Override
	public PracticeCardHistory update(PracticeCardHistory m) {
		throw new BaseException("discards method");
	}

	@Override
	public void delete(Long id) {
		throw new BaseException("discards method");
	}

	@Override
	public void delete(PracticeCardHistory m) {
		throw new BaseException("discards method");
	}

	@Override
	public void delete(List<Long> ids) {
		throw new BaseException("discards method");
	}

}
