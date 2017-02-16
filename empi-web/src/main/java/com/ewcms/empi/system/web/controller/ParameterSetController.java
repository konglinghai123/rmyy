package com.ewcms.empi.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.empi.system.entity.ParameterSet;

/**
 *@author zhoudongchu
 */
@Controller
@RequestMapping(value = "/empi/system/parameterset")
public class ParameterSetController extends	BaseCRUDController<ParameterSet, String> {

}
