package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.service.HospitalContentsService;
import com.google.common.collect.Lists;

/**
 *@author zhoudongchu
 */

@Controller
@RequestMapping(value = "/yjk/zd/hospitalcontents")
public class HospitalContentsController extends BaseCRUDController<HospitalContents, Long> {
	
	private HospitalContentsService getHospitalContentsService() {
		return (HospitalContentsService) baseService;
	}
	
    public HospitalContentsController() {
    	setListAlsoSetCommonData(true);
        setResourceIdentity("yjk:hospitalcontents");
    }
    
    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        //model.addAttribute("booleanList", BooleanEnum.values());
    }
    
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "{commonNameContentsId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "commonNameContentsId")Long commonNameContentsId, Model model){
		List<HospitalContents> hospitalContentsList = getHospitalContentsService().matchByCommonNameContentsId(commonNameContentsId);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(hospitalContentsList != null){
			queryMap.put("total", hospitalContentsList.size());
			queryMap.put("rows", hospitalContentsList);
		}
		return queryMap;
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
				noSave = getHospitalContentsService().importExcel(excelFile.getInputStream());
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

}
