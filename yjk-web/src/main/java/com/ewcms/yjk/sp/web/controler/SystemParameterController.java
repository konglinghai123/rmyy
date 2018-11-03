package com.ewcms.yjk.sp.web.controler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.dictionary.entity.Appointment;
import com.ewcms.security.dictionary.entity.DepartmentAttribute;
import com.ewcms.security.dictionary.entity.Profession;
import com.ewcms.security.dictionary.entity.TechnicalTitle;
import com.ewcms.security.dictionary.service.AppointmentService;
import com.ewcms.security.dictionary.service.DepartmentAttributeService;
import com.ewcms.security.dictionary.service.ProfessionService;
import com.ewcms.security.dictionary.service.TechnicalTitleService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;

/**
 * 
 * @author wuzhijun
 *
 */
@Controller
@RequestMapping(value = "/yjk/sp/systemparamter")
public class SystemParameterController extends BaseCRUDController<SystemParameter, Long> {
	
	private SystemParameterService getSystemParameterService() {
		return (SystemParameterService) baseService;
	}
	
	@Autowired
	private DepartmentAttributeService departmentAttributeService;
	@Autowired
	private TechnicalTitleService technicalTitleService;
	@Autowired
	private ProfessionService professionService;
	@Autowired
	private AppointmentService appointmentService;
	
    public SystemParameterController() {
    	setListAlsoSetCommonData(true);
        setResourceIdentity("yjk:systemparamter");
    }
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "{systemParameterId}/closedeclare")
	@ResponseBody
	public AjaxResponse closeDeclare(@CurrentUser User user, @PathVariable(value = "systemParameterId") Long systemParameterId) {
		AjaxResponse ajaxResponse = new AjaxResponse("关闭申报成功");
		try{
			getSystemParameterService().closeDeclare(user, systemParameterId);
		} catch(Exception e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("关闭申报失败");
		}
		return ajaxResponse;
	}	
	
	@RequestMapping(value = "{systemParameterId}/opendeclare")
	@ResponseBody
	public AjaxResponse openDeclare(@CurrentUser User user, @PathVariable(value = "systemParameterId") Long systemParameterId) {
		AjaxResponse ajaxResponse = new AjaxResponse("启动申报成功");
		try{
			if(getSystemParameterService().openDeclare(user, systemParameterId) == null){
				ajaxResponse.setMessage("当前时间不在启动时间内");
			}
		} catch(Exception e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("启动申报失败");
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "departmentAttribute/canUse")
	@ResponseBody
	public List<DepartmentAttribute> getDepartmentAttribute(){
		return departmentAttributeService.findAll();
	}
	
	@RequestMapping(value = "profession/canUse")
	@ResponseBody
	public List<Profession> getProfession(){
		return professionService.findAll();
	}
	
	@RequestMapping(value = "technicalTitle/canUse")
	@ResponseBody
	public List<TechnicalTitle> getTechnical(){
		return technicalTitleService.findAll();
	}
	
	@RequestMapping(value = "appointment/canUse")
	@ResponseBody
	public List<Appointment> getAppointment(){
		return appointmentService.findAll();
	}
	/*
	@RequestMapping(value = "{systemParameterId}/delete")
	@ResponseBody
	public AjaxResponse delete(@PathVariable(value = "systemParameterId") Long systemParameterId) {
		AjaxResponse ajaxResponse = new AjaxResponse("删除成功");
		try{
			SystemParameter vo = baseService.findOne(systemParameterId);
			if(vo != null){
				 if(vo.getApplyEndDate().after(new Date())){
					 baseService.delete(systemParameterId);
					 return ajaxResponse;
				 }
			}
		} catch(Exception e){
		}
		ajaxResponse.setSuccess(Boolean.FALSE);
		ajaxResponse.setMessage("删除失败");
		return ajaxResponse;
	}
	*/
}
