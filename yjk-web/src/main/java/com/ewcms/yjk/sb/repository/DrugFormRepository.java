package com.ewcms.yjk.sb.repository;

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
																	
	@Query("select count(*) from DrugForm c where c.userId=?1 and c.systemParameterId=?2")
	Long findDeclareTotalByUserId(Long userId,Long systemParameterId);
	
	List<DrugForm> findByUserIdAndDeclaredFalseAndSystemParameterId(Long userId, Long systemParameterId);
	
	List<DrugForm> findByUserIdAndAuditStatusAndSystemParameterId(Long userId,AuditStatusEnum auditStatus, Long systemParameterId);
	
	Long countByAuditStatusAndSystemParameterId(AuditStatusEnum auditStatus, Long systemParameterId);
	
	Long countByUserIdInAndAuditStatusAndSystemParameterId(Set<Long> userIds, AuditStatusEnum auditStatus, Long systemParameterId);
	
}
