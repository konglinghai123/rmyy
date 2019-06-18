package com.ewcms.yjk.sb.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.service.UserService;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.service.DrugFormService;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.ewcms.yjk.zd.commonname.service.CommonNameRuleService;

/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/sb/initaudit")
public class InitAuditController extends BaseCRUDController<DrugForm, Long> {
	@Autowired
	private CommonNameRuleService commonNameRuleService;
	@Autowired
	private UserService userService;
	@Autowired
	private SystemParameterService systemParameterService;
	
	private DrugFormService getDrugFormService() {
		return (DrugFormService) baseService;
	}

	public InitAuditController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("yjk:initaudit");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("stateList",AuditStatusEnum.values());
		//model.addAttribute("userList",userService.findAll());
		model.addAttribute("systemParameterList", systemParameterService.findAll());
		model.addAttribute("commonNameRuleList",commonNameRuleService.findByDeletedFalseAndEnabledTrueOrderByWeightAsc());
	}
	
	@RequestMapping(value = "{commonNameContentsId}/detail")
	public String index(@PathVariable(value = "commonNameContentsId") Long commonNameContentsId) {
		return viewName("detail");
	}

	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		if(searchParameter.getParameters().size() == 0){
			searchParameter.getParameters().put("EQ_auditStatus",AuditStatusEnum.init);
		}
		if (searchParameter.getParameters().get("EQ_auditStatus").equals("nodeclare")) {
			searchParameter.getParameters().put("EQ_declared", Boolean.FALSE);
		}else{
			searchParameter.getParameters().put("EQ_declared", Boolean.TRUE);
		}
		
		searchParameter.getParameters().put("EQ_reviewed", Boolean.FALSE);
		
		if (EmptyUtil.isStringEmpty(searchParameter.getParameters().get("EQ_systemParameterId"))) {
			SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
			if (systemParameter != null) {
				searchParameter.getParameters().put("EQ_systemParameterId", systemParameter.getId());
			} else {
				searchParameter.getParameters().put("EQ_systemParameterId", -1L);
			}
		}
		
		searchParameter.getSorts().put("userId", Direction.DESC);
		searchParameter.getSorts().put("auditStatus", Direction.ASC);
		return super.query(searchParameter, model);
	}

	@RequestMapping(value = "save/discard")
	@Override
	public String save(Model model, DrugForm m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}	
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Model model, @RequestParam(required = false) List<Long> selections,
			@RequestParam(required = false) Boolean isAuditPassed,
			@RequestParam(required = false) String remark,RedirectAttributes redirectAttributes) {
		try{
			if(EmptyUtil.isCollectionNotEmpty(selections)){
				getDrugFormService().initAudit(selections, isAuditPassed, remark);
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, "初审成功");
			}
		}catch(Exception e){
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "初审失败");
		}
		return redirectToUrl(viewName("save"));
	}
}
