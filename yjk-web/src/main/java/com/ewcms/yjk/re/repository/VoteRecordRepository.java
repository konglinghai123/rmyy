package com.ewcms.yjk.re.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.model.VoteMonitor;

public interface VoteRecordRepository extends BaseRepository<VoteRecord, Long> {
	
	Long countByUserIdAndReviewProcessId(Long userId, Long reviewProcessId);
	
	Long countByUserIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewProcessId);
	
	VoteRecord findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(Long userId, Long reviewMainId, Long drugFormId, Long reviewProcessId);
	
	@Query("select distinct v.userId from VoteRecord v where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.submitted=true")
	List<Long> findSubmittedUserIds(Long reviewMainId, Long reviewProcessId);
	
	//查询中止投票
	List<VoteRecord> findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIdIsNullAndCommonNameContentsIdIsNull(Long userId, Long reviewMainId, Long reviewProcessId);
	
	//查询当前用户未签字数量
	Long countByUserIdAndReviewMainIdAndReviewProcessIdAndSignedFalse(Long userId, Long reviewMainId, Long reviewProcessId);
	
	@Modifying
	@Query("update VoteRecord v set v.signed=true where v.userId=?1 and v.reviewMainId=?2 and v.reviewProcessId=?3")
	void updateSigned(Long userId, Long reviewMainId, Long reviewProcessId);

	//查询当前用户投票结果
	List<VoteRecord> findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIsNotNull(Long userId, Long reviewMainId, Long reviewProcessId);
	
	@Query("select distinct new com.ewcms.yjk.re.model.VoteMonitor("
			+ "u, v.signed"
			+ ") "
			+ "from User u, VoteRecord v "
			+ "where u.id=v.userId and u.id in (?1) and v.reviewMainId=?2 and v.reviewProcessId=?3 "
			+ "order by u.id")
	List<VoteMonitor> findVoteResultMonitor(List<Long> userIds, Long reviewMainId, Long reviewProcessId);

	@Query("select distinct new com.ewcms.yjk.re.model.VoteMonitor("
			+ "u, v.voteTypeEnum"
			+ ") "
			+ "from User u, VoteRecord v "
			+ "where u.id=v.userId and v.reviewMainId=?1 and v.reviewProcessId=?2 and v.drugForm.id=?3 and v.submitted=true "
			+ "order by u.id")
	List<VoteMonitor> findVoteUserMonitorDrugForm(Long reviewMainId, Long reviewProcessId, Long drugFormId);
	
	@Query("select distinct new com.ewcms.yjk.re.model.VoteMonitor("
			+ "u, v.voteTypeEnum"
			+ ") "
			+ "from User u, VoteRecord v "
			+ "where u.id=v.userId and v.reviewMainId=?1 and v.reviewProcessId=?2 and v.commonNameContents.id=?3 and v.submitted=true "
			+ "order by u.id")
	List<VoteMonitor> findVoteUserMonitorCommonNameContents(Long reviewMainId, Long reviewProcessId, Long commonNameContentsId);
	
	@Modifying
	@Query("delete from VoteRecord v where v.userId=?1 and v.reviewMainId=?2 and v.reviewProcessId=?3 and (v.submitted=false or v.signed=false)")
	void deleteGvieUpVoteRecord(Long userId, Long reviewMainId, Long reviewProcessId);
	
	Long countByUserIdAndReviewMainIdAndReviewProcessIdAndSubmittedTrueAndSignedTrue(Long userId, Long reviewMainId, Long reviewProcessId);
	
	@Query("select count(distinct v.userId) from VoteRecord v where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.submitted=true and v.signed=true")
	Long findSubmittedAndSinged(Long reviewMainId, Long reviewProcessId);

	@Query(value = "select string_agg(distinct t2.name,'/') from sec_organization t2 left join sec_user_organization_job t3 on t2.id=t3.organization_id  where " + 
			"t3.user_id in (" + 
			"select df.user_id from sb_drug_form df left join zd_common_name_contents cnc on df.commonnamecontents_id=cnc.id left join zd_common_name cn on cnc.common_name_id=cn.id where cn.common_name=?1 and cnc.administration_id=?2" + 
			")", nativeQuery = true)
	String findOrganizationNames(String commonName, int administrationId);
}
