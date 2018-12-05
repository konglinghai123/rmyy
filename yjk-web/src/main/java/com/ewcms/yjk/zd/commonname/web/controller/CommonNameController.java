package com.ewcms.yjk.zd.commonname.web.controller;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.ewcms.yjk.zd.commonname.service.AdministrationService;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.google.common.collect.Lists;
import com.ewcms.common.entity.enums.BooleanEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/zd/commonname")
public class CommonNameController extends BaseCRUDController<CommonName, Long> {
	@Autowired
	private AdministrationService administrationService;
	
	private CommonNameService getCommonNameService() {
		return (CommonNameService) baseService;
	}
	
    public CommonNameController() {
    	setListAlsoSetCommonData(true);
        setResourceIdentity("yjk:commonname");
    }
    
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("drugCategoryList", DrugCategoryEnum.values());
		model.addAttribute("booleanList", BooleanEnum.values());
	}
	
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}

	@RequestMapping(value = "findbyspell")
	@ResponseBody
	public List<CommonName> findBySpell(@RequestParam(value="spell", required = false) String spell) {
		if(EmptyUtil.isStringEmpty(spell)) return Lists.newArrayList();
		spell = spell.toLowerCase();
		return getCommonNameService().findCommonNameBySpell(spell);
	}
	
 
    @RequestMapping(value = "import")
	public String importStudent() {
		return viewName("import");
	}
    
    @RequestMapping(value = "saveimport", method = RequestMethod.POST)
    @ResponseBody
	public String saveImportStudent(@RequestParam(value = "excelFile", required = false) MultipartFile excelFile, HttpServletRequest request) {
		List<Integer> noSave = Lists.newArrayList();
		String message = "导入信息";
		try {
			request.setCharacterEncoding("UTF-8");
			if (excelFile != null && !excelFile.isEmpty()) {
				noSave = getCommonNameService().importExcel(excelFile.getInputStream());
			}
		} catch (Exception e) {
			message += "失败";
		}
		if (noSave.isEmpty())
			message += "成功";
		else
			message += "部分成功，不成功的所在Excel行数为：\r\n" + noSave;
		return message;
	}
    
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("matchNumber".equals(fieldId)) {
    	   CommonName vo =  getCommonNameService().findByMatchNumber(fieldValue);
    	   if(vo == null || (vo.getId().equals(id) && vo.getMatchNumber().equals(fieldValue))){
    		   response.validateSuccess(fieldId, "");
   			}else{
   			 response.validateFail(fieldId, "匹配编号已存在！");
   			}
        }

        return response.result();
    }

}
