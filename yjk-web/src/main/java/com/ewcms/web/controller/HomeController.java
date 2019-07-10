package com.ewcms.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.XMLUtil;
import com.ewcms.extra.push.PushService;
import com.ewcms.personal.calendar.service.CalendarService;
import com.ewcms.security.resource.entity.Menu;
import com.ewcms.security.resource.service.ResourceService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.generate.entity.ParameterBuilder;
import com.ewcms.system.report.service.TextReportService;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.service.ReviewMainService;
import com.ewcms.yjk.re.service.VoteResultService;
import com.ewcms.yjk.sb.service.DrugFormService;
import com.ewcms.yjk.sp.entity.SystemParameter;
import com.ewcms.yjk.sp.service.SystemParameterService;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
public class HomeController {

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private PushService pushApiService;
	@Autowired
	private CalendarService calendarService;
	@Autowired
	private DrugFormService drugFormService;
	@Autowired
	private SystemParameterService systemParameterService;
	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private VoteResultService voteResultService;
	@Autowired
	private TextReportService textReportService;

	@RequestMapping(value = "home")
	public String home(@CurrentUser User user, Model model) {
		List<Menu> menus = resourceService.findMenus(user);
		model.addAttribute("menus", menus);

		pushApiService.offline(user.getId());

		Subject subject = SecurityUtils.getSubject();
		model.addAttribute("isRunas", subject.isRunAs());
		if (subject.isRunAs()) {
			String previousUsername = (String) subject.getPreviousPrincipals().getPrimaryPrincipal();
			model.addAttribute("previousUsername", previousUsername);
		}
		
		SystemParameter systemParameter = systemParameterService.findByEnabledTrue();
		if (systemParameter != null) model.addAttribute("systemParameterId", systemParameter.getId());
		
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain != null) model.addAttribute("reviewMainId", reviewMain.getId());
		
		// 最近3天的日历
		model.addAttribute("calendarCount", calendarService.countRecentlyCalendar(user.getId(), 2));
		// 所有新药申报
		model.addAttribute("systemParameterList", systemParameterService.findAll(new Sort(new Order(Direction.DESC, "id"))));
		// 所有新药评审
		model.addAttribute("reviewMainList", reviewMainService.findAll(new Sort(new Order(Direction.DESC, "id"))));

		return "home";
	}

	@RequestMapping(value = "{systemParameterId}/drugFormCountReport")
	@ResponseBody
	public Map<String, Object> findDrugFormCountReport(@CurrentUser User user,
			@PathVariable(value = "systemParameterId") Long systemParameterId,
			@ModelAttribute SearchParameter<Long> searchParameter) {
		return drugFormService.findDrugFormCount(user, systemParameterId, searchParameter);
	}

	@RequestMapping(value = "{systemParameterId}/drugFormStatistic")
	@ResponseBody
	public Map<String, Object> findDrugFormStatistic(@PathVariable(value = "systemParameterId") Long systemParameterId) {
		SystemParameter systemParameter = systemParameterService.findOne(systemParameterId);

		Map<String, Object> map = Maps.newHashMap();
		if (systemParameter == null) return map;
		
		List<String> drugFormStatistics = Lists.newArrayList();
		
		drugFormStatistics.add("1、未提交初审数： " + systemParameter.getNodeclareNumber() + " 条");
		drugFormStatistics.add("2、已提交初审数： " + systemParameter.getInitNumber() + " 条");
		drugFormStatistics.add("3、初审已通过数： " + systemParameter.getPassedNumber() + " 条");
		drugFormStatistics.add("4、初审未通过数： " + systemParameter.getUnPassedNumber() + " 条");
		
		map.put("drugFormStatistic", drugFormStatistics);
		
		return map;
	}

	@RequestMapping(value = "{systemParameterId}/drugFormCountChart")
	@ResponseBody
	public String findDrugFormCountChart(@PathVariable(value = "systemParameterId") Long systemParameterId) {
		XMLUtil xml = new XMLUtil();
		
		Element graph = xml.addRoot("graph");
		xml.addAttribute(graph, "basefontsize", "12");
		xml.addAttribute(graph, "showNames", "1");
		xml.addAttribute(graph, "decimalPrecision", "0");
		xml.addAttribute(graph, "formatNumberScale", "0");
		
		Map<String, Long> map = drugFormService.findDrugFromCountChart(systemParameterId);
		Iterator<Entry<String, Long>> it = map.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, Long> m = (Map.Entry<String, Long>)it.next();
			String key = m.getKey();
			Long total = (Long)map.get(key);
			Element set = xml.addNode(graph, "set");
			set.addAttribute("name", key);
			set.addAttribute("value", total.toString());
		}

		return xml.getXMLSub();
	}

	@RequestMapping(value = "buildSystemParameter")
	public void buildSystemParameter(@RequestParam(value = "reportId", defaultValue = "8") Long reportId,
			@RequestParam(value = "systemParameterId", defaultValue = "-1") Long systemParameterId,
			HttpServletResponse response) {
		response.setDateHeader("Expires", 0L);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");

		try {
			ParameterBuilder parameterBuilder = new ParameterBuilder();
			parameterBuilder.getParamMap().put("systemParameterId", String.valueOf(systemParameterId));
			parameterBuilder.setTextType(TextReport.Type.PDF);

			textReportService.buildText(parameterBuilder.getParamMap(), reportId, parameterBuilder.getTextType(),
					response);
		} catch (Exception e) {
			String str = "各部门填报情况打印错误，请联系管理员！";
			response.setHeader("content-type", "text/html;charset=UTF-8");
			OutputStream os = null;
			try {
				os = response.getOutputStream();
				byte[] b = str.getBytes("utf-8");
				os.write(b);
				os.flush();
			} catch (IOException ex) {
			} finally {
				IOUtils.closeQuietly(os);
			}
		}
	}

	@RequestMapping(value = "{reviewMainId}/reviewStatistic")
	@ResponseBody
	public Map<String, Object> findReivewStatistic(@PathVariable(value = "reviewMainId")Long reviewMainId){
		ReviewMain reviewMain = reviewMainService.findOne(reviewMainId);

		Map<String, Object> map = Maps.newHashMap();
		if (reviewMain == null) return map;
		List<ReviewProcess> reviewProcesses = reviewMain.getReviewProcesses();
		if (reviewProcesses == null) return map;
		
		List<String> reviewStatistics = Lists.newArrayList();
		for (int i = 0; i < reviewProcesses.size(); i++) {
			reviewStatistics.add("第 " + (i + 1) + " 轮投票结果：" + DrugCategoryEnum.H.getInfo() + "入围 " + voteResultService.countResult(reviewMainId, reviewProcesses.get(i).getId(), DrugCategoryEnum.H) + " 条，" + DrugCategoryEnum.Z.getInfo() + "入围 " + voteResultService.countResult(reviewMainId, reviewProcesses.get(i).getId(), DrugCategoryEnum.Z) + " 条");
		}
		map.put("reviewStatistic", reviewStatistics);
		
        return map;
	}

	@RequestMapping(value = "{reviewMainId}/reviewCountChart")
	@ResponseBody
	public String findReviewCountChart(@PathVariable(value = "reviewMainId") Long reviewMainId) {
		XMLUtil xml = new XMLUtil();
		
		Element graph = xml.addRoot("graph");
		xml.addAttribute(graph, "basefontsize", "12");
		xml.addAttribute(graph, "showNames", "1");
		xml.addAttribute(graph, "decimalPrecision", "0");
		xml.addAttribute(graph, "formatNumberScale", "0");
		xml.addAttribute(graph, "xaxisname", "轮次");
		xml.addAttribute(graph, "shownames", "1");
		xml.addAttribute(graph, "rotatenames", "1");
		xml.addAttribute(graph, "labeldisplay", "rotate");
		xml.addAttribute(graph, "slantlabels", "1");

		ReviewMain reviewMain = reviewMainService.findOne(reviewMainId);

		if (reviewMain == null) return xml.getXML();
		List<ReviewProcess> reviewProcesses = reviewMain.getReviewProcesses();
		if (reviewProcesses == null) return xml.getXML();
		
		Element categories = xml.addNode(graph, "categories");
		Element hdataset = xml.addNode(graph, "dataset");
		hdataset.addAttribute("seriesname", DrugCategoryEnum.H.getInfo());
		Element zdataset = xml.addNode(graph, "dataset");
		zdataset.addAttribute("seriesname", DrugCategoryEnum.Z.getInfo());
		for (int i = 0; i < reviewProcesses.size(); i++) {
			Element category = xml.addNode(categories, "category");
			category.addAttribute("name", "第 " + (i + 1) + " 轮");
			
			Long hCount = voteResultService.countResult(reviewMainId, reviewProcesses.get(i).getId(), DrugCategoryEnum.H);
			Element hset = xml.addNode(hdataset, "set");
			hset.addAttribute("value", hCount.toString());
			
			Long zCount = voteResultService.countResult(reviewMainId, reviewProcesses.get(i).getId(), DrugCategoryEnum.Z);
			Element zset = xml.addNode(zdataset, "set");
			zset.addAttribute("value", zCount.toString());
		}
		
		return xml.getXMLSub();
	}

}
