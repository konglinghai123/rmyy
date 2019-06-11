package com.ewcms.yjk.re.zd.web.controller;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.re.zd.entity.DisplayColumn;
import com.ewcms.yjk.re.zd.service.DisplayColumnService;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.google.common.collect.Lists;
import com.ewcms.common.entity.enums.BooleanEnum;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/re/zd/displaycolumn")
public class DisplayColumnController extends BaseCRUDController<DisplayColumn, Long> {
	
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
 
}
