package com.ewcms.empi.dictionary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.dictionary.entity.Nation;
import com.ewcms.empi.dictionary.service.NationService;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/card/manage/nation")
public class NationController extends BaseCRUDController<Nation, Long> {
	private NationService getNationService(){
		return (NationService) baseService;
	}  
	
	public NationController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("dictionary:nation");
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("name".equals(fieldId)) {
    	   Nation nation = getNationService().findByName(fieldValue);
            if (nation == null|| (nation.getId().equals(id) && nation.getName().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "民族已存在");
            }
        }
        return response.result();
    }
}
