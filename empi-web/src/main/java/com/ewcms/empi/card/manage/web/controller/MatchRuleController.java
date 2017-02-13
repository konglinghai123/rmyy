package com.ewcms.empi.card.manage.web.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.controller.entity.ComboBox;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.service.MatchRuleService;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/matchrule")
public class MatchRuleController extends BaseCRUDController<MatchRule, Long> {
	private MatchRuleService getMatchRuleService(){
		return (MatchRuleService) baseService;
	}  
	
	public MatchRuleController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("card:matchrule");
	}

	@RequestMapping(value = "match", method = RequestMethod.GET)
	public String showSaveFormMatch(Model model) {
		setCommonData(model);
	    if (permissionList != null) {
	        this.permissionList.assertHasCreatePermission();
	    }
	    return viewName("match");
	}
	
	@RequestMapping(value = "match", method = RequestMethod.POST)
	public String match(Model model, @RequestParam(required = false) List<Long> matchFields) {
		setCommonData(model);
	    if (permissionList != null) {
	    	this.permissionList.assertHasCreatePermission();
	    }
	    getMatchRuleService().matchFields(matchFields);
		model.addAttribute("operate", "upd");	
		return viewName("match");
	}	
	
	
	@RequestMapping(value = "readfields")
	public @ResponseBody List<ComboBox> readFields(){
		Iterable<MatchRule> matchRules = baseService.findAll();
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		if (matchRules != null) {
			ComboBox comboBox = null;
			for (MatchRule matchRule : matchRules) {
				comboBox = new ComboBox();
				comboBox.setId(matchRule.getId());
				comboBox.setText(matchRule.getCnName());
				if (matchRule.getMatched())comboBox.setSelected(true);
				comboBoxs.add(comboBox);
			}
		}
		return comboBoxs;
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("fieldName".equals(fieldId)) {
    	   MatchRule matchRule = getMatchRuleService().findByFieldName(fieldValue);
            if (matchRule == null|| (matchRule.getId().equals(id) && matchRule.getFieldName().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "字段已存在");
            }
        }
        return response.result();
    }
}
