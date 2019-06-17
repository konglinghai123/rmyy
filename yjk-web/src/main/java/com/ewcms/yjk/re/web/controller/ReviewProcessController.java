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

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.re.entity.ReviewExpert;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.ReviewProcessService;

@Controller
@RequestMapping(value = "/yjk/re/reviewprocess")
public class ReviewProcessController extends BaseSequenceMovableController<ReviewProcess, Long>{

	private ReviewProcessService getReviewProcessService() {
		return (ReviewProcessService) baseService;
	}
	
	@Autowired
	private ReviewMainService reviewMainService;
	
	public ReviewProcessController() {
		setResourceIdentity("yjk:reviewprocess");
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
			@PathVariable(value = "reviewMainId") Long reviewMainId, Model model){
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
	public String showSaveForm(@PathVariable(value = "reviewMainId") Long reviewMainId, Model model, @RequestParam(required = false) List<Long> selections) {
		return super.showSaveForm(model, selections);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, ReviewProcess m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{reviewMainId}/save", method = RequestMethod.POST)
	public String save(@PathVariable(value = "reviewMainId") Long reviewMainId, Model model, @Valid @ModelAttribute("m") ReviewProcess m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
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
			
			ReviewProcess lastM = baseService.update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
			if (permissionList != null) {
				this.permissionList.assertHasCreatePermission();
			}
			
			ReviewProcess lastM = baseService.save(m);
			
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

	@RequestMapping(value = "{reviewMainId}/delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections,
			@PathVariable(value = "reviewMainId") Long reviewMainId) {
		return super.delete(selections);
	}

	@RequestMapping(value = "{reviewMainId}/validate")
	@ResponseBody
	public Object validate(@PathVariable("reviewMainId") ReviewMain reviewMain, @ModelAttribute("m") ReviewExpert m) {
		m.setReviewMain(reviewMain);
		ReviewProcess dbReviewProcess = getReviewProcessService().findByReviewMainAndId(reviewMain, m.getId());

		ValidateResponse response = ValidateResponse.newInstance();
		if (dbReviewProcess == null
				|| (dbReviewProcess.getId().equals(m.getId()) && dbReviewProcess.getReviewMain().equals(reviewMain))) {
			response.validateSuccess("", "");
		} else {
			response.validateFail("", "记录已存在，请重新选择");
		}
		return response.result();
	}
}
