package com.ewcms.yjk.sp.web.controler;

import java.util.Date;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.yjk.sp.entity.SystemParameter;

/**
 * 
 * @author wuzhijun
 *
 */
@Controller
@RequestMapping(value = "/yjk/sp/systemparamter")
public class SystemParameterController extends BaseCRUDController<SystemParameter, Long> {
    public SystemParameterController() {
    	setListAlsoSetCommonData(true);
        setResourceIdentity("yjk:systemparamter");
    }
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "{systemParameterId}/delete")
	@ResponseBody
	public AjaxResponse delete(@PathVariable(value = "systemParameterId") Long systemParameterId) {
		AjaxResponse ajaxResponse = new AjaxResponse("删除成功");
		try{
			SystemParameter vo = baseService.findOne(systemParameterId);
			if(vo != null){
				 if(vo.getApplyEndDate().after(new Date())){
					 baseService.delete(systemParameterId);
					 return ajaxResponse;
				 }
			}
		} catch(Exception e){
		}
		ajaxResponse.setSuccess(Boolean.FALSE);
		ajaxResponse.setMessage("删除失败");
		return ajaxResponse;
	}
}
