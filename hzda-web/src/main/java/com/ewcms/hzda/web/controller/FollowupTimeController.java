package com.ewcms.hzda.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.hzda.entity.FollowupTime;
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.service.FollowupTimeService;
import com.ewcms.hzda.web.controller.util.HzdaUtil;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping(value = "/hzda/followuptime")
public class FollowupTimeController extends BaseCRUDController<FollowupTime, Long>{
	
	private FollowupTimeService getFollowupTimeService() {
		return (FollowupTimeService) baseService;
	}
	
	public FollowupTimeController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("hzda:followuptime");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("sexList", GeneralInformation.Sex.values());
		model.addAttribute("booleanList", BooleanEnum.values());
	}

	@Override
	public String index(Model model) {
		setCommonData(model);
		return HzdaUtil.HZDA_GENERAL_INFORMATION_INDEX_URL;
	}
		
	@RequestMapping(value = "index/{generalInformationId}")
	public String index(@PathVariable(value = "generalInformationId")Long generalInformationId, Model model){
		return super.index(model);
	}
	
	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "query/{generalInformationId}")
	@ResponseBody
	public List<FollowupTime> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "generalInformationId")Long generalInformationId){
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, FollowupTime.class);
		searchable.addSearchFilter("generalInformation.id", SearchOperator.EQ, generalInformationId);
		searchable.addSort(Direction.DESC, "id");
		searchable.addSort(Direction.DESC, "nextTime");
		
		return baseService.findAllWithSort(searchable);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save/{generalInformationId}", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "generalInformationId") Long generalInformationId, Model model, @RequestParam(required = false) List<Long> selections){
		return super.showSaveForm(model, selections);
	}
	
	@Override
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	public String save(Model model, FollowupTime m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save/{generalInformationId}", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") FollowupTime m, BindingResult result, @CurrentUser User user, @PathVariable(value = "generalInformationId") GeneralInformation generalInformation, @RequestParam(required = false) List<Long> selections, RedirectAttributes redirectAttributes) {
		if (hasError(m, result)) {
			return showSaveForm(model, selections);
		}

		setCommonData(model);

		if (permissionList != null) {
			this.permissionList.assertHasCreatePermission();
		}
		
		m.setUserId(user.getId());
		m.setOrganizationId(user.getOrganizationJobs().get(0).getOrganizationId());
		m.setGeneralInformation(generalInformation);
		
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
	        if (permissionList != null) {
	            this.permissionList.assertHasUpdatePermission();
	        }
			
	        FollowupTime lastM = baseService.update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
	        if (permissionList != null) {
	            this.permissionList.assertHasCreatePermission();
	        }
			
	        FollowupTime lastM = baseService.save(m);
	        
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		
		return showSaveForm(generalInformation.getId(), model, selections);
	}
	
	@RequestMapping(value = "delete/discard")
	@ResponseBody
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "delete/{generalInformationId}")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections, @PathVariable(value = "generalInformationId") Long generalInformationId){
		return super.delete(selections);
	}
	
	@RequestMapping(value = "send")
	@ResponseBody
	public AjaxResponse send(@RequestParam(value = "id")FollowupTime followupTime) {
		AjaxResponse ajaxResponse = new AjaxResponse("发送成功！");
		getFollowupTimeService().updateSms(followupTime);
		if (!followupTime.getMessage().equals("OK")) {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage(followupTime.getMessage());
		}
		return ajaxResponse;
	}
}
