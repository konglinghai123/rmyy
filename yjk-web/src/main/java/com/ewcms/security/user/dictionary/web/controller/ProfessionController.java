package com.ewcms.security.user.dictionary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.dictionary.entity.Profession;

@Controller
@RequestMapping("/security/user/dictionary/profession")
public class ProfessionController extends BaseCRUDController<Profession, Long> {
}
