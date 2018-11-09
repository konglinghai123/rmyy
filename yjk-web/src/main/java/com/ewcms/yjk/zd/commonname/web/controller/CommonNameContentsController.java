package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.service.CommonNameContentsService;
import com.ewcms.yjk.zd.commonname.service.CommonNameRuleService;
import com.ewcms.yjk.zd.commonname.util.DuplicateRemovalUtil;
import com.google.common.collect.Lists;

/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/zd/commonnamecontents")
public class CommonNameContentsController extends BaseCRUDController<CommonNameContents, Long> {
	@Autowired
	private CommonNameRuleService commonNameRuleService;
	
	private CommonNameContentsService getCommonNameContentsService() {
		return (CommonNameContentsService) baseService;
	}

	public CommonNameContentsController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("yjk:commonnamecontents");
	}

	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		// model.addAttribute("booleanList", BooleanEnum.values());
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/querydeclare")
	@ResponseBody
	public List<CommonNameContents> queryDeclare(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		searchParameter.getSorts().put("updateDate", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		Map<String, Object> map = getCommonNameContentsService().query(searchParameter);
		return DuplicateRemovalUtil.removeDuplicateOrder((List<CommonNameContents>) map.get("rows"),
				commonNameRuleService.findByDeletedFalseAndEnabledTrueOrderByWeightAsc()
						.get(Integer.parseInt(searchParameter.getParameters().get("objIndex").toString()))
						.getRuleName());
	}

	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}

	@RequestMapping(value = "{commonNameContentsId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "commonNameContentsId")Long commonNameContentsId, Model model){
		Pageable pageable = new PageRequest(searchParameter.getPage() - 1, searchParameter.getRows());
		
		Page<CommonNameContents> pages = getCommonNameContentsService().matchByCommonNameContentsId(commonNameContentsId, pageable);
		
		Map<String, Object> queryMap = new HashMap<String, Object>(2);
		
		queryMap.put("total", pages.getTotalElements());
		queryMap.put("rows", pages.getContent());
		
		return queryMap;
	}  
	
	@RequestMapping(value = "queryadministration")
	@ResponseBody
	public List<Administration> queryAdministration(@RequestParam(value = "commonName") String commonName, Model model) {
		List<Administration> admList = getCommonNameContentsService().findAdministrationByCommonName(commonName);
		return admList;
	}

	@RequestMapping(value = "/import")
	public String importStudent() {
		return viewName("import");
	}

	@RequestMapping(value = "/saveimport", method = RequestMethod.POST)
	@ResponseBody
	public String saveImportStudent(@RequestParam(value = "excelFile", required = false) MultipartFile excelFile,
			@RequestParam(value = "isDisabledOriginalData", required = false) Boolean isDisabledOriginalData,
			HttpServletRequest request) {
		List<Integer> noSave = Lists.newArrayList();
		String message = "导入信息";
		try {
			request.setCharacterEncoding("UTF-8");
			if (excelFile != null && !excelFile.isEmpty()) {
				noSave = getCommonNameContentsService().importExcel(excelFile.getInputStream(),isDisabledOriginalData);
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
