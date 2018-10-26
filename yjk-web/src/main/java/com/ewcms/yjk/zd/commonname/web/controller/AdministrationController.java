package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.service.AdministrationService;

@Controller
@RequestMapping(value = "/yjk/zd/administration")
public class AdministrationController extends BaseCRUDController<Administration, Long> {
	
	private AdministrationService getAdministrationService() {
		return (AdministrationService) baseService;
	}

	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<Administration> canUseAdministration() {
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(Direction.ASC, "id");

		return baseService.findAllWithSort(searchable);
	}

	
	@RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("name".equals(fieldId)) {
    	   List<Administration> administrationList =  getAdministrationService().findByName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(administrationList)){
	   			for(Administration administration : administrationList){
	   				if(administration .getId().equals(id) && administration.getName().equals(fieldValue)){
	   					exist = Boolean.FALSE;
	   				}
	   			}
			}else{
				exist = Boolean.FALSE;
			}
	   		
            if (exist) {
                response.validateFail(fieldId, "用药途径名称已存在");
            } else {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            }
        }
        return response.result();
    }
}
