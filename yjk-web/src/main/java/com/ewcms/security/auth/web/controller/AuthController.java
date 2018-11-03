package com.ewcms.security.auth.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.auth.entity.Auth;
import com.ewcms.security.auth.entity.AuthType;
import com.ewcms.security.auth.service.AuthService;
import com.ewcms.security.permission.service.RoleService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/auth")
public class AuthController extends BaseCRUDController<Auth, Long> {

	@Autowired
	private RoleService roleService;

	private AuthService getAuthService() {
		return (AuthService) baseService;
	}

	public AuthController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("security:auth");
	}

	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("types", AuthType.values());
		// model.addAttribute("roles", roleService.findAll());
	}

	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}

		setCommonData(model);

		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, Auth.class);
		searchable.addSort(Direction.ASC, "id");
		searchable.addSort(Direction.ASC, "type");

		Page<Auth> auths = baseService.findAll(searchable);
		// List<Auth> newAuths = Lists.newArrayList();

		for (Auth auth : auths) {
			Set<Long> roleIds = auth.getRoleIds();
			if (!roleIds.isEmpty() && roleIds.size() > 0) {
				Set<String> roleNames = roleService.findRoleNames(roleIds);
				if (!roleNames.isEmpty() && roleNames.size() > 0) {
					auth.setRoleNames(Collections3.convertToString(roleNames, ","));
				}
			}
			// auths.add(auth);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", auths.getTotalElements());
		resultMap.put("rows", auths.getContent());
		return resultMap;
	}

	@Override
	@RequestMapping(value = "save/discarded", method = RequestMethod.POST)
	public String save(Model model, Auth m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discard method");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)	
	public String save(Model model, @Valid @ModelAttribute("m") Auth m,
			@RequestParam(value = "userIds", required = false) Long[] userIds,
			@RequestParam(value = "groupIds", required = false) Long[] groupIds,
			@RequestParam(value = "organizationIds", required = false) Long[] organizationIds,
			@RequestParam(value = "jobIds", required = false) Long[] jobIds, BindingResult result,
			@RequestParam(required = false) List<Long> selections) {
		this.permissionList.assertHasCreatePermission();

        if (hasError(m, result)) {
            return showSaveForm(model, selections);
        }

        if (m.getType() == AuthType.user) {
            getAuthService().addUserAuth(userIds, m);
        } else if (m.getType() == AuthType.user_group || m.getType() == AuthType.organization_group) {
            getAuthService().addGroupAuth(groupIds, m);
        } else if (m.getType() == AuthType.organization_job) {
            getAuthService().addOrganizationJobAuth(organizationIds, jobIds, m);
        }
        
        model.addAttribute("close", true);
		return showSaveForm(model, selections);
	}
}
