package com.ewcms.security.user.dictionary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.dictionary.entity.Technical;

@Controller
@RequestMapping("/security/user/dictionary/technical")
public class TechnicalController extends BaseCRUDController<Technical, Long> {
}
