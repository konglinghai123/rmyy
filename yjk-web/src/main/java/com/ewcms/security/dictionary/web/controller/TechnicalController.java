package com.ewcms.security.dictionary.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.dictionary.entity.Technical;
import com.ewcms.security.dictionary.service.TechnicalService;

@Controller
@RequestMapping("/security/dictionary/technical")
public class TechnicalController extends BaseCRUDController<Technical, Long> {

	private TechnicalService getTechnicalService() {
		return (TechnicalService) baseService;
	}
	
	public TechnicalController() {
		setResourceIdentity("security:technical");
	}
	
	@RequestMapping(value = "delete/discard")
	@Override
	public AjaxResponse delete(List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") Technical m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m")Technical m, BindingResult result, @RequestParam(required = false) List<Long> selections, RedirectAttributes redirectAttributes) {
		Technical technical = getTechnicalService().findByNameAndLevel(m.getName(), m.getLevel());
		if (technical != null) {
			redirectAttributes.addFlashAttribute(Constants.ERROR, "数据已存在，请重新输入！");
            return redirectToUrl(viewName("edit"));
		}
		return super.save(model, m, result, selections);
	}
	
}
