package com.ewcms.security.user.service;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.PatternUtils;
import com.ewcms.security.organization.entity.Job;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.JobService;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.exception.UserBlockedException;
import com.ewcms.security.user.exception.UserNotExistsException;
import com.ewcms.security.user.exception.UserPasswordNotMatchException;
import com.ewcms.security.user.repository.UserRepository;
import com.ewcms.security.user.util.UserLogUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class UserService extends BaseService<User, Long> {

    private UserRepository getUserRepository() {
        return (UserRepository) baseRepository;
    }

    @Autowired
    private UserStatusHistoryService userStatusHistoryService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private JobService jobService;
    @Autowired
    private UserOrganizationJobService userOrganizationJobService;

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public User save(User user) {
    	user.setCreateDate(Calendar.getInstance().getTime());
    	user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));

        return super.save(user);
    }

    @Override
    public User update(User user) {
    	User dbUser = findOne(user.getId());
    	user.setOrganizationJobs(dbUser.getOrganizationJobs());
    	user.setCreateDate(dbUser.getCreateDate());
    	
    	return super.update(user);
    }
    
    public User findByUsername(String username) {
        if(StringUtils.isEmpty(username)) {
            return null;
        }
        return getUserRepository().findByUsername(username);
    }

    public User findByEmail(String email) {
        if(StringUtils.isEmpty(email)) {
            return null;
        }
        return getUserRepository().findByEmail(email);
    }

    public User findByMobilePhoneNumber(String mobilePhoneNumber) {
        if(StringUtils.isEmpty(mobilePhoneNumber)) {
            return null;
        }
        return getUserRepository().findByMobilePhoneNumber(mobilePhoneNumber);
    }

    public User changePassword(User user, String newPassword) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), newPassword, user.getSalt()));
        update(user);
        return user;
    }

    public User changeStatus(User opUser, User user, UserStatus newStatus, String reason) {
        user.setStatus(newStatus);
        update(user);
        userStatusHistoryService.log(opUser, user, newStatus, reason);
        return user;
    }

    public User login(String username, String password) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            UserLogUtils.log(
                    username,
                    "loginError",
                    "username is empty");
            throw new UserNotExistsException();
        }
        //密码如果不在指定范围内 肯定错误
        if (password.length() < PatternUtils.PASSWORD_MIN_LENGTH || password.length() > PatternUtils.PASSWORD_MAX_LENGTH) {
            UserLogUtils.log(
                    username,
                    "loginError",
                    "password length error! password is between {} and {}",
                    PatternUtils.PASSWORD_MIN_LENGTH, PatternUtils.PASSWORD_MAX_LENGTH);

            throw new UserPasswordNotMatchException();
        }

        User user = null;

        //此处需要走代理对象，目的是能走缓存切面
        UserService proxyUserService = (UserService) AopContext.currentProxy();
        if (maybeUsername(username)) {
            user = proxyUserService.findByUsername(username);
        }

        if (user == null && maybeEmail(username)) {
            user = proxyUserService.findByEmail(username);
        }

        if (user == null && maybeMobilePhoneNumber(username)) {
            user = proxyUserService.findByMobilePhoneNumber(username);
        }

        if (user == null || Boolean.TRUE.equals(user.getDeleted())) {
            UserLogUtils.log(
                    username,
                    "loginError",
                    "user is not exists!");

            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);

        if (user.getStatus() == UserStatus.blocked) {
            UserLogUtils.log(
                    username,
                    "loginError",
                    "user is blocked!");
            throw new UserBlockedException(userStatusHistoryService.getLastReason(user));
        }

        UserLogUtils.log(
                username,
                "loginSuccess",
                "");
        return user;
    }


    private boolean maybeUsername(String username) {
//        if (!username.matches(PatternUtils.USERNAME_PATTERN)) {
//            return false;
//        }
        //如果用户名不在指定范围内也是错误的
        if (username.length() < PatternUtils.USERNAME_MIN_LENGTH || username.length() > PatternUtils.USERNAME_MAX_LENGTH) {
            return false;
        }

        return true;
    }

    private boolean maybeEmail(String username) {
        if (!username.matches(PatternUtils.EMAIL_PATTERN)) {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username) {
        if (!username.matches(PatternUtils.MOBILE_PHONE_NUMBER_PATTERN)) {
            return false;
        }
        return true;
    }

    public void changePassword(User opUser, List<Long> ids, String newPassword) {
        UserService proxyUserService = (UserService) AopContext.currentProxy();
        for (Long id : ids) {
            User user = findOne(id);
            proxyUserService.changePassword(user, newPassword);
            UserLogUtils.log(
                    user.getUsername(),
                    "changePassword",
                    "admin user {} change password!", opUser.getUsername());
        }
    }

    public void changeStatus(User opUser, List<Long> ids, UserStatus newStatus, String reason) {
        UserService proxyUserService = (UserService) AopContext.currentProxy();
        for (Long id : ids) {
            User user = findOne(id);
            proxyUserService.changeStatus(opUser, user, newStatus, reason);
            UserLogUtils.log(
                    user.getUsername(),
                    "changeStatus",
                    "admin user {} change status!", opUser.getUsername());
        }
    }

    public Set<Map<String, Object>> findIdAndNames(Searchable searchable, String usernme) {

        searchable.addSearchFilter("username", SearchOperator.LIKE, usernme);
        searchable.addSearchFilter("deleted", SearchOperator.EQ, false);

        return Sets.newHashSet(
                Lists.transform(
                        findAll(searchable).getContent(),
                        new Function<User, Map<String, Object>>() {
                            @Override
                            public Map<String, Object> apply(User input) {
                                Map<String, Object> data = Maps.newHashMap();
                                data.put("value", input.getUsername());
                                data.put("data", input.getId());
                                return data;
                            }
                        }
                )
        );
    }
    
    public Set<User> findUserDisplay(Set<Long> userIds){
    	if (EmptyUtil.isCollectionEmpty(userIds)){
    		return Sets.newHashSet();
    	} else {
    		return getUserRepository().findUserDisplay(userIds);
    	}
    }
    
	public List<Integer> importExcel(InputStream in){
		List<Integer> noSave = Lists.newArrayList();
		
		try {
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int records = sheet.getLastRowNum();
			
			//获得列名，为第0行位置
			HSSFRow rows = sheet.getRow(0);
			int cols = rows.getLastCellNum() - 1;
			String columnNames[] = new String[cols + 1];
			
			for (int i = 0; i <= cols; i++) {
				columnNames[i] = rows.getCell(i).getStringCellValue().trim();
			}
			
			//获得数据，数据从第1行开始
			for (int i = 1; i <= records; i++) {
				User user = new User();
				rows = sheet.getRow(i);
				try {
					Long organizationId = null;
					Long jobId = null;
					for (int j = 0; j <= cols; j++) {
							if (columnNames[j].equals("工号")) {
								try {
									user.setUsername(rows.getCell(j).getStringCellValue().trim());
								} catch (Exception e) {
									Double userName = rows.getCell(j).getNumericCellValue();
									user.setUsername(String.valueOf(userName.longValue()));
								}
							} else if (columnNames[j].equals("姓名")) {
								user.setRealname(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("科室")) {
								try {
									String organizationName = rows.getCell(j).getStringCellValue().trim();
									if (EmptyUtil.isStringNotEmpty(organizationName)) {
										List<Organization> organizations = organizationService.findByName(organizationName);
										if (organizations != null && organizations.size() > 0) {
											organizationId = organizations.get(0).getId();
										}
									}
								}catch (Exception e) {
									
								}
							} else if (columnNames[j].equals("职称")) {
								try {
									String jobName = rows.getCell(j).getStringCellValue().trim();
									if (EmptyUtil.isStringNotEmpty(jobName)) {
										List<Job> jobs = jobService.findByName(jobName);
										if (jobs != null && jobs.size() > 0) {
											jobId = jobs.get(0).getId();
										}
									}
								}catch(Exception e) {
									
								}
							}
					}
					if (findByUsername(user.getUsername()) == null) {
						user.setPassword("123456");
						user.randomSalt();
				        user.setPassword(passwordService.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));

				        super.save(user);
						
						UserOrganizationJob userOrganizationJob = new UserOrganizationJob();
						
						userOrganizationJob.setUser(user);
						userOrganizationJob.setOrganizationId(organizationId);
						userOrganizationJob.setJobId(jobId);
						
						userOrganizationJobService.saveAndFlush(userOrganizationJob);
					}
				}catch(Exception e) {
					noSave.add(i + 1);
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			
		}
		return noSave;
	}

}
