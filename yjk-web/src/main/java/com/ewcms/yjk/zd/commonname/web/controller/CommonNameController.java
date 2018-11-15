package com.ewcms.yjk.zd.commonname.web.controller;

import com.alibaba.fastjson.util.IOUtils;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(Direction.ASC, "id");
		model.addAttribute("administrationList", administrationService.findAllWithSort(searchable));
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
	

    @Override
	public String save(Model model, @Valid @ModelAttribute("m")CommonName m, BindingResult result,@RequestParam(required = false) List<Long> selections) {
    	List<CommonName> commonNames = getCommonNameService().findByCommonNameAndNumberAndAdministrationIdAndDrugCategory(m.getCommonName(),m.getNumber(), m.getAdministration().getId(),m.getDrugCategory());
		if (EmptyUtil.isNull(m.getId())) {
			if (EmptyUtil.isCollectionEmpty(commonNames)) {
				return super.save(model, m, result, selections);
			}
		} else {
			if (EmptyUtil.isCollectionNotEmpty(commonNames)) {
				CommonName commonName = commonNames.get(0);
				if (m.getId().longValue() == commonName.getId().longValue()) {
					return super.save(model, m, result, selections);
				}
			} else {
				return super.save(model, m, result, selections);
			}
			
		}
		return showSaveForm(model, selections);
	}

	@RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

       if ("commonName".equals(fieldId)) {
    	   List<CommonName> commonNameList =  getCommonNameService().findByCommonName(fieldValue);
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
    
    @RequestMapping(value = "/import")
	public String importStudent() {
		return viewName("import");
	}
    
    @RequestMapping(value = "/saveimport", method = RequestMethod.POST)
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
    
	@RequestMapping(value = "/export")
	public void saveExportStudent(HttpServletResponse response) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + (new Date()) + "_通用名.xls");
			response.setContentType("application/msexcel");

			getCommonNameService().writeExcel(os);
			
			os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			IOUtils.close(os);
		}
	}
}
