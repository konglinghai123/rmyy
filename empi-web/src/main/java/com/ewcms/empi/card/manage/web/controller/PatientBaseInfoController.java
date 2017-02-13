package com.ewcms.empi.card.manage.web.controller;

import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.service.PatientBaseInfoService;
/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/patientbaseinfo")
public class PatientBaseInfoController extends BaseCRUDController<PatientBaseInfo, Long> {
	private PatientBaseInfoService getPatientBaseInfoService(){
		return (PatientBaseInfoService) baseService;
	} 

    public PatientBaseInfoController() {
        setListAlsoSetCommonData(true);
//        setResourceIdentity("system:icon");
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
	
	@RequestMapping(value = "readpatient", method = RequestMethod.GET)
	@ResponseBody
	public PatientBaseInfo readPatient(@RequestParam("certificateNo")String certificateNo,@RequestParam("certificateType")String certificateType) {
		return getPatientBaseInfoService().findByCertificateNoAndCertificateType(certificateNo, certificateType);
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("certificateNo".equals(fieldId)) {
    	   PatientBaseInfo patientBaseInfo = getPatientBaseInfoService().findByCertificateNo(fieldValue);
            if (patientBaseInfo == null|| (patientBaseInfo.getId().equals(id) && patientBaseInfo.getCertificateNo().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "证件号已存在");
            }
        }
        return response.result();
    }
}
