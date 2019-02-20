package com.ewcms.hzda.zd.web.controller;

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
import com.ewcms.hzda.zd.entity.Nation;
import com.ewcms.hzda.zd.service.NationService;

@Controller
@RequestMapping(value = "/hzda/zd/nation")
public class NationController extends BaseCRUDController<Nation, Long> {

	private NationService getNationService() {
		return (NationService) baseService;
	}
	
	private NationController() {
		setResourceIdentity("hzda:nation");
	}
	
	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<Nation> canUseNation(){
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
    	   List<Nation> nationList =  getNationService().findByName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(nationList)){
	   			for(Nation nation : nationList){
	   				if(nation .getId().equals(id) && nation.getName().equals(fieldValue)){
	   					exist = Boolean.FALSE;
	   				}
	   			}
			}else{
				exist = Boolean.FALSE;
			}
	   		
            if (exist) {
                response.validateFail(fieldId, "民族名称已存在");
            } else {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            }
        }
        return response.result();
    }
}
