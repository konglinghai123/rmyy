package com.ewcms.yjk.re.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.plugin.web.controller.BaseSequenceMovableController;
import com.ewcms.yjk.re.entity.ReviewProcess;

@Controller
@RequestMapping(value = "/yjk/re/reviewprocess")
public class ReviewProcessController extends BaseSequenceMovableController<ReviewProcess, Long>{

}
