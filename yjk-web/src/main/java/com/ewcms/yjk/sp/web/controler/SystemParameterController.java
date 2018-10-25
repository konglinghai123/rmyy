package com.ewcms.yjk.sp.web.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.yjk.sp.entity.SystemParameter;

/**
 * 
 * @author wuzhijun
 *
 */
@Controller
@RequestMapping(value = "/yjk/sp/systemparamter")
public class SystemParameterController extends BaseCRUDController<SystemParameter, Long> {

}
