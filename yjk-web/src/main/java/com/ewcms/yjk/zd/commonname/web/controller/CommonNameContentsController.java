package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}
	
	public List<CommonNameContents> findByCommonParse(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		List<String> ruleNames = commonNameRuleService.findRuleNameOrderByWeight();
		
		for (String ruleName : ruleNames) {
			searchParameter.getParameters().put("LIKE_" + ruleName, searchParameter.getParameters().get(ruleName));
		}
		
		Map<String, Object> map = super.query(searchParameter, model);
		
		return (List<CommonNameContents>)map.get("rows");
		
	}
}
