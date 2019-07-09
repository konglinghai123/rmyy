package com.ewcms.yjk.re.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;

public interface VoteResultRepository extends BaseRepository<VoteResult, Long> {
	Long countByReviewProcessId(Long reviewProcessId);
	List<VoteResult> findByReviewProcessId(Long reviewProcessId);
	VoteResult findByDrugFormIdAndReviewProcessId(Long drugFormId, Long reviewProcessId);
	VoteResult findByCommonNameContentsIdAndReviewProcessId(Long commonNameContentsId, Long reviewProcessId);
	
	@Query("select c.drugForm "
			+ "from VoteResult c "
			+ "where c.reviewMainId=?1 and c.drugForm.declareCategory=?2 and c.selected=true and c.affirmVoteResulted=true")
	List<DrugForm> findSelectedDrugForm(Long reviewMainId, String declareCategory);
	
	@Query("from VoteResult v "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 "
			+ "order by v.passSum desc, v.selected desc, v.adjusted asc, v.drugForm.commonNameContents.common.drugCategory asc, v.drugForm.commonNameContents.common.commonName asc, drugForm.commonNameContents.pill asc, v.id desc")
	List<VoteResult> findCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId);
	
	@Query("from VoteResult v "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.selected = true and v.affirmVoteResulted=true "
			+ "order by v.passSum desc, v.selected desc, v.adjusted asc, v.id")
	List<VoteResult> findLastReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId);
	
	@Modifying
	@Query("update VoteResult v "
			+ "set v.adjusted=true, v.selected=true "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3) and v.selected=false and v.affirmVoteResulted=false")
	void adjust(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);
	
	@Modifying
	@Query("update VoteResult v "
			+ "set v.adjusted=true, v.selected=false "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3) and v.selected=true and v.affirmVoteResulted=false")
	void cancel(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);
	
	@Modifying
	@Query("update VoteResult v "
			+ "set v.affirmVoteResulted=true "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3)")
	void affirm(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);
	
	Long countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedFalse(Long reviewMainId, Long reviewProcessId);
	
	Long countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedTrueAndSelectedTrue(Long reviewMainId, Long reviewProcessId);
	
	@Query("from VoteResult v "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.drugForm.commonNameContents.common.drugCategory=?3 "
			+ "order by v.passSum desc, v.opposeSum asc, v.abstainSum asc")
	List<VoteResult> findCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId, DrugCategoryEnum drugCategoryEnum);
	
	Long countByReviewMainIdAndReviewProcessIdAndDrugFormCommonNameContentsCommonDrugCategoryAndSelectedTrueAndAffirmVoteResultedTrue(Long reviewMainId, Long reviewProcessId, DrugCategoryEnum drugCategoryEnum);
}
