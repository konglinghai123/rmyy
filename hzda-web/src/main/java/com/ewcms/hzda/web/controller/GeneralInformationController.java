package com.ewcms.hzda.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.zd.service.NationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping(value = "/hzda/generalinformation")
public class GeneralInformationController extends BaseCRUDController<GeneralInformation, Long> {

	@Autowired
	private NationService nationService;
	
	public GeneralInformationController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("hzda:generalinformation");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("sexList", GeneralInformation.Sex.values());
		model.addAttribute("nationList", nationService.findAll());
	}

	@Override
	@RequestMapping(value = "query/discard")
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "query")
	@ResponseBody
	public Map<String, Object> query(@CurrentUser User user, @ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		if (!user.getAdmin()) {
			searchParameter.getParameters().put("EQ_userId", user.getId());
		}
		return super.query(searchParameter, model);
	}
	
	@Override
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	public String save(Model model, GeneralInformation m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@CurrentUser User user, Model model, @Valid @ModelAttribute("m") GeneralInformation generalInformation, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		generalInformation.setUserId(user.getId());
		if (EmptyUtil.isCollectionNotEmpty(user.getOrganizationJobs())) {
			generalInformation.setOrganizationId(user.getOrganizationJobs().get(0).getOrganizationId());
		}
		return super.save(model, generalInformation, result, selections);
	}
}
