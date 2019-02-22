package com.ewcms.hzda.web.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.entity.MedicationRecord;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping(value = "/hzda/medicationrecord")
public class MedicationRecordController extends BaseCRUDController<MedicationRecord, Long> {
	public MedicationRecordController() {
		//setListAlsoSetCommonData(true);
		setResourceIdentity("hzda:medicationrecord");
	}
	
	@Override
	public String index(Model model) {
		return "/hzda/generalinformation/index";
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
	public List<MedicationRecord> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "generalInformationId")Long generalInformationId){
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, MedicationRecord.class);
		searchable.addSearchFilter("generalInformationId", SearchOperator.EQ, generalInformationId);
		searchable.addSort(Direction.DESC, "id");
		
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
	public String save(Model model, MedicationRecord m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save/{generalInformationId}", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") MedicationRecord m, BindingResult result, @CurrentUser User user, @PathVariable(value = "generalInformationId") GeneralInformation generalInformation, @RequestParam(required = false) List<Long> selections, RedirectAttributes redirectAttributes) {
		if (hasError(m, result)) {
			return showSaveForm(model, selections);
		}

		setCommonData(model);

		if (permissionList != null) {
			this.permissionList.assertHasCreatePermission();
		}
		
		m.setUserId(user.getId());
		m.setOrganizationId(generalInformation.getOrganizationId());
		m.setGeneralInformationId(generalInformation.getId());
		
		if (m.getId() != null) {
			baseService.update(m);
		} else {
			baseService.save(m);
		}

		return showSaveForm(generalInformation.getId(), model, selections);
	}
}
