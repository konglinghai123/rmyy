package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;
import com.ewcms.yjk.zd.commonname.service.CommonNameRuleService;

@Controller
@RequestMapping(value = "/yjk/zd/commonnamerule")
public class CommonNameRuleController extends BaseSequenceMovableController<CommonNameRule, Long> {
	private CommonNameRuleService getCommonNameRuleService() {
		return (CommonNameRuleService) baseService;
	}
	
    public CommonNameRuleController() {
        setResourceIdentity("yjk:commonnamerule");
    }
    
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}  
	
	@RequestMapping(value = "{commonNameRuleId}/restore")
	@ResponseBody
	public AjaxResponse restoreCommonName(@PathVariable(value = "commonNameRuleId") Long commonNameRuleId) {
		AjaxResponse ajaxResponse = new AjaxResponse("还原成功");
		try{
			getCommonNameRuleService().restore(commonNameRuleId);
		} catch(IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("还原失败");
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
    	   List<CommonNameRule> commonNameRuleList =  getCommonNameRuleService().findByRuleName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(commonNameRuleList)){
	   			for(CommonNameRule commonNameRule:commonNameRuleList){
	   				if(commonNameRule.getId().equals(id) && commonNameRule.getRuleName().equals(fieldValue)){
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
    	   List<CommonNameRule> commonNameRuleList =  getCommonNameRuleService().findByRuleCnName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(commonNameRuleList)){
	   			for(CommonNameRule commonNameRule:commonNameRuleList){
	   				if(commonNameRule.getId().equals(id) && commonNameRule.getRuleCnName().equals(fieldValue)){
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
