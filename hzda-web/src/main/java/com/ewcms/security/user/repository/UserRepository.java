package com.ewcms.security.user.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;

/**
 * @author wu_zhijun
 */
public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsername(String username);

    User findByMobilePhoneNumber(String mobilePhoneNumber);

    User findByEmail(String email);
    
	@Query("from User where id in ?1")
	Set<User> findUserDisplay(Set<Long> userIds);
	
	@Query("select id from User where admin = false and deleted = false")
	List<Long> findUserIds();
	
	List<User> findByAdminTrueAndDeletedFalseAndStatus(UserStatus status);
	
	@Query("select id from User where admin = false and deleted = false and id not in (?1)")
	List<Long> findUserIdOffscale(List<Long> userIds);
	
	List<User> findByRealnameContaining(String realname);
}
