package com.ewcms.yjk.zd.commonname.web.controller;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/yjk/zd/commonname")
public class CommonNameController extends BaseCRUDController<CommonName, Long> {
	private CommonNameService getCommonNameService() {
		return (CommonNameService) baseService;
	}

    public CommonNameController() {
        setResourceIdentity("yjk:commonname");
    }
    
	@RequestMapping(value = "findbyspell", method = RequestMethod.GET)
	@ResponseBody
	public List<CommonName> findBySpell(@RequestParam(value="spell") String spell) {
		if(spell==null||spell.length()==0) return null;
		spell = spell.toLowerCase();
		return getCommonNameService().findCommonNameBySpell(spell);
	}
}
