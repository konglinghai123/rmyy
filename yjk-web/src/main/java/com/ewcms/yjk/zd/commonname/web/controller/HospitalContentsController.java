package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.service.PillService;

/**
 *@author zhoudongchu
 */

@Controller
@RequestMapping(value = "/yjk/zd/hospitalcontents")
public class HospitalContentsController extends BaseCRUDController<HospitalContents, Long> {
	@Autowired
	private PillService pillService;
	
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
}
