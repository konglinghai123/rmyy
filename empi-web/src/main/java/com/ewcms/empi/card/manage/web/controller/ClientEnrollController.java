package com.ewcms.empi.card.manage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.card.manage.entity.ClientEnroll;
import com.ewcms.empi.card.manage.entity.HapiVersion;
import com.ewcms.empi.card.manage.service.ClientEnrollService;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/clientenroll")
public class ClientEnrollController extends	BaseCRUDController<ClientEnroll, Long> {
	private ClientEnrollService getClientEnrollService(){
		return (ClientEnrollService) baseService;
	}
	
    public ClientEnrollController() {
        setListAlsoSetCommonData(true);
//        setResourceIdentity("system:icon");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("hapiVersionList", HapiVersion.values());
    }
    
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("ip".equals(fieldId)) {
    	   ClientEnroll clientEnroll = getClientEnrollService().findByIp(fieldValue);
            if (clientEnroll == null|| (clientEnroll.getId().equals(id) && clientEnroll.getIp().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "IP地址已存在");
            }
        }
       
       if ("userName".equals(fieldId)) {
    	   ClientEnroll clientEnroll = getClientEnrollService().findByUserName(fieldValue);
            if (clientEnroll == null|| (clientEnroll.getId().equals(id) && clientEnroll.getUserName().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "用户名已存在");
            }
        }
        return response.result();
    }
}
