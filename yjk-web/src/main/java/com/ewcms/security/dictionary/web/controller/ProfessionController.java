package com.ewcms.security.dictionary.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.dictionary.entity.Profession;
import com.ewcms.security.dictionary.service.ProfessionService;

@Controller
@RequestMapping("/security/dictionary/profession")
public class ProfessionController extends BaseCRUDController<Profession, Long> {
	
	private ProfessionService getProfessionService() {
		return (ProfessionService) baseService;
	}
	
	public ProfessionController() {
		setResourceIdentity("security:profession");
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.GET)
	@ResponseBody
	public Object validate(@RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
			@RequestParam(value = "id", required = false) Long id) {

		ValidateResponse response = ValidateResponse.newInstance();

		if ("name".equals(fieldId)) {
			Profession profession = getProfessionService().findByName(fieldValue);
			if (profession == null || (profession.getId().equals(id) && profession.getName().equals(fieldValue))) {
				// 如果msg 不为空 将弹出提示框
				response.validateSuccess(fieldId, "");
			} else {
				response.validateFail(fieldId, "名称存在");
			}
		}

		return response.result();
	}
	
	@RequestMapping(value = "delete/discard")
	@Override
	public AjaxResponse delete(List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
}
