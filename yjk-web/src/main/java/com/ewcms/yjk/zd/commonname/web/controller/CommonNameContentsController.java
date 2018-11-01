package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.service.CommonNameContentsService;
import com.ewcms.yjk.zd.commonname.service.CommonNameRuleService;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
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
		//model.addAttribute("booleanList", BooleanEnum.values());
	}
	
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "querydeclare")
	@ResponseBody
	public List<CommonNameContents> queryDeclare(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		searchParameter.getSorts().put("updateDate", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		Map<String, Object> map = super.query(searchParameter, model);
		return (List<CommonNameContents>)map.get("rows");
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
					noSave = getCommonNameContentsService().importExcel(excelFile.getInputStream());
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
