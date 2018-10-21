package com.ewcms.yjk.zd.commonname.web.controller;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/zd/commonname")
public class CommonNameController extends BaseCRUDController<CommonName, Long> {
	private CommonNameService getCommonNameService() {
		return (CommonNameService) baseService;
	}

    public CommonNameController() {
        setResourceIdentity("yjk:commonname");
    }
    
	@RequestMapping(value = "findbyspell", method = RequestMethod.GET)
	@ResponseBody
	public List<CommonName> findBySpell(@RequestParam(value="spell") String spell) {
		if(spell==null||spell.length()==0) return null;
		spell = spell.toLowerCase();
		return getCommonNameService().findCommonNameBySpell(spell);
	}
	
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("commonName".equals(fieldId)) {
    	   List<CommonName> commonNameList =  getCommonNameService().findCommonNameByName(fieldValue);
    	   Boolean exist = Boolean.TRUE;
	   		if(EmptyUtil.isCollectionNotEmpty(commonNameList)){
	   			for(CommonName commonName:commonNameList){
	   				if(commonName.getId().equals(id) && commonName.getCommonName().equals(fieldValue)){
	   					exist = Boolean.FALSE;
	   				}
	   			}
			}else{
				exist = Boolean.FALSE;
			}
	   		
            if (exist) {
                response.validateFail(fieldId, "通用名已存在");
            } else {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            }
        }
        return response.result();
    }
}
