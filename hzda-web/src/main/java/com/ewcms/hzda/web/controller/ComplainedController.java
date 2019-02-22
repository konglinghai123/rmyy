package com.ewcms.hzda.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.hzda.entity.Complained;
import com.ewcms.hzda.service.ComplainedService;

@Controller
@RequestMapping(value = "/hzda/complained")
public class ComplainedController extends BaseCRUDController<Complained, Long> {
	
	private ComplainedService getComplainedService() {
		return (ComplainedService) baseService;
	}
	
	@Override
	public String index(Model model) {
		return "/hzda/generalinformation/index";
	}
	
	@RequestMapping(value = "index/{generalInformationId}")
	public String index(@PathVariable(value = "generalInformationId") Long generalInformationId, Model model) {
		Complained complained = getComplainedService().findByGeneralInformationId(generalInformationId);
		if (complained == null) {
			model.addAttribute("operate", "add");
			complained = new Complained();
		} else {
			model.addAttribute("operate", "update");
		}
		model.addAttribute("m", complained);
		
		return viewName("edit");
	}
}
