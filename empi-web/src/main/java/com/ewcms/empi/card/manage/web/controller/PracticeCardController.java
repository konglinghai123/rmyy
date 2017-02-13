package com.ewcms.empi.card.manage.web.controller;

import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.service.PracticeCardService;
/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/practicecard")
public class PracticeCardController extends BaseCRUDController<PracticeCard, Long> {
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
