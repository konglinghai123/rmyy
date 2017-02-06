package com.ewcms.empi.card.manage.web.controller;

import java.util.Calendar;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.empi.card.manage.entity.PracticeCardJournal;
import com.ewcms.empi.card.manage.entity.PracticeCardJournalOperate;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/practicecardjournal")
public class PracticeCardJournalController extends BaseCRUDController<PracticeCardJournal, Long> {
	
    public PracticeCardJournalController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("card:practicecardjournal");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("journalOperateList", PracticeCardJournalOperate.values());
    }
    
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter,Model model) {
		if(searchParameter.isParameterEmpty()){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.MILLISECOND, 0); 
			
			searchParameter.getParameters().put("GTE_journalDate", cal.getTime());
		}
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}

	@RequestMapping(value = "{practiceCardId}/detail")
	public String index(@PathVariable(value = "practiceCardId")Long practiceCardId, Model model){
		return this.viewName("detail");
	}
	
	@RequestMapping(value = "{practiceCardId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "practiceCardId")Long practiceCardId, Model model){
		searchParameter.getParameters().put("EQ_practiceCard.id", practiceCardId);
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}
}
