package com.ewcms.hzda.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.utils.MessageUtils;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.hzda.entity.Anamnesis;
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.service.AnamnesisService;
import com.ewcms.hzda.web.controller.util.HzdaUtil;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping(value = "/hzda/anamnesis")
public class AnamnesisController extends BaseCRUDController<Anamnesis, Long> {
	
	private AnamnesisService getAnamnesisService() {
		return (AnamnesisService) baseService;
	}

	@Override
	public String index(Model model) {
		return HzdaUtil.HZDA_GENERAL_INFORMATION_INDEX_URL;
	}

	@RequestMapping(value = "index/{generalInformationId}")
	public String showSaveForm(@CurrentUser User user,
			@PathVariable(value = "generalInformationId") GeneralInformation generalInformation, Model model, RedirectAttributes redirectAttributes) {
		if (!user.getAdmin() && user.getId() != generalInformation.getUserId())
			throw new UnauthorizedException(MessageUtils.message("no.permission", "no.view.permission"));

		Anamnesis anamnesis = getAnamnesisService().findByGeneralInformationId(generalInformation.getId());
		if (anamnesis == null) {
			anamnesis = new Anamnesis();
		}
		model.addAttribute("generalInformationId", generalInformation.getId());
		model.addAttribute("m", anamnesis);

		return viewName("edit");
	}

	@Override
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	public String save(Model model, Anamnesis m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "save/{generalInformationId}", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") Anamnesis m, BindingResult result, @CurrentUser User user, @PathVariable(value = "generalInformationId") GeneralInformation generalInformation, RedirectAttributes redirectAttributes) {
		if (hasError(m, result)) {
			return showSaveForm(user, generalInformation, model, redirectAttributes);
		}
		
		if (!user.getAdmin() && user.getId() != generalInformation.getUserId())
			throw new UnauthorizedException(MessageUtils.message("no.permission", "no.update.permission"));
		
		m.setUserId(user.getId());
		m.setOrganizationId(generalInformation.getOrganizationId());
		m.setGeneralInformationId(generalInformation.getId());
		
		if (m.getId() != null) {
			baseService.update(m);
		} else {
			baseService.save(m);
		}

		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "保存成功");
		return redirectToUrl(viewName("index/" + generalInformation.getId()));
	}
}
