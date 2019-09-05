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
																	
	@Query("select count(*) from DrugForm c where c.userId=?1 and c.systemParameterId=?2 and c.auditStatus !='nodeclare'")
	Long findDeclareTotalByUserId(Long userId,Long systemParameterId);
	
	List<DrugForm> findByUserIdAndDeclaredFalseAndSystemParameterIdAndReviewedFalse(Long userId, Long systemParameterId);
	
	List<DrugForm> findByUserIdAndAuditStatusAndSystemParameterId(Long userId,AuditStatusEnum auditStatus, Long systemParameterId);
	List<DrugForm> findByAuditStatusAndSystemParameterIdAndDeclareCategoryAndReviewedFalseOrderByIdAsc(AuditStatusEnum auditStatus, Long systemParameterId, String declareCategory);	
	List<DrugForm> findByAuditStatusAndSystemParameterIdOrderByIdAsc(AuditStatusEnum auditStatus, Long systemParameterId);
	Long countByUserIdInAndAuditStatusAndSystemParameterId(Set<Long> userIds, AuditStatusEnum auditStatus, Long systemParameterId);
	@Query(value = "select string_agg(distinct t2.name,'/') from sec_organization t2 left join sec_user_organization_job t3 on t2.id=t3.organization_id  where " + 
			"t3.user_id in (" + 
			"select df.user_id from sb_drug_form df left join zd_common_name_contents cnc on df.commonnamecontents_id=cnc.id left join zd_common_name cn on cnc.common_name_id=cn.id where cn.common_name=?1 and cnc.administration_id=?2" + 
			")", nativeQuery = true)
	String findOrganizationNames(String commonName, int administrationId);
}
