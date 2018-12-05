package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
	}
	
	/**
	 * 新药填写时根据填写信息匹配大目录的相应记录
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/querydeclare")
	@ResponseBody
	public List<CommonNameContents> queryDeclare(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		searchParameter.getSorts().put("updateDate", Direction.DESC);
		searchParameter.getParameters().put("EQ_deleted", Boolean.FALSE);
		searchParameter.getParameters().put("EQ_declared", Boolean.TRUE);
		
		Map<String, Object> map = getCommonNameContentsService().query(searchParameter);
		String duplicateColumn = commonNameRuleService.findByDeletedFalseAndEnabledTrueOrderByWeightAsc()
				.get(Integer.parseInt(searchParameter.getParameters().get("objIndex").toString()))
				.getRuleName();
		if(duplicateColumn.equals("common.drugCategory")){
			return removeDrugCategoryDuplicateOrder((List<CommonNameContents>) map.get("rows"));
		}else if(duplicateColumn.equals("administration.id")){
			return removeAdministrationDuplicateOrder((List<CommonNameContents>) map.get("rows"));
			
		}else{
			return DuplicateRemovalUtil.removeDuplicateOrder((List<CommonNameContents>) map.get("rows"),duplicateColumn);
		}
	}
	
	@RequestMapping(value = "/querydeclarebyspell")
	@ResponseBody
	public List<CommonNameContents> queryDeclareBySpell(@RequestParam(value="spell") String spell) {
		if(spell==null||spell.length()==0) return Lists.newArrayList();
		spell = spell.toLowerCase();
		List<CommonNameContents> commonNameContentsList = getCommonNameContentsService().findCommonNameContentsBySpell(spell);
		if(commonNameContentsList!=null&&commonNameContentsList.size()>1){
			return removeExtractCommonNameDuplicateOrder(commonNameContentsList);
		}
		return commonNameContentsList;
	}


    /**
     * 去重 药品类别
     * 
     * @param orderList
     * @return
     * @author jqlin
     */
    private static List<CommonNameContents> removeDrugCategoryDuplicateOrder(List<CommonNameContents> orderList) {
        Set<CommonNameContents> set = new TreeSet<CommonNameContents>(new Comparator<CommonNameContents>() {
            @Override
            public int compare(CommonNameContents a, CommonNameContents b) {
                // 字符串则按照asicc码升序排列
                return a.getCommon().getDrugCategory().compareTo(b.getCommon().getDrugCategory());
            }
        });
        
        set.addAll(orderList);
        return new ArrayList<CommonNameContents>(set);
    } 
    /**
     * 去重 提取通用名
     * 
     * @param orderList
     * @return
     * @author jqlin
     */
    private static List<CommonNameContents> removeExtractCommonNameDuplicateOrder(List<CommonNameContents> orderList) {
        Set<CommonNameContents> set = new TreeSet<CommonNameContents>(new Comparator<CommonNameContents>() {
            @Override
            public int compare(CommonNameContents a, CommonNameContents b) {
                // 字符串则按照asicc码升序排列
                return a.getCommon().getCommonName().compareTo(b.getCommon().getCommonName());
            }
        });
        
        set.addAll(orderList);
        return new ArrayList<CommonNameContents>(set);
    }    
    
    /**
     * 去重给药途径
     * 
     * @param orderList
     * @return
     * @author jqlin
     */
    private static List<CommonNameContents> removeAdministrationDuplicateOrder(List<CommonNameContents> orderList) {
        Set<CommonNameContents> set = new TreeSet<CommonNameContents>(new Comparator<CommonNameContents>() {
            @Override
            public int compare(CommonNameContents a, CommonNameContents b) {
                // 字符串则按照asicc码升序排列
                return a.getAdministration().getId().compareTo(b.getAdministration().getId());
            }
        });
        
        set.addAll(orderList);
        return new ArrayList<CommonNameContents>(set);
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
	
	@RequestMapping(value = "/filterdeclared")
	public String filterDeclared() {
		return viewName("filterdeclared");
	}

	@RequestMapping(value = "/savefilterdeclared", method = RequestMethod.POST)
	@ResponseBody
	public String savefilterDeclared(@RequestParam(value = "projectDeclareds", required = false) List<String> projectDeclareds,
			HttpServletRequest request) {
		String message = "过滤大目录成功！";
		try {
			getCommonNameContentsService().filterDeclaredByProjectName(projectDeclareds);
		} catch (Exception e) {
			message = "过滤大目录失败！";
		}

		return message;
	}
}
