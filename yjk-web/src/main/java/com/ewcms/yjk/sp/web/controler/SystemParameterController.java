package com.ewcms.yjk.sp.web.controler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.entity.search.filter.SearchFilterHelper;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.yjk.sp.entity.SystemExpert;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author wuzhijun
 *
 */
@Controller
@RequestMapping(value = "/yjk/sp/systemparameter")
public class SystemParameterController extends BaseCRUDController<SystemParameter, Long> {

	private SystemParameterService getSystemParameterService() {
		return (SystemParameterService) baseService;
	}

	@Autowired
	private UserService userService;

	public SystemParameterController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("yjk:systemparameter");
	}

	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}

	@RequestMapping(value = "{systemParameterId}/closedeclare")
	@ResponseBody
	public AjaxResponse closeDeclare(@CurrentUser User user,
			@PathVariable(value = "systemParameterId") Long systemParameterId) {
		AjaxResponse ajaxResponse = new AjaxResponse("关闭申报成功");
		try {
			getSystemParameterService().closeDeclare(user, systemParameterId);
		} catch (Exception e) {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("关闭申报失败");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "{systemParameterId}/opendeclare")
	@ResponseBody
	public AjaxResponse openDeclare(@CurrentUser User user,
			@PathVariable(value = "systemParameterId") Long systemParameterId) {
		AjaxResponse ajaxResponse = new AjaxResponse("启动申报成功");
		try {
			SystemParameter vo = getSystemParameterService().openDeclare(user, systemParameterId);

			if (vo == null) {
				ajaxResponse.setMessage("数据启动异常");
			} else {
				if (!vo.getEnabled()) {
					ajaxResponse.setMessage("当前时间不在启动时间范围内");
				}
			}
		} catch (Exception e) {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("启动申报失败");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "{systemParameterId}/filter")
	@ResponseBody
	public AjaxResponse filter(@PathVariable("systemParameterId") SystemParameter systemParameter) {
		AjaxResponse ajaxResponse = new AjaxResponse("筛选成功");
		getSystemParameterService().filter(systemParameter.getId());
		return ajaxResponse;
	}

	@RequestMapping(value = "{systemParameterId}/indexUser")
	public String indexUser(@PathVariable(value = "systemParameterId") SystemParameter systemParameter, Model model) {
		model.addAttribute("m", systemParameter);
		return viewName("indexUser");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "{systemParameterId}/queryUser")
	@ResponseBody
	public Map<String, Object> queryUser(@PathVariable(value = "systemParameterId") SystemParameter systemParameter, @ModelAttribute SearchParameter<Long> searchParameter) {
		Map<String, Object> map = Maps.newHashMap();

		List<Long> allUserIds = Lists.newArrayList();

		List<User> systemParameterUsers = systemParameter.getUsers();
		if (EmptyUtil.isCollectionNotEmpty(systemParameterUsers)) {
			allUserIds.addAll(Collections3.extractToList(systemParameterUsers, "id"));
		}

		List<SystemExpert> systemExperts = systemParameter.getSystemExperts();
		if (EmptyUtil.isCollectionNotEmpty(systemExperts)) {
			for (SystemExpert systemExpert : systemExperts) {
				allUserIds.addAll(Collections3.extractToList(systemExpert.getUsers(), "id"));
			}
		}
		
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, User.class);
		searchable.addSort(Direction.ASC, "id");
		if (EmptyUtil.isCollectionNotEmpty(allUserIds)) {
			searchable.addSearchFilter("id", SearchOperator.IN, allUserIds);
		}
		
		Page<User> users = userService.findAll(searchable);
		
		map.put("total", users.getTotalElements());
		map.put("rows", users.getContent());
		
		return map;
	}

	@RequestMapping(value = "{systemParameterId}/saveUser", method = RequestMethod.GET)
	public String showSaveUserForm(@PathVariable(value = "systemParameterId") Long systemParameterId, Model model) {
		if (permissionList != null) {
			this.permissionList.assertHasCreatePermission();
		}
		return viewName("editUser");
	}

	@RequestMapping(value = "{systemParameterId}/saveUser", method = RequestMethod.POST)
	public String saveUser(@CurrentUser User user, @PathVariable(value = "systemParameterId") SystemParameter systemParameter, @RequestParam(required = false) List<Long> userIds) {
		if (permissionList != null) {
			this.permissionList.assertHasCreatePermission();
		}
		getSystemParameterService().addUser(systemParameter, userIds);
		
		return redirectToUrl(viewName(systemParameter.getId() + "/saveUser"));
	}

	@RequestMapping(value = "{systemParameterId}/removeUser")
	@ResponseBody
	public AjaxResponse removeUser(@CurrentUser User user, @PathVariable(value = "systemParameterId") SystemParameter systemParameter, @RequestParam(required = false) List<Long> selections) {
		AjaxResponse ajaxResponse = new AjaxResponse("除移成功！");

		try {
			if (selections != null && !selections.isEmpty()) {
				getSystemParameterService().removeUser(systemParameter, selections);
			}
		} catch (IllegalStateException e) {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("移除失败了！");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "canUseUser")
	@ResponseBody
	public Map<String, Object> canUseUser(@RequestParam(value = "name", required = false) String name, @ModelAttribute SearchParameter<Long> searchParameter) {
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, User.class);
		
		if (EmptyUtil.isStringNotEmpty(name)) {
			searchable.or(SearchFilterHelper.newCondition("username", SearchOperator.LIKE, name), SearchFilterHelper.newCondition("realname", SearchOperator.LIKE, name));
		}

		searchable.and(SearchFilterHelper.newCondition("admin", SearchOperator.EQ, Boolean.FALSE), SearchFilterHelper.newCondition("deleted", SearchOperator.EQ, Boolean.FALSE));
		
		searchable.addSort(Direction.ASC, "id");

		Page<User> users = userService.findAll(searchable);
		
		Map<String, Object> map = Maps.newHashMap();
		
		map.put("total", users.getTotalElements());
		map.put("rows", users.getContent());
		
		return map;
	}

}
