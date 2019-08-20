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
import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.yjk.re.entity.EnsurePassThrough;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.service.EnsurePassThroughService;
import com.ewcms.yjk.re.service.ReviewProcessService;

@Controller
@RequestMapping(value = "/yjk/re/ensurepassthrough")
public class EnsurePassThroughController extends BaseSequenceMovableController<EnsurePassThrough, Long>{

	private EnsurePassThroughService getEnsurePassThroughService() {
		return (EnsurePassThroughService) baseService;
	}
	
	@Autowired
	private ReviewProcessService reviewProcessService;
	
	public EnsurePassThroughController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("yjk:reviewmain");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("booleanList", BooleanEnum.values());
	}
	
	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{reviewProcessId}/index")
	public String index(@PathVariable(value = "reviewProcessId") ReviewProcess reviewProcess, Model model) {
		model.addAttribute("reviewProcessId", reviewProcess.getId());
		return super.index(model);
	}
	
	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{reviewProcessId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "reviewProcessId") Long reviewProcessId, Model model) {
		Map<String, Object> parameters = searchParameter.getParameters();
		parameters.put("EQ_reviewProcess.id", reviewProcessId);
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

	@RequestMapping(value = "{reviewProcessId}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "reviewProcessId")Long reviewProcessId, Model model, @RequestParam(required =  false) List<Long> selections) {
		return super.showSaveForm(model, selections);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") EnsurePassThrough m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{reviewProcessId}/save", method = RequestMethod.POST)
	public String save(@PathVariable(value = "reviewProcessId") Long reviewProcessId, Model model, @Valid @ModelAttribute("m") EnsurePassThrough m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		setCommonData(model);
		
		if (hasError(m, result)) {
			return showSaveForm(reviewProcessId, model, selections);
		}
		
		ReviewProcess reviewProcess = reviewProcessService.findOne(reviewProcessId);
		reviewProcess.addEnsurePassThrough(m);
		
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
			if (permissionList != null) {
				this.permissionList.assertHasUpdatePermission();
			}
			
			EnsurePassThrough lastM = baseService.update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
			if (permissionList != null) {
				this.permissionList.assertHasCreatePermission();
			}
			
			EnsurePassThrough lastM = baseService.save(m);
			
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		
		return showSaveForm(reviewProcessId, model, selections);
	}
	
	@RequestMapping(value = "delete/discard")
	@ResponseBody
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{reviewProcessId}/delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections, @PathVariable(value = "reviewProcessId") Long reviewProcessId) {
		return super.delete(selections);
	}
	
	@RequestMapping(value = "{reviewProcessId}/changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(@PathVariable(value = "reviewProcessId") Long reviewProcessId, @PathVariable("newStatus") Boolean newStatus, @RequestParam("selections") List<Long> selections) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		try {
			getEnsurePassThroughService().changeStatus(selections, newStatus);
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}
}
