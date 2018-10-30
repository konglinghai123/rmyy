package com.ewcms.security.user.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.multipart.MultipartFile;

import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/user/user")
public class UserController extends BaseCRUDController<User, Long> {

	private UserService getUserService() {
		return (UserService) baseService;
	}

	public UserController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("security:user");
	}

	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("statusList", UserStatus.values());
		model.addAttribute("booleanList", BooleanEnum.values());
	}
	
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") User m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		if (m.getId() != null){
			User dbUser = baseService.findOne(m.getId());
			m.setOrganizationJobs(dbUser.getOrganizationJobs());
		}
		return super.save(model, m, result, selections);
	}

	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<User> canUseUser() {
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchFilter("deleted", SearchOperator.EQ, Boolean.FALSE);
		searchable.addSort(Direction.ASC, "id");

		return baseService.findAllWithSort(searchable);
	}

	@RequestMapping(value = "changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(@PathVariable("newStatus") UserStatus newStatus,
			@RequestParam("selections") List<Long> selections,
			@RequestParam("reason") String reason, @CurrentUser User opUser) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			getUserService().changeStatus(opUser, selections, newStatus, reason);
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "changePassword")
	@ResponseBody
	public AjaxResponse changePassword(@RequestParam("selections") List<Long> selections,
			@RequestParam("newPassword") String newPassword,
			@CurrentUser User opUser) {
		AjaxResponse ajaxResponse = new AjaxResponse("修改密码成功！");
		
		try{
			getUserService().changePassword(opUser, selections, newPassword);
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("修改密码失败了！");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "recycle")
	@ResponseBody
	public AjaxResponse recycle(@RequestParam("selections") List<Long> selections) {
		AjaxResponse ajaxResponse = new AjaxResponse("还原成功！");
		
		try{
			for (Long id : selections) {
				User user = getUserService().findOne(id);
				user.setDeleted(Boolean.FALSE);
				getUserService().update(user);
			}
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("还原失败了！");
		}
		return ajaxResponse;
	}

    /**
     * 验证返回格式
     * 单个：[fieldId, 1|0, msg]
     * 多个：[[fieldId, 1|0, msg],[fieldId, 1|0, msg]]
     *
     * @param fieldId
     * @param fieldValue
     * @return
     */
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

        if ("username".equals(fieldId)) {
            User user = getUserService().findByUsername(fieldValue);
            if (user == null || (user.getId().equals(id) && user.getUsername().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "用户名已被其他人使用");
            }
        }

        if ("email".equals(fieldId)) {
            User user = getUserService().findByEmail(fieldValue);
            if (user == null || (user.getId().equals(id) && user.getEmail().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "邮箱已被其他人使用");
            }
        }

        if ("mobilePhoneNumber".equals(fieldId)) {
            User user = getUserService().findByMobilePhoneNumber(fieldValue);
            if (user == null || (user.getId().equals(id) && user.getMobilePhoneNumber().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "手机号已被其他人使用");
            }
        }

        return response.result();
    }
    
    @RequestMapping("ajax/autocomplete")
    public @ResponseBody Map<String, Object> autoComplete(@RequestParam("query") String query){
    	Searchable searchable = Searchable.newSearchable();
    	searchable.setPage(0, 30);
    	
    	Map<String, Object> result = Maps.newHashMap();
    	
    	result.put("query", query);
    	result.put("suggestions", getUserService().findIdAndNames(searchable, query));
    	
    	
    	return result;
    }
    
    @RequestMapping(value = "/import")
	public String importStudent() {
		return viewName("import");
	}
    
    @RequestMapping(value = "/saveimport", method = RequestMethod.POST)
    @ResponseBody
	public String saveImportStudent(@RequestParam(value = "excelFile", required = false) MultipartFile excelFile, HttpServletRequest request) {
		List<Integer> noSave = Lists.newArrayList();
		String message = "导入信息";
		try {
			request.setCharacterEncoding("UTF-8");
			if (excelFile != null && !excelFile.isEmpty()) {
				noSave = getUserService().importExcel(excelFile.getInputStream());
			}
		} catch (Exception e) {
			message += "失败";
		}
		if (noSave.isEmpty())
			message += "成功";
		else
			message += "部分成功，不成功的所在的Excel行数为：\r\n" + noSave;
		return message;
	}

}
