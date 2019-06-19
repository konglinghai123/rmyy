package com.ewcms.yjk.re.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.VoteRecordService;
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
	
	private VoteRecordService getVoteRecordService() {
		return (VoteRecordService) baseService;
	}
	
	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}


	/**
	 * 投票流程处理
	 * 
	 */
	@RequestMapping(value = "index")
	public String index(@CurrentUser User user,Model model) {
		model.addAttribute("reviewProcessId", getVoteRecordService().ExpertStartVoteAddCommonName(user.getId()));
		return super.index(model);
	}


	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}

	/**
	 * 查询需要投票的申报药品
	 * 
	 */
	@RequestMapping(value = "{reviewProcessId}/query")
	public Map<String, Object> query(@CurrentUser User user,SearchParameter<Long> searchParameter,	Model model,@PathVariable(value = "reviewProcessId") Long reviewProcessId) {
		searchParameter.getParameters().put("EQ_userId", user.getId());
		searchParameter.getParameters().put("EQ_reviewProcessId",reviewProcessId);
		return super.query(searchParameter, model);
	}

}
