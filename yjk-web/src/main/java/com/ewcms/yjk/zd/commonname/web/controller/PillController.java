package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.Pill;

/**
 *@author zhoudongchu
 */

@Controller
@RequestMapping(value = "/yjk/zd/pill")
public class PillController extends BaseCRUDController<Pill, Long> {
	
    public PillController() {
        setResourceIdentity("yjk:pill");
    }
    
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}
}
