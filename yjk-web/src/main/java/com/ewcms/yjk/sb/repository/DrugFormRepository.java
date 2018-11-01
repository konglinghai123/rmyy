package com.ewcms.yjk.sb.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;

/**
 *@author zhoudongchu
 */
public interface DrugFormRepository extends BaseRepository<DrugForm, Long> {
	List<DrugForm> findByUserIdAndDeclaredFalse(Long userId);
	List<DrugForm> findByUserIdAndAuditStatus(Long userId,AuditStatusEnum auditStatus);
}
