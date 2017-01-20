package org.apache.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.BaseUserIT;

/**
 * 用户权限校验测试类
 * 
 * @author wu_zhijun
 */
public class UserRealmIT extends BaseUserIT {
	
	@Test
	public void testLoginSuccess(){
		createUser(username, email, mobilePhoneNumber, password);
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		Assert.assertEquals(username, subject.getPrincipal());
	}
	
	@Test(expected = UnknownAccountException.class)
	public void testLoginFailWithUserNoExists(){
		createUser(username, email, mobilePhoneNumber, password);
		
		UsernamePasswordToken token = new UsernamePasswordToken(username + "T", password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}
	
	@Test(expected = AuthenticationException.class)
	public void testLoginFailWithUserPasswordNoMatch(){
		createUser(username, email, mobilePhoneNumber, password);
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password + "X");
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}
	
	@Test(expected = LockedAccountException.class)
	public void testLoginFailWithSystemBlocked(){
		User user = createUser(username, email, mobilePhoneNumber, password);
		userService.changeStatus(user, user, UserStatus.blocked, "test use");
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}
	
	@Test(expected = ExcessiveAttemptsException.class)
	public void testLoginFailWithRetryLimitExceed(){
		createUser(username, email, mobilePhoneNumber, password);
		for (int i = 0; i < maxRetryCount; i++){
			try{
				UsernamePasswordToken token = new UsernamePasswordToken(username, password + i);
				Subject subject = SecurityUtils.getSubject();
				subject.login(token);
			}catch(AuthenticationException e){}
		}
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}
}
