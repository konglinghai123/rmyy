package com.ewcms.hzda.zd.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.hzda.zd.entity.CertificateType;
import com.ewcms.hzda.zd.service.CertificateTypeService;

@Controller
@RequestMapping(value = "/hzda/zd/certificatetype")
public class CertificateTypeController extends BaseCRUDController<CertificateType, Long> {
	
	private CertificateTypeService getCertificateTypeService() {
		return (CertificateTypeService) baseService;
	}
	
	public CertificateTypeController() {
		setResourceIdentity("hzda:certificatetype");
	}
	
	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<CertificateType> canUseCertificateType(){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(Direction.ASC, "id");
		return baseService.findAllWithSort(searchable);
	}

	@RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("name".equals(fieldId)) {
    	   List<CertificateType> certificateTypeList =  getCertificateTypeService().findByName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(certificateTypeList)){
	   			for(CertificateType certificateType : certificateTypeList){
	   				if(certificateType .getId().equals(id) && certificateType.getName().equals(fieldValue)){
	   					exist = Boolean.FALSE;
	   				}
	   			}
			}else{
				exist = Boolean.FALSE;
			}
	   		
            if (exist) {
                response.validateFail(fieldId, "证件类型名称已存在");
            } else {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            }
        }
        return response.result();
    }
}
