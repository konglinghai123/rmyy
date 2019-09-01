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
import com.ewcms.hzda.entity.GeneralInformation;
import com.ewcms.hzda.entity.Operation;
import com.ewcms.hzda.service.OperationService;
import com.ewcms.hzda.web.controller.util.HzdaUtil;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping(value = "/hzda/operation")
public class OperationController extends BaseCRUDController<Operation, Long> {
	
	private OperationService getOperationService() {
		return (OperationService) baseService;
	}

	public OperationController() {
		setResourceIdentity("hzda:operation");
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

		Operation operation = getOperationService().findByGeneralInformationId(generalInformation.getId());
		if (operation == null) {
			operation = new Operation();
		}
		model.addAttribute("generalInformationId", generalInformation.getId());
		model.addAttribute("m", operation);

		return viewName("edit");
	}

	@Override
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	public String save(Model model, Operation m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "save/{generalInformationId}", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") Operation m, BindingResult result, @CurrentUser User user, @PathVariable(value = "generalInformationId") GeneralInformation generalInformation, RedirectAttributes redirectAttributes) {
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
