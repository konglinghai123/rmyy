package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.service.CommonNameRuleService;


/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/zd/commonnamecontents")
public class CommonNameContentsController extends BaseCRUDController<CommonNameContents, Long> {
   
	@Autowired
	private CommonNameRuleService commonNameRuleService;
	
	public CommonNameContentsController() {
        setResourceIdentity("yjk:commonnamecontents");
    }

	@RequestMapping(value = "query1")
	@ResponseBody
	public List<CommonNameContents> findByCommonParse(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		Map<String, Object> map = super.query(searchParameter, model);
		return (List<CommonNameContents>)map.get("rows");
	}
}
