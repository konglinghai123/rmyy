package com.ewcms.empi.card.manage.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.service.PatientBaseInfoService;
import com.ewcms.empi.dictionary.service.CountryCodeService;
import com.ewcms.empi.dictionary.service.MaritalService;
import com.ewcms.empi.dictionary.service.SexService;
/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/patientbaseinfo")
public class PatientBaseInfoController extends BaseCRUDController<PatientBaseInfo, Long> {
	private PatientBaseInfoService getPatientBaseInfoService(){
		return (PatientBaseInfoService) baseService;
	} 
	@Autowired
	private SexService sexService;
	@Autowired
	private MaritalService maritalService;
	@Autowired
	private CountryCodeService countryCodeService;
	
    public PatientBaseInfoController() {
        setListAlsoSetCommonData(true);
//        setResourceIdentity("system:icon");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("sexList", sexService.findAll(new Sort("id")));
        model.addAttribute("maritalList", maritalService.findAll(new Sort("id")));
        model.addAttribute("countryCodeList", countryCodeService.findAll(new Sort("id")));
    }
    
	
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "readpatient", method = RequestMethod.POST)
	@ResponseBody
	public PatientBaseInfo readPatient(@RequestParam("certificateNo")String certificateNo,@RequestParam("certificateType")String certificateType) {
		List<PatientBaseInfo> patientBaseInfoList =  getPatientBaseInfoService().findByCertificateNoAndCertificateType(certificateNo, certificateType);
		if(EmptyUtil.isCollectionNotEmpty(patientBaseInfoList)){
			return patientBaseInfoList.get(0);
		}
		return null;
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("certificateNo".equals(fieldId)) {
    	   List<PatientBaseInfo> patientBaseInfoList =  getPatientBaseInfoService().findByCertificateNo(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(patientBaseInfoList)){
	   			for(PatientBaseInfo patientBaseInfo:patientBaseInfoList){
	   				if(patientBaseInfo.getId().equals(id) && patientBaseInfo.getCertificateNo().equals(fieldValue)){
	   					exist = Boolean.FALSE;
	   				}
	   			}
			}else{
				exist = Boolean.FALSE;
			}
	   		
            if (exist) {
                response.validateFail(fieldId, "证件号已存在");
            } else {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            }
        }
        return response.result();
    }
}
