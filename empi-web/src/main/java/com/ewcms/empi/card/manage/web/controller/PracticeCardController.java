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
import com.ewcms.common.exception.BaseException;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.entity.PracticeCardStatus;
import com.ewcms.empi.card.manage.entity.Sex;
import com.ewcms.empi.card.manage.service.PracticeCardService;
import com.ewcms.empi.dictionary.service.CertificateTypeService;
import com.ewcms.empi.dictionary.service.NationService;
import com.ewcms.empi.system.service.ParameterSetService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.empi.card.manage.entity.PracticeCardDepositOperate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	@Autowired
	private ParameterSetService parameterSetService;
	
    public PracticeCardController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("card:practicecard");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("depositParameterValue", parameterSetService.findOne("001").getVariableValue());
        model.addAttribute("sexList", Sex.values());
        model.addAttribute("depositOperateList", PracticeCardDepositOperate.values());
        model.addAttribute("statusList", PracticeCardStatus.values());
        model.addAttribute("nationList", nationService.findAll(new Sort("id")));
        model.addAttribute("certificateTypeList", certificateTypeService.findAll(new Sort("id")));
    }
    
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,Model model) {
		if (!searchParameter.getSelections().isEmpty()){
			searchParameter.getParameters().put("IN_id", searchParameter.getSelections());
		} else if (searchParameter.isParameterEmpty()){
			searchParameter.getParameters().put("EQ_id", -9999);
		}
		
		searchParameter.getSorts().put("id", Direction.DESC);
		
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "indexcombine", method = RequestMethod.GET)
	public String indexcompine(Model model) {
		if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
		model.addAttribute("practiceCardId", -1);
		setCommonData(model);
		return viewName("combine");
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
	
	@RequestMapping(value = "{practiceCardId}/querycombine")
	@ResponseBody
	public Map<String, Object> queryCombine(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "practiceCardId")Long practiceCardId, Model model){
		searchParameter.getParameters().put("EQ_id", practiceCardId);
		return super.query(searchParameter, model);
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
	public String distribute(Model model, @Valid @ModelAttribute("m") PracticeCard m, BindingResult result, @RequestParam(required = false) List<Long> selections,@CurrentUser User opUser) {
		if (hasError(m, result)) {
            return showSaveFormDistribute(model, selections);
        }

		setCommonData(model);
	    if (permissionList != null) {
	    	this.permissionList.assertHasCreatePermission();
	    }
		PracticeCard lastM = getPracticeCardService().distribute(m, opUser.getId()); 
		if(EmptyUtil.isNull(selections))selections = Lists.newArrayList();
		
		model.addAttribute("selections", selections.add(lastM.getId()));
		model.addAttribute("m", newModel());
		model.addAttribute("lastM", JSON.toJSONString(lastM));	
		model.addAttribute("operate", "add");	
		return showSaveFormDistribute(model, selections);
	}	

	@RequestMapping(value = "loss")
	@ResponseBody
	public AjaxResponse loss(@RequestParam(required = false) List<Long> selections,@CurrentUser User opUser){
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
	public Map<String, Object> invalid(@RequestParam(required = false) Long selections,@CurrentUser User opUser){
		Map<String, Object> resultMap = Maps.newHashMap();
        
        try{
	        if (selections != null){
	        	resultMap = getPracticeCardService().invalid(selections, opUser.getId());
			}
        }catch (BaseException e){
        	resultMap.put("success", Boolean.FALSE);
        	resultMap.put("message", e.getMessage());
        }
		return resultMap;
	}
	
	@RequestMapping(value = "combine")
	@ResponseBody
	public Map<String, Object> combine(@RequestParam(required = false) List<Long> selections,@RequestParam(required = false)String practiceNo,@CurrentUser User opUser){
		Map<String, Object> resultMap = Maps.newHashMap();
        try{
	        if (selections != null && !selections.isEmpty() && practiceNo != null && practiceNo.length()>0){
	        	resultMap = getPracticeCardService().combine(selections, practiceNo, opUser.getId());
			}
        } catch (BaseException e){
        	resultMap.put("success", Boolean.FALSE);
        	resultMap.put("message", e.getMessage());
        }
        return resultMap;
	}	
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("practiceNo".equals(fieldId)) {
    	   PracticeCard parcticeCard = getPracticeCardService().findByPracticeNo(fieldValue);
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
