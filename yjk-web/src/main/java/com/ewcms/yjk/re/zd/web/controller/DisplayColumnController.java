package com.ewcms.yjk.re.zd.web.controller;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.re.zd.entity.DisplayColumn;
import com.ewcms.yjk.re.zd.service.DisplayColumnService;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/re/zd/displaycolumn")
public class DisplayColumnController extends BaseCRUDController<DisplayColumn, Long> {
	private DisplayColumnService getDisplayColumnService(){
		return (DisplayColumnService) baseService;
	}
    public DisplayColumnController() {
    	setListAlsoSetCommonData(true);
        setResourceIdentity("yjk:displaycolumn");
    }
    
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
	}
	
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "canUse")
	@ResponseBody
	public List<DisplayColumn> getDisplayColumn(){
		return getDisplayColumnService().findAll();
	}
}
