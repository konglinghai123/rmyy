package com.ewcms.yjk.sb.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.entity.SbState;
import com.ewcms.yjk.sb.service.DrugFormService;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.ewcms.yjk.zd.commonname.service.CommonNameRuleService;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/sb/drugform")
public class DrugFormController extends BaseCRUDController<DrugForm, Long> {
	@Autowired
	private CommonNameRuleService commonNameRuleService;
	@Autowired
	private UserService userService;
	@Autowired
	private SystemParameterService systemParameterService;
	
	private DrugFormService getDrugFormService() {
		return (DrugFormService) baseService;
	}
	
	public DrugFormController() {
		setResourceIdentity("sb:drugform");
		setListAlsoSetCommonData(true);
		setResourceIdentity("sb:drugform");
	}
	
    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("isOpenDeclare", systemParameterService.isOpenDrugDeclare());
        model.addAttribute("stateList", SbState.values());
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("commonNameRuleList", commonNameRuleService.findByDeletedFalseOrderByWeightAsc());
    }

	
    @RequestMapping(value = "querybyuser")
    @ResponseBody
	public Map<String, Object> queryByUser(@CurrentUser User user,@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		searchParameter.getSorts().put("id", Direction.DESC);
		if (!user.getAdmin()) {
			searchParameter.getParameters().put("EQ_userId", user.getId());
		}
		Map<String, Object> queryObj = super.query(searchParameter, model);
		return queryObj;
	}

	@RequestMapping(value = "drugdeclare", method = RequestMethod.POST)
	public String drugDeclare(@CurrentUser User user,Model model, @Valid @ModelAttribute("m")DrugForm m, BindingResult result,
			 @RequestParam(required = false) List<Long> selections) {
		if (hasError(m, result)) {
            return showSaveForm(model, selections);
        }

		setCommonData(model);
		
        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }
        
		DrugForm lastM = getDrugFormService().drugDeclare(user, m.getCommonNameContents());
		
		if(lastM == null){
			result.rejectValue("commonNameContents.common.commonName","","新药已超过限数，不能申报");
		}
		model.addAttribute("m", newModel());
		
		model.addAttribute("lastM", JSON.toJSONString(lastM));
		
		return showSaveForm(model, selections);
	}
	
	@RequestMapping(value = "{commonNameContentsId}/detail")
	public String index(@PathVariable(value = "commonNameContentsId")Long commonNameContentsId, Model model){
		return viewName("detail");
	}
	
	
}
