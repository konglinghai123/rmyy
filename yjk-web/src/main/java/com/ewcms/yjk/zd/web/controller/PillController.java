package com.ewcms.yjk.zd.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.zd.entity.Pill;
import com.ewcms.yjk.zd.service.PillService;

/**
 *@author zhoudongchu
 */

@Controller
@RequestMapping(value = "/yjk/zd/pill")
public class PillController extends BaseCRUDController<Pill, Long> {
	private PillService getPillService() {
		return (PillService) baseService;
	}
	
    public PillController() {
        setResourceIdentity("yjk:pill");
    }
    
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("pillName".equals(fieldId)) {
    	   List<Pill> pillList =  getPillService().findPillByName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(pillList)){
	   			for(Pill pill:pillList){
	   				if(pill.getId().equals(id) && pill.getPillName().equals(fieldValue)){
	   					exist = Boolean.FALSE;
	   				}
	   			}
			}else{
				exist = Boolean.FALSE;
			}
	   		
            if (exist) {
                response.validateFail(fieldId, "剂型名已存在");
            } else {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            }
        }
        return response.result();
    }	
}
