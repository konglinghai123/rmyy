package com.ewcms.empi.card.manage.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.Sex;
import com.ewcms.empi.card.manage.service.PatientBaseInfoService;
import com.ewcms.empi.card.manage.service.PracticeCardService;
import com.ewcms.empi.dictionary.service.CertificateTypeService;
import com.ewcms.empi.dictionary.service.NationService;
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
	private NationService nationService;
	@Autowired
	private CertificateTypeService certificateTypeService;	
	
    public PatientBaseInfoController() {
        setListAlsoSetCommonData(true);
//        setResourceIdentity("system:icon");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("sexs", Sex.values());
        model.addAttribute("nationList", nationService.findAll(new Sort("id")));
        model.addAttribute("certificateTypeList", certificateTypeService.findAll(new Sort("id")));
    }
    
	
	@RequestMapping(value = "readpatient", method = RequestMethod.GET)
	@ResponseBody
	public PatientBaseInfo readPatient(@RequestParam("certificateNo")String certificateNo,@RequestParam("certificateTypeId")Long certificateTypeId) {
		return getPatientBaseInfoService().findByCertificateNoAndCertificateTypeId(certificateNo, certificateTypeId);
	}
}
