package com.ewcms.hzda.zd.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.hzda.zd.entity.Nation;

@Controller
@RequestMapping(value = "/hzda/zd/nation")
public class NationController extends BaseCRUDController<Nation, Long> {

}
