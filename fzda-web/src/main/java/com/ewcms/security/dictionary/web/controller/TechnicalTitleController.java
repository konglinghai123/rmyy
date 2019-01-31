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
import com.ewcms.security.dictionary.entity.TechnicalTitle;
import com.ewcms.security.dictionary.service.TechnicalTitleService;

@Controller
@RequestMapping("/security/dictionary/technicalTitle")
public class TechnicalTitleController extends BaseCRUDController<TechnicalTitle, Long> {

	private TechnicalTitleService getTechnicalTitleService() {
		return (TechnicalTitleService) baseService;
	}
	
	public TechnicalTitleController() {
		setResourceIdentity("security:technicalTitle");
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.GET)
	@ResponseBody
	public Object validate(@RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
			@RequestParam(value = "id", required = false) Long id) {

		ValidateResponse response = ValidateResponse.newInstance();

		if ("name".equals(fieldId)) {
			TechnicalTitle technicalTitle = getTechnicalTitleService().findByName(fieldValue);
			if (technicalTitle == null || (technicalTitle.getId().equals(id) && technicalTitle.getName().equals(fieldValue))) {
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
	
	@RequestMapping(value = "canUse")
	@ResponseBody
	public List<TechnicalTitle> getTechnical(){
		return getTechnicalTitleService().findAll();
	}
}
