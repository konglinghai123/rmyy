package com.ewcms.security.group.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.group.entity.GroupRelation;

import java.util.List;
import java.util.Set;

/**
 * @author wu_zhijun
 */
public interface GroupRelationRepository extends BaseRepository<GroupRelation, Long> {

	GroupRelation findByGroupIdAndUserId(Long groupId, Long userId);

	GroupRelation findByGroupIdAndOrganizationId(Long groupId, Long organizationId);

	@Query("select groupId from GroupRelation where userId=?1")
	List<Long> findGroupIds(Long userId);

	@Query("select groupId from GroupRelation where userId=?1 or (organizationId in (?2))")
	List<Long> findGroupIds(Long userId, Set<Long> organizationIds);

	@Query("select id from GroupRelation where groupId=?1")
	List<Long> findGroupRelationIds(Long groupId);
	
	@Query("select id from GroupRelation where groupId=?1 and userId in (?2)")
	List<Long> findGroupRelationIds(Long groupId, Set<Long> userIds);
	
	// 无需删除用户 因为用户并不逻辑删除
	@Modifying
	@Query("delete from GroupRelation r where not exists (select 1 from Group g where r.groupId = g.id)")
	void clearDeletedGroupRelation();
}
