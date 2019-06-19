package com.ewcms.yjk.re.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.service.DrugFormService;

@Controller
@RequestMapping(value = "/yjk/re/voterecord")
public class VoteRecordController extends BaseCRUDController<VoteRecord, Long> {
	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private DrugFormService drugFormService;
	
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,	Model model) {
		searchParameter.getParameters().put("EQ_auditStatus",AuditStatusEnum.passed);
		searchParameter.getParameters().put("EQ_reviewed", Boolean.FALSE);
		searchParameter.getParameters().put("EQ_declareCategory","新增通用名");
		ReviewMain reviewMainEnable = reviewMainService.findByEnabledTrue();
		if(reviewMainEnable != null){
			if(reviewMainEnable.getSystemParameter() != null){
			searchParameter.getParameters().put("EQ_systemParameterId",reviewMainEnable.getSystemParameter().getId());
			}else{
				searchParameter.getParameters().put("EQ_systemParameterId",5);
			}
		}
		searchParameter.getSorts().put("commonNameContents.common.drugCategory", Direction.ASC);
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, DrugForm.class);
		Page<DrugForm> drubFormList= drugFormService.findAll(searchable);
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", drubFormList.getContent().size());
		resultMap.put("rows", drubFormList.getContent());
		return resultMap;
	}

}
