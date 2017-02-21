package com.ewcms.empi.card.manage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.empi.card.manage.entity.ClientEnroll;
import com.ewcms.empi.card.manage.entity.HapiVersion;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/card/manage/clientenroll")
public class ClientEnrollController extends	BaseCRUDController<ClientEnroll, Long> {
    public ClientEnrollController() {
        setListAlsoSetCommonData(true);
//        setResourceIdentity("system:icon");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("hapiVersionList", HapiVersion.values());
    }
}
