package com.ewcms.yjk.zd.commonname.web.controller;

import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;

@Controller
@RequestMapping(value = "/yjk/zd/commonnamerule")
public class CommonNameRuleController extends BaseSequenceMovableController<CommonNameRule, Long> {
    public CommonNameRuleController() {
        setResourceIdentity("yjk:commonnamerule");
    }
}
