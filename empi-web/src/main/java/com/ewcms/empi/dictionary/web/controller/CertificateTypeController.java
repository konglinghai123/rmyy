package com.ewcms.empi.dictionary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.dictionary.entity.CertificateType;
import com.ewcms.empi.dictionary.service.CertificateTypeService;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/card/manage/certificatetype")
public class CertificateTypeController extends BaseCRUDController<CertificateType, Long> {
	private CertificateTypeService getCertificateTypeService(){
		return (CertificateTypeService) baseService;
	}  
	
	public CertificateTypeController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("dictionary:certificatetype");
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("name".equals(fieldId)) {
        	CertificateType certificateType = getCertificateTypeService().findByName(fieldValue);
            if (certificateType == null|| (certificateType.getId().equals(id) && certificateType.getName().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "证件类型已存在");
            }
        }
        return response.result();
    }
}
