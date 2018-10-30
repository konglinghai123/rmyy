package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.service.HospitalContentsService;

/**
 *@author zhoudongchu
 */

@Controller
@RequestMapping(value = "/yjk/zd/hospitalcontents")
public class HospitalContentsController extends BaseCRUDController<HospitalContents, Long> {
	private HospitalContentsService getHospitalContentsService() {
		return (HospitalContentsService) baseService;
	}
	
    public HospitalContentsController() {
    	setListAlsoSetCommonData(true);
        setResourceIdentity("yjk:hospitalcontents");
    }
    
    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("booleanList", BooleanEnum.values());
    }
    
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "{commonNameContentsId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "commonNameContentsId")Long commonNameContentsId, Model model){
		List<HospitalContents> hospitalContentsList = getHospitalContentsService().matchByCommonNameContentsId(commonNameContentsId);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(hospitalContentsList != null){
			queryMap.put("total", hospitalContentsList.size());
			queryMap.put("rows", hospitalContentsList);
		}
		return queryMap;
	}
}
