package com.ewcms.empi.card.manage.web.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.Constants;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.exception.BaseException;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.card.manage.entity.HapiOperate;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.entity.MessageLog;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.service.MatchRuleService;
import com.ewcms.empi.card.manage.service.PracticeCardService;
import com.google.common.collect.Lists;
/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/practicecard")
public class PracticeCardController extends BaseCRUDController<PracticeCard, Long> {
	@Autowired
	private MatchRuleService matchRuleService;
	
	private PracticeCardService getPracticeCardService(){
		return (PracticeCardService) baseService;
	}  

	
    public PracticeCardController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("card:practicecard");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
    }
    
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}   
	
	@RequestMapping(value = "{patientBaseInfoId}/detail")
	public String index(@PathVariable(value = "patientBaseInfoId")Long patientBaseInfoId, Model model){
		return this.viewName("detail");
	}
	
	@RequestMapping(value = "{patientBaseInfoId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "patientBaseInfoId")Long patientBaseInfoId, Model model){
		searchParameter.getParameters().put("EQ_patientBaseInfo.id", patientBaseInfoId);
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "autocombine", method = RequestMethod.GET)
	public String autoCombine(Model model) {
		List<MatchRule> matchRuleList = matchRuleService.findMatchRuleByMatched();
		String matchFields = (EmptyUtil.isCollectionNotEmpty(matchRuleList)) ? Collections3.extractToString(matchRuleList, "cnName", ",") : "";
	    model.addAttribute("matchFields", matchFields) ;
	    return viewName("autocombine");
	}
	
	@RequestMapping(value = "autocombine", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse autoCombine(){
        AjaxResponse ajaxResponse = new AjaxResponse("诊疗卡合并成功！");
        
        try{
        	getPracticeCardService().autoCombine();
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("诊疗卡合并失败！");
        }
		return ajaxResponse;
	}
	
	
	@RequestMapping(value = "distribute", method = RequestMethod.GET)
	public String showSaveFormDistribute(Model model, @RequestParam(required = false) List<Long> selections) {
		setCommonData(model);
		
	    if (permissionList != null) {
	        this.permissionList.assertHasCreatePermission();
	    }
	    model.addAttribute("m", newModel());
	    model.addAttribute("selections", selections);
	        
	    return viewName("edit");
	}
	
	@RequestMapping(value = "distribute", method = RequestMethod.POST)
	public String distribute(Model model, @Valid @ModelAttribute("m") PracticeCard m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		if (hasError(m, result)) {
            return showSaveFormDistribute(model, selections);
        }

		setCommonData(model);
	    if (permissionList != null) {
	    	this.permissionList.assertHasCreatePermission();
	    }
	    try{
	    	MessageLog messageLog = new MessageLog();
	    	messageLog.setPracticeNo(m.getPracticeNo());
	    	messageLog.setHapiOperate(HapiOperate.receive);
	    	messageLog.setReceiveDate(Calendar.getInstance().getTime());
			PracticeCard lastM = getPracticeCardService().register(m.getPracticeNo(), m.getPatientBaseInfo(),messageLog); 
			if(EmptyUtil.isNull(selections))selections = Lists.newArrayList();
			
			model.addAttribute("selections", selections.add(lastM.getId()));
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));	
			model.addAttribute("operate", "add");
	    }catch(BaseException e){
	    	model.addAttribute(Constants.ERROR, e.getMessage());
	    	return viewName("edit");
	    }
		return showSaveFormDistribute(model, selections);
	}	
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("practiceNo".equals(fieldId)) {
    	   PracticeCard parcticeCard = getPracticeCardService().findByPracticeNoAndDeleted(fieldValue,Boolean.FALSE);
            if (parcticeCard == null|| (parcticeCard.getId().equals(id) && parcticeCard.getPracticeNo().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "诊疗卡号已存在");
            }
        }
        return response.result();
    }
}
