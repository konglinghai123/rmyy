package com.ewcms.yjk.re.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ewcms.common.Constants;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.common.utils.MessageUtils;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.ReviewProcessService;
import com.ewcms.yjk.re.zd.service.ReviewBaseRuleService;

@Controller
@RequestMapping(value = "/yjk/re/reviewprocess")
public class ReviewProcessController extends BaseSequenceMovableController<ReviewProcess, Long> {

	private ReviewProcessService getReviewProcessService() {
		return (ReviewProcessService) baseService;
	}

	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private ReviewBaseRuleService reviewBaseRuleService;

	public ReviewProcessController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("yjk:reviewprocess");
	}

	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("baseRuleList", reviewBaseRuleService.findAll());
	}

	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{reviewMainId}/index")
	public String index(@PathVariable(value = "reviewMainId") ReviewMain reviewMain, Model model) {
		model.addAttribute("reviewMainId", reviewMain.getId());
		return super.index(model);
	}

	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{reviewMainId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter,
			@PathVariable(value = "reviewMainId") Long reviewMainId, Model model) {
		Map<String, Object> parameters = searchParameter.getParameters();
		parameters.put("EQ_reviewMain.id", reviewMainId);
		searchParameter.setParameters(parameters);

		Map<String, Direction> sorts = searchParameter.getSorts();
		sorts.put("weight", Direction.ASC);
		searchParameter.setSorts(sorts);

		return super.query(searchParameter, model);
	}

	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{reviewMainId}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "reviewMainId") Long reviewMainId, Model model,
			@RequestParam(required = false) List<Long> selections) {
		return super.showSaveForm(model, selections);
	}

	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, ReviewProcess m, BindingResult result,
			@RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{reviewMainId}/save", method = RequestMethod.POST)
	public String save(@PathVariable(value = "reviewMainId") Long reviewMainId, Model model,
			@Valid @ModelAttribute("m") ReviewProcess m, BindingResult result,
			@RequestParam(required = false) List<Long> selections, @CurrentUser User user, RedirectAttributes redirectAttributes) {
		setCommonData(model);

		if (hasError(m, result)) {
			return showSaveForm(reviewMainId, model, selections);
		}

		ReviewMain reviewMain = reviewMainService.findOne(reviewMainId);
		reviewMain.addReviewProcess(m);

		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
			if (permissionList != null) {
				this.permissionList.assertHasUpdatePermission();
			}

			ReviewProcess lastM = getReviewProcessService().update(m, user.getId());
			if (lastM == null) {
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改的评审规则已存在，不能修改");
				return redirectToUrl(viewName(m.getReviewMain().getId() + "/save"));
			}
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
			if (permissionList != null) {
				this.permissionList.assertHasCreatePermission();
			}

			ReviewProcess lastM = getReviewProcessService().save(m, user.getId());

			if (lastM == null) {
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, "新增的评审规则已存在，不能增加");
				return redirectToUrl(viewName(m.getReviewMain().getId() + "/save"));
			}
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}

		return showSaveForm(reviewMainId, model, selections);
	}

	@RequestMapping(value = "delete/discard")
	@ResponseBody
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "down/discard")
	@ResponseBody
	@Override
	public AjaxResponse down(Long fromId, Long toId) {
		throw new RuntimeException("discarded method");
	}
	
    @RequestMapping(value = "{fromId}/{toId}/down")
    @ResponseBody
    public AjaxResponse down(@PathVariable("fromId") Long fromId, @PathVariable("toId") Long toId, @CurrentUser User user) {
        if (this.permissionList != null) {
            this.permissionList.assertHasEditPermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("移动位置成功");
        try {
        	getReviewProcessService().down(fromId, toId, user.getId());
        } catch (IllegalStateException e) {
            ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage(MessageUtils.message("move.not.enough"));
        }
        return ajaxResponse;
	}

	@RequestMapping(value = "{reviewMainId}/validate", method = RequestMethod.GET)
	@ResponseBody
	public Object validate(@PathVariable(value = "reviewMainId") Long reviewMainId,
			@RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
			@RequestParam(value = "id", required = false) Long id) {

		ValidateResponse response = ValidateResponse.newInstance();

		if ("reviewBaseRule".equals(fieldId)) {
			ReviewProcess reviewProcess = getReviewProcessService().findByReviewMainIdAndReviewBaseRuleId(reviewMainId,
					Long.valueOf(fieldValue));
			if (reviewProcess == null || (reviewProcess.getId().equals(id)
					&& reviewProcess.getReviewBaseRule().getId().equals(Long.valueOf(fieldValue)))) {
				// 如果msg 不为空 将弹出提示框
				response.validateSuccess(fieldId, "");
			} else {
				response.validateFail(fieldId, "在此评审流程中已使用了本次规则，请选择其他的规则");
			}
		}

		return response.result();
	}
	
	@RequestMapping(value = "{reviewMainId}/changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(@PathVariable(value = "reviewMainId") Long reviewMainId, @PathVariable("newStatus") Boolean newStatus,
			@RequestParam("selections") List<Long> selections,
			@RequestParam("reason") String reason, @CurrentUser User opUser) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			getReviewProcessService().changeStatus(opUser.getId(), selections, newStatus, reason);
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}

}
