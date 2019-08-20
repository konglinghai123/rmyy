package com.ewcms.yjk.re.repository;

import java.util.List;
import java.util.Set;

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
			+ "order by v.drugForm.commonNameContents.common.drugCategory asc,v.selected desc, v.passSum desc, v.adjusted asc, v.drugForm.commonNameContents.common.commonName asc, drugForm.commonNameContents.pill asc, v.id desc")
	List<VoteResult> findCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId);
	
	@Query("from VoteResult v "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.selected = true and v.affirmVoteResulted=true "
			+ "order by v.passSum desc, v.selected desc, v.adjusted asc, v.id")
	List<VoteResult> findLastReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId);
	
	@Modifying
	@Query("update VoteResult v "
			+ "set v.adjusted='transferIn' "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3) and v.affirmVoteResulted=false and v.selected=false")
	void transferIn(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);
	
	@Modifying
	@Query("update VoteResult v "
			+ "set v.adjusted='callOut' "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3) and v.affirmVoteResulted=false and v.selected=true")
	void callOut(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);
	
	@Modifying
	@Query("update VoteResult v "
			+ "set v.affirmVoteResulted=true "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3) and v.affirmVoteResulted=false")
	void affirm(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);
	
	@Modifying
	@Query("update VoteResult v "
			+ "set v.chosen=true "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.id in (?3) and v.affirmVoteResulted=true and ((v.selected=false and v.adjusted='transferIn') or (v.selected=true and v.adjusted=null))")
	void result(Long reviewMainId, Long reviewProcessId, List<Long> voteResultIds);
	
	Long countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedFalse(Long reviewMainId, Long reviewProcessId);
	
	Long countByReviewMainIdAndReviewProcessIdAndAffirmVoteResultedTrueAndSelectedTrue(Long reviewMainId, Long reviewProcessId);
	
	@Query("from VoteResult v "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.drugForm.commonNameContents.common.drugCategory=?3 "
			+ "order by v.passSum desc, v.opposeSum asc, v.abstainSum asc")
	List<VoteResult> findCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId, DrugCategoryEnum drugCategoryEnum);
	
	@Query("select v.id from VoteResult v "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.drugForm.commonNameContents.common.drugCategory=?3 and v.drugForm.userId in (?4) "
			+ "order by v.passSum desc, v.opposeSum asc, v.abstainSum asc")
	List<Long> findCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId, DrugCategoryEnum drugCategoryEnum, Set<Long> userIds);
	
	@Modifying
	@Query("update VoteResult v "
			+ "set v.selected=true, v.ensureOrgan=true "
			+ "where v.id in (?1)")
	void ensure(List<Long> voteResultIds);
	
	@Query("from VoteResult v "
			+ "where v.reviewMainId=?1 and v.reviewProcessId=?2 and v.drugForm.commonNameContents.common.drugCategory=?3 and v.id not in (?4) "
			+ "order by v.passSum desc, v.opposeSum asc, v.abstainSum asc")
	List<VoteResult> findCurrentReviewProcessVoteResults(Long reviewMainId, Long reviewProcessId, DrugCategoryEnum drugCategoryEnum, List<Long> excludeVoteResutIds);
 	
	Long countByReviewMainIdAndReviewProcessIdAndDrugFormCommonNameContentsCommonDrugCategoryAndSelectedTrueAndAffirmVoteResultedTrue(Long reviewMainId, Long reviewProcessId, DrugCategoryEnum drugCategoryEnum);
	
	Long countByReviewMainIdAndReviewProcessIdAndSelectedTrue(Long reviewMainId, Long reviewProcessId);
	
	@Query("from VoteResult v "
			+ "where v.reviewMainId=?1 and v.affirmVoteResulted=true and v.commonNameContents is not null "
			+ "order by v.drugForm.commonNameContents.common.drugCategory asc,v.selected desc, v.passSum desc, v.adjusted asc, v.drugForm.commonNameContents.common.commonName asc, drugForm.commonNameContents.pill asc, v.id desc")
	List<VoteResult> findAllVoteResultLast(Long reviewMainId);
	
	@Query("from VoteResult v "
			+ "where v.reviewMainId=?1 and v.affirmVoteResulted=true and v.chosen=true and v.commonNameContents is not null "
			+ "order by v.drugForm.commonNameContents.common.drugCategory asc,v.selected desc, v.passSum desc, v.adjusted asc, v.drugForm.commonNameContents.common.commonName asc, drugForm.commonNameContents.pill asc, v.id desc")
	List<VoteResult> findChosnResult(Long reviewMainId);
}
