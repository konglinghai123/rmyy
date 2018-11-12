package com.ewcms.yjk.re.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.ewcms.yjk.re.entity.ReviewExpert;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/yjk/re/reviewmain")
public class ReviewMainController extends BaseCRUDController<ReviewMain, Long> {

	private ReviewMainService getReviewMainService() {
		return (ReviewMainService) baseService;
	}

	@Autowired
	private UserService userService;
	
	public ReviewMainController() {
		setResourceIdentity("yjk:reviewmain");
	}

	@Override
	public String save(Model model, @Valid @ModelAttribute("m") ReviewMain m, BindingResult result,
			@RequestParam(required = false) List<Long> selections) {
		if (m.getId() != null) {
			ReviewMain dbReviewMain = baseService.findOne(m.getId());
			m.setReviewExperts(dbReviewMain.getReviewExperts());
		}
		return super.save(model, m, result, selections);
	}

	@RequestMapping(value = "validate", method = RequestMethod.GET)
	@ResponseBody
	public Object validate(@RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
			@RequestParam(value = "id", required = false) Long id) {

		ValidateResponse response = ValidateResponse.newInstance();

		if ("name".equals(fieldId)) {
			ReviewMain reviewMain = getReviewMainService().findByName(fieldValue);
			if (reviewMain == null || (reviewMain.getId().equals(id) && reviewMain.getName().equals(fieldValue))) {
				// 如果msg 不为空 将弹出提示框
				response.validateSuccess(fieldId, "");
			} else {
				response.validateFail(fieldId, "名称存在");
			}
		}

		return response.result();
	}

	@RequestMapping(value = "{reviewMainId}/filter")
	@ResponseBody
	public AjaxResponse filter(@PathVariable("reviewMainId") ReviewMain reviewMain) {
		AjaxResponse ajaxResponse = new AjaxResponse("筛选成功");
		if (reviewMain.getExtractDate() == null) {
			getReviewMainService().filter(reviewMain.getId());
		} else {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("已筛选过不能再进行筛选，如需要必须新增记录进行操作");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "{reviewMainId}/indexUser")
	public String indexUser(@PathVariable("reviewMainId") Long reviewMainId) {
		return viewName("indexUser");
	}

	@RequestMapping(value = "{reviewMainId}/queryUser")
	@ResponseBody
	public Map<String, Object> queryUser(@PathVariable("reviewMainId") ReviewMain reviewMain) {
		Map<String, Object> map = Maps.newHashMap();
		
		List<User> allUsers = Lists.newArrayList();
		
		List<User> reviewMainUsers = reviewMain.getUsers();
		if (EmptyUtil.isCollectionNotEmpty(reviewMainUsers)) {
			allUsers.addAll(reviewMainUsers);
		}
		
		List<ReviewExpert> reviewExperts = reviewMain.getReviewExperts();
		if (EmptyUtil.isCollectionNotEmpty(reviewExperts)) {
			for (ReviewExpert reviewExpert : reviewExperts) {
				allUsers.addAll(reviewExpert.getUsers());
			}
		}
		
		map.put("total", allUsers.size());
		map.put("rows", allUsers);
		return map;
	}

	@RequestMapping(value = "{reviewMainId}/saveUser", method = RequestMethod.GET)
	public String showSaveUserForm(@PathVariable("reviewMainId") Long reviewMainId) {
		if (permissionList != null) {
			this.permissionList.assertHasCreatePermission();
		}
		return viewName("editUser");
	}

	@RequestMapping(value = "{reviewMainId}/saveUser", method = RequestMethod.POST)
	public String saveUser(@PathVariable("reviewMainId") ReviewMain reviewMain,  @RequestParam(required = false) List<Long> userIds) {
		if (permissionList != null) {
			this.permissionList.assertHasCreatePermission();
		}

		getReviewMainService().addUser(reviewMain, userIds);
		
		return redirectToUrl(viewName(reviewMain.getId() + "/saveUser"));
	}

	@RequestMapping(value = "{reviewMainId}/removeUser")
	@ResponseBody
	public AjaxResponse removeUser(@PathVariable("reviewMainId") ReviewMain reviewMain,
			@RequestParam(required = false) List<Long> selections) {
		if (permissionList != null) {
			this.permissionList.assertHasDeletePermission();
		}

		AjaxResponse ajaxResponse = new AjaxResponse("除移成功！");

		try {
			if (selections != null && !selections.isEmpty()) {
				getReviewMainService().removeUser(reviewMain, selections);
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
