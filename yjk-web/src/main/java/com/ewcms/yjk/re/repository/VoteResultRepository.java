package com.ewcms.yjk.re.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.model.VoteMonitor;

public interface VoteResultRepository extends BaseRepository<VoteResult, Long> {
	Long countByReviewProcessId(Long reviewProcessId);
	
	VoteResult findByDrugFormIdAndReviewProcessId(Long drugFormId, Long reviewProcessId);
	
	@Query("select distinct new com.ewcms.yjk.re.model.VoteMonitor("
			+ "u, v.sined"
			+ ") "
			+ "from User u, VoteRecord v "
			+ "where u.id=v.userId and u.id in (?1) "
			+ "order by u.id")
	List<VoteMonitor> findVoteMonitor(List<Long> userIds);
}
