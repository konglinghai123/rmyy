package com.ewcms.yjk.zd.commonname.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.yjk.zd.commonname.entity.CommonNameRule;

@Controller
@RequestMapping(value = "/yjk/zd/commonnamerule")
public class CommonNameRuleController extends BaseSequenceMovableController<CommonNameRule, Long> {

}
