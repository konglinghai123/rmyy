package com.ewcms.empi.dictionary.web.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.dictionary.entity.Marital;
import com.ewcms.empi.dictionary.service.MaritalService;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/dictionary/marital")
public class MaritalController extends BaseCRUDController<Marital, String> {
	private MaritalService getMaritalService(){
		return (MaritalService) baseService;
	} 
	
	@Override
	public String save(Model model, @Valid @ModelAttribute("m")Marital m, BindingResult result, @RequestParam(required = false)List<String> selections) {
		if (hasError(m, result)) {
            return showSaveForm(model, selections);
        }

		setCommonData(model);
		
		if (selections != null && !selections.isEmpty()) {
	        if (permissionList != null) {
	            this.permissionList.assertHasUpdatePermission();
	        }
			
	        Marital lastM = baseService.update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
	        if (permissionList != null) {
	            this.permissionList.assertHasCreatePermission();
	        }
			
	        Marital lastM = baseService.save(m);
	        
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		
		return showSaveForm(model, selections);
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(@RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("id".equals(fieldId)) {
    	   Marital marital = getMaritalService().findOne(fieldValue);
            if (marital == null) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "字段已存在");
            }
        }
        return response.result();
    }
}
