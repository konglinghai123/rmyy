package com.ewcms.yjk.re.zd.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.re.zd.entity.ReviewRule;
import com.ewcms.yjk.re.zd.service.ReviewRuleService;

@Controller
@RequestMapping(value = "/yjk/re/zd/reviewrule")
public class ReviewRuleController extends BaseSequenceMovableController<ReviewRule, Long> {
	private ReviewRuleService getReviewRuleService() {
		return (ReviewRuleService) baseService;
	}
	
    public ReviewRuleController() {
        setResourceIdentity("yjk:reviewrule");
    }
    
	@RequestMapping(value = "{reviewRuleId}/close")
	@ResponseBody
	public AjaxResponse close(@PathVariable(value = "reviewRuleId") Long reviewRuleId) {
		AjaxResponse ajaxResponse = new AjaxResponse("关闭成功");
		try{
			getReviewRuleService().close(reviewRuleId);
		} catch(IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("关闭失败");
		}
		return ajaxResponse;
	}
	@RequestMapping(value = "{reviewRuleId}/open")
	@ResponseBody
	public AjaxResponse open(@PathVariable(value = "reviewRuleId") Long reviewRuleId) {
		AjaxResponse ajaxResponse = new AjaxResponse("启用成功");
		try{
			getReviewRuleService().open(reviewRuleId);
		} catch(IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("启用失败");
		}
		return ajaxResponse;
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("ruleName".equals(fieldId)) {
    	   List<ReviewRule> reviewRuleList =  getReviewRuleService().findByRuleName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(reviewRuleList)){
	   			for(ReviewRule reviewRule:reviewRuleList){
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
    	   List<ReviewRule> reviewRuleList =  getReviewRuleService().findByRuleCnName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(reviewRuleList)){
	   			for(ReviewRule reviewRule:reviewRuleList){
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
