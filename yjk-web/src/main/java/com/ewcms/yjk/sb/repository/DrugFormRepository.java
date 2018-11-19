package com.ewcms.yjk.sb.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;

/**
 *@author zhoudongchu
 */
public interface DrugFormRepository extends BaseRepository<DrugForm, Long> {
	@Query("select count(*) from DrugForm c where c.userId=?1 and c.declareDate between ?2 and ?3")
	Long findDeclareTotalByUserId(Long userId,Date startDate,Date endDate);
	
	List<DrugForm> findByUserIdAndDeclaredFalseAndFillInDateBetween(Long userId, Date beginDate, Date endDate);
	
	List<DrugForm> findByUserIdAndAuditStatusAndFillInDateBetween(Long userId,AuditStatusEnum auditStatus, Date beginDate, Date endDate);
	
	Long countByAuditStatusAndFillInDateBetween(AuditStatusEnum auditStatus, Date beginDate, Date endDate);
	
	Long countByUserIdInAndAuditStatusAndFillInDateBetween(Set<Long> userIds, AuditStatusEnum auditStatus, Date beginDate, Date endDate);
	
}
