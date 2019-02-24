package com.ewcms.system.notice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.system.notice.entity.Notice;
import com.ewcms.system.notice.service.NoticeService;

@Controller
@RequestMapping(value = "/system/notice")
public class NoticeController extends BaseSequenceMovableController<Notice, Long> {

	private NoticeService getNoticeService() {
		return (NoticeService) baseService;
	}

	public NoticeController() {
		setResourceIdentity("system:notice");
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.GET)
	@ResponseBody
	public Object validate(@RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
			@RequestParam(value = "id", required = false) Long id) {

		ValidateResponse response = ValidateResponse.newInstance();

		if ("title".equals(fieldId)) {
			Notice notice = getNoticeService().findByTitle(fieldValue);
			if (notice == null || (notice.getId().equals(id) && notice.getTitle().equals(fieldValue))) {
				// 如果msg 不为空 将弹出提示框
				response.validateSuccess(fieldId, "");
			} else {
				response.validateFail(fieldId, "标题已存在");
			}
		}

		return response.result();
	}

	@RequestMapping(value = "{id}/detail")
	public String index(@PathVariable(value = "id") Notice notice,  Model model){
		model.addAttribute("title", notice.getTitle());
		model.addAttribute("detail", notice.getContent());
		return viewName("detail");
	}
}
