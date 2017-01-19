package com.ewcms.empi.card.manage.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.entity.PracticeCardStatus;
import com.ewcms.empi.card.manage.entity.Sex;
import com.ewcms.empi.card.manage.service.PracticeCardService;
import com.ewcms.empi.dictionary.service.CertificateTypeService;
import com.ewcms.empi.dictionary.service.NationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.empi.card.manage.entity.PracticeCardDepositOperate;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/practicecard")
public class PracticeCardController extends BaseCRUDController<PracticeCard, Long> {
	private PracticeCardService getPracticeCardService(){
		return (PracticeCardService) baseService;
	}  
	
	@Autowired
	private NationService nationService;
	@Autowired
	private CertificateTypeService certificateTypeService;
	
    public PracticeCardController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("card:practicecard");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("sexs", Sex.values());
        model.addAttribute("depositOperates", PracticeCardDepositOperate.values());
        model.addAttribute("statusList", PracticeCardStatus.values());
        model.addAttribute("nationList", nationService.findAll(new Sort("id")));
        model.addAttribute("certificateTypeList", certificateTypeService.findAll(new Sort("id")));
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
		
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "savedeposit", method = RequestMethod.POST)
	public String saveDeposit(Model model, @Valid @ModelAttribute("m") PracticeCard m, BindingResult result, @RequestParam(required = false) List<Long> selections,@CurrentUser User opUser) {
		if (hasError(m, result)) {
            return showSaveForm(model, selections);
        }

		setCommonData(model);
	    if (permissionList != null) {
	    	this.permissionList.assertHasCreatePermission();
	    }
		PracticeCard lastM = getPracticeCardService().saveDeposit(m, opUser.getId());       
		model.addAttribute("m", newModel());
		model.addAttribute("lastM", JSON.toJSONString(lastM));		
		return showSaveForm(model, selections);
	}	

	@RequestMapping(value = "loss")
	@ResponseBody
	public AjaxResponse loss(@RequestParam(required = false) List<Long> selections,@CurrentUser User opUser){
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("诊疗卡挂失成功！");
        
        try{
	        if (selections != null && !selections.isEmpty()){
	        	getPracticeCardService().loss(selections, opUser.getId());
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("诊疗卡挂失失败！");
        }
		return ajaxResponse;
	}	
	
	@RequestMapping(value = "cancelloss")
	@ResponseBody
	public AjaxResponse cancelLoss(@RequestParam(required = false) List<Long> selections,@CurrentUser User opUser){
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("诊疗卡取消挂失成功！");
        
        try{
	        if (selections != null && !selections.isEmpty()){
	        	getPracticeCardService().cancelLoss(selections, opUser.getId());
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("诊疗卡取消挂失失败！");
        }
		return ajaxResponse;
	}
	
	@RequestMapping(value = "close")
	@ResponseBody
	public AjaxResponse close(@RequestParam(required = false) List<Long> selections,@CurrentUser User opUser){
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("诊疗卡销户成功！");
        
        try{
	        if (selections != null && !selections.isEmpty()){
	        	getPracticeCardService().close(selections, opUser.getId());
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("诊疗卡销户失败！");
        }
		return ajaxResponse;
	}
	
	@RequestMapping(value = "invalid")
	@ResponseBody
	public AjaxResponse invalid(@RequestParam(required = false) List<Long> selections,@CurrentUser User opUser){
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("诊疗卡作废成功！");
        
        try{
	        if (selections != null && !selections.isEmpty()){
	        	getPracticeCardService().invalid(selections, opUser.getId());
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("诊疗卡作废失败！");
        }
		return ajaxResponse;
	}
}
