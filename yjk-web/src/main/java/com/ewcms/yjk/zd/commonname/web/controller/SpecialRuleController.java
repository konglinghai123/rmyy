package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.Constants;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.SpecialRule;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.ewcms.yjk.zd.commonname.service.SpecialRuleService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/yjk/zd/specialrule")
public class SpecialRuleController extends BaseCRUDController<SpecialRule, Long> {

	private SpecialRuleService getSpecialRuleService() {
		return (SpecialRuleService) baseService;
	}
	
	@Autowired
	private CommonNameService commonNameService;

	public SpecialRuleController() {
		setResourceIdentity("yjk:specialrule");
	}

	@RequestMapping(value = "changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(@PathVariable("newStatus") Boolean newStatus,
			@RequestParam("selections") List<Long> selections) {
		if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }
		
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			getSpecialRuleService().changeStatus(selections, newStatus);
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "validate", method = RequestMethod.GET)
	@ResponseBody
	public Object validate(@RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
			@RequestParam(value = "id", required = false) Long id) {

		ValidateResponse response = ValidateResponse.newInstance();

		if ("name".equals(fieldId)) {
			SpecialRule specialRule = getSpecialRuleService().findByName(fieldValue);
			Boolean exist = Boolean.TRUE;
			if (EmptyUtil.isNotNull(specialRule)) {
				if (specialRule.getId().equals(id) && specialRule.getName().equals(fieldValue)) {
					exist = Boolean.FALSE;
				}
			} else {
				exist = Boolean.FALSE;
			}

			if (exist) {
				response.validateFail(fieldId, "名称已存在");
			} else {
				// 如果msg 不为空 将弹出提示框
				response.validateSuccess(fieldId, "");
			}
		}
		return response.result();
	}

	@RequestMapping(value = "{specialRuleId}/detail")
	public String detail(@PathVariable(value = "specialRuleId") Long specialRuleId) {
		return viewName("detail");
	}

	@RequestMapping(value = "{specialRuleId}/query")
	@ResponseBody
	public Map<String, Object> query(@PathVariable(value = "specialRuleId") Long specialRuleId,
			@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }
		
		Map<String, Object> maps = Maps.newHashMap();

		SpecialRule specialRule = getSpecialRuleService().findOne(specialRuleId);
		if (EmptyUtil.isNotNull(specialRule)) {
			List<CommonName> commonNames = specialRule.getCommonNames();

			maps.put("total", commonNames.size());
			maps.put("rows", commonNames);
		}
		return maps;
	}

	@RequestMapping(value = "{specialRuleId}/save", method = RequestMethod.GET)
	public String showDetailSaveForm(@PathVariable(value = "specialRuleId") Long specialRuleId, Model model) {
		return viewName("detailEdit");
	}

	@RequestMapping(value = "{specialRuleId}/save", method = RequestMethod.POST)
	public String detailSave(@PathVariable(value = "specialRuleId") Long specialRuleId,
			@RequestParam(value = "commonNameIds", required = false) Long[] commonNameIds, Model model, RedirectAttributes redirectAttributes) {
		if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }
		
		if (EmptyUtil.isArrayEmpty(commonNameIds)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "通用名不能为空，请重新选择");
			return redirectToUrl(viewName(specialRuleId + "/save"));
		}
		
		SpecialRule specialRule = getSpecialRuleService().findOne(specialRuleId);

		List<CommonName> commonNames = specialRule.getCommonNames();
		if (commonNames == null)
			commonNames = Lists.newArrayList();

		CommonName commonName = null;
		for (Long commonNameId : commonNameIds) {
			Long count = getSpecialRuleService().findExist(specialRuleId, commonNameId);
			if (count == 0) {
				commonName = commonNameService.findOne(commonNameId);
				commonNames.add(commonName);
			}
		}
		
		specialRule.setCommonNames(commonNames);

		getSpecialRuleService().save(specialRule);

		model.addAttribute("operate", "add");
		model.addAttribute("lastM", JSON.toJSONString(commonName));

		return showDetailSaveForm(specialRuleId, model);
	}
	
	@RequestMapping(value = "{specialRuleId}/delete")
	@ResponseBody
	public AjaxResponse delete(@PathVariable(value = "specialRuleId") Long specialRuleId, @RequestParam(required = false) List<Long> selections){
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("删除成功！");
        
        try{
	        if (selections != null && !selections.isEmpty()){
	        	getSpecialRuleService().deleteSpecialRuleLink(specialRuleId, selections);
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("删除失败了！");
        }
		return ajaxResponse;
	}

	@RequestMapping(value = "{specialRuleId}/detailValidate", method = RequestMethod.GET)
	@ResponseBody
	public Object detailValidate(@PathVariable(value = "specialRuleId") Long specialRuleId,
			@RequestParam(value = "commonNameId") Long commonNameId) {
		ValidateResponse response = ValidateResponse.newInstance();

		Long count = getSpecialRuleService().findExist(specialRuleId, commonNameId);
		Boolean exist = Boolean.TRUE;
		if (count == 0) {
			exist = Boolean.FALSE;
		} else {
			exist = Boolean.FALSE;
		}

		if (exist) {
			response.validateFail("commonNameId", "选择的通用名已存在");
		} else {
			// 如果msg 不为空 将弹出提示框
			response.validateSuccess("commonNameId", "");
		}
		return response.result();
	}

}
