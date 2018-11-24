package com.ewcms.yjk.zd.commonname.web.controller;

import com.ewcms.common.Constants;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
	

	@RequestMapping(value = "save/discard")
	@Override
	public String save(Model model, @Valid @ModelAttribute("m")CommonName m, BindingResult result,@RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m")CommonName m, BindingResult result,@RequestParam(required = false) List<Long> selections,RedirectAttributes redirectAttributes) {
    	List<CommonName> commonNames = getCommonNameService().findByCommonNameAndNumberAndAdministrationIdAndDrugCategoryAndDeletedFalse(m.getCommonName(),m.getNumber(), m.getAdministration().getId(),m.getDrugCategory());
		if (EmptyUtil.isNull(m.getId())) {
			if (EmptyUtil.isCollectionEmpty(commonNames)) {
				return super.save(model, m, result, selections);
			}else{
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, "通用名已存在，不能保存入库！");
				return redirectToUrl(viewName("save"));
			}
		} else {
			if (EmptyUtil.isCollectionNotEmpty(commonNames)) {
				CommonName commonName = commonNames.get(0);
				if (m.getId().longValue() == commonName.getId().longValue()) {
					return super.save(model, m, result, selections);
				}else{
					redirectAttributes.addFlashAttribute(Constants.MESSAGE, "通用名已存在，不能保存入库！");
					return redirectToUrl(viewName("save"));
				}
			} else {
				return super.save(model, m, result, selections);
			}
		}
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
    
//	@RequestMapping(value = "export")
//	public void saveExportStudent(HttpServletResponse response) {
//		OutputStream os = null;
//		try {
//			os = response.getOutputStream();
//			response.reset();
//			response.setHeader("Content-disposition", "attachment; filename=" + (new Date()) + "_通用名.xls");
//			response.setContentType("application/msexcel");
//
//			getCommonNameService().writeExcel(os);
//			
//			os.close(); // 关闭流
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		} finally {
//			IOUtils.close(os);
//		}
//	}
}
