package com.ewcms.yjk.re.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.sb.entity.DrugForm;

public interface VoteResultRepository extends BaseRepository<VoteResult, Long> {
	Long countByReviewProcessId(Long reviewProcessId);
	List<VoteResult> findByReviewProcessId(Long reviewProcessId);
	VoteResult findByDrugFormIdAndReviewProcessId(Long drugFormId, Long reviewProcessId);
	
	@Query("select distinct new com.ewcms.yjk.re.model.VoteMonitor("
			+ "u, v.signed"
			+ ") "
			+ "from User u, VoteRecord v "
			+ "where u.id=v.userId and u.id in (?1) "
			+ "order by u.id")
	List<VoteMonitor> findVoteMonitor(List<Long> userIds);
	
	@Query("select c.drugForm from VoteResult c where c.reviewMainId=?1 and c.drugForm.declareCategory=?2 and c.selected=true and c.affirmVoteResulted=true")
	List<DrugForm> findSelectedDrugForm(Long reviewMainId, String declareCategory);
	
	@Query("from VoteResult v where v.reviewMainId=?1 and v.reviewProcessId=?2 order by v.passSum desc, v.selected desc, v.adjusted asc, v.id")
	List<VoteResult> findCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId);
	
	@Modifying
	@Query("update VoteResult v set v.adjusted=true, v.selected=true where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3) and v.selected=false")
	void adjust(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);
	
	@Modifying
	@Query("update VoteResult v set v.adjusted=true, v.selected=false where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3) and v.selected=true")
	void cancel(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);

}
