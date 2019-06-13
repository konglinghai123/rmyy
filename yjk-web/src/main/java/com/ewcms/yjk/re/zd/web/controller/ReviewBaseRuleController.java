package com.ewcms.yjk.re.zd.web.controller;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.re.zd.entity.ReviewBaseRule;
import com.ewcms.yjk.re.zd.service.ReviewBaseRuleService;

@Controller
@RequestMapping(value = "/yjk/re/zd/reviebasewrule")
public class ReviewBaseRuleController extends BaseCRUDController<ReviewBaseRule, Long> {
	private ReviewBaseRuleService getReviewRuleService() {
		return (ReviewBaseRuleService) baseService;
	}
	
    public ReviewBaseRuleController() {
        setResourceIdentity("yjk:reviewrule");
    }
    
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}   
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("ruleName".equals(fieldId)) {
    	   List<ReviewBaseRule> reviewRuleList =  getReviewRuleService().findByRuleName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(reviewRuleList)){
	   			for(ReviewBaseRule reviewRule:reviewRuleList){
	   				if(reviewRule.getId().equals(id) && reviewRule.getRuleName().equals(fieldValue)){
	   					exist = Boolean.FALSE;
	   				}
	   			}
			}else{
				exist = Boolean.FALSE;
			}
	   		
            if (exist) {
                response.validateFail(fieldId, "规则名已存在");
            } else {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            }
        }
       
       if ("ruleCnName".equals(fieldId)) {
    	   List<ReviewBaseRule> reviewRuleList =  getReviewRuleService().findByRuleCnName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(reviewRuleList)){
	   			for(ReviewBaseRule reviewRule:reviewRuleList){
	   				if(reviewRule.getId().equals(id) && reviewRule.getRuleCnName().equals(fieldValue)){
	   					exist = Boolean.FALSE;
	   				}
	   			}
			}else{
				exist = Boolean.FALSE;
			}
	   		
            if (exist) {
                response.validateFail(fieldId, "规则中文名已存在");
            } else {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            }
        }
        return response.result();
    }
}
