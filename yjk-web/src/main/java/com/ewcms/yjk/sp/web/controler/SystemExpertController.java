package com.ewcms.yjk.sp.web.controler;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.sp.entity.SystemExpert;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemExpertService;
import com.ewcms.yjk.sp.service.SystemParameterService;

@Controller
@RequestMapping(value = "/yjk/sp/systemexpert")
public class SystemExpertController extends BaseSequenceMovableController<SystemExpert, Long> {

	private SystemExpertService getSystemExpertService() {
		return (SystemExpertService) baseService;
	}

	@Autowired
	private SystemParameterService systemParameterService;

	public SystemExpertController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("yjk:systemparamter");
	}

	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("booleanList", BooleanEnum.values());
	}

	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{systemParameterId}/index")
	public String index(@PathVariable(value = "systemParameterId") SystemParameter systemParameter, Model model) {
		model.addAttribute("systemParameterId", systemParameter.getId());
		if (systemParameter.getEnabled() != null && systemParameter.getEnabled()) {
			model.addAttribute("isOperation", Boolean.FALSE);
		} else {
			model.addAttribute("isOperation", Boolean.TRUE);
			
		}
		return super.index(model);
	}

	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{systemParameterId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter,
			@PathVariable(value = "systemParameterId") Long systemParameterId, Model model) {
		Map<String, Object> parameters = searchParameter.getParameters();
		parameters.put("EQ_systemParameter.id", systemParameterId);
		searchParameter.setParameters(parameters);

		Map<String, Direction> sorts = searchParameter.getSorts();
		sorts.put("weight", Direction.ASC);
		searchParameter.setSorts(sorts);

		return super.query(searchParameter, model);
	}

	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{systemParameterId}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "systemParameterId") Long systemParameterId, Model model,
			@RequestParam(required = false) List<Long> selections) {
		return super.showSaveForm(model, selections);
	}

	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") SystemExpert m, BindingResult result,
			@RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{systemParameterId}/save", method = RequestMethod.POST)
	public String save(@PathVariable(value = "systemParameterId") Long systemParameterId, Model model,
			@Valid @ModelAttribute("m") SystemExpert m, BindingResult result,
			@RequestParam(required = false) List<Long> selections) {
		setCommonData(model);

		if (hasError(m, result)) {
			return showSaveForm(systemParameterId, model, selections);
		}

		SystemParameter systemParameter = systemParameterService.findOne(systemParameterId);
		systemParameter.addSystemExpert(m);

		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
			if (permissionList != null) {
				this.permissionList.assertHasUpdatePermission();
			}

			SystemExpert lastM = baseService.update(m);

			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
			if (permissionList != null) {
				this.permissionList.assertHasCreatePermission();
			}

			SystemExpert lastM = baseService.save(m);

			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}

		return showSaveForm(systemParameterId, model, selections);
	}

	@RequestMapping(value = "delete/discard")
	@ResponseBody
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{systemParameterId}/delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections,
			@PathVariable(value = "systemParameterId") Long systemParameterId) {
		return super.delete(selections);
	}

	@RequestMapping(value = "{systemParameterId}/validate")
	@ResponseBody
	public Object validate(@PathVariable("systemParameterId") SystemParameter systemParameter, @ModelAttribute("m") SystemExpert m) {
		m.setSystemParameter(systemParameter);
		SystemExpert dbSystemExpert = getSystemExpertService().findBySystemParameterAndId(systemParameter, m.getId());

		ValidateResponse response = ValidateResponse.newInstance();
		if (dbSystemExpert == null
				|| (dbSystemExpert.getId().equals(m.getId()) && dbSystemExpert.getSystemParameter().equals(systemParameter))) {
			response.validateSuccess("", "");
		} else {
			response.validateFail("", "记录已存在，请重新选择");
		}
		return response.result();
	}
	
	@RequestMapping(value = "{systemParameterId}/changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(@PathVariable(value = "systemParameterId") Long systemParameterId, @PathVariable("newStatus") Boolean newStatus,
			@RequestParam("selections") List<Long> selections) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			getSystemExpertService().changeStatus(selections, newStatus);
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}

}
